package com.valfed.githubclient.entity;

import java.util.Objects;

public class Owner {
  private final String login;
  private final int id;
  private final String avatarUrl;

  public Owner(String login, int id, String avatarUrl) {
    this.login = login;
    this.id = id;
    this.avatarUrl = avatarUrl;
  }

  public String getLogin() {
    return login;
  }

  public int getId() {
    return id;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Owner owner = (Owner) o;
    return id == owner.id &&
        Objects.equals(login, owner.login) &&
        Objects.equals(avatarUrl, owner.avatarUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(login, id, avatarUrl);
  }

  @Override
  public String toString() {
    return "Owner{" +
        "login='" + login + '\'' +
        ", id=" + id +
        ", avatarUrl='" + avatarUrl + '\'' +
        '}';
  }
}
