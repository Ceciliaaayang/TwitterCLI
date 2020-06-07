package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class Entities {
  @JsonProperty ("hashtag")
  private Hashtag[] hashtag;

  @JsonProperty ("user_mentions")
  private UserMention[] user_mentions;

  public Hashtag[] getHashtag () {
    return hashtag;
  }
  public void setHashtag (Hashtag[] hashtag){
    this.hashtag = hashtag;
  }
  public UserMention[] getUserMentions(){
    return user_mentions;
  }
  public void setUserMention(UserMention[] mention){
    this.user_mentions = mention;
  }
}
