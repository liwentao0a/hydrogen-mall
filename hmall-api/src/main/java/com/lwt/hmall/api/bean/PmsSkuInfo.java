package com.lwt.hmall.api.bean;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.List;

public class PmsSkuInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long productId;
  private BigDecimal price;
  private String name;
  private String description;
  private Long catalog3Id;
  private String defaultImg;

  @Transient
  private List<PmsSkuImage> skuImages;
  @Transient
  private List<PmsSkuSaleAttrValue> skuSaleAttrValues;
  @Transient
  private List<PmsSkuAttrValue> skuAttrValues;

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


  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public Long getCatalog3Id() {
    return catalog3Id;
  }

  public void setCatalog3Id(Long catalog3Id) {
    this.catalog3Id = catalog3Id;
  }


  public String getDefaultImg() {
    return defaultImg;
  }

  public void setDefaultImg(String defaultImg) {
    this.defaultImg = defaultImg;
  }

  public List<PmsSkuImage> getSkuImages() {
    return skuImages;
  }

  public void setSkuImages(List<PmsSkuImage> skuImages) {
    this.skuImages = skuImages;
  }

  public List<PmsSkuSaleAttrValue> getSkuSaleAttrValues() {
    return skuSaleAttrValues;
  }

  public void setSkuSaleAttrValues(List<PmsSkuSaleAttrValue> skuSaleAttrValues) {
    this.skuSaleAttrValues = skuSaleAttrValues;
  }

  public List<PmsSkuAttrValue> getSkuAttrValues() {
    return skuAttrValues;
  }

  public void setSkuAttrValues(List<PmsSkuAttrValue> skuAttrValues) {
    this.skuAttrValues = skuAttrValues;
  }
}
