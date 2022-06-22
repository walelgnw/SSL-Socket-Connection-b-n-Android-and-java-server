
package server;

import java.io.IOException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class SocketListener {
    /**
     * main - listen a specific port. When receiving socket, start a new thread
     * to process data so that the program can process multiple socket at the
     * same time
     */
    public static void main(String[] args) {
        if (System.getProperty("javax.net.ssl.keyStore") == null || System.getProperty("javax.net.ssl.keyStorePassword") == null) {
            // set keystore store location
            System.setProperty("javax.net.ssl.keyStore", "sslserverkeys");
            System.setProperty("javax.net.ssl.keyStorePassword", "keystore");
        }
        // create socket
        SSLServerSocket sslserversocket = null;
        SSLSocket sslsocket = null;
        // create a listener on port 9999
        try {
            SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            sslserversocket = (SSLServerSocket) 
            sslserversocketfactory.createServerSocket(9999);
                System.out.println("server started at port 9999");
            while (true) {
                // blocks the program when no socket floats in
                sslsocket = (SSLSocket) sslserversocket.accept();
                System.out.println("sslsocket:" + sslsocket);
                // assign a handler to process data
                new SocketHandler(sslsocket);
            }
        } catch (Exception e) {
            try {
                if(sslsocket != null){
                    sslsocket.close();
                }else{
                    System.out.println("sslsocket: was empty");
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            System.out.println("sslsocket: "+e.getMessage());
        }
    }
}
