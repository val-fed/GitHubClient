package com.valfed.githubclient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.valfed.githubclient.network.HttpClient;

import java.io.IOException;

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

  private class GetRepositoriesTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... strings) {
      try {
        return httpClient.getRepositories(strings[0]);
      } catch (IOException ioException) {
        ioException.printStackTrace();
        return null;
      }
    }

    @Override
    protected void onPostExecute(String result) {
      if (result != null) {
        Log.d(LOG_TAG, result);
      } else {
        Toast
            .makeText(MainActivity.this, R.string.error_message, Toast.LENGTH_SHORT)
            .show();
      }
    }
  }
}