package com.valfed.githubclient.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.valfed.githubclient.R;
import com.valfed.githubclient.entity.Repository;
import com.valfed.githubclient.network.HttpClient;

import org.json.JSONException;

import java.io.IOException;

public class RepoDetailsActivity extends AppCompatActivity {

  public static final String EXTRA_REPO_NAME = "repoName";
  public static final String EXTRA_USER_LOGIN = "userLogin";
  private static final String LOG_TAG = "dc.RepoDetailsActivity";
  private HttpClient httpClient;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_repo_details);

    String repoName = getIntent().getStringExtra(EXTRA_REPO_NAME);
    String userLogin = getIntent().getStringExtra(EXTRA_USER_LOGIN);

    httpClient = new HttpClient();

    new GetRepositoryAsyncTask().execute(repoName, userLogin);
  }

  private class GetRepositoryAsyncTask extends AsyncTask<String, Void, Repository> {

    @Override
    protected Repository doInBackground(String... strings) {
      String repoName = strings[0];
      String userLogin = strings[1];
      try {
        return httpClient.getRepository(repoName, userLogin);
      } catch (IOException | JSONException e) {
        e.printStackTrace();
        return null;
      }
    }

    @Override
    protected void onPostExecute(Repository repository) {
      if (repository != null) {
        Log.d(LOG_TAG, repository.toString());
      } else {
        Toast.makeText(RepoDetailsActivity.this, R.string.error_message, Toast.LENGTH_SHORT)
            .show();
      }
    }
  }
}