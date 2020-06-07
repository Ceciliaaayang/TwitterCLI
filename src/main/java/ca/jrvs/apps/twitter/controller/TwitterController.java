package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.util.TweetUtil;
import java.util.List;

public class TwitterController implements Controller {

  private static final String COORD_SEP = ":";
  private static final String COMMA = ",";

  private Service service;

  public TwitterController(Service service) {
    this.service = service;
  }

  @Override
  public Tweet postTweet(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException(
          "Need three arguments\n USAGE: TwitterCLIApp post \"text\" \"latitude:longitude\"");
    }
      String text = args[1];
      String coordinate = args[2];
      //args[2] is latitude:longitude
      String[] coorStr = coordinate.split(COORD_SEP);
      if (coorStr.length != 2 || text.isEmpty() == true) {
        throw new IllegalArgumentException("Location format :\"latitude:longitude\"");
      }
      float lat;
      float lon;
      try {
        lat = Float.valueOf(coorStr[0]);
        lon = Float.valueOf(coorStr[1]);
      } catch (Exception e) {
        throw new IllegalArgumentException(
            "Can not convert location value to float format", e);
      }
      Tweet postTweet = TweetUtil.createTweet(text, lon, lat);
      return service.postTweet(postTweet);
  }

  @Override
  public Tweet showTweet(String[] args) {
    if (args.length < 3) {
      throw new IllegalArgumentException(
          "Need at least 2 arguments \n USAGE: TwitterCLIApp show \"id\" \"fields\"");
    }
    String id = args[1];
    String fields = args[2];
    String[] fieldArr = fields.split(COMMA);
    return service.showTweet(id, fieldArr);
  }

  @Override
  public List<Tweet> deleteTweet(String[] args) {
    if (args.length <= 1) {
      throw new IllegalArgumentException(
          "Need at least 1 arguments \n USAGE:TwitterCLIApp delete \"id_1\" \"id_2\"...");
    }
    String ids = args[1];
    String[] idArray = ids.split(COMMA);
    return service.deleteTweets(idArray);
  }
}