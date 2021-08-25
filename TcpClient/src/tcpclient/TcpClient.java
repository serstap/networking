package tcpclient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class TcpClient {
    private static final int PORT = 11_240;
    
    public static void main(String[] args) throws Exception {
        /*if(args.length < 1) {
            System.out.println("Usage: TcpClient <tcp-server-address>");
            return;
        }*/
        
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a message: ");
        String msg = sc.nextLine();
        
        /*try(Socket sock = new Socket(args[0], PORT)) {
            byte[] buf;
            try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
                   ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(msg);
                buf = baos.toByteArray();
            }
            sock.getOutputStream().write(buf);
            
            buf = new byte[1_024];
            int n = sock.getInputStream().read(buf);
            Date d;
            try(ObjectInputStream ois = new ObjectInputStream(
                    new ByteArrayInputStream(buf, 0, n))) {
                d = (Date) ois.readObject();
            }
            
            System.out.println(d);
        }*/
        
        try(Socket sock = new Socket(PORT);
                ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
                ) {
            oos.writeObject(msg);
            System.out.println(ois.readObject());
        }
    }
}
