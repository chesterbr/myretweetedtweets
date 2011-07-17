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

function mtrWidgetCallback(tweetsJson) {

	var tweets = tweetsJson.tweets;
	var mtrHTML = "";
	var numTweets = 0;
	for (id in tweets) {
		var tweet = tweets[id];
		var list = tweet
				.match(/\b(http:\/\/|www\.|http:\/\/www\.)[^ ]{2,100}\b/g);
		if (list) {
			for (i = 0; i < list.length; i++) {
				tweet = tweet.replace(list[i], "<a target='_blank' href='"
						+ list[i] + "'>" + list[i] + "</a>");
			}
		}
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
	} else {
		document.write(mtrHTML);
	}

}

if (window.mtrWidgetId) {
	var url = 'http://myretweetedtweets.appspot.com/json/' + mtrWidgetId
			+ '?callback=mtrWidgetCallback';
	var script = document.createElement('script');
	script.setAttribute('src', url);
	document.getElementsByTagName('head')[0].appendChild(script);
}