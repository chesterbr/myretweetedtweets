package me.chester.myretweetedtweets.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.logging.Logger;

import me.chester.myretweetedtweets.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twitter4j.TwitterException;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class TwitterIntegrationTestCase {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	@Before
	public void setUp() {
		helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void testRequestAuthorization() throws TwitterException {
		User user = new User();
		assertNull(user.getToken());
		assertNull(user.getTokenSecret());
		assertNull(user.getAuthorizationURL());
		user.setupForAuthorization();
		assertNotNull(user.getToken());
		assertNotNull(user.getTokenSecret());
		assertTrue(user.getAuthorizationURL().startsWith("http"));
		assertTrue(user.getAuthorizationURL().contains("twitter.com"));
		Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		log.info("testFinishAuthorization URL =  " + user.getAuthorizationURL());
		log.info("TOKEN = " + user.getToken());
		log.info("SECRET = " + user.getTokenSecret());
	}

	@Test(expected = IllegalStateException.class)
	public void testGetTweetsWithoutAuthorization() throws Exception {
		User user = new User();
		assertNull(user.getMyRetweetedTweets());
	}

	@Test
	public void testPersist() throws Exception {
		User newUser = new User();
		User user = new User();
		assertEquals(newUser, user);
		user.persist();
		assertFalse(newUser.equals(user));
		assertNotSame(0, user.getId());
		User retrievedUser = new User(user.getId());
		assertEquals(user, retrievedUser);
		assertFalse(newUser.equals(retrievedUser));
	}

	// This is a one-time test. To run, see below
	// @Test
	public void verifyAuthorizationAndTest() throws TwitterException {
		// To run this test, get a URL and SECRET from the log.
		// Put the SECRET below and call the URL in your browser
		final String TOKEN = "GobNB6bO3G3JZ3JJMU5oV1N23XqdKdMNCfXAjIMUBM";
		final String SECRET = "ANkRZ4ma4tqNrteNymGWhOwHHlkuWVnuGxheXfqE4";
		// After the authorization, browser will redirect to an URL with
		// the two parameters below. Paste them here, uncomment @Test and go!
		User user = new User();
		user.finishAuthorization(TOKEN, SECRET);
		assertNotNull(user.getMyRetweetedTweets());
	}

}
