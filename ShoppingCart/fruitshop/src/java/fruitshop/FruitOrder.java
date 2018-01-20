package fruitshop;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named("fruitOrder")
public class FruitOrder {
  private String fruitName;
  private int quantity;
  
  @Inject private FruitBasket basket;

  public String getFruitName() {
    return fruitName;
  }

  public void setFruitName(String name) {
    this.fruitName = name;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
  
  public String addToBasket() {
    if(fruitName == null || fruitName.trim().isEmpty()) {
      
      FacesMessage msg = new FacesMessage("Please tell me your fruit name");
      msg.setSeverity(FacesMessage.SEVERITY_ERROR);
      FacesContext.getCurrentInstance()
          .addMessage("fruitForm:fruitName", msg);
      
      return (null);
    }
    
    basket.add(this.getContent());
    
    fruitName = "";
    quantity = 0;
    
    return ("list");
  }
  
  private FruitOrder getContent() {
    FruitOrder o = new FruitOrder();
    o.fruitName = fruitName;
    o.quantity = quantity;
    return (o);
  }
  
  
}
