package com.valfed.githubclient.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.valfed.githubclient.R;
import com.valfed.githubclient.adapter.RepositoryAdapter;
import com.valfed.githubclient.entity.Repository;
import com.valfed.githubclient.network.HttpClient;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
  private static final String LOG_TAG = "dc.MainActivity";

  private HttpClient httpClient;
  private RepositoryAdapter repositoryAdapter;
  private EditText queryEditText;
  private ProgressBar progressBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initRecyclerView();
    queryEditText = findViewById(R.id.query_edit_text);
    progressBar = findViewById(R.id.progress_bar);

    httpClient = new HttpClient();
  }

  private void initRecyclerView() {
    RecyclerView recyclerView = findViewById(R.id.repository_recycler_view);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    RepositoryAdapter.OnRepoClickListener listener = new RepositoryAdapter.OnRepoClickListener() {
      @Override
      public void onRepoClick(Repository repository) {
        Intent intent = new Intent(MainActivity.this, RepoDetailsActivity.class);
        intent.putExtra(RepoDetailsActivity.EXTRA_REPO_NAME, repository.getName());
        String userLogin = repository.getOwner().getLogin();
        intent.putExtra(RepoDetailsActivity.EXTRA_USER_LOGIN, userLogin);
        startActivity(intent);
      }
    };

    repositoryAdapter = new RepositoryAdapter(listener);
    recyclerView.setAdapter(repositoryAdapter);
  }

  public void searchRepositories(View view) {
    String query = queryEditText.getText().toString();
    if (query.isEmpty()) {
      Toast.makeText(this, R.string.empty_text, Toast.LENGTH_SHORT).show();
    } else {
      repositoryAdapter.clearItems();
      new GetRepositoriesTask().execute(query);
    }
  }

  private class GetRepositoriesTask extends AsyncTask<String, Void, List<Repository>> {

    @Override
    protected List<Repository> doInBackground(String... strings) {
      try {
        return httpClient.getRepositories(strings[0]);
      } catch (IOException e) {
        e.printStackTrace();
        return null;
      }
    }

    @Override
    protected void onPreExecute() {
      progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(List<Repository> result) {
      progressBar.setVisibility(View.GONE);
      if (result != null) {
        repositoryAdapter.addItems(result);
      } else {
        Toast
            .makeText(MainActivity.this, R.string.error_message, Toast.LENGTH_SHORT)
            .show();
      }
    }
  }
}