<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
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
	String id = (String) request.getParameter("id");
	String json_url = "http://myretweetedtweets.appspot.com/json/" + id;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>My Retweeted Tweets</title>
<link rel="stylesheet" type="text/css" media="all" href="style.css" />
</head>
<body>
	<h1>Cool, you've done it!</h1>

	<div id="mtrWidget"
		style="font-family: verdana, arial, sans; float: left; width: 130px; font-size: 10px; border: 2px solid; padding: 0 4px 0 4px; margin-right: 8px;"></div>
	<div style="margin-left: 150px">
		Check the result (with a bit of CSS styles applied) on the left.
		<h2>Adding the widget to your site:</h2>
		<p>To add a widget with your retweets, paste the following line
			where you want it to appear:</p>
		<pre>&lt;div id="mtrWidget"&gt;&lt;/div&gt;</pre>
		<p>and put this code just below the
		<code>&lt;/html&gt;</code>
		tag:</p>
		<pre>&lt;script type="text/javascript"&gt;
	var mtrWidgetId=<%=id%>;
	var mtrMaxTweets=5;
&lt;/script&gt;
&lt;script type="text/javascript" src="http://myretweetedtweets.appspot.com/widget-min.js"&gt;&lt;/script&gt;</pre>
		<p>
			You can adjust the
			<code>mrtMaxTweets</code>
			to a number between 1 and 20. You will need to add some CSS to the
			div (as seen on the left here), and you can also style the tweets
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
		<h2>Observations</h2>
		<ul>
			<li>You should bookmark this page to retrieve these instructions
				(otherwise, you'll need to re-authorize)</li>
			<li>There is a server-side memory cache, so your tweets won't
				appear right after they are retweeted. It is supposed to expire
				after two hours</li>
		</ul>
	</div>

	<script type="text/javascript">
		var mtrWidgetId=<%=id%>;
		var mtrMaxTweets=5;
	</script>
	<script type="text/javascript"
		src="http://myretweetedtweets.appspot.com/widget-min.js"></script>
</body>
</html>