package com.valfed.githubclient.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.valfed.githubclient.entity.Repository;
import com.valfed.githubclient.repository.DataRepository;

import java.io.IOException;

import javax.inject.Inject;

public class RepoDetailsViewModel extends ViewModel {
  private final DataRepository dataRepository;

  private final MutableLiveData<Repository> repository = new MutableLiveData<>();
  private final MutableLiveData<Boolean> isNetworkException = new MutableLiveData<>();
  private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

  @Inject
  public RepoDetailsViewModel(DataRepository dataRepository) {
    this.dataRepository = dataRepository;
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
    new GetRepositoryAsyncTask().execute(repoName, userLogin);
  }

  private class GetRepositoryAsyncTask extends AsyncTask<String, Void, Repository> {

    @Override
    protected void onPreExecute() {
      isLoading.setValue(true);
    }

    @Override
    protected Repository doInBackground(String... params) {
      String repoName = params[0];
      String userLogin = params[1];
      try {
        return dataRepository.getRepository(repoName, userLogin);
      } catch (IOException e) {
        e.printStackTrace();
        return null;
      }
    }

    @Override
    protected void onPostExecute(Repository result) {

      isLoading.setValue(false);

      if (result != null) {
        repository.setValue(result);
      } else {
        isNetworkException.setValue(true);
      }
    }
  }
}
