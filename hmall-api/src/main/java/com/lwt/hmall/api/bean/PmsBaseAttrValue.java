package com.lwt.hmall.api.bean;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class PmsBaseAttrValue {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String valueName;
  private Long attrId;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public String getValueName() {
    return valueName;
  }

  public void setValueName(String valueName) {
    this.valueName = valueName;
  }


  public Long getAttrId() {
    return attrId;
  }

  public void setAttrId(Long attrId) {
    this.attrId = attrId;
  }

}
