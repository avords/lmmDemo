package com.lmm.jettDemo;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Created by Administrator on 2018/2/8.
 */
public class JettyServer {

    public static void main(String[] args) throws Exception {
        Server server= new Server(8080);

        ServletContextHandler context= new ServletContextHandler(ServletContextHandler.SESSIONS );
        context.setContextPath( "/");

        server.setHandler( context);

        context.addServlet( new ServletHolder( new LoginServlet()),"/here");

        server.start();
        server.join();
    }

}
