package com.valfed.githubclient.di;

import androidx.lifecycle.ViewModel;

import com.valfed.githubclient.viewmodel.RepoDetailsViewModel;
import com.valfed.githubclient.viewmodel.RepoListViewModel;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public class ViewModelModule {

  @IntoMap
  @Provides
  @ViewModelKey(RepoListViewModel.class)
  public ViewModel repoListViewModel(RepoListViewModel repoListViewModel) {
    return repoListViewModel;
  }

  @IntoMap
  @Provides
  @ViewModelKey(RepoDetailsViewModel.class)
  public ViewModel repoDetailsViewModel(RepoDetailsViewModel repoDetailsViewModel) {
    return repoDetailsViewModel;
  }
}
