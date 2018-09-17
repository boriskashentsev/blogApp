/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boriska.maven.blogapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.boriska.maven.blogapp.Post;

/**
 *
 * @author bk
 */
public class DBWorker {
    String dbTableName;
    Connection connection;
    String columns[] = {"TIMESTAMP", "TITLE", "TEXT"};
    
    public DBWorker(String dbTableName_orig, Connection connection_orig) {
        this.dbTableName = dbTableName_orig;
        this.connection = connection_orig;
        
        this.initializeDB();
    }
    
    private void initializeDB() {
        try {
            ResultSet res = connection.getMetaData().getTables(null, null, dbTableName, null);
            if (!res.next()) {
                PreparedStatement ps = connection.prepareStatement("CREATE TABLE "+dbTableName+" ("+columns[0]+" BIGINT UNIQUE, "+columns[1]+" VARCHAR(256), "+columns[2]+" VARCHAR(4096))");
                ps.executeUpdate();
            }
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
    
    
    public List<Post> listOfPosts() {
        List<Post> result = new ArrayList<Post>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM APP."+dbTableName);
            ResultSet rs = ps.executeQuery();

            if(rs != null) {
                while (rs.next()) {
                    long postTimestamp = rs.getLong(columns[0]);
                    String postTitle = rs.getString(columns[1]);
                    String postText = rs.getString(columns[2]);
                    result.add(new Post(postTimestamp, postTitle, postText));
                }
            }           
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
        return result;
    }
    
    public Post getPost(long timestamp) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM APP."+dbTableName+" WHERE "+columns[0]+"="+timestamp);
            ResultSet rs = ps.executeQuery();
            
            if(rs != null && rs.next()) {
                long postTimestamp = rs.getLong(columns[0]);
                String postTitle = rs.getString(columns[1]);
                String postText = rs.getString(columns[2]);
                return new Post(postTimestamp, postTitle, postText);
            }
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }
    
    public void addPost(String title, String text) {
        try {
            Date date = new Date();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO "+dbTableName+" ("+columns[0]+", "+columns[1]+", "+columns[2]+") VALUES ("+date.getTime()+", '"+title+"', '"+text+"')");
            ps.executeUpdate();
        }
        catch(SQLException e) {
            System.out.println(e.toString());
        }
    }
    
    public void removePost(long timestamp) {
        try {
            Date date = new Date();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM "+dbTableName+"  WHERE "+columns[0]+"="+timestamp);
            ps.executeUpdate();
        }
        catch(SQLException e) {
            System.out.println(e.toString());
        }
    }
}
