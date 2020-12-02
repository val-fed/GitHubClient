package com.valfed.githubclient.repository;

import com.valfed.githubclient.App;
import com.valfed.githubclient.db.AppDatabase;
import com.valfed.githubclient.entity.Repository;
import com.valfed.githubclient.network.HttpClient;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

public class DataRepository {
  private final HttpClient httpClient = App.getHttpClient();
  private final AppDatabase db = App.getAppDatabase();

  @Inject
  public DataRepository() {
  }

  public Repository getRepository(String repoName, String userLogin) throws IOException {
    Repository repository = db.repositoryDao().getRepository(repoName, userLogin);
    if (repository == null) throw new IOException("Can't find repository entity in db");

    return repository;
  }

  public List<Repository> getRepositories(String query) throws IOException {
    List<Repository> repositories = null;
    try {

      repositories = httpClient.getRepositories(query);
      db.repositoryDao().insertRepositories(repositories);
    } catch (IOException e) {
      String dbWildCardQuery = "%" + query + "%";
      repositories = db.repositoryDao().getRepositories(dbWildCardQuery);
    }

    if (repositories == null)
      throw new IOException("Can't find repositories entities in db or api");

    return repositories;
  }
}
