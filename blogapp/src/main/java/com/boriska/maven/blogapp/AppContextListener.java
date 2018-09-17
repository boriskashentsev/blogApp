/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boriska.maven.blogapp;

import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.boriska.maven.blogapp.DBConnectionManager;
import java.sql.Connection;

/**
 * Web application lifecycle listener.
 *
 * @author bk
 */
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        
        //initialize DB Connection
    	String dbURL = ctx.getInitParameter("dbURL");
    	String user = ctx.getInitParameter("dbUser");
    	String pwd = ctx.getInitParameter("dbPassword");
        
        try {
            DBConnectionManager connectionManager = new DBConnectionManager(dbURL, user, pwd);
            ctx.setAttribute("DBConnection", connectionManager.getConnection());
            String dbTableName = ctx.getInitParameter("dbTableName");
            DBWorker dbWorker = new DBWorker(dbTableName, connectionManager.getConnection());
            ctx.setAttribute("DBWorker", dbWorker);
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
                Connection con = (Connection) sce.getServletContext().getAttribute("DBConnection");
    	try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
