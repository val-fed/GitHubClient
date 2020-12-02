package com.valfed.githubclient;

import com.valfed.githubclient.entity.Owner;
import com.valfed.githubclient.entity.Repository;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

public class TestDataProvider {
  public static final Repository REPOSITORY = new Repository(
      1,
      "name",
      "description",
      "2019-01-01T04:02:57Z",
      "2018-11-05T04:02:57Z",
      100,
      "en",
      100,
      new Owner("login", 1, "https://sample/sample.png")
  );

  public static final List<Repository> REPOSITORIES = Collections.singletonList(REPOSITORY);
  public static Executor TEST_EXECUTOR = new Executor() {
    @Override
    public void execute(Runnable command) {
      command.run();
    }
  };
}
