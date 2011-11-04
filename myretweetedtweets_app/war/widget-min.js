
function mtrWidgetLinkRegexp(tweet,regexp,prefix){var list=tweet.match(regexp);if(list){for(i=0;i<list.length;i++){tweet=tweet.replace(list[i],"<a target='_blank' href='"
+prefix+list[i]+"'>"+list[i]+"</a>");}}
return tweet}
function mtrWidgetAddLinksToTweet(tweet){tweet=mtrWidgetLinkRegexp(tweet,/\b(http:\/\/|www\.|http:\/\/www\.)[^ ]{2,100}\b/g,"");return tweet;}
function mtrWidgetCallback(tweetsJson){var tweets=tweetsJson.tweets;var mtrHTML="";var numTweets=0;for(id in tweets){var tweet=tweets[id];tweet=mtrWidgetAddLinksToTweet(tweet);mtrHTML+='<p class="mtrWidgetLine">';mtrHTML+=tweet;mtrHTML+='</p>';numTweets++;if(window.mtrMaxTweets&&numTweets==mtrMaxTweets){break;}}
var mtrWidget=document.getElementById("mtrWidget");if(mtrWidget){mtrWidget.innerHTML=mtrHTML;}}
if(window.mtrWidgetId&&document.getElementById("mtrWidget")){var url='http://myretweetedtweets.appspot.com/json/'+mtrWidgetId
+'?callback=mtrWidgetCallback';var script=document.createElement('script');script.setAttribute('src',url);document.getElementsByTagName('head')[0].appendChild(script);}