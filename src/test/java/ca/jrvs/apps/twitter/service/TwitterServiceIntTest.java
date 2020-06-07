package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import ca.jrvs.apps.twitter.dao.HttpHelper;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TwitterServiceIntTest {
  private TwitterDao dao;
  private TwitterService twitterService;
  private Tweet tweet;
  private Tweet tweet2;

  @Before
  public void setup(){
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
    System.out.println(consumerKey + "|" + consumerSecret + "|" + accessToken + "|" + tokenSecret);
    //setup dependency
    HttpHelper httpHelper = new TwitterHttpHelper(consumerKey,consumerSecret,accessToken,tokenSecret);
    //pass dependency
    TwitterDao dao = new TwitterDao(httpHelper);
    this.twitterService = new TwitterService(dao);
    //create tweet #1
    String text = "Test twitter service#1" + System.currentTimeMillis();
    float lat = 2f;
    float lon =-2f;
    Coordinates coordinates = new Coordinates(lat, lon);
    Tweet postTweet = new Tweet();
    postTweet.setText(text);
    postTweet.setCoordinates(coordinates);
    //create tweet #2
    String text2 = "Test twitter service#2" + System.currentTimeMillis();
    float lat2 = 2f;
    float lon2 =-2f;
    Coordinates coordinates2 = new Coordinates(lat2, lon2);
    Tweet postTweet2 = new Tweet();
    postTweet2.setText(text2);
    postTweet2.setCoordinates(coordinates2);
    // post tweet
    tweet = twitterService.postTweet(postTweet);
    tweet2 = twitterService.postTweet(postTweet2);
    assertEquals(text, tweet.getText());
    assertNotNull(tweet.getCoordinates());
    assertEquals(2, tweet.getCoordinates().getCoordinates().length);
    assertEquals(lon, tweet.getCoordinates().getCoordinates()[0]);
    assertEquals(lat, tweet.getCoordinates().getCoordinates()[1]);
  }

  @Test
  public void showTweet() throws Exception{
    String[] fields = {"created_at", "id", "id_str", "text", "entities",
        "retweet_count", "favorite_count", "favorited", "retwee"};
    Tweet showTweet = twitterService.showTweet(tweet.getId_str(), fields);
    assertEquals(tweet.getText(), showTweet.getText());
  }

  @After
  public void deleteTweet() throws Exception{
    String[] delete_ids = {tweet.getId_str(), tweet2.getId_str()};
    List<Tweet> deleteTweets = twitterService.deleteTweets(delete_ids);
    assertNotNull(deleteTweets.get(0));
    assertNotNull(deleteTweets.get(0).getText());
    assertNotNull(deleteTweets.get(0).getId());
  }
}
