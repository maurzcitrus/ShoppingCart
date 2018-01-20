package nus.iss.ejava.fruitshop.web;

import fruitshop.FruitBasket;
import fruitshop.FruitOrder;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import nus.iss.ejava.fruitshop.business.FruitBean;
import nus.iss.ejava.fruitshop.model.Fruit;

@SessionScoped
@Named
public class shoppingPageView implements Serializable {

  @Inject
  private FruitOrder myFruit;
  
  @Inject
  private FruitBasket basket;

  @EJB
  private FruitBean fruitBean;

  private Fruit fruit;

  private List<Fruit> selectedFruit;

  private String fruitIdFromView;
  
  public int getCartSize() {
    return basket.getBasketSize();
  }

  public List<Fruit> getSelectedFruit() {
    return selectedFruit;
  }

  public void setSelectedFruit(List<Fruit> selectedFruit) {
    this.selectedFruit = selectedFruit;
  }

  public Fruit getFruit() {
    return fruit;
  }

  public void setFruit(Fruit fruit) {
    this.fruit = fruit;
  }

  public String getFruitIdFromView() {
    return fruitIdFromView;
  }

  public void setFruitIdFromView(String fruitIdFromView) {
    this.fruitIdFromView = fruitIdFromView;
  }

  public List<Fruit> listFruits() {
    return (fruitBean.findAllFruits());
  }

  public String addToCart(int id) {
    System.out.println(" >> quantity: " + myFruit.getQuantity());
    System.out.println(" >> name: " + myFruit.getFruitName());
    
    String val = FacesContext.getCurrentInstance()
        .getExternalContext()
        .getRequestParameterMap()
        .get("hiddenFruitId");
    
    int fruitId = Integer.parseInt(val);
    
    System.out.println(" >> fruit id: " + fruitId);
    
    for(Fruit f : listFruits()) {
      if(f.getFruitid() == fruitId) {
        myFruit.setFruitName(f.getFruitName());
        myFruit.addToBasket();
        break;
      }
    }
    
    myFruit.setFruitName("");
    myFruit.setQuantity(0);
    
//        List<Fruit> sendForCheckout = null;
//        if (fruit.getQuantity() != 0) {
//            for (Fruit item : getSelectedFruit()) {
//                    sendForCheckout.add(item);
//            }
//        } else {
//            FacesMessage msg = new FacesMessage("Fruit Not Available, We will add Soon");
//            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
//            FacesContext.getCurrentInstance()
//                    .addMessage("shoppingPageView:quantity", msg);
//        }
//        return (sendForCheckout);

    return ("shoppingPageView");
  }

}
