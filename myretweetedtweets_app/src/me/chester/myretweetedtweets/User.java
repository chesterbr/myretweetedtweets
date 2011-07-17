package me.chester.myretweetedtweets;

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

import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;

public class User {

	private String token;
	private String tokenSecret;
	private String authorizationURL;
	private Twitter twitter;
	private Entity entity;

	private static final int MAX_RETWEETED_TWEETS = 20;
	private DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();

	public User() {
		twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(Config.CONSUMER_KEY, Config.CONSUMER_SECRET);
	}

	public User(long id) throws EntityNotFoundException {
		this();
		entity = datastore.get(KeyFactory.createKey("User", id));
		token = (String) entity.getProperty("token");
		tokenSecret = (String) entity.getProperty("tokenSecret");
		// AccessToken accessToken = twitter.getOAuthAccessToken(token,
		// tokenSecret);
		// twitter.setOAuthAccessToken(accessToken);
	}

	public void setupForAuthorization() throws TwitterException {
		RequestToken requestToken = twitter.getOAuthRequestToken();
		token = requestToken.getToken();
		tokenSecret = requestToken.getTokenSecret();
		authorizationURL = requestToken.getAuthorizationURL();
	}

	public void finishAuthorization(String token, String tokenSecret)
			throws TwitterException {
		RequestToken requestToken = new RequestToken(token, tokenSecret);
		AccessToken accessToken = twitter.getOAuthAccessToken(requestToken);
		twitter.setOAuthAccessToken(accessToken);
		this.token = accessToken.getToken();
		this.tokenSecret = accessToken.getTokenSecret();
	}

	public void persist() {
		if (entity == null) {
			entity = new Entity("User");
		}
		entity.setProperty("token", token);
		entity.setProperty("tokenSecret", tokenSecret);
		datastore.put(entity);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof User)) {
			return false;
		}
		User user = (User) obj;
		if (getId() != user.getId()) {
			return false;
		}
		if (getToken() == null) {
			return user.getToken() == null;
		}
		if (getTokenSecret() == null) {
			return user.getTokenSecret() == null;
		}
		return getToken().equals(user.getToken())
				&& getTokenSecret().equals(user.getTokenSecret());
	}

	public List<String> getMyRetweetedTweets() throws TwitterException {
		if (token == null || tokenSecret == null) {
			throw new IllegalStateException(
					"Token and secret have not been set up and confirmed yet");
		}
		AccessToken accessToken = new AccessToken(token, tokenSecret);
		twitter.setOAuthAccessToken(accessToken);
		List<String> tweets = new ArrayList<String>(MAX_RETWEETED_TWEETS);
		for (Status status : twitter.getRetweetsOfMe()) {
			tweets.add(status.getText());
		}
		return tweets;
	}

	public String getToken() {
		return token;
	}

	public String getTokenSecret() {
		return tokenSecret;
	}

	public String getAuthorizationURL() {
		return authorizationURL;
	}

	public long getId() {
		return (entity != null ? entity.getKey().getId() : 0);
	}

}
