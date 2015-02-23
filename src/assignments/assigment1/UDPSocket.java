package assignments.assigment1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asankanissanka on 2/23/15.
 */
public class UDPSocket {
    private DatagramSocket datagramSocket;
    private RiddleResponseGenerator riddleResponseGenerator;

    private static List<String> connectedClients = new ArrayList<String>();

    public UDPSocket(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
        riddleResponseGenerator = new RiddleResponseGenerator();
    }

    public void start() {
        try {
            DatagramPacket receivePacket = new DatagramPacket(new byte[1024], new byte[1024].length);
            datagramSocket.receive(receivePacket);
            String clientCommands = new String(receivePacket.getData()).trim();
            InetAddress inetAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            if (clientCommands != null) {
                String[] splittedClientCommands = clientCommands.split("#");
                String clientResponse="";
                for(int i=0;i<splittedClientCommands.length;i++) {
                    String clientCommand = splittedClientCommands[i].trim();
                    System.out.println("[RIDDLE SERVER] Running command : " + clientCommand);
                    if (clientCommand.equals("hello")) {
                        System.out.println("[RIDDLE SERVER] Communication Started...");
                        System.out.println("[RIDDLE SERVER] Connecting with : " + inetAddress.getHostAddress());
                        if (!connectedClients.contains(inetAddress.toString())) {
                            connectedClients.add(inetAddress.getHostAddress());
                        }
                        clientResponse = clientResponse+"#"+riddleResponseGenerator.generateRiddleResponse(clientCommand);
                    } else {
                        if (connectedClients.contains(inetAddress.getHostAddress())) {
                            if (clientCommand.equals("bye")) {
                                clientResponse = clientResponse+"#"+riddleResponseGenerator.generateRiddleResponse(clientCommand);
                                connectedClients.remove(inetAddress.getHostAddress());
                            } else {
                                clientResponse = clientResponse+"#"+riddleResponseGenerator.generateRiddleResponse(clientCommand);
                            }
                        } else {
                            clientResponse = clientResponse+"#"+"Say hello to initiate connection";
                            connectedClients.remove(inetAddress.getHostAddress());
                        }
                    }
                }
                DatagramPacket sendPacket = new DatagramPacket(clientResponse.getBytes(), clientResponse.getBytes().length, inetAddress, port);
                datagramSocket.send(sendPacket);
            }
        } catch (Exception e) {
            System.out.println("[RIDDLE SERVER][ERROR] : " + e.getMessage());
        }
    }
}
