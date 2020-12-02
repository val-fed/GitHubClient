package com.valfed.githubclient.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.valfed.githubclient.entity.Repository;
import com.valfed.githubclient.repository.DataRepository;

import java.io.IOException;
import java.util.concurrent.Executor;

import javax.inject.Inject;

public class RepoDetailsViewModel extends ViewModel {
  private final DataRepository dataRepository;
  private final Executor executor;

  private final MutableLiveData<Repository> repository = new MutableLiveData<>();
  private final MutableLiveData<Boolean> isNetworkException = new MutableLiveData<>();
  private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

  @Inject
  public RepoDetailsViewModel(DataRepository dataRepository, Executor executor) {
    this.dataRepository = dataRepository;
    this.executor = executor;
  }

  public LiveData<Repository> getRepository() {
    return repository;
  }

  public LiveData<Boolean> isException() {
    return isNetworkException;
  }

  public LiveData<Boolean> isLoading() {
    return isLoading;
  }

  public void updateContent(String repoName, String userLogin) {
    isLoading.setValue(true);
    executor.execute(new Runnable() {
      @Override
      public void run() {
        try {
          Repository result = dataRepository.getRepository(repoName, userLogin);
          repository.postValue(result);
        } catch (IOException e) {
          e.printStackTrace();
          isNetworkException.postValue(true);
        } finally {
          isLoading.postValue(false);
        }
      }
    });
  }
}
