package com.valfed.githubclient.repository;

import com.valfed.githubclient.db.RepositoryDao;
import com.valfed.githubclient.entity.Repository;
import com.valfed.githubclient.network.HttpClient;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

public class DataRepository {
  private final HttpClient httpClient;
  private final RepositoryDao repositoryDao;

  @Inject
  public DataRepository(HttpClient httpClient, RepositoryDao repositoryDao) {
    this.httpClient = httpClient;
    this.repositoryDao = repositoryDao;
  }

  public Repository getRepository(String repoName, String userLogin) throws IOException {
    Repository repository = repositoryDao.getRepository(repoName, userLogin);
    if (repository == null) throw new IOException("Can't find repository entity in db");

    return repository;
  }

  public List<Repository> getRepositories(String query) throws IOException {
    List<Repository> repositories = null;
    try {

      repositories = httpClient.getRepositories(query);
      repositoryDao.insertRepositories(repositories);
    } catch (IOException e) {
      String dbWildCardQuery = "%" + query + "%";
      repositories = repositoryDao.getRepositories(dbWildCardQuery);
    }

    if (repositories == null)
      throw new IOException("Can't find repositories entities in db or api");

    return repositories;
  }
}
