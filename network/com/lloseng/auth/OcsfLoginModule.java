package com.lloseng.auth;

import java.util.*;
import java.security.*;
import java.io.IOException;
import javax.security.auth.*;
import javax.security.auth.callback.*;
import javax.security.auth.login.*;
import javax.security.auth.spi.*;

/**
 * The <code>OcsfLoginModule</code> is a login module that 
 * uses an ocsf client to validate username and passwords.
 * It uses the <code>host</code>, <code>port</code>, 
 * <code>timeout</code> options.
 *
 * @author Robert Lagani&egrave;re
 * @version September 2003
 * @see com.lloseng.ocsf.client.AbstractClient
 * @see javax.security.auth.spi.LoginModule
 */
public class OcsfLoginModule implements LoginModule {

    private Subject subject;
    private CallbackHandler callbackHandler;
    private Map sharedState;
    private Map options;

    private boolean loginSucceeded = false;
    private boolean commitSucceeded = false;

    private Principal userPrincipal;
    private LoginClient client;
    private String hostname;
    private int port;
    private int timeout;
    Callback[] callbacks;

    public void initialize(Subject subject, CallbackHandler callbackHandler,
            Map sharedState, Map options) {
 
      this.subject = subject;
      this.callbackHandler = callbackHandler;
      this.sharedState = sharedState;
      this.options = options;
      
      hostname = (String)options.get("host");
      port = Integer.parseInt((String)options.get("port"));
      timeout = Integer.parseInt((String)options.get("timeout"));
      
      client= new LoginClient(this, hostname, port);
      callbacks = new Callback[2];
    }

    public boolean login() throws LoginException {

      if (callbackHandler == null)
        throw new LoginException("Error: no CallbackHandler available");

      // prepare callbacks
      callbacks[0] = new NameCallback("username");
      callbacks[1] = new PasswordCallback("password", false);
 
      try {

        // get and verify the username/password
        userPrincipal= null;
        callbackHandler.handle(callbacks);
        
        obtainPrincipal();
 
      } catch (UnsupportedCallbackException uce) {
        throw new LoginException("Error: " + uce.getCallback().toString());
 
      } catch (Exception ioe) {
        throw new LoginException("Error: " + ioe.toString());
      }
   
      if (userPrincipal != null) {
       
        // authentication succeeded!!!
        loginSucceeded = true;
        return true;
        
      } else {

        loginSucceeded = false;
        
        try {

          callbacks[0] = new TextOutputCallback(TextOutputCallback.ERROR, "Authentication failed!");
          callbacks[1] = null;
          callbackHandler.handle(callbacks);
 
        } catch (UnsupportedCallbackException uce) { 
        } catch (IOException ioe) { }
        
        throw new FailedLoginException("Authentication failed");
      }
    }

    public boolean commit() throws LoginException {
    
      if (loginSucceeded == false) {
      
        return false;
        
      } else {
        // add a Principal (authenticated identity)
        // to the Subject

        if (!subject.getPrincipals().contains(userPrincipal))
          subject.getPrincipals().add(userPrincipal);

        commitSucceeded = true;
        return true;
      }
    }

    public boolean abort() throws LoginException {
    
      if (loginSucceeded == false) {
      
        return false;
        
      } else if (loginSucceeded == true && commitSucceeded == false) {

        loginSucceeded = false;
        userPrincipal = null;
        
      } else {

        logout();
      }
      
      return true;
    }

    public boolean logout() throws LoginException {

      subject.getPrincipals().remove(userPrincipal);
      loginSucceeded = false;
      userPrincipal = null;
        
      return true;
    }
    
    synchronized void setPrincipal(Principal p) {
    
      if (client.isConnected()) {        
      
        userPrincipal= p;
      }
        
      notifyAll();
    }
    
    private synchronized void obtainPrincipal() throws IOException, InterruptedException {
    
      client.openConnection();
      client.sendToServer(callbacks);
        
      wait(timeout);
      client.closeConnection();
    }
}

class LoginClient extends com.lloseng.ocsf.client.AbstractClient {

  private OcsfLoginModule module;

  public LoginClient(OcsfLoginModule module, String host, int port) {

    super(host,port);  
    this.module= module;
  }
   
  protected void handleMessageFromServer(Object msg) {

    if (msg instanceof Principal) {
    
      module.setPrincipal((Principal)msg);
      
    } else {
    
      module.setPrincipal(null);
    }
  }
}
