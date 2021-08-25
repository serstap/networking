package udpserver;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Date;

public class UdpServer {
    private static final int PORT = 11_240;
    
    public static void main(String[] args) throws Exception {
        try(DatagramSocket sock = new DatagramSocket(PORT)) {
            while(true) {
                byte[] buf = new byte[1_024];
                DatagramPacket pack = new DatagramPacket(buf, buf.length);
                sock.receive(pack);

                Date d = new Date();
                String s;
                try(ObjectInputStream ois = new ObjectInputStream(
                        new ByteArrayInputStream(buf, 0, pack.getLength()))) {
                    s = (String) ois.readObject();
                }

                System.out.println(d);
                System.out.println(s);
                System.out.println(pack.getAddress());

                byte[] buf2;
                try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                    oos.writeObject(d);
                    buf2 = baos.toByteArray();
                }

                DatagramPacket outPack = new DatagramPacket(buf2, buf2.length,
                    pack.getAddress(), pack.getPort());
                sock.send(outPack);
                
                System.out.println();
            }
        }
    }
}
