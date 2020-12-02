package com.valfed.githubclient.di;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class ViewModelFactory implements ViewModelProvider.Factory {
  private final Map<Class<? extends ViewModel>, Provider<ViewModel>> viewModelMap;

  @Inject
  public ViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> viewModelMap) {
    this.viewModelMap = viewModelMap;
  }

  @NonNull
  @Override
  public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
    Provider<ViewModel> provider = viewModelMap.get(modelClass);
    if (provider == null) {
      throw new IllegalArgumentException(
          "Object " + modelClass.getSimpleName() + " can not be created");
    }
    return (T) provider.get();
  }
}
