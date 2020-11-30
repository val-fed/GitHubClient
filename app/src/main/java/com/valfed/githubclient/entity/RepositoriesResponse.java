package com.valfed.githubclient.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RepositoriesResponse {
  @SerializedName("total_count")
  private Long totalCount;

  @SerializedName("incomplete_results")
  private Boolean incompleteResults;

  @SerializedName("items")
  private List<Repository> repositories;


  public Long getTotalCount() {
    return totalCount;
  }

  public Boolean getIncompleteResults() {
    return incompleteResults;
  }

  public List<Repository> getRepositories() {
    return repositories;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final RepositoriesResponse that = (RepositoriesResponse) o;

    if (totalCount != null ? !totalCount.equals(that.totalCount) : that.totalCount != null)
      return false;
    if (incompleteResults != null ? !incompleteResults.equals(that.incompleteResults)
        : that.incompleteResults != null)
      return false;
    return repositories != null ? repositories.equals(that.repositories) : that.repositories == null;
  }

  @Override
  public int hashCode() {
    int result = totalCount != null ? totalCount.hashCode() : 0;
    result = 31 * result + (incompleteResults != null ? incompleteResults.hashCode() : 0);
    result = 31 * result + (repositories != null ? repositories.hashCode() : 0);
    return result;
  }
}
