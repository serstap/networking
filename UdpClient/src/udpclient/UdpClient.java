package udpclient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;
import java.util.Scanner;

public class UdpClient {
    private static final int PORT = 11_240;
    
    public static void main(String[] args) throws Exception {
        if(args.length < 1) {
            System.out.println("Usage: UdpClient <udp-server-address>");
            return;
        }
        
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a message: ");
        String msg = sc.nextLine();
        
        try(DatagramSocket sock = new DatagramSocket()) {
            byte[] buf;
            try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(msg);
                buf = baos.toByteArray();
            }
            
            DatagramPacket pack = new DatagramPacket(buf, buf.length, 
                    InetAddress.getByName(args[0]), PORT);
            sock.send(pack);
            
            buf = new byte[1_024];
            pack = new DatagramPacket(buf, buf.length);
            sock.receive(pack);
            
            Date d;
            try(ObjectInputStream ois = new ObjectInputStream(
                    new ByteArrayInputStream(buf, 0, pack.getLength()))) {
                d = (Date) ois.readObject();
            }
            
            System.out.println(d);
        }
    }
}
