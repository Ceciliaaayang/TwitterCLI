package ca.jrvs.apps.twitter.dao;

import java.io.IOException;
import java.net.URI;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class TwitterHttpHelper implements HttpHelper {

  private OAuthConsumer consumer;
  private HttpClient httpClient;

  public TwitterHttpHelper(String consumerKey, String consumerSecret, String accessToken,
      String tokenSecret) {
    this.consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
    this.consumer.setTokenWithSecret(accessToken, tokenSecret);
    this.httpClient = HttpClientBuilder.create().build();
  }

  @Override
  public HttpResponse httpPost(URI uri) {
    try {
      return executeHttpRequest(HttpMethod.POST, uri, null);
    } catch (OAuthException | IOException | IllegalAccessException e) {
      throw new RuntimeException("Failed to execute", e);
    }
  }


  private HttpResponse executeHttpRequest(HttpMethod method, URI uri, StringEntity entity)
      throws OAuthException, IOException, IllegalAccessException {
    if (method == HttpMethod.GET) {
      HttpGet request = new HttpGet(uri);
      consumer.sign(request);
      return httpClient.execute(request);
    } else if (method == HttpMethod.POST) {
      HttpPost request = new HttpPost(uri);
      if (entity != null) {
        request.setEntity(entity);
      }
      consumer.sign(request);
      return httpClient.execute(request);
    } else {
      throw new IllegalAccessException("Unsupported HTTP method:" + method.name());
    }
  }

  @Override
  public HttpResponse httpGet(URI uri) {
    try {
      return executeHttpRequest(HttpMethod.GET, uri, null);
    } catch (OAuthException | IOException | IllegalAccessException e) {
      throw new RuntimeException("Failed to execute", e);
    }
  }

  public static void main(String[] args) throws Exception{
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
    System.out.println(consumerKey + "|" + consumerSecret + "|" + accessToken + "|" + tokenSecret);
    HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
    HttpResponse response = httpHelper.httpPost(new URI("https://api.twitter.com/1.1/statuses/update.json?status=first_tweet2"));
    System.out.println(EntityUtils.toString(response.getEntity()));
  }

}
