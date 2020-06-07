package ca.jrvs.apps.twitter.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import ca.jrvs.apps.twitter.dao.HttpHelper;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.TweetUtil;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TwitterControllerIntTest {
  private Controller controller;
  private TwitterService twitterService;
  private TwitterDao dao;
  private Tweet tweet;
  private Tweet tweet2;

  @Before
  public void setup(){
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
    System.out.println(consumerKey + "|" + consumerSecret + "|" + accessToken + "|" + tokenSecret);
    HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);

    String text = "Test controller" + System.currentTimeMillis();
    float lat = 1f;
    float lon = -1f;
    Tweet postTweet = TweetUtil.createTweet(text,lon,lat);
    String[] input = {"post", postTweet.getText(),"-1d:1d"};
    tweet = controller.postTweet(input);

    String text2 = "Test controller" + System.currentTimeMillis();
    float lat2 = 1f;
    float lon2 = -1f;
    Tweet postTweet2 = TweetUtil.createTweet(text,lon,lat);
    String[] input2 = {"post", postTweet.getText(),"-1d:1d"};
    tweet = controller.postTweet(input2);

    assertEquals(text, tweet.getText());
    assertNotNull(tweet.getCoordinates());
    assertEquals(2, tweet.getCoordinates().getCoordinates().length);
    assertEquals(lon, tweet.getCoordinates().getCoordinates()[0]);
    assertEquals(lat, tweet.getCoordinates().getCoordinates()[1]);
  }

  @Test
  public void showTweet(){
    String[] input = {"show", tweet.getId_str(),"created_at"};
    Tweet showTweet = controller.showTweet(input);
    assertEquals(tweet.getText(), showTweet.getText());
    assertEquals(tweet.getId_str(), showTweet.getId_str());
  }

  @Test
  public void deleteTweet(){
    String deleted_ids = tweet.getId_str() + "," + tweet2.getId_str();
    String[] user_input = {"delete", deleted_ids};
    List<Tweet> deletedTweets = controller.deleteTweet(user_input);
    assertEquals(deletedTweets.get(0).getText(), tweet.getText());
    assertEquals(deletedTweets.get(1).getText(), tweet2.getText());
  }
}
