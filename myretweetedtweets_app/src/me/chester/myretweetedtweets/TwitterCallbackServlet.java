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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import twitter4j.TwitterException;

@SuppressWarnings("serial")
public class TwitterCallbackServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try {
			HttpSession session = request.getSession();
			String token = (String) session.getAttribute("token");
			String tokenSecret = (String) session.getAttribute("tokenSecret");
			if (token == null || tokenSecret == null
					|| !token.equals(request.getParameter("oauth_token"))) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				return;
			}
			User user = new User();
			user.finishAuthorization(token, tokenSecret);
			user.persist();
			response.sendRedirect("/instructions.jsp?id=" + user.getId());
		} catch (TwitterException te) {
			throw new ServletException(te);
		}
	}
}
