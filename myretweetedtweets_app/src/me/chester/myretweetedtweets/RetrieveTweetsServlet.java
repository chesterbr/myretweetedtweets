package me.chester.myretweetedtweets;

/*
 * Copyright © 2011 Carlos Duarte do Nascimento (Chester) <cd@pobox.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * Distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import twitter4j.TwitterException;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

@SuppressWarnings("serial")
public class RetrieveTweetsServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		String callback = request.getParameter("callback");
		PrintWriter out = response.getWriter();
		if (path != null && path.length() > 1) {
			try {
				long id = Long.parseLong(path.substring(1));
				String json = getJsonWithCache(id, callback);
				response.setContentType("application/json");
				out.write(json);
				return;
			} catch (NumberFormatException e) {
				// Invalid id, will be treated as 404-not-found below
			} catch (EntityNotFoundException e) {
				// Invalid id, will be treated as 404-not-found below
			} catch (TwitterException e) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				out.write("Error retrieving tweets. Maybe you should re-authorize?");
			} catch (CacheException e) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				out.write("Error with Memcache. We are all doomed.");
			}
		}
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		out.write("Invalid URL. Try authorizing again to get a valid one");
	}

	private static final int TWO_HOURS_IN_SECONDS = 7200;

	private String getJsonWithCache(long id, String callback)
			throws EntityNotFoundException, TwitterException, CacheException {
		MemcacheService cache = MemcacheServiceFactory.getMemcacheService();
		String json;
		if (cache.contains(id)) {
			json = (String) cache.get(id);
		} else {
			User user = new User(id);
			List<String> statuses = user.getMyRetweetedTweets();
			StringBuilder sb = new StringBuilder();

			sb.append("{tweets:[");
			boolean first = true;
			for (String status : statuses) {
				sb.append(first ? "" : ",");
				sb.append("\"");
				sb.append(escapeHtml(status));
				sb.append("\"");
				first = false;
			}
			sb.append("]}");
			json = sb.toString();
			cache.put(id, json, Expiration.byDeltaSeconds(TWO_HOURS_IN_SECONDS));
		}
		if (callback != null) {
			return callback + "(" + json + ")";
		} else {
			return json;
		}
	}
}
