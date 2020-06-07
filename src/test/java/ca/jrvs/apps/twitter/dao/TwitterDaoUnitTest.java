package ca.jrvs.apps.twitter.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDaoUnitTest {
  @Mock
  HttpHelper mockHelper;

  @InjectMocks
  TwitterDao dao;

  public static final String tweetJsonStr = "{"
      + "  \"created_at\": \"Mon Feb 18 21:24:39 +0000 2019\", \n"
      + "  \"id\": 1097607853932564480, \n"
      + "  \"id_str\":\"1097607853932564480\",\n"
      + "  \"text\":\"text with loc223\",\n"
      + "  \"entities\":{\n"
      + "     \"hashtags\":[],\n"
      + "     \"userMention\":[]"
      + " },\n"
      + "  \"coordinates\":null,\n"
      + "  \"retweet_count\":0,\n"
      + "  \"favorite_count\":0,\n"
      + "  \"favorited\":false,\n"
      + "  \"retweeted\":false}\n";
  public Tweet tweet;

  @Before
  public void init() {
    String hashtag = "#abc";
    String text = "@somone sometext " + hashtag + " " + System.currentTimeMillis();
    float lat = 1f;
    float lon = -1f;
    Coordinates coordinates = new Coordinates(lat, lon);
    when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
    tweet = new Tweet();
    tweet.setText(text);
    tweet.setCoordinates(coordinates);
    try {
      dao.create(tweet);
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }
  }

  @Test
  public void showTweet() throws Exception{
    when(mockHelper.httpPost(isNotNull())).thenReturn(null);
    TwitterDao spyDao = Mockito.spy(dao);
    Tweet expectedTweet = TwitterDao.toObjectFromJson(tweetJsonStr,Tweet.class);

    doReturn(expectedTweet).when(spyDao).parseResponseBody(any(),anyInt());
    Tweet testTweet = spyDao.findById("1097607853932564480");
    assertNotNull(testTweet);
    assertNotNull(testTweet.getText());
  }

  @Test
  public void postTweet() throws Exception{
    when(mockHelper.httpPost(isNotNull())).thenReturn(null);
    TwitterDao spyDao = Mockito.spy(dao);
    Tweet expectedTweet = TwitterDao.toObjectFromJson(tweetJsonStr,Tweet.class);

    doReturn(expectedTweet).when(spyDao).parseResponseBody(any(),anyInt());
    Tweet testTweet = spyDao.create(tweet);
    assertNotNull(testTweet);
    assertNotNull(testTweet.getText());
  }

  @Test
  public void deleteTweet() throws Exception{
    when(mockHelper.httpPost(isNotNull())).thenReturn(null);
    TwitterDao spyDao = Mockito.spy(dao);
    Tweet expectedTweet = TwitterDao.toObjectFromJson(tweetJsonStr,Tweet.class);

    doReturn(expectedTweet).when(spyDao).parseResponseBody(any(),anyInt());
    Tweet testTweet = spyDao.deleteById("1097607853932564480");
    assertNotNull(testTweet);
    assertNotNull(testTweet.getText());
  }
}