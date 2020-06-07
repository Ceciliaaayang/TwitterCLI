package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class Coordinates {
  @JsonProperty("coordinates")
  private float[] coordinates;
  @JsonProperty("type")
  private String type;

  public Coordinates (float longitude , float latitude ){
    this.coordinates = new float[2];
    this.coordinates[0] = longitude;
    this.coordinates[1] = latitude;
  }
  public float [] getCoordinates(){
    return coordinates;
  }
  public void setCoordinates(float[] coordinates){
    this.coordinates = coordinates;
  }
  public String getType(){
    return type;
  }
  public void setType(String type){
    this.type = type;
  }
}
