package ca.jrvs.apps.twitter.controller;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

public class TwitterControllerUnitTest {

  @Mock
  TwitterService twitterService;

  @InjectMocks
  TwitterController twitterController;

  @Test
  public void postTweet() {
    String[] input = {"post", "testingController", "1d:-1d"};
    Mockito.when(twitterService.postTweet(any())).thenReturn(new Tweet());
    Tweet postTestTweet = twitterController.postTweet(input);
    assertNotNull(postTestTweet);
    assertNotNull(postTestTweet.getText());
    assertNotNull(postTestTweet.getCoordinates());
    assertNotNull(postTestTweet.getId_str());
  }

  @Test
  public void showTweet() {
    String[] input = {"show", "1097607853932564480", "text,created_at"};
    Mockito.when(twitterService.showTweet(any(), any())).thenReturn(new Tweet());
    Tweet showTestTweet = twitterController.showTweet(input);
    assertNotNull(showTestTweet);
    assertNotNull(showTestTweet.getText());
    assertNotNull(showTestTweet.getCoordinates());
    assertNotNull(showTestTweet.getId_str());
  }

  @Test
  public void deleteTweet() {
    String[] input = {"delete", "1097607853932564480,1097607853932564220"};
    Mockito.when(twitterService.deleteTweets(any())).thenReturn(new ArrayList<>());
    List<Tweet> deletedTweet = twitterController.deleteTweet(input);
    for (Tweet tweet : deletedTweet) {
      assertNotNull(tweet);
      assertNotNull(tweet.getText());
      assertNotNull(tweet.getCoordinates());
      assertNotNull(tweet.getId_str());
    }
  }
}


