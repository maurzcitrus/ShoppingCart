/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nus.iss.ejava.fruitshop.business;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Raghu
 */
@SessionScoped
@Named
public class CustomerBean implements Serializable {

  private String customerName;
  private String address;
  private String pincode;
  private String metrostation;

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPincode() {
    return pincode;
  }

  public void setPincode(String pincode) {
    this.pincode = pincode;
  }

  public String getMetrostation() {
    return metrostation;
  }

  public void setMetrostation(String metrostation) {
    this.metrostation = metrostation;
  }

  public String addAddress() {
    CustomerBean addressBean = new CustomerBean();
    addressBean.setAddress(address);
    addressBean.setCustomerName(customerName);
    addressBean.setMetrostation(metrostation);
    addressBean.setPincode(pincode);

    return ("saveAddress");
  }

  public void resetFail() {
    pincode = null;
    metrostation = null;
    address = null;
    customerName = null;

  }

  public String toString() {
    return String.format("(Your ShippingAddress is %s , %s , %s )",
        this.getCustomerName(),
        this.getAddress(),
        this.getPincode()
    );
  }
}
