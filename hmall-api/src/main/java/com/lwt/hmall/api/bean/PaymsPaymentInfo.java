package com.lwt.hmall.api.bean;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

public class PaymsPaymentInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String orderSn;
  private String orderId;
  private String alipayTradeNo;
  private BigDecimal totalAmount;
  private String subject;
  private String paymentStatus;
  private java.sql.Timestamp createTime;
  private java.sql.Timestamp confirmTime;
  private String callbackContent;
  private java.sql.Timestamp callbackTime;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public String getOrderSn() {
    return orderSn;
  }

  public void setOrderSn(String orderSn) {
    this.orderSn = orderSn;
  }


  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }


  public String getAlipayTradeNo() {
    return alipayTradeNo;
  }

  public void setAlipayTradeNo(String alipayTradeNo) {
    this.alipayTradeNo = alipayTradeNo;
  }


  public BigDecimal getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(BigDecimal totalAmount) {
    this.totalAmount = totalAmount;
  }


  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }


  public String getPaymentStatus() {
    return paymentStatus;
  }

  public void setPaymentStatus(String paymentStatus) {
    this.paymentStatus = paymentStatus;
  }


  public java.sql.Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.sql.Timestamp createTime) {
    this.createTime = createTime;
  }


  public java.sql.Timestamp getConfirmTime() {
    return confirmTime;
  }

  public void setConfirmTime(java.sql.Timestamp confirmTime) {
    this.confirmTime = confirmTime;
  }


  public String getCallbackContent() {
    return callbackContent;
  }

  public void setCallbackContent(String callbackContent) {
    this.callbackContent = callbackContent;
  }


  public java.sql.Timestamp getCallbackTime() {
    return callbackTime;
  }

  public void setCallbackTime(java.sql.Timestamp callbackTime) {
    this.callbackTime = callbackTime;
  }

}
