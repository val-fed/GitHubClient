package com.valfed.githubclient.repository;

import com.valfed.githubclient.db.RepositoryDao;
import com.valfed.githubclient.network.HttpClient;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static com.valfed.githubclient.TestDataProvider.REPOSITORIES;
import static com.valfed.githubclient.TestDataProvider.REPOSITORY;
import static org.junit.Assert.assertEquals;

public class DataRepositoryTest {

  @Mock
  private HttpClient httpClient;

  @Mock
  private RepositoryDao repositoryDao;
  private DataRepository dataRepository;

  @Before
  public void before() {
    MockitoAnnotations.openMocks(this);
    dataRepository = new DataRepository(httpClient, repositoryDao);
  }

  @Test
  public void getRepositories_ValidServerResponse_StoreResponseInDbAndReturn() throws IOException {
    String query = "Android";

    Mockito.when(httpClient.getRepositories(query)).thenReturn(REPOSITORIES);

    assertEquals(REPOSITORIES, dataRepository.getRepositories(query));
    Mockito.verify(repositoryDao).insertRepositories(REPOSITORIES);

  }

  @Test
  public void getRepositories_HttpErrorAndDatabaseIsNotEmpty_ReturnDbResponse() throws IOException {
    String query = "Android";
    String dbQuery = "%Android%";

    Mockito.when(httpClient.getRepositories(query)).thenThrow(new IOException());
    Mockito.when(repositoryDao.getRepositories(dbQuery)).thenReturn(REPOSITORIES);

    assertEquals(REPOSITORIES, dataRepository.getRepositories(query));
    Mockito.verify(repositoryDao, Mockito.never()).insertRepositories(REPOSITORIES);
  }

  @Test(expected = IOException.class)
  public void getRepositories_ServerErrorAndDatabaseIsEmpty_throwIoException() throws IOException {
    String query = "Android";
    String dbQuery = "%Android%";

    Mockito.when(httpClient.getRepositories(query)).thenThrow(new IOException());
    Mockito.when(repositoryDao.getRepositories(dbQuery)).thenReturn(null);

    dataRepository.getRepositories(query);
  }

  @Test
  public void getRepository_DatabaseIsNotEmpty_ReturnDbResponse() throws IOException {
    String repoName = "repoName";
    String userLogin = "userLogin";

    Mockito.when(repositoryDao.getRepository(repoName, userLogin)).thenReturn(REPOSITORY);

    assertEquals(REPOSITORY, dataRepository.getRepository(repoName, userLogin));
  }

  @Test(expected = IOException.class)
  public void getRepository_DatabaseIsEmpty_throwIoException() throws IOException {
    String repoName = "repoName";
    String userLogin = "userLogin";

    Mockito.when(repositoryDao.getRepository(repoName, userLogin)).thenReturn(null);

    dataRepository.getRepository(repoName, userLogin);
  }
}
