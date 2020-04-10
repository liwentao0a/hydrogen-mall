package com.lwt.hmall.api.bean;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

public class PmsBaseCatalog1 {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;

  @Transient
  private List<PmsBaseCatalog2> catalog2s;


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

  public List<PmsBaseCatalog2> getCatalog2s() {
    return catalog2s;
  }

  public void setCatalog2s(List<PmsBaseCatalog2> catalog2s) {
    this.catalog2s = catalog2s;
  }
}
