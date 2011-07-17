
function mtrWidgetCallback(tweetsJson){var tweets=tweetsJson.tweets;var mtrHTML="";var numTweets=0;for(id in tweets){var tweet=tweets[id];var list=tweet.match(/\b(http:\/\/|www\.|http:\/\/www\.)[^ ]{2,100}\b/g);if(list){for(i=0;i<list.length;i++){tweet=tweet.replace(list[i],"<a target='_blank' href='"
+list[i]+"'>"+list[i]+"</a>");}}
mtrHTML+='<p class="mtrWidgetLine">';mtrHTML+=tweet;mtrHTML+='</p>';numTweets++;if(window.mtrMaxTweets&&numTweets==mtrMaxTweets){break;}}
var mtrWidget=document.getElementById("mtrWidget");if(mtrWidget){mtrWidget.innerHTML=mtrHTML;}else{document.write(mtrHTML);}}
if(window.mtrWidgetId){var url='http://myretweetedtweets.appspot.com/json/'+mtrWidgetId
+'?callback=mtrWidgetCallback';var script=document.createElement('script');script.setAttribute('src',url);document.getElementsByTagName('head')[0].appendChild(script);}