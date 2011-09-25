package com.lloseng.auth;

import java.util.Observable;
import java.util.Observer;
import com.lloseng.ocsf.server.*;
import javax.security.auth.callback.*;
import javax.security.auth.*;
import java.security.*;
import javax.security.auth.login.*;
import java.io.*;

/**
 * The <code>AuthentificationServer</code> class authentifies
 * a user in the context of a client/server application. It
 * uses the information saved in a user database. It observes
 * an observable server and react to messages made of an array
 * of two callbacks.<p>
 *
 * @author Robert Lagani&egrave;re
 * @version September 2003
 * @see com.lloseng.auth.UserDB
 * @see com.lloseng.ocsf.server.ObservableServer
 * @see javax.security.auth.callback.Callback
 */
public class AuthentificationServer implements Observer
{
  private ObservableServer server;
  private UserDB userDB;

  /**
   * Construit un nouveau AuthentificationServer.
   *
   * @param server le serveur observe.
   */
  public AuthentificationServer(ObservableServer server, String dbName) throws IOException
  {
    this.server= server;
    userDB= new UserDB(dbName,true);
    userDB.display();
  }

  /**
   * Traite les messages.
   *
   * @param obs l'objet observe.
   * @param message le message.
   */
  public void update(Observable obs, Object message) {

    if (message instanceof OriginatorMessage) {

      OriginatorMessage om= (OriginatorMessage)message;

      message= om.getMessage();
      ConnectionToClient cc= om.getOriginator();

      if (message instanceof Callback[]) {  

        Callback[] callbacks= (Callback[])message;

        Principal p= null;
        
        if (callbacks.length == 2 && callbacks[0] instanceof NameCallback
                                  && callbacks[1] instanceof PasswordCallback) {

          p= userDB.getPrincipal(((NameCallback)callbacks[0]).getName(),
                                   new String(((PasswordCallback)callbacks[1]).getPassword()));
        }
        
        try {
        
          if (p!=null) {
        
            cc.sendToClient(p);
          
          } else {
        
            cc.sendToClient(new Boolean(false));
          }
 
        } catch (Exception ex) { }

        if (p==null) {

          System.out.println("Login failed.");
        
        } else {
        
          System.out.println("Login of " + p);
        }
      }
    }
  }
}
