package com.valfed.githubclient.entity;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity
public class Repository {
  @PrimaryKey
  private final int id;

  private final String name;
  private final String description;

  @SerializedName("created_at")
  private final String createdAt;

  @SerializedName("updated_at")
  private final String updatedAt;

  @SerializedName("stargazers_count")
  private final int stargazersCount;
  private final String language;

  @SerializedName("forks_count")
  private final int forksCount;

  @Embedded(prefix = "owner_")
  private final Owner owner;

  public Repository(int id,
                    String name,
                    String description,
                    String createdAt,
                    String updatedAt,
                    int stargazersCount,
                    String language,
                    int forksCount,
                    Owner owner) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.stargazersCount = stargazersCount;
    this.language = language;
    this.forksCount = forksCount;
    this.owner = owner;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public int getStargazersCount() {
    return stargazersCount;
  }

  public String getLanguage() {
    return language;
  }

  public int getForksCount() {
    return forksCount;
  }

  public Owner getOwner() {
    return owner;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Repository that = (Repository) o;
    return id == that.id &&
        stargazersCount == that.stargazersCount &&
        forksCount == that.forksCount &&
        Objects.equals(name, that.name) &&
        Objects.equals(description, that.description) &&
        Objects.equals(createdAt, that.createdAt) &&
        Objects.equals(updatedAt, that.updatedAt) &&
        Objects.equals(language, that.language) &&
        Objects.equals(owner, that.owner);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id,
        name,
        description,
        createdAt,
        updatedAt,
        stargazersCount,
        language,
        forksCount,
        owner);
  }

  @Override
  public String toString() {
    return "Repository{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", createdAt='" + createdAt + '\'' +
        ", updatedAt='" + updatedAt + '\'' +
        ", stargazersCount=" + stargazersCount +
        ", language='" + language + '\'' +
        ", forksCount=" + forksCount +
        ", owner=" + owner +
        '}';
  }
}
