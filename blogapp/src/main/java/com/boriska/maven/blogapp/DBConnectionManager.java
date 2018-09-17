/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boriska.maven.blogapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author bk
 */
public class DBConnectionManager {
    private Connection connection;
    
    public DBConnectionManager(String dbURL, String user, String pwd) throws ClassNotFoundException, SQLException{
            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
            this.connection = DriverManager.getConnection(dbURL, user, pwd);
    }

    public Connection getConnection(){
            return this.connection;
    }
}
