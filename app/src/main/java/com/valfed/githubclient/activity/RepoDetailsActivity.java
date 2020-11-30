package com.valfed.githubclient.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.valfed.githubclient.R;
import com.valfed.githubclient.entity.Repository;
import com.valfed.githubclient.network.HttpClient;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RepoDetailsActivity extends AppCompatActivity {

  public static final String EXTRA_REPO_NAME = "repoName";
  public static final String EXTRA_USER_LOGIN = "userLogin";
  private static final String LOG_TAG = "dc.RepoDetailsActivity";
  private HttpClient httpClient;

  private ImageView ownerImageView;
  private TextView nameTextView;
  private TextView descriptionTextView;
  private TextView starsTextView;
  private TextView forksTextView;
  private TextView createdTextView;
  private TextView updatedTextView;
  private TextView languageTextView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_repo_details);

    String repoName = getIntent().getStringExtra(EXTRA_REPO_NAME);
    String userLogin = getIntent().getStringExtra(EXTRA_USER_LOGIN);

    initViews();

    httpClient = new HttpClient();

    new GetRepositoryAsyncTask().execute(repoName, userLogin);
  }

  private void initViews() {
    ownerImageView = findViewById(R.id.owner_image_view);
    nameTextView = findViewById(R.id.name_text_view);
    descriptionTextView = findViewById(R.id.description_text_view);
    starsTextView = findViewById(R.id.stars_text_view);
    forksTextView = findViewById(R.id.forks_text_view);
    createdTextView = findViewById(R.id.created_text_view);
    updatedTextView = findViewById(R.id.updated_text_view);
    languageTextView = findViewById(R.id.language_text_view);
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
        display(repository);

      } else {
        Toast.makeText(RepoDetailsActivity.this, R.string.error_message, Toast.LENGTH_SHORT)
            .show();
      }
    }

    private void display(Repository repository) {
      String avatarUrl = repository.getOwner().getAvatarUrl();
      Picasso.get().load(avatarUrl).into(ownerImageView);
      nameTextView.setText(repository.getName());
      descriptionTextView.setText(repository.getDescription());

      int starsCount = repository.getStargazersCount();
      starsTextView.setText(String.valueOf(starsCount));

      int forksCount = repository.getForksCount();
      forksTextView.setText(String.valueOf(forksCount));

      languageTextView.setText(repository.getLanguage());
      String createdAt = repository.getCreatedAt();
      createdTextView.setText(getFormattedDate(createdAt));
      String updatedAt = repository.getUpdatedAt();
      updatedTextView.setText(getFormattedDate(updatedAt));
    }

    private String getFormattedDate(String dateString) {
      SimpleDateFormat githubFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
      try {
        Date date = githubFormat.parse(dateString);

        SimpleDateFormat outputFormat = new SimpleDateFormat("dd.MM.yyyy");
        return outputFormat.format(date);
      } catch (ParseException e) {
        throw new RuntimeException(e);
      }
    }
  }
}