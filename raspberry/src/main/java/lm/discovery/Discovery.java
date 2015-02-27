package lm.discovery;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class Discovery {

    private final int timeout;

    public static final byte[] MESSAGE = "DISCOVERY_REQUEST".getBytes();

    public Discovery(int timeout) {
        this.timeout = timeout;
    }

    private void send(DatagramSocket socket, InetAddress address, byte[] data) throws IOException {
        DatagramPacket sendPacket = new DatagramPacket(data, data.length, address, 8888);
        socket.send(sendPacket);
    }

    public InetAddress discover() throws IOException {
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(timeout);
        socket.setBroadcast(true);

        send(socket, InetAddress.getByName("255.255.255.255"), MESSAGE);

        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();

            if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                continue;
            }

            for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                InetAddress broadcast = interfaceAddress.getBroadcast();
                if (broadcast == null) {
                    continue;
                }

                send(socket, broadcast, MESSAGE);
            }
        }

        byte[] recvBuf = new byte[15000];
        DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
        socket.receive(receivePacket);


        String message = new String(receivePacket.getData()).trim();

        if (message.equals("DISCOVERY_RESPONSE")) {
            socket.close();
            return receivePacket.getAddress();
        }

        socket.close();
        return null;
    }
}
