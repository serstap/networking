package tcpserver;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class TcpServer {
    private static final int PORT = 11_240;
    
    public static void main(String[] args) throws Exception {
        try(ServerSocket srvSock = new ServerSocket(PORT)) {
            while(true) {
                /*try(Socket sock = srvSock.accept()) {
                    Date d = new Date();
                    
                    byte[] buf = new byte[1024];
                    int n = sock.getInputStream().read(buf);
                    String msg;
                    try(ObjectInputStream ois = new ObjectInputStream(
                            new ByteArrayInputStream(buf, 0, n))) {
                        msg = (String) ois.readObject();
                    }
                    
                    System.out.println(d);
                    System.out.println(msg);
                    System.out.println(sock.getInetAddress());
                    System.out.println();

                    try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                        oos.writeObject(d);
                        buf = baos.toByteArray();
                    }
                    
                    sock.getOutputStream().write(buf);
                }*/
                System.out.println("wait");
                try(Socket sock = srvSock.accept();

                        ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
                        ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream())) {

                    Date d = new Date();
                    System.out.println(d);
                    System.out.println(ois.readObject());
                    System.out.println(sock.getInetAddress());
                    
                    oos.writeObject(d);
                } catch(Exception e)
                {}
                System.out.println("disconnected");
                System.out.println();
            }
        }
    }
}
