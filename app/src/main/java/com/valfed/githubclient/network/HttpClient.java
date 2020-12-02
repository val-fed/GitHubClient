package com.valfed.githubclient.network;


import com.valfed.githubclient.entity.RepositoriesResponse;
import com.valfed.githubclient.entity.Repository;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Response;


@Singleton
public class HttpClient {
  private final GithubService githubService;

  @Inject
  public HttpClient(GithubService githubService) {
    this.githubService = githubService;
  }

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
