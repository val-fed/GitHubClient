package com.valfed.githubclient.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.valfed.githubclient.repository.DataRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static com.valfed.githubclient.TestDataProvider.REPOSITORIES;
import static com.valfed.githubclient.TestDataProvider.TEST_EXECUTOR;
import static org.junit.Assert.assertEquals;

public class RepoListViewModelTest {
  @Rule
  public TestRule rule = new InstantTaskExecutorRule();

  @Mock
  private DataRepository dataRepository;
  private RepoListViewModel viewModel;

  @Before
  public void before() {
    MockitoAnnotations.openMocks(this);
    viewModel = new RepoListViewModel(dataRepository, TEST_EXECUTOR);
    viewModel.getRepositories().observeForever(repositories -> {
    });
  }

  @Test
  public void searchRepositories_ValidRepositoryResponse_SameValueInLiveData() throws IOException {
    String query = "Android";
    Mockito.when(dataRepository.getRepositories(query)).thenReturn(REPOSITORIES);

    viewModel.searchRepositories(query);

    assertEquals(REPOSITORIES, viewModel.getRepositories().getValue());
  }

  @Test
  public void searchRepositories_ErrorResponse_ErrorLiveDataIsTrue() throws IOException {
    String query = "Android";
    Mockito.when(dataRepository.getRepositories(query)).thenThrow(new IOException());

    viewModel.searchRepositories(query);

    assertEquals(true, viewModel.isNetworkException().getValue());
  }

  @Test
  public void searchRepositories_QueryIsEmpty_ValidationError() {
    String query = "";

    viewModel.searchRepositories(query);

    assertEquals(true, viewModel.isQueryValidationException().getValue());
  }
}
