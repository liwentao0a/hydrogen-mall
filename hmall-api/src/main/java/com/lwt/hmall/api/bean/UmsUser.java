package com.lwt.hmall.api.bean;


import com.lwt.hmall.api.group.UmsUserGroup;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.*;
import java.util.List;

public class UmsUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Null(groups = {UmsUserGroup.RegisteredUser.class})
  private Long id;
  @NotBlank(groups = {UmsUserGroup.RegisteredUser.class})
  private String username;
  @NotBlank(groups = {UmsUserGroup.RegisteredUser.class})
  private String password;
  @Null(groups = {UmsUserGroup.RegisteredUser.class})
  private Long roleLevel;
  @NotBlank(groups = {UmsUserGroup.RegisteredUser.class})
  private String nickname;
  @NotNull(groups = {UmsUserGroup.RegisteredUser.class})
  @Max(value = 1,groups = {UmsUserGroup.RegisteredUser.class})
  @Min(value = 0,groups = {UmsUserGroup.RegisteredUser.class})
  private Long gender;
  @NotBlank(groups = {UmsUserGroup.RegisteredUser.class})
  private String avatarUrl;
  @NotBlank(groups = {UmsUserGroup.RegisteredUser.class})
  private String phone;
  @NotBlank(groups = {UmsUserGroup.RegisteredUser.class})
  private String email;
  @Null(groups = {UmsUserGroup.RegisteredUser.class})
  private Long status;

  @Transient
  private List<UmsUserReceiveAddress> userReceiveAddresses;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public Long getRoleLevel() {
    return roleLevel;
  }

  public void setRoleLevel(Long roleLevel) {
    this.roleLevel = roleLevel;
  }


  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }


  public Long getGender() {
    return gender;
  }

  public void setGender(Long gender) {
    this.gender = gender;
  }


  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public Long getStatus() {
    return status;
  }

  public void setStatus(Long status) {
    this.status = status;
  }

  public List<UmsUserReceiveAddress> getUserReceiveAddresses() {
    return userReceiveAddresses;
  }

  public void setUserReceiveAddresses(List<UmsUserReceiveAddress> userReceiveAddresses) {
    this.userReceiveAddresses = userReceiveAddresses;
  }
}
