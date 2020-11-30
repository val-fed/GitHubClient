package com.valfed.githubclient.network;

import com.valfed.githubclient.entity.Owner;
import com.valfed.githubclient.entity.Repository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {
  public List<Repository> getRepositories(String jsonString) throws JSONException {
    JSONObject json = new JSONObject(jsonString);

    JSONArray items = json.getJSONArray("items");
    List<Repository> result = new ArrayList<>();

    for (int i = 0; i < items.length(); i++) {
      JSONObject repositoryJson = items.getJSONObject(i);

      int id = repositoryJson.getInt("id");
      String name = repositoryJson.getString("name");
      String description = repositoryJson.getString("description");
      String createdAt = repositoryJson.getString("created_at");
      String updatedAt = repositoryJson.getString("updated_at");
      int starsCount = repositoryJson.getInt("stargazers_count");
      int forksCount = repositoryJson.getInt("forks_count");
      String language = repositoryJson.getString("language");

      JSONObject ownerJson = repositoryJson.getJSONObject("owner");
      String login = ownerJson.getString("login");
      String avatarUrl = ownerJson.getString("avatar_url");
      int ownerId = ownerJson.getInt("id");

      Owner owner = new Owner(login, ownerId, avatarUrl);
      Repository repository = new Repository(id, name, description, createdAt, updatedAt,
          starsCount, language, forksCount, owner);

      result.add(repository);
    }

    return result;
  }
}
