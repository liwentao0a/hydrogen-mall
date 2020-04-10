package com.lwt.hmall.api.bean;


import com.lwt.hmall.api.group.UmsUserReceiveAddressGroup;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class UmsUserReceiveAddress {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Null(groups = {UmsUserReceiveAddressGroup.SaveUserReceiveAddress.class,
          UmsUserReceiveAddressGroup.SaveUserReceiveAddressNotUserId.class})
  private Long id;

  @NotNull(groups = UmsUserReceiveAddressGroup.SaveUserReceiveAddress.class)
  @Null(groups = UmsUserReceiveAddressGroup.SaveUserReceiveAddressNotUserId.class)
  private Long userId;

  @NotBlank(groups = {UmsUserReceiveAddressGroup.SaveUserReceiveAddress.class,
          UmsUserReceiveAddressGroup.SaveUserReceiveAddressNotUserId.class})
  private String name;

  @NotBlank(groups = {UmsUserReceiveAddressGroup.SaveUserReceiveAddress.class,
          UmsUserReceiveAddressGroup.SaveUserReceiveAddressNotUserId.class})
  private String phone;

  @NotNull(groups = {UmsUserReceiveAddressGroup.SaveUserReceiveAddress.class,
          UmsUserReceiveAddressGroup.SaveUserReceiveAddressNotUserId.class})
  private Long defaultStatus;

  private String postCode;

  @NotBlank(groups = {UmsUserReceiveAddressGroup.SaveUserReceiveAddress.class,
          UmsUserReceiveAddressGroup.SaveUserReceiveAddressNotUserId.class})
  private String province;

  @NotBlank(groups = {UmsUserReceiveAddressGroup.SaveUserReceiveAddress.class,
          UmsUserReceiveAddressGroup.SaveUserReceiveAddressNotUserId.class})
  private String city;

  @NotBlank(groups = {UmsUserReceiveAddressGroup.SaveUserReceiveAddress.class,
          UmsUserReceiveAddressGroup.SaveUserReceiveAddressNotUserId.class})
  private String region;

  @NotBlank(groups = {UmsUserReceiveAddressGroup.SaveUserReceiveAddress.class,
          UmsUserReceiveAddressGroup.SaveUserReceiveAddressNotUserId.class})
  private String detailAddress;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


  public Long getDefaultStatus() {
    return defaultStatus;
  }

  public void setDefaultStatus(Long defaultStatus) {
    this.defaultStatus = defaultStatus;
  }


  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }


  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }


  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }


  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }


  public String getDetailAddress() {
    return detailAddress;
  }

  public void setDetailAddress(String detailAddress) {
    this.detailAddress = detailAddress;
  }

}
