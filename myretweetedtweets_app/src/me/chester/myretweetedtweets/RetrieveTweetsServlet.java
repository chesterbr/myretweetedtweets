package me.chester.myretweetedtweets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.TwitterException;

import com.google.appengine.api.datastore.EntityNotFoundException;

@SuppressWarnings("serial")
public class RetrieveTweetsServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		PrintWriter out = response.getWriter();
		if (path != null && path.length() > 1) {
			try {
				long id = Long.parseLong(path.substring(1));
				User user = new User(id);
				List<String> statuses = user.getMyRetweetedTweets();
				out.write("<ul>");
				for (String status : statuses) {
					out.write("<li>");
					out.write(status);
					out.write("</li>");
				}
				out.write("</ul>");
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
