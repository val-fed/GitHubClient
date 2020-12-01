package com.valfed.githubclient.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.valfed.githubclient.entity.Repository;

import java.util.Collection;
import java.util.List;

@Dao
public interface RepositoryDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertRepositories(Collection<Repository> repositories);

  @Query("SELECT * FROM Repository WHERE name = :repoName AND owner_login = :userLogin LIMIT 1")
  Repository getRepository(String repoName, String userLogin);

  @Query("SELECT * FROM Repository WHERE name LIKE :query ORDER BY stargazersCount")
  List<Repository> getRepositories(String query);
}
