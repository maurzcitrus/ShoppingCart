package warehouse;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@RequestScoped
@ServerEndpoint("/process/{location}")
public class OrderProcessor {

  @Inject
  private OrderProcessorSessions sessions;

  @OnOpen
  public void open(Session newSession, @PathParam(value = "location") String location) {
    System.out.println(">>>connection opened: " + newSession.getId());
    System.out.println(">>>location: " + location);

    sessions.lock(() -> {
      sessions.add(location, newSession);
    });
  }

  @OnClose
  public void close(Session session, @PathParam(value = "location") String location) {
    System.out.println(">>>connection closed: " + session.getId());
    System.out.println(">>>location: " + location);

    sessions.lock(() -> {
      sessions.remove(location, session);
    });
  }

}
