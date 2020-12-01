package com.valfed.githubclient.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.valfed.githubclient.App;
import com.valfed.githubclient.R;
import com.valfed.githubclient.activity.MainActivity;
import com.valfed.githubclient.adapter.RepositoryAdapter;
import com.valfed.githubclient.entity.Repository;
import com.valfed.githubclient.repository.DataRepository;
import com.valfed.githubclient.viewmodel.RepoListViewModel;


public class RepoListFragment extends Fragment {

  public static final String TAG = "RepoListFragment";

  private DataRepository dataRepository;
  private RepositoryAdapter repositoryAdapter;
  private ProgressBar progressBar;
  private MainActivity mainActivity;
  private RepoListViewModel viewModel;

  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    if (context instanceof MainActivity) {
      mainActivity = (MainActivity) context;
    } else {
      throw new RuntimeException("Can not cast context to MainActivity");
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_repo_list, container, false);

    initRecyclerView(view);
    initViewModel();

    progressBar = view.findViewById(R.id.progress_bar);
    dataRepository = App.getDataRepository();
    return view;
  }

  private void initViewModel() {
    viewModel = ViewModelProviders.of(this).get(RepoListViewModel.class);

    viewModel.getRepositories().observe(this, (repositories -> {
      if (repositories != null) {
        repositoryAdapter.addItems(repositories);
      }
    }));

    viewModel.isLoading().observe(this, (isLoading) -> {
      if (isLoading != null) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
      }
    });

    viewModel.isNetworkException().observe(this, (isException) -> {
      if (isException != null && isException) {
        Toast.makeText(getActivity(), R.string.error_message, Toast.LENGTH_SHORT).show();
      }
    });

    viewModel.isQueryValidationException().observe(this, (isQueryValidationException) -> {
      if (isQueryValidationException != null && isQueryValidationException) {
        Toast.makeText(getActivity(), R.string.empty_text, Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void initRecyclerView(View view) {
    RecyclerView recyclerView = view.findViewById(R.id.repository_recycler_view);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    RepositoryAdapter.OnRepoClickListener listener = new RepositoryAdapter.OnRepoClickListener() {
      @Override
      public void onRepoClick(Repository repository) {
        mainActivity.navigateToDetailsScreen(repository.getName(), repository.getOwner().getLogin());
      }
    };
    repositoryAdapter = new RepositoryAdapter(listener);
    recyclerView.setAdapter(repositoryAdapter);
  }

  public void onSearchQueryChanged(String query) {
    viewModel.searchRepositories(query);
  }
}
