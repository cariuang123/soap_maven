package com.server;


import net.code.UserService;
import net.code.ProductService;

import javax.xml.ws.Endpoint;

public class SOAPServer {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8080/ws/userService", new UserService());
        Endpoint.publish("http://localhost:8080/ws/productService", new ProductService());
        System.out.println("SOAP Web Services are running...");
    }
}
