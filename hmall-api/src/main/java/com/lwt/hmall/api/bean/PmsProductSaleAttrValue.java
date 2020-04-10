package com.lwt.hmall.api.bean;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class PmsProductSaleAttrValue {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long productId;
  private Long saleAttrId;
  private Long productSaleAttrId;
  private String saleAttrValueName;


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

  public Long getSaleAttrId() {
    return saleAttrId;
  }

  public void setSaleAttrId(Long saleAttrId) {
    this.saleAttrId = saleAttrId;
  }

  public Long getProductSaleAttrId() {
    return productSaleAttrId;
  }

  public void setProductSaleAttrId(Long productSaleAttrId) {
    this.productSaleAttrId = productSaleAttrId;
  }

  public String getSaleAttrValueName() {
    return saleAttrValueName;
  }

  public void setSaleAttrValueName(String saleAttrValueName) {
    this.saleAttrValueName = saleAttrValueName;
  }

}
