package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.model.Tweet;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class TwitterDao implements CrdDao<Tweet,String> {

  /* URI constants */
  private static final String API_BASE_URI = "https://api.twitter.com";
  private static final String POST_PATH = "/1.1/statuses/update.json";
  private static final String SHOW_PATH = "/1.1/statuses/show.json";
  private static final String DELETE_PATH = "/1.1/statuses/destroy/";

  /* URI symbols */
  private static final String QUERY_SYM = "?";
  private static final String AMPERSAND = "&";
  private static final String EQUAL = "=";

  private static final int HTTP_OK = 200;
  private HttpHelper httpHelper;

  @Autowired
  public TwitterDao(HttpHelper httpHelper) {
    this.httpHelper = httpHelper;
  }

  public static <T> T toObjectFromJson(String json, Class clazz) throws IOException{
    ObjectMapper mapper = new ObjectMapper();
    return (T) mapper.readValue(json,clazz);
  }

  private URI getDeleteUri(String string) throws URISyntaxException {
    URI uri = new URI(API_BASE_URI + DELETE_PATH + string + ".json");
    return uri;
  }

  private URI getPostUri(Tweet tweet) throws URISyntaxException {
    String text = tweet.getText();
    float longitude = tweet.getCoordinates().getCoordinates()[0];
    float latitude = tweet.getCoordinates().getCoordinates()[1];
    URI uri = new URI(API_BASE_URI + POST_PATH + QUERY_SYM +
        "status" + EQUAL + AMPERSAND + "longitude" + EQUAL + longitude +
        AMPERSAND + "latitude" + EQUAL + latitude);
    return uri;
  }

  private URI getShowUri(String id) throws URISyntaxException {
    URI uri = new URI(API_BASE_URI + SHOW_PATH + QUERY_SYM + AMPERSAND
        + "id" + EQUAL + id);
    return uri;
  }


  /**
   * Create an entity(Tweet) to the underlying storage
   * @param entity entity that to be created
   * @return created entity
   */
  @Override
  public Tweet create(Tweet entity) {
    try {
      HttpResponse response = httpHelper.httpPost(getPostUri(entity));
      return parseResponseBody(response, HTTP_OK);
    } catch (URISyntaxException | IOException e) {
      throw new IllegalArgumentException("Invalid tweet input", e);
    }
  }

  Tweet parseResponseBody(HttpResponse response, int i) throws IOException {
    HttpEntity entity = response.getEntity();
    if (entity == null) {
      throw new RuntimeException("Empty response body");
    } else {
        System.out.println(EntityUtils.toString(entity));
    }
    String jsonString;
    try {
      jsonString = EntityUtils.toString(entity);
    } catch (IOException e) {
      throw new RuntimeException("Failed to convert entity to string", e);
    }
    try {
      return toObjectFromJson(jsonString, Tweet.class);
    } catch (IOException e) {
      throw new RuntimeException("Failed to covert JSON to Tweet Object", e);
    }
  }

  /**
   * Create an entity(Tweet) to the underlying storage
   * @param Entity entity that to be created
   * @return created entity
   */
  @Override
  public Tweet findById(String id) {
    try {
      HttpResponse response = httpHelper.httpGet(getShowUri(id));
      return parseResponseBody(response,HTTP_OK);
    } catch (URISyntaxException | IOException e) {
      throw new IllegalArgumentException("Invalid ID input", e);
    }
  }

  /**
   * Delete an entity(Tweet) by its ID
   * @param id of the entity to be deleted
   * @return deleted entity
   */
  @Override
  public Tweet deleteById(String id) {
    try {
      HttpResponse response = httpHelper.httpPost(getDeleteUri(id));
      return parseResponseBody(response, HTTP_OK);
    } catch (URISyntaxException | IOException e) {
      throw new IllegalArgumentException("Invalid ID input", e);
    }
  }
}
