package com.Servidor.ServidorBanco;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;

import Controllers.ControllerCuenta;
import Controllers.ControllerCuenta;
import Controllers.ServletTransacciones;
import Controllers.ServletUsuario;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        
//brindamo el puerto para nuestro server 
        Server server = new Server(8080);
        server.setHandler(new DefaultHandler());

        ServletContextHandler context = new ServletContextHandler();

      
        context.addServlet(ServletUsuario.class, "/usuario/*");
        context.addServlet(ControllerCuenta.class, "/cuenta/*");
        context.addServlet(ServletTransacciones.class, "/transaccion/*");

        server.setHandler(context);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
