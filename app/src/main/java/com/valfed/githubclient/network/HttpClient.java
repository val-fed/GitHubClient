package com.valfed.githubclient.network;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.valfed.githubclient.entity.Repository;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class HttpClient {
  private static final String REPOSITORY_SEARCH_URL = "https://api.github.com/search/repositories";
  private static final String REPOS_URL = "https://api.github.com/repos";
  private static final String QUERY_PARAM = "q";

  private final JsonParser jsonParser = new JsonParser();

  public List<Repository> getRepositories(String query) throws IOException, JSONException {
    String requestUrl = Uri.parse(REPOSITORY_SEARCH_URL)
        .buildUpon()
        .appendQueryParameter(QUERY_PARAM, query)
        .build()
        .toString();

    String response = getResponse(requestUrl);

    return jsonParser.getRepositories(response);
  }

  public Repository getRepository(String repoName, String userLogin) throws IOException, JSONException {
    String requestUrl = Uri.parse(REPOS_URL)
        .buildUpon()
        .appendPath(userLogin)
        .appendPath(repoName)
        .build()
        .toString();
    String response = getResponse(requestUrl);
    return jsonParser.getRepository(response);
  }

  @NonNull
  private String getResponse(String requestUrl) throws IOException {
    URL url = new URL(requestUrl);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

    try {
      connection.connect();
      InputStream in;
      int status = connection.getResponseCode();
      if (status == HttpURLConnection.HTTP_OK) {
        in = connection.getInputStream();
      } else {
        in = connection.getErrorStream();
      }
      return convertStreamToString(in);
    } finally {
      connection.disconnect();
    }
  }

  private String convertStreamToString(InputStream stream) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
    StringBuilder sb = new StringBuilder();

    String line;
    while ((line = reader.readLine()) != null) {
      sb.append(line).append("\n");
    }
    stream.close();

    return sb.toString();
  }
}
