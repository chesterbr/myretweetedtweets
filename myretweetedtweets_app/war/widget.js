// Copyright © 2011 Carlos Duarte do Nascimento (Chester) <cd@pobox.com>
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// Distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

// IF YOU EDIT THIS FILE, RUN jsmin < widget.js > widget-min.js !!!!!
// (get jsmin via apt/ports/brew or google :-P )

function mtrWidgetLinkRegexp(tweet, regexp, prefix) {
	var list = tweet.match(regexp);
	if (list) {
		for (i = 0; i < list.length; i++) {
			tweet = tweet.replace(list[i], "<a target='_blank' href='"
					+ prefix + list[i] + "'>" + list[i] + "</a>");
		}
	}
	return tweet
}

function mtrWidgetAddLinksToTweet(tweet) {
	tweet = mtrWidgetLinkRegexp(tweet, /\b(http:\/\/|www\.|http:\/\/www\.)[^ ]{2,100}\b/g, "");
//	tweet = mtrWidgetLinkRegexp(tweet.match(/\b\@(.)[^ ]{2,100}\b/g);
//	tweet
	return tweet;
}

function mtrWidgetCallback(tweetsJson) {

	var tweets = tweetsJson.tweets;
	var mtrHTML = "";
	var numTweets = 0;
	for (id in tweets) {
		var tweet = tweets[id];
		tweet = mtrWidgetAddLinksToTweet(tweet);
		mtrHTML += '<p class="mtrWidgetLine">';
		mtrHTML += tweet;
		mtrHTML += '</p>';
		numTweets++;
		if (window.mtrMaxTweets && numTweets == mtrMaxTweets) {
			break;
		}
	}

	var mtrWidget = document.getElementById("mtrWidget");
	if (mtrWidget) {
		mtrWidget.innerHTML = mtrHTML;
	}

}

if (window.mtrWidgetId && document.getElementById("mtrWidget")) {
	var url = 'http://myretweetedtweets.appspot.com/json/' + mtrWidgetId
			+ '?callback=mtrWidgetCallback';
	var script = document.createElement('script');
	script.setAttribute('src', url);
	document.getElementsByTagName('head')[0].appendChild(script);
}