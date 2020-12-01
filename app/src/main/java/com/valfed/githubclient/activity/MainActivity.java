package com.valfed.githubclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.valfed.githubclient.R;
import com.valfed.githubclient.fragment.RepoDetailsFragment;
import com.valfed.githubclient.fragment.RepoListFragment;


public class MainActivity extends AppCompatActivity {
  private static final String LOG_TAG = "dc.MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (savedInstanceState == null) {
      getSupportFragmentManager()
          .beginTransaction()
          .add(R.id.master_fragment_container, new RepoListFragment(), RepoListFragment.TAG)
          .commit();
    }

    initView();
  }

  private void initView() {
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    final ImageView searchImageView = findViewById(R.id.search_button);
    final EditText searchEditText = findViewById(R.id.query_edit_text);

    searchImageView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Fragment fragment = getSupportFragmentManager()
            .findFragmentById(R.id.master_fragment_container);
        final RepoListFragment repoListFragment = (RepoListFragment) fragment;
        if (repoListFragment == null) {
          throw new NullPointerException("Fragment can not be null");
        }
        String query = searchEditText.getText().toString();
        repoListFragment.onSearchQueryChanged(query);
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater menuInflater = getMenuInflater();
    menuInflater.inflate(R.menu.main_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
      case R.id.settings:
        Toast.makeText(this, R.string.settings_clicked_hint, Toast.LENGTH_SHORT).show();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  public void navigateToDetailsScreen(String name, String login) {
    boolean isDualPaneMode = findViewById(R.id.details_fragment_container) != null;
    if (isDualPaneMode) {
      showDetailsFragment(name, login);
    } else {
      startDetailsActivity(name, login);
    }
  }

  private void showDetailsFragment(String name, String login) {
    Fragment fragment = getSupportFragmentManager()
        .findFragmentById(R.id.details_fragment_container);
    if (fragment != null) {
      RepoDetailsFragment repoDetailsFragment = (RepoDetailsFragment) fragment;
      repoDetailsFragment.updateContent(name, login);
    } else {
      getSupportFragmentManager()
          .beginTransaction()
          .add(R.id.details_fragment_container, RepoDetailsFragment.newInstance(name, login))
          .commit();
    }
  }

  private void startDetailsActivity(String name, String login) {
    Intent intent = new Intent(this, RepoDetailsActivity.class);
    intent.putExtra(RepoDetailsActivity.EXTRA_REPO_NAME, name);
    intent.putExtra(RepoDetailsActivity.EXTRA_USER_LOGIN, login);
    startActivity(intent);
  }
}
