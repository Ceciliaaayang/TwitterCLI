package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {
  @Mock
  CrdDao dao;

  @InjectMocks
  TwitterService service;

  @Test
  public void postTweet(){
    Mockito.when(dao.create(any())).thenReturn(new Tweet());
    Tweet postTweet = service.postTweet(TweetUtil.createTweet("unitTesting",1f,-1f));
    assertNotNull(postTweet);
    assertNotNull(postTweet.getText());
    assertNotNull(postTweet.getCoordinates());
    assertNotNull(postTweet.getId_str());
  }

  @Test
  public void showTweet(){
    String[] fields = {"created_at", "id", "id_str", "text", "entities",
        "retweet_count", "favorite_count", "favorited", "retwee"};
    Mockito.when(dao.findById(any())).thenReturn(new Tweet());
    Tweet showTweet = service.showTweet("1097607853932564480",fields);
    assertNotNull(showTweet);
    assertNotNull(showTweet.getText());
    assertNotNull(showTweet.getCoordinates());
    assertNotNull(showTweet.getId_str());
  }

  @Test
  public void deleteTweet() {
    Tweet deleteTestTweet = new Tweet();
    String[] delete_ids = {"1097607853932564230", "1097607853932562380"};
    Mockito.when(dao.deleteById(any())).thenReturn(deleteTestTweet);
    List<Tweet> deletedTweet = service.deleteTweets(delete_ids);
    for (Tweet tweet : deletedTweet) {
      assertNotNull(tweet);
      assertNotNull(tweet.getText());
      assertNotNull(tweet.getCoordinates());
      assertNotNull(tweet.getId_str());
    }
  }
}



