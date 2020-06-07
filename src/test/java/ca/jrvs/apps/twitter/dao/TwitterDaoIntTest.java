package ca.jrvs.apps.twitter.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TwitterDaoIntTest {
  private TwitterDao dao;
  private Tweet tweet;

  @Before
  public void setup(){
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
    HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken,
        tokenSecret);
    this.dao = new TwitterDao(httpHelper);
  }

  @Test
  public void create() throws Exception{
    // create tweet
    String hashtag = "#test";
    String text = "@somone sometext " + hashtag + " " + System.currentTimeMillis();
    float lat = 2f;
    float lon =-2f;
    Coordinates coordinates = new Coordinates(lat, lon);
    Tweet postTweet = new Tweet();
    postTweet.setText(text);
    postTweet.setCoordinates(coordinates);
    tweet = dao.create(postTweet);

    // post tweet
    assertEquals(text, tweet.getText());
    assertNotNull(tweet.getCoordinates());
    assertEquals(2, tweet.getCoordinates().getCoordinates().length);
    assertEquals(lon, tweet.getCoordinates().getCoordinates()[0]);
    assertEquals(lat, tweet.getCoordinates().getCoordinates()[1]);
  }
  @Test
  public void findById() {
    Tweet findTweet = dao.findById(tweet.getId_str());
    assertEquals(tweet.getText(), findTweet.getText());
    assertEquals(tweet.getId(), findTweet.getId());
  }

  @After
  public void deleteById() {
    Tweet deleteTweet = dao.deleteById(tweet.getId_str());
    assertEquals(tweet.getText(), deleteTweet.getText());
    assertEquals(tweet.getId(), deleteTweet.getId());
  }
}