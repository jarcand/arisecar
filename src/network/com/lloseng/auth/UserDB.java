package com.lloseng.auth;

import java.io.*;
import java.security.*;
import java.util.*;

/**
 * The <code>UserDB</code> class saves information concerning
 * the users of a system. It uses a username and a password to
 * identify each user. The information concerning a user must be
 * contained in an object that must implement the <code>Principal</code>
 * interface. This class creates two binary files to manage the user
 * database.<p>
 *
 * @author Robert Lagani&egrave;re
 * @version August 2003
 * @see java.security.Principal
 */
public class UserDB {

  /**
   * The map that contains the username/password relation.
   */
    private Map passwordMap;

  /**
   * The list of user Object.
   */
    private List principals;

  /**
   * The name of the two files to be created.
   */
    private String name;
    
   /**
   * The extensions of the two files to be created.
   */
    private final String DB_EXT = ".db";
    private final String PWD_EXT = ".pwd";
 
   /**
    * Constructs a user database object.
    *
    * @param  name  name of the files to be created.
    * @param  createIfNotExist if true, the files are created if they do not exist.
    */
    public UserDB(String name, boolean createIfNotExist) throws IOException {
    
      this.name= name;
      
      try {
      
        ObjectInputStream oi= new ObjectInputStream(new FileInputStream(name+PWD_EXT));    
        passwordMap= (Map)oi.readObject();
        oi.close();        

        oi= new ObjectInputStream(new FileInputStream(name+DB_EXT));    
        principals= (List)oi.readObject();
        oi.close();        
       
      } catch (FileNotFoundException ex) {
      
        if (createIfNotExist) {
        
          passwordMap= new HashMap();
          principals= new ArrayList();
          
        } else {
        
          throw ex;
        }
        
      } catch (ClassNotFoundException ex) {
      
        throw new IOException(ex.toString());
      }
    }

   /**
    * Constructs a user database object. Equivalent to UserDB(name,false).
    *
    * @param  name  name of the files to be created.
    */
    public UserDB(String name) throws IOException {

      this(name, false);      
    }


   /**
    * Saves all changes made to the database.
    *
    */
    public void commit() throws IOException {
    
      ObjectOutputStream oo= new ObjectOutputStream(new FileOutputStream(name+PWD_EXT));    
      oo.writeObject(passwordMap);
      oo.close();

      oo= new ObjectOutputStream(new FileOutputStream(name+DB_EXT));    
      oo.writeObject(principals);
      oo.close();
    }
    
   /**
    * Adds a new user to the database.
    *
    * @param p   The object containing user information.
    * @param password   The user's password.
    * @return false if username already exists.
    */
    public boolean addPrincipal(Principal p, String password) {
    
      if (passwordMap.containsKey(p.getName()))
        return false;
        
      passwordMap.put(p.getName(), password);
      principals.add(p);
      
      return true;
    }
    
   /**
    * Gets the object corresponding to the specified username and password.
    *
    * @param username   The user's username.
    * @param password   The user's password.
    * @return The object containing user information. It returns null
    * if unexisting username or wrong password is provided.
    */
    public Principal getPrincipal(String username, String password) {
    
      String pwd= (String)passwordMap.get(username);
      
      if (pwd!=null && pwd.equals(password)) {
            
        Iterator it= principals.iterator();
        while(it.hasNext()) {
        
          Principal p= (Principal)it.next();
          if (p.getName().equals(username)) {
          
            return p;
          }
        }        
      }
      
      return null;
    }
    
   /**
    * Returns the list of usernames in the database.
    *
    * @return An iterator to the list of usernames.
    */
    public Iterator getNames() {
    
      return passwordMap.keySet().iterator();      
    }    
    
   /**
    * Displays the list of users in the database.
    *
    */
    public void display() {
    
      Iterator it= getNames();
      
      while (it.hasNext()) {
      
        System.out.println(it.next().toString());
      }
    }        
}
