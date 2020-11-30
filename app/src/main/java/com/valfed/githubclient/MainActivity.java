package com.valfed.githubclient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.valfed.githubclient.entity.Repository;
import com.valfed.githubclient.network.HttpClient;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
  private static final String LOG_TAG = "dc.MainActivity";

  private HttpClient httpClient;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    httpClient = new HttpClient();

    new GetRepositoriesTask().execute("android");
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
        Log.d(LOG_TAG, result.toString());
      } else {
        Toast
            .makeText(MainActivity.this, R.string.error_message, Toast.LENGTH_SHORT)
            .show();
      }
    }
  }
}