package com.valfed.githubclient.network;


import com.valfed.githubclient.entity.RepositoriesResponse;
import com.valfed.githubclient.entity.Repository;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HttpClient {
  private final Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(GithubService.BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build();

  private final GithubService githubService = retrofit.create(GithubService.class);

  public List<Repository> getRepositories(String query) throws IOException {
    RepositoriesResponse response = getResponse(githubService.searchRepos(query));
    return response.getRepositories();
  }

  public Repository getRepository(String repoName, String userLogin) throws IOException {
    return getResponse(githubService.getRepo(userLogin, repoName));
  }

  private <T> T getResponse(Call<T> call) throws IOException {
    Response<T> response = call.execute();
    if (response.isSuccessful()) {
      return response.body();
    } else {
      throw new IOException("Response: " + response);
    }
  }
}
