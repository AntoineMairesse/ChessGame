package com.company;
import javax.swing.event.EventListenerList;
import java.io.Serializable;

public class AutreEventNotifieur implements Serializable {
  private EventListenerList listenerList = new EventListenerList();

  public void addAutreEventListener(AutreEventListener listener) {
    listenerList.add(AutreEventListener.class, listener);
  }
  public void removeAutreEventListener(AutreEventListener listener) {
    listenerList.remove(AutreEventListener.class, listener);
  }
  public void diffuserAutreEvent(AutreEvent evt) {
    Object[] listeners = listenerList.getListenerList();
    for (int i = 0; i < listeners.length; i = i+2) {
      if (listeners[i] == AutreEventListener.class) {
        ((AutreEventListener) listeners[i+1]).actionADeclancher(evt);
      }
    }
  }
}
