package com.valfed.githubclient.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.valfed.githubclient.entity.Repository;

@Database(entities = {Repository.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
  public static final String DB_NAME = "github_client";

  public abstract RepositoryDao repositoryDao();
}
