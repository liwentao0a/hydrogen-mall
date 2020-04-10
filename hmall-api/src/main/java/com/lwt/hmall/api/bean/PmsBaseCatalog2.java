package com.lwt.hmall.api.bean;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

public class PmsBaseCatalog2 {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private Long catalog1Id;

  @Transient
  private List<PmsBaseCatalog3> catalog3s;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public Long getCatalog1Id() {
    return catalog1Id;
  }

  public void setCatalog1Id(Long catalog1Id) {
    this.catalog1Id = catalog1Id;
  }

  public List<PmsBaseCatalog3> getCatalog3s() {
    return catalog3s;
  }

  public void setCatalog3s(List<PmsBaseCatalog3> catalog3s) {
    this.catalog3s = catalog3s;
  }
}
