package com.valfed.githubclient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.valfed.githubclient.adapter.RepositoryAdapter;
import com.valfed.githubclient.entity.Repository;
import com.valfed.githubclient.network.HttpClient;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
  private static final String LOG_TAG = "dc.MainActivity";

  private HttpClient httpClient;
  private RepositoryAdapter repositoryAdapter;
  private EditText queryEditText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    RecyclerView recyclerView = findViewById(R.id.repository_recycler_view);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    repositoryAdapter = new RepositoryAdapter();
    recyclerView.setAdapter(repositoryAdapter);
    queryEditText = findViewById(R.id.query_edit_text);

    httpClient = new HttpClient();
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
      } catch (IOException | JSONException e) {
        e.printStackTrace();
        return null;
      }
    }

    @Override
    protected void onPostExecute(List<Repository> result) {
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