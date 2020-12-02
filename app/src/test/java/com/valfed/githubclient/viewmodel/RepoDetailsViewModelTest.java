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

import static com.valfed.githubclient.viewmodel.TestDataProvider.REPOSITORY;
import static com.valfed.githubclient.viewmodel.TestDataProvider.TEST_EXECUTOR;
import static org.junit.Assert.assertEquals;


public class RepoDetailsViewModelTest {

  @Rule
  public TestRule rule = new InstantTaskExecutorRule();

  @Mock
  private DataRepository dataRepository;
  private RepoDetailsViewModel viewModel;

  @Before
  public void before() {
    MockitoAnnotations.openMocks(this);
    viewModel = new RepoDetailsViewModel(dataRepository, TEST_EXECUTOR);
    viewModel.getRepository().observeForever(repository -> {
    });
  }

  @Test
  public void searchRepositories_ValidRepositoryResponse_SameValueInLiveData() throws IOException {
    String repoName = "Android";
    String userLogin = "Androider";
    Mockito.when(dataRepository.getRepository(repoName, userLogin)).thenReturn(REPOSITORY);

    viewModel.updateContent(repoName, userLogin);

    assertEquals(REPOSITORY, viewModel.getRepository().getValue());
  }

  @Test
  public void updateContent_ErrorResponse_ErrorLiveDataIsTrue() throws IOException {
    String repoName = "Android";
    String userLogin = "Androider";
    Mockito.when(dataRepository.getRepository(repoName, userLogin)).thenThrow(new IOException());

    viewModel.updateContent(repoName, userLogin);

    assertEquals(true, viewModel.isException().getValue());
  }
}
