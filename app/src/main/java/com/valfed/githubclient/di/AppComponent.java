package com.valfed.githubclient.di;

import com.valfed.githubclient.fragment.RepoDetailsFragment;
import com.valfed.githubclient.fragment.RepoListFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ViewModelModule.class)
public interface AppComponent {
  void inject(RepoListFragment repoListFragment);

  void inject(RepoDetailsFragment repoDetailsFragment);
}
