package com.valfed.githubclient.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.valfed.githubclient.entity.Repository;
import com.valfed.githubclient.repository.DataRepository;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

public class RepoListViewModel extends ViewModel {

  private final DataRepository dataRepository;
  private final Executor executor;

  private final MutableLiveData<List<Repository>> repositories = new MutableLiveData<>();
  private final MutableLiveData<Boolean> isNetworkException = new MutableLiveData<>();
  private final MutableLiveData<Boolean> isQueryValidationException = new MutableLiveData<>();
  private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

  @Inject
  public RepoListViewModel(DataRepository dataRepository, Executor executor) {
    this.dataRepository = dataRepository;
    this.executor = executor;
  }

  public LiveData<List<Repository>> getRepositories() {
    return repositories;
  }


  public LiveData<Boolean> isNetworkException() {
    return isNetworkException;
  }

  public LiveData<Boolean> isQueryValidationException() {
    return isQueryValidationException;
  }

  public LiveData<Boolean> isLoading() {
    return isLoading;
  }

  public void searchRepositories(String query) {
    if (query.isEmpty()) {
      isQueryValidationException.setValue(true);
    } else {
      isLoading.setValue(true);
      executor.execute(new Runnable() {
        @Override
        public void run() {
          try {
            List<Repository> result = dataRepository.getRepositories(query);
            repositories.postValue(result);
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
}
