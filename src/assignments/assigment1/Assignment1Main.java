/*
 * Copyright (c) 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package assignments.assigment1;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.MessageFormat;

/**
 * Part1: 
Using TCP sockets, write a riddle server that return riddles. ( You can find riddles from http://www.hobbitcentral.com/lore/riddles.php). The server should do following. 

1.On "hello" returns number of riddles it has
2.On "riddle" returns a random riddle with its number
3.On "riddle n" return nth riddle
4.On "answer n x" matches x with the answer of nth riddle and return "correct" or "incorrenct" 
5.On "bye" sends "bye" back and disconnects
6.On anything elese, print "unknown message" 

Use # as the seperator between messages. The client should connect to the server by sending hello. Then keep reading the standard input stream, and when a user type in a command, send the command to the server. When the user types bye, send "bye" to the server and then disconnect from the server. 

Part 2:
Do the same using UDP sockets. 
 * @author srinath
 *
 */

public class Assignment1Main {

    private static final int TCP_PORT = 8081;
    private static final int UDP_PORT = 8082;

    private ServerSocket serverSocket;
    private DatagramSocket datagramSocket;

    private Socket clientSocketConnection;
    private DataOutputStream dataOutputStream;
    private BufferedReader bufferedReader;

    public void setup(){
		//Start your server here

        //TCP Server
        try {
            serverSocket = new ServerSocket(TCP_PORT);
            System.out.println(MessageFormat.format("[INFO] Riddle TCP Server running on {0}", TCP_PORT));
            int clientCount = 1;
            while (true) {
                System.out.println("[TCP RIDDLE SERVER] Waiting for request....");
                Socket socket = serverSocket.accept();
                String threadName = "T-Client-"+clientCount;
                new Thread(new SocketThread(socket),threadName).start();
                clientCount++;
            }
        } catch (IOException e) {
            System.out.println(MessageFormat.format("[ERROR] Setting up Riddle TCP Server on {0} : {1}", TCP_PORT, e.getMessage()));
        }

        //UDP Server
        try {
            datagramSocket = new DatagramSocket(UDP_PORT);
            System.out.println(MessageFormat.format("[INFO] Riddle UDP Server running on {0}", UDP_PORT));

            //listen here
        } catch (IOException e) {
            System.out.println(MessageFormat.format("[ERROR] Setting up Riddle UDP Server on {0} : {1}", UDP_PORT, e.getMessage()));
        }
	}
	
	/**
	 * 
	 * @param command - to be send to the server
	 * @return - results received from the server, return null if nothing to be returned
	 */
	public String[] runCommandOnTcpEchoServer(String command){
		//Send the message to the server and get a result
        String serverResponse=null;
        try {
            if(clientSocketConnection==null){
                clientSocketConnection = new Socket("localhost",TCP_PORT);
                dataOutputStream = new DataOutputStream(clientSocketConnection.getOutputStream());
                bufferedReader = new BufferedReader(new InputStreamReader(clientSocketConnection.getInputStream()));
            }
            dataOutputStream.writeBytes(command+"\n");
            dataOutputStream.flush();
            serverResponse = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String[]{serverResponse};
	}
	
	/**
	 * 
	 * @param command - to be send to the server
	 * @return - results received from the server, return null if nothing to be returned
	 */
	public String[] runCommandOnUdpEchoServer(String command){
		//Send the message to the server and get a result
		return null;
	}

	
	public void shutdown(){
		//shutdown the server here

        //shutdown TCP socket
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.out.println(MessageFormat.format("[ERROR] Shutting down Riddle TCP server running on {0} : {1}", TCP_PORT, e.getMessage()));
        }

        //shutdown UDP socket
        if (datagramSocket != null) {
            datagramSocket.close();
        }

    }
}
