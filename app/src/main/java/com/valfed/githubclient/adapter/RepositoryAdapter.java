package com.valfed.githubclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.valfed.githubclient.R;
import com.valfed.githubclient.entity.Repository;

import java.util.ArrayList;
import java.util.List;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder> {

  private final List<Repository> repositories = new ArrayList<>();

  @NonNull
  @Override
  public RepositoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    LayoutInflater inflater = LayoutInflater.from(context);
    View itemView = inflater.inflate(R.layout.repository_item_view, parent, false);
    return new RepositoryViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(@NonNull RepositoryViewHolder holder, int position) {
    Repository repository = repositories.get(position);
    holder.bind(repository);
  }

  @Override
  public int getItemCount() {
    return repositories.size();
  }

  public void addItems(List<Repository> items) {
    repositories.addAll(items);
    notifyDataSetChanged();
  }

  public void clearItems() {
    repositories.clear();
    notifyDataSetChanged();
  }

  class RepositoryViewHolder extends RecyclerView.ViewHolder {
    private final TextView nameTextView;
    private final TextView descriptionTextView;
    private final ImageView ownerImageView;

    public RepositoryViewHolder(@NonNull View itemView) {
      super(itemView);

      nameTextView = itemView.findViewById(R.id.name_text_view);
      descriptionTextView = itemView.findViewById(R.id.description_text_view);
      ownerImageView = itemView.findViewById(R.id.owner_image_view);
    }

    void bind(Repository repository) {
      nameTextView.setText(repository.getName());
      descriptionTextView.setText(repository.getDescription());

      String avatarUrl = repository.getOwner().getAvatarUrl();
      Picasso.get()
          .load(avatarUrl)
          .placeholder(R.drawable.ic_person_24)
          .into(ownerImageView);
    }
  }
}
