/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boriska.maven.blogapp;

/**
 *
 * @author bk
 */
public class Post {
    private long timestamp;
    private String title;
    private String text;
    
    public Post(long ts, String ttl, String txt) {
        this.timestamp = ts;
        this.title = ttl;
        this.text = txt;
    }
    
    public void setTimestamp(long ts) {
        this.timestamp = ts;
    }
    
    public void setTitle(String ttl) {
        this.title = ttl;
    }
    
    public void setText(String txt) {
        this.text = txt;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getText() {
        return text;
    }
    
    public String toString() {
        return "   timestamp="+this.timestamp+ " , title=" +this.title +" , text="+ this.text;
    }
}
