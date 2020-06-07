package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.ArrayList;
import java.util.List;

public class TwitterService implements Service {
  private CrdDao dao;
  public TwitterService(CrdDao dao){
    this.dao = dao;
  }

  /**
   * Validate and post a user input Tweet
   *
   * @param tweet tweet to be created
   * @return created tweet
   *
   * @throws IllegalArgumentException if text exceed max number of allowed characters or lat/long out of range
   */
  @Override
  public Tweet postTweet(Tweet tweet) {
    double lon = tweet.getCoordinates().getCoordinates()[0];
    double lat = tweet.getCoordinates().getCoordinates()[1];
    // check text characters
    if(tweet.getText().length() > 140){
      throw new IllegalArgumentException("Text over 140 characters");
    }
    // check lon and lat range
    if(lon > 180 || lon < -180 || lat < -90 || lat > 90){
      throw new IllegalArgumentException("Invalid coordinates");
    }
    //create tweet via dao
    return (Tweet) dao.create(tweet);
  }

  /**
   * Search a tweet by ID
   *
   * @param id tweet id
   * @param fields set fields not in the list to null
   * @return Tweet object which is returned by the Twitter API
   *
   * @throws IllegalArgumentException if id or fields param is invalid
   */
  @Override
  public Tweet showTweet(String id, String[] fields){
    //check id
    if(!id.matches("[0-9]+")) {
      throw new IllegalArgumentException("Invalid ID");
    }
    return (Tweet) dao.findById(id);
    // validate input fields
    //if (fields == null){
      //return tweet;
    //}
  }
  /**
   * Delete Tweet(s) by id(s).
   *
   * @param ids tweet IDs which will be deleted
   * @return A list of Tweets
   *
   * @throws IllegalArgumentException if one of the IDs is invalid.
   */
  @Override
  public List<Tweet> deleteTweets(String[] ids) {
    List<Tweet> deletedTweet = new ArrayList<Tweet>();
    for(String id : ids){
      if(!id.matches("[0-9]+")){
        throw new IllegalArgumentException("Invalid ID");
      }else{
        deletedTweet.add((Tweet) dao.deleteById(id));
      }
    }
    return deletedTweet;
  }
}
