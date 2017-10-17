//TODO: make package  like : package com.myjavarecipes.web.listener;
import javax.persistence.Lob;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        java.util.Date date = new java.util.Date();
        System.out.println(date+ "   Session Created");
    }
    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        //write your logic
        java.util.Date date = new java.util.Date();
        System.out.println(date + "   Destroy session..");
        String userName =(String) event.getSession().getAttribute("userName");
        LobbyManager lobbyManager = (LobbyManager) event.getSession().getServletContext().getAttribute("lobbyManager");
        if (lobbyManager == null){
            System.out.println("Warnnig: at sessionDestroyed lobbyManager is null");
            return;

        }
        lobbyManager.removePlayerFromList(userName);
        event.getSession().invalidate();
        System.out.println("Session Destroyed");
    }
}