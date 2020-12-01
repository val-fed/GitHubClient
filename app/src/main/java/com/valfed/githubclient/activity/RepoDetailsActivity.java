package com.valfed.githubclient.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.valfed.githubclient.R;
import com.valfed.githubclient.fragment.RepoDetailsFragment;


public class RepoDetailsActivity extends AppCompatActivity {

  private static final String LOG_TAG = "dc.RepoDetailsActivity";

  public static final String EXTRA_REPO_NAME = "repoName";
  public static final String EXTRA_USER_LOGIN = "userLogin";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_repo_details);

    String repoName = getIntent().getStringExtra(EXTRA_REPO_NAME);
    String userLogin = getIntent().getStringExtra(EXTRA_USER_LOGIN);

    Fragment fragment = getSupportFragmentManager()
        .findFragmentById(R.id.repo_details_fragment);
    if (fragment == null) {
      throw new NullPointerException("Fragment can not be null");
    }
    RepoDetailsFragment repoDetailsFragment = (RepoDetailsFragment) fragment;
    repoDetailsFragment.updateContent(repoName, userLogin);
  }
}