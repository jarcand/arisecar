package com.lloseng.auth;

import java.io.*;
import java.util.*;
import javax.security.auth.login.*;
import javax.security.auth.*;
import javax.security.auth.callback.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The <code>AWTCallbackHandler</code> is a module that interact
 * with a user through a simple AWT window. It asks for a
 * username and a password.
 *
 * @author Robert Lagani&egrave;re
 * @version September 2003
 * @see javax.security.auth.callback.CallbackHandler;
 */
public class AWTCallbackHandler implements CallbackHandler {

    private LoginWindow window;
    private LoginDialog dialog = null;
    
    public AWTCallbackHandler() {
    
        window= new LoginWindow(this);
    }
    
    public void handle(Callback[] callbacks)
      throws IOException, UnsupportedCallbackException {
      
      NameCallback nc= null;
      PasswordCallback pc= null;
      
      for (int i = 0; i < callbacks.length; i++) {
      
        if (callbacks[i] instanceof TextOutputCallback) {
      
          TextOutputCallback toc = (TextOutputCallback)callbacks[i];
          
          if (dialog == null) {
            dialog= new LoginDialog(window);
          }
          
          dialog.setText(toc.getMessage());
          dialog.show();
 
        } else if (callbacks[i] instanceof NameCallback) {
  
          nc = (NameCallback)callbacks[i];
  
        } else if (callbacks[i] instanceof PasswordCallback) {
  
          pc = (PasswordCallback)callbacks[i];
  
        } else {
          throw new UnsupportedCallbackException
            (callbacks[i], "Unrecognized Callback");
        }
      }
      
      if (nc!=null && pc!=null) {
      
        display();
        nc.setName(window.getUsername());
        pc.setPassword(window.getPassword().toCharArray());
      }
    }
    
    synchronized void display() {
    
      window.setVisible(true);
      
      try {
        wait();
      } catch (Exception e) { }
    }
      
    synchronized void returnInfo() {
    
      window.setVisible(false);
      notifyAll();
    }
      
    protected void finalize() {
      
      if (dialog != null) {
        
        dialog.dispose();
      }
      window.dispose();
    }
}

class LoginWindow extends Frame {

    private Button login;
    private TextField username;
    private TextField password;
    private AWTCallbackHandler handler;

    public LoginWindow(AWTCallbackHandler h) {
    
        super("Login window");
        
        handler= h;
        
        login= new Button("Login");
        username= new TextField();
        password= new TextField();
        password.setEchoChar('*');
        
        addWindowListener(new WindowAdapter() {
          public void windowClosing(WindowEvent e)
          {
            System.exit(0);
          }
        });

        login.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e)
          {
            login();
          }
        });


        setLayout(new BorderLayout(2,2));
        
        Panel p= new Panel();
        p.setLayout(new BorderLayout(2,2));

        Panel p1= new Panel();
        p1.setLayout(new GridLayout(2,1));
        Panel p2= new Panel();
        p2.setLayout(new GridLayout(2,1));
        p1.add(new Label("Username:",Label.RIGHT));
        p2.add(username);
        p1.add(new Label("Password:",Label.RIGHT));
        p2.add(password);
        
        p.add(p1,"West");
        p.add(p2,"Center");
        
        add(p,"North");
        add(login,"Center");
        
        setSize(250,100);
        setVisible(false);
    }
    
    public String getUsername() {
    
      return username.getText();
    }
    
    public String getPassword() {
    
      return password.getText();
    }
    
    public void login() {
    
      handler.returnInfo();
    }
}
    
class LoginDialog extends Dialog {

    private Label message;
    private Button close;

    public LoginDialog(Frame owner) {
    
        super(owner, "Login Info", true);

        close= new Button("Close");
        message= new Label("!",Label.CENTER);        
        
        addWindowListener(new WindowAdapter() {
          public void windowClosing(WindowEvent e)
          {
            hide();
          }
        });

        close.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e)
          {
            hide();
          }
        });
        
        add(message);
        setSize(200,100);
    }    
    
    public void setText(String text) {
    
      message.setText(text);
    }
}
