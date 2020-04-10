package com.lwt.hmall.api.bean;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class PmsSkuSaleAttrValue {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long skuId;
  private Long saleAttrId;
  private Long productSaleAttrId;
  private Long saleAttrValueId;
  private String saleAttrName;
  private String saleAttrValueName;


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

  public Long getSaleAttrValueId() {
    return saleAttrValueId;
  }

  public void setSaleAttrValueId(Long saleAttrValueId) {
    this.saleAttrValueId = saleAttrValueId;
  }


  public String getSaleAttrName() {
    return saleAttrName;
  }

  public void setSaleAttrName(String saleAttrName) {
    this.saleAttrName = saleAttrName;
  }


  public String getSaleAttrValueName() {
    return saleAttrValueName;
  }

  public void setSaleAttrValueName(String saleAttrValueName) {
    this.saleAttrValueName = saleAttrValueName;
  }

}
