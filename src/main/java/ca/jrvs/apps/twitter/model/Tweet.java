package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class Tweet {
  @JsonProperty("created_at")
  private String created_at;
  @JsonProperty("id")
  private Long id;
  @JsonProperty("id_str")
  private String id_str;
  @JsonProperty ("text")
  private String text;
  @JsonProperty("entities")
  private Entities entities;
  @JsonProperty("coordinates")
  private Coordinates coordinates;
  @JsonProperty("retweet_count")
  private Integer retweet_count;
  @JsonProperty("favorite_count")
  private Integer favorite_count;
  @JsonProperty ("favorited")
  private Boolean favorited;
  @JsonProperty ("retweeted")
  private Boolean retweeted;

  public String getCreated_at(){
    return created_at;
  }
  public void setCreated_at(String created_at){
    this.created_at = created_at;
  }
  public Long getId(){
    return id;
  }
  public void setId (long id){
    this.id = id;
  }
  public String getId_str() {
    return id_str;
  }

  public void setId_str(String id_str) {
    this.id_str = id_str;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Entities getEntities() {
    return entities;
  }

  public void setEntities(Entities entities) {
    this.entities = entities;
  }

  public Coordinates getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(Coordinates coordinates) {
    this.coordinates = coordinates;
  }

  public Integer getRetweet_count() {
    return retweet_count;
  }
  public void setRetweet_count(Integer retweet_count) {
    this.retweet_count = retweet_count;
  }

  public Integer getFavorite_count() {
    return favorite_count;
  }

  public void setFavorite_count(Integer favorite_count) {
    this.favorite_count = favorite_count;
  }

  public Boolean getFavorited() { return favorited; }

  public void setFavorited(boolean favorited) {
    this.favorited = favorited;
  }

  public Boolean getRetweeted() {
    return retweeted;
  }

  public void setRetweeted(boolean retweeted) {
    this.retweeted = retweeted;
  }
}
