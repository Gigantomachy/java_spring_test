package com.exercises.springmvc;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import com.exercises.springmvc.config.WebConfig;
import jakarta.servlet.ServletRegistration;
import java.io.File;

public class Application {
    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();

        String docBase = new File("src/main/webapp/").getAbsolutePath();
        Context context = tomcat.addContext("", docBase);

        AnnotationConfigWebApplicationContext springContext = new AnnotationConfigWebApplicationContext();
        springContext.register(WebConfig.class);
        
        springContext.setServletContext(context.getServletContext());

        springContext.refresh();

        DispatcherServlet dispatcherServlet = new DispatcherServlet(springContext);
        Tomcat.addServlet(context, "dispatcher", dispatcherServlet);
        context.addServletMappingDecoded("/", "dispatcher");

        System.out.println("starting Tomcat on port 8080...");
        tomcat.start();
        tomcat.getServer().await();
    }
}