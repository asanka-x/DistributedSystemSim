package tests;

import assignments.assigment1.Assignment1Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by asankanissanka on 2/22/15.
 */
public class RiddleClientTest {
    public static void main(String[] args) {
        Assignment1Main assignment1Main = new Assignment1Main();
        while(true){
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String[] response = null;
            try {
                String userInput = bufferedReader.readLine();
                if(userInput!=null && userInput.trim()!=""){
                    response = assignment1Main.runCommandOnTcpEchoServer(userInput);
                }
                System.out.println("Response : "+response[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
