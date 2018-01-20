package fruitshop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.servlet.http.HttpSession;

@SessionScoped
@Named
public class FruitBasket implements Serializable {

  @Resource(lookup = "jms/factory")
  private ConnectionFactory factory;

  @Resource(lookup = "jms/fruitWarehouseCommonQueue")
  private Queue warehouseQueue;

  private List<FruitOrder> fruits = new ArrayList<>();

  private String location;
  
  private String name;
  
  private String comment;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  
  
  public List<FruitOrder> getFruits() {
    return fruits;
  }

  public void setFruits(List<FruitOrder> fruits) {
    this.fruits = fruits;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }
  
  public int getBasketSize() {
    return fruits.size();
  }

  public void add(FruitOrder fruit) {
    fruits.add(fruit);
  }

  public void remove(String fruitName) {
    List<FruitOrder> updated = new ArrayList<>();
    for (FruitOrder f : fruits) {
      if (!f.getFruitName().equalsIgnoreCase(fruitName)) {
        updated.add(f);
      }
    }

    fruits = updated;
  }

  public String checkOut() {
    try (JMSContext jmsCtx = factory.createContext()) {
      JMSProducer producer = jmsCtx.createProducer();
      TextMessage txtMsg = jmsCtx.createTextMessage();
      txtMsg.setText(toJson());
      txtMsg.setStringProperty("location", getLocation());
      producer.send(warehouseQueue, txtMsg);

    } catch (JMSException ex) {
      ex.printStackTrace();
    }

    FacesContext fc = FacesContext.getCurrentInstance();
    ExternalContext extCtx = fc.getExternalContext();

    HttpSession sess = (HttpSession) extCtx.getSession(false);

    sess.invalidate();

    fruits = new ArrayList<>();

    return ("shoppingpage");
  }

  private String toJson() {

    JsonArrayBuilder items = Json
        .createArrayBuilder();

    for (FruitOrder fruit : fruits) {

      JsonObject item = Json
          .createObjectBuilder()
          .add("item", fruit.getFruitName())
          .add("quantity", fruit.getQuantity())
          .build();

      items.add(item);
    }

    JsonObject data = Json
        .createObjectBuilder()
        .add("name", getName())
        .add("address", getLocation())
        .add("comment", getComment()) 
        .add("cart", items)
        .build();

    return data.toString();
  }

}
