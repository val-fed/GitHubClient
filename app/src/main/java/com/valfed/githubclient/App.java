package com.valfed.githubclient;

import android.app.Application;

import androidx.room.Room;

import com.valfed.githubclient.db.AppDatabase;
import com.valfed.githubclient.di.AppComponent;
import com.valfed.githubclient.di.DaggerAppComponent;
import com.valfed.githubclient.network.HttpClient;
import com.valfed.githubclient.repository.DataRepository;

public class App extends Application {
  private static HttpClient httpClient;
  private static AppDatabase appDatabase;
  private static DataRepository dataRepository;
  private static AppComponent appComponent;
  private static App INSTANCE;

  public static DataRepository getDataRepository() {
    if (dataRepository == null) {
      dataRepository = new DataRepository();
    }
    return dataRepository;
  }

  public static HttpClient getHttpClient() {
    if (httpClient == null) {
      httpClient = new HttpClient();
    }
    return httpClient;
  }

  public static AppDatabase getAppDatabase() {
    if (appDatabase == null) {
      appDatabase = Room.databaseBuilder(INSTANCE, AppDatabase.class, AppDatabase.DB_NAME).build();
    }
    return appDatabase;
  }

  public static AppComponent getAppComponent() {
    return appComponent;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    INSTANCE = this;
    appComponent = DaggerAppComponent.create();
  }
}
