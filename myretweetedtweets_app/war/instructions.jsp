<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
	String id = (String) request.getParameter("id");
	String json_url = "http://myretweetedtweets.appspot.com/json/" + id;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>My Retweeted Tweets</title>
</head>
<body>
	<h1>Cool, you've done it!</h1>
	<div id="mtrWidget"
		style="font-family: verdana, arial, sans; float: left; width: 130px; font-size: 10px; border: 2px solid; padding: 0 4px 0 4px; margin-right: 8px;"></div>
	<p>You can check the result on the left:</p>
	<h2>Adding the Widget:</h2>
	<p>To add a widget with your retweets, paste the following line
		where you want it to appear:</p>
	<pre>&lt;div id="mtrWidget"&gt;&lt;/div&gt;</pre>
	and put this code just below the
	<code>&lt;/html&gt;</code>
	tag:
	<pre>&lt;script type="text/javascript"&gt;
	var mtrWidgetId=<%=id%>;
	var mtrMaxTweets=5;
&lt;/script&gt;
&lt;script type="text/javascript" src="http://myretweetedtweets.appspot.com/widget-min.js"&gt;&lt;/script&gt;</pre>
	<p>
		You can adjust the
		<code>mrtMaxTweets</code>
		to a number between 1 and 20. You will need to add some CSS to the div
		(as seen on the left here), and you can also style the tweets
		themselves (each line is a
		<code>&lt;p&gt;</code>
		with the
		<code>mtrWidgetLine</code>
		style).
	</p>
	<h2>Getting the raw data (JSON):</h2>
	<p>You can use the following URL to retrieve your retweeted tweets
		as a JSON object:</p>
	<p>
		<a href="<%=json_url%>"><%=json_url%></a>
	</p>
	<p>
		It supports <a href="http://en.wikipedia.org/wiki/JSONP">jsonp</a> -
		if you add a "callback" parameter, it will output its value as a
		padding fucntion.
	</p>
	<script type="text/javascript">
		var mtrWidgetId=<%=id%>;
		var mtrMaxTweets=5;
	</script>
	<script type="text/javascript"
		src="http://myretweetedtweets.appspot.com/widget-min.js"></script>
</body>
</html>