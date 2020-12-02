package com.valfed.githubclient;

import android.app.Application;

import com.valfed.githubclient.di.AppComponent;
import com.valfed.githubclient.di.AppModule;
import com.valfed.githubclient.di.DaggerAppComponent;

public class App extends Application {
  private static AppComponent appComponent;
  private static App INSTANCE;

  public static AppComponent getAppComponent() {
    return appComponent;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    INSTANCE = this;
    appComponent = DaggerAppComponent.builder()
        .appModule(new AppModule(this))
        .build();
  }
}
