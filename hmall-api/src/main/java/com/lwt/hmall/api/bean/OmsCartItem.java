package com.lwt.hmall.api.bean;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

public class OmsCartItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long productId;
  private Long productSkuId;
  private Long userId;
  private Long quantity;
  private BigDecimal price;
  private String productPic;
  private String productName;
  private String productSubTitle;
  private String userNickname;
  private java.sql.Timestamp createDate;
  private java.sql.Timestamp modifyDate;
  private Long deleteStatus;
  private Long productCategoryId;
  private String productSn;
  private String productAttr;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }


  public Long getProductSkuId() {
    return productSkuId;
  }

  public void setProductSkuId(Long productSkuId) {
    this.productSkuId = productSkuId;
  }


  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }


  public Long getQuantity() {
    return quantity;
  }

  public void setQuantity(Long quantity) {
    this.quantity = quantity;
  }


  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }


  public String getProductPic() {
    return productPic;
  }

  public void setProductPic(String productPic) {
    this.productPic = productPic;
  }


  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }


  public String getProductSubTitle() {
    return productSubTitle;
  }

  public void setProductSubTitle(String productSubTitle) {
    this.productSubTitle = productSubTitle;
  }


  public String getUserNickname() {
    return userNickname;
  }

  public void setUserNickname(String userNickname) {
    this.userNickname = userNickname;
  }


  public java.sql.Timestamp getCreateDate() {
    return createDate;
  }

  public void setCreateDate(java.sql.Timestamp createDate) {
    this.createDate = createDate;
  }


  public java.sql.Timestamp getModifyDate() {
    return modifyDate;
  }

  public void setModifyDate(java.sql.Timestamp modifyDate) {
    this.modifyDate = modifyDate;
  }


  public Long getDeleteStatus() {
    return deleteStatus;
  }

  public void setDeleteStatus(Long deleteStatus) {
    this.deleteStatus = deleteStatus;
  }


  public Long getProductCategoryId() {
    return productCategoryId;
  }

  public void setProductCategoryId(Long productCategoryId) {
    this.productCategoryId = productCategoryId;
  }


  public String getProductSn() {
    return productSn;
  }

  public void setProductSn(String productSn) {
    this.productSn = productSn;
  }


  public String getProductAttr() {
    return productAttr;
  }

  public void setProductAttr(String productAttr) {
    this.productAttr = productAttr;
  }

}
