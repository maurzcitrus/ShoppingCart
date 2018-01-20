package warehouse;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(mappedName = "jms/fruitWarehouseCommonQueue",
    activationConfig = {
      @ActivationConfigProperty(
          propertyName = "destinationType",
          propertyValue = "javax.jms.Queue")
    }
)
public class FruitWarehouseCommonBean implements MessageListener {

  @Inject
  private OrderProcessorSessions sessions;

  @Override
  public void onMessage(Message message) {
    System.out.println("... received at Fruit Warehouse Common Queue");

    try {
      TextMessage text = (TextMessage) message;
      String location = text.getStringProperty("location");
      String data = text.getText();

      System.out.println("\t" + data);

      sessions.notify(location, data);

    } catch (JMSException e) {
      e.printStackTrace();
    }

  }

}
