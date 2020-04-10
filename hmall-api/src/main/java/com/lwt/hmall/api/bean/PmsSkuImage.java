package com.lwt.hmall.api.bean;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class PmsSkuImage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long skuId;
  private String imgName;
  private String imgUrl;
  private Long productImgId;
  private Long isDefault;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public Long getSkuId() {
    return skuId;
  }

  public void setSkuId(Long skuId) {
    this.skuId = skuId;
  }


  public String getImgName() {
    return imgName;
  }

  public void setImgName(String imgName) {
    this.imgName = imgName;
  }


  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }


  public Long getProductImgId() {
    return productImgId;
  }

  public void setProductImgId(Long productImgId) {
    this.productImgId = productImgId;
  }


  public Long getIsDefault() {
    return isDefault;
  }

  public void setIsDefault(Long isDefault) {
    this.isDefault = isDefault;
  }

}
