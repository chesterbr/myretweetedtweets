<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
	String id = (String) request.getAttribute("id");
	String ul_url = "http://myretweetedtweets.appspot.com/ul/" + id;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>My Retweeted Tweets</title>
</head>
<body>
	<h1>Cool, you've done it!</h1>
	<p>You can use the following URL to retrieve your retweeted tweets
		as an HTML list (&lt;ul&rt;):</p>
	<p>
		<a href="<%=ul_url%>"><%=ul_url%></a>
	</p>
	<p>(remember this URL - if you forget it, you will need to redo the
		authorization).</p>
</body>
</html>