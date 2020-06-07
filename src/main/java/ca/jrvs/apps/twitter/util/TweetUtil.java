package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;

public class TweetUtil {

  public static Tweet createTweet(String text, float lon, float lat) {
    Coordinates coordinates = new Coordinates(lat, lon);
    Tweet tweet = new Tweet();
    tweet.setText(text);
    tweet.setCoordinates(coordinates);
    return tweet;
  }
}