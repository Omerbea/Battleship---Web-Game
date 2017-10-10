//TODO: make package  like : package com.myjavarecipes.web.listener;
import javax.persistence.Lob;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent event) {
        System.out.println("Session Created");
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        //write your logic
        LobbyManager lobbyManager = (LobbyManager) event.getSession().getAttribute("lobbyManager");
        lobbyManager.removePlayerFromList((String)event.getSession().getAttribute("userName"));
        //event.getSession().invalidate();
        System.out.println("Session Destroyed");
    }
}