package com.valfed.githubclient.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.valfed.githubclient.App;
import com.valfed.githubclient.entity.Repository;
import com.valfed.githubclient.repository.DataRepository;

import java.io.IOException;
import java.util.List;

public class RepoListViewModel extends ViewModel {

  private final DataRepository dataRepository = App.getDataRepository();

  private final MutableLiveData<List<Repository>> repositories = new MutableLiveData<>();
  private final MutableLiveData<Boolean> isNetworkException = new MutableLiveData<>();
  private final MutableLiveData<Boolean> isQueryValidationException = new MutableLiveData<>();
  private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();


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
      new GetRepositoriesAsyncTask().execute(query);
    }
  }

  private class GetRepositoriesAsyncTask extends AsyncTask<String, Void, List<Repository>> {

    @Override
    protected void onPreExecute() {
      isLoading.setValue(true);
    }

    @Override
    protected List<Repository> doInBackground(String... queries) {

      try {
        return dataRepository.getRepositories(queries[0]);
      } catch (IOException e) {
        e.printStackTrace();
        return null;
      }
    }

    @Override
    protected void onPostExecute(List<Repository> result) {
      isLoading.setValue(false);

      if (result != null) {
        repositories.setValue(result);
      } else {
        isNetworkException.setValue(true);
      }
    }
  }
}
