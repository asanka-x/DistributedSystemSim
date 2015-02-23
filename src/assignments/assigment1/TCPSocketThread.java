package assignments.assigment1;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by asankanissanka on 2/22/15.
 */
public class TCPSocketThread implements Runnable {
    private Socket socket;
    private boolean isCommunicationStarted;
    private RiddleResponseGenerator riddleResponseGenerator;

    public TCPSocketThread(Socket socket) {
        this.socket = socket;
        this.isCommunicationStarted = false;
        riddleResponseGenerator = new RiddleResponseGenerator();
    }

    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            boolean terminateConnection = false;
            while (true) {
                String clientCommands = bufferedReader.readLine();
                if(clientCommands!=null){
                    String[] splittedClientCommands = clientCommands.split("#");
                    String clientResponse="";
                    for(int i=0;i<splittedClientCommands.length;i++){
                        String clientCommand = splittedClientCommands[i].trim();
                        System.out.println("[RIDDLE SERVER] Running command : "+clientCommand);
                        if (clientCommand.equals("hello")) {
                            System.out.println("[RIDDLE SERVER] Communication Started...");
                            isCommunicationStarted = true;
                            clientResponse = clientResponse+"#"+riddleResponseGenerator.generateRiddleResponse(clientCommand);
                        } else {
                            if (isCommunicationStarted) {
                                if (clientCommand.equals("bye")) {
                                    clientResponse = clientResponse+"#"+riddleResponseGenerator.generateRiddleResponse(clientCommand);
                                    isCommunicationStarted=false;
                                    terminateConnection = true;
                                } else {
                                    clientResponse = clientResponse+"#"+riddleResponseGenerator.generateRiddleResponse(clientCommand);
                                }
                            } else {
                                clientResponse = clientResponse+"#Say hello to initiate connection";
                            }
                        }
                    }
                    dataOutputStream.writeBytes(clientResponse + "\n");
                    dataOutputStream.flush();
                    if(terminateConnection){
                        break;
                    }
                }

            }
            System.out.println("[RIDDLE SERVER] Communication Terminated...");
            socket.close();
            bufferedReader.close();
            dataOutputStream.close();
        } catch (IOException e) {
            System.out.println("[RIDDLE SERVER][ERROR] : "+e.getMessage());
        }
    }
}
