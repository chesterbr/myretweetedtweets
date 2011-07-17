package me.chester.myretweetedtweets;

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
			request.setAttribute("id", Long.toString(user.getId()));
			request.getRequestDispatcher("/instructions.jsp").forward(request,
					response);
		} catch (TwitterException te) {
			throw new ServletException(te);
		}
	}
}
