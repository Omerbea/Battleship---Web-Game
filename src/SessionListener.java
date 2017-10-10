//TODO: make package  like : package com.myjavarecipes.web.listener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent event) {
        System.out.println("Session Created");
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        //write your logic
        System.out.println("Session Destroyed");
    }
}