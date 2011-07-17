package me.chester.myretweetedtweets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import twitter4j.TwitterException;

@SuppressWarnings("serial")
public class BeginTwitterAuthorizationServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			User user = new User();
			user.setupForAuthorization();
			HttpSession session = request.getSession();
			session.setAttribute("token", user.getToken());
			session.setAttribute("tokenSecret", user.getTokenSecret());
			response.sendRedirect(user.getAuthorizationURL());
		} catch (TwitterException te) {
			throw new IOException(te);
		}
	}
}
