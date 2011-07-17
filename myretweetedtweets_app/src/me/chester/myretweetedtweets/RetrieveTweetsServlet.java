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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.TwitterException;

import com.google.appengine.api.datastore.EntityNotFoundException;
import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;

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
				User user = new User(id);
				List<String> statuses = user.getMyRetweetedTweets();
				response.setContentType("application/json");
				if (callback != null) {
					out.write(callback + "(");
				}
				out.write("{tweets:[");
				boolean first = true;
				for (String status : statuses) {
					out.write(first ? "" : ",");
					out.write("\"");
					out.write(escapeHtml(status));
					out.write("\"");
					first = false;
				}
				out.write("]}");
				if (callback != null) {
					out.write(")");
				}
				return;
			} catch (NumberFormatException e) {
				// Invalid id, will be treated as 404-not-found below
			} catch (EntityNotFoundException e) {
				// Invalid id, will be treated as 404-not-found below
			} catch (TwitterException e) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				out.write("Error retrieving tweets. Maybe you should re-authorize?");
			}
		}
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		out.write("Invalid URL. Try authorizing again to get a valid one");
	}
}
