package com.valfed.githubclient.di;

import android.content.Context;

import androidx.room.Room;

import com.valfed.githubclient.db.AppDatabase;
import com.valfed.githubclient.db.RepositoryDao;
import com.valfed.githubclient.network.GithubService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {
  private final Context context;

  public AppModule(Context context) {
    this.context = context;
  }

  @Provides
  @Singleton
  public Context getContext() {
    return context;
  }

  @Provides
  @Singleton
  public AppDatabase providesAppDatabase() {
    return Room.databaseBuilder(context, AppDatabase.class, AppDatabase.DB_NAME).build();
  }

  @Provides
  @Singleton
  public RepositoryDao providesRepositoryDao(AppDatabase appDatabase) {
    return appDatabase.repositoryDao();
  }

  @Provides
  @Singleton
  public Retrofit providesRetrofit() {
    return new Retrofit.Builder()
        .baseUrl(GithubService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

  @Provides
  @Singleton
  public GithubService providesGithubService(Retrofit retrofit) {
    return retrofit.create(GithubService.class);
  }
}
