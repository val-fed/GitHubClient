package com.valfed.githubclient.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;
import com.valfed.githubclient.App;
import com.valfed.githubclient.R;
import com.valfed.githubclient.entity.Repository;
import com.valfed.githubclient.repository.DataRepository;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class RepoDetailsFragment extends Fragment {

  public static final String EXTRA_REPO_NAME = "repoName";
  public static final String EXTRA_USER_LOGIN = "userLogin";
  private static final String LOG_TAG = "dc.RepoDetailsActivity";

  private DataRepository dataRepository;

  private ImageView ownerImageView;
  private TextView nameTextView;
  private TextView descriptionTextView;
  private TextView starsTextView;
  private TextView forksTextView;
  private TextView createdTextView;
  private TextView updatedTextView;
  private TextView languageTextView;


  public static RepoDetailsFragment newInstance(String repoName, String userLogin) {
    Bundle args = new Bundle();
    RepoDetailsFragment repoDetailsFragment = new RepoDetailsFragment();
    args.putString(EXTRA_REPO_NAME, repoName);
    args.putString(EXTRA_USER_LOGIN, userLogin);
    repoDetailsFragment.setArguments(args);
    return repoDetailsFragment;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater,
                           @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_repo_details, container, false);


    initView(view);

    dataRepository = App.getDataRepository();

    Bundle args = getArguments();
    if (args != null) {
      String repoName = args.getString(EXTRA_REPO_NAME);
      String userLogin = args.getString(EXTRA_USER_LOGIN);
      updateContent(repoName, userLogin);
    }
    return view;
  }

  private void initView(View view) {
    ownerImageView = view.findViewById(R.id.owner_image_view);
    nameTextView = view.findViewById(R.id.name_text_view);
    descriptionTextView = view.findViewById(R.id.description_text_view);
    starsTextView = view.findViewById(R.id.stars_text_view);
    forksTextView = view.findViewById(R.id.forks_text_view);
    createdTextView = view.findViewById(R.id.created_text_view);
    updatedTextView = view.findViewById(R.id.updated_text_view);
    languageTextView = view.findViewById(R.id.language_text_view);
  }

  void display(Repository repository) {
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

  public void updateContent(String repoName, String userLogin) {
    new GetRepositoryAsyncTask().execute(repoName, userLogin);
  }

  private class GetRepositoryAsyncTask extends AsyncTask<String, Void, Repository> {

    @Override
    protected Repository doInBackground(String... params) {
      String repoName = params[0];
      String userLogin = params[1];
      try {
        return dataRepository.getRepository(repoName, userLogin);
      } catch (IOException e) {
        e.printStackTrace();
        return null;
      }
    }

    @Override
    protected void onPostExecute(Repository repository) {
      if (repository != null) {
        display(repository);
      } else {
        Toast.makeText(getActivity(), R.string.error_message, Toast.LENGTH_SHORT).show();
      }
    }
  }
}
