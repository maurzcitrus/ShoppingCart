package warehouse;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;

@ApplicationScoped
public class OrderProcessorSessions {

  private final Lock lock = new ReentrantLock();
  private Map<String, List<Session>> locations = new HashMap<>();
  
  public void add(String location, Session session) {
    List<Session> allSessions = locations.computeIfAbsent(location, s -> new LinkedList<>());
    allSessions.add(session);
  }

  public void notify(String location, String text) {

    locations
        .get(location)
        .stream()
        .forEach(s -> {
          try {
            s.getBasicRemote().sendText(text);
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
  }

  public void lock(Runnable block) {
    lock.lock();
    try {
      block.run();
    } finally {
      lock.unlock();
    }
  }


  void remove(String location, Session session) {

    List<Session> allSessions = locations.computeIfAbsent(location, s -> new LinkedList<>());
    List<Session> remaining = new LinkedList<>();

    for (Session s : allSessions) {
      if (!s.getId().equals(session.getId())) {
        remaining.add(s);
      }
    }

    locations.put(location, remaining);
  }

}
