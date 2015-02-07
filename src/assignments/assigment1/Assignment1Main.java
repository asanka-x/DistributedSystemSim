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
	
	public void setup(){
		//Start your server here 
	}
	
	/**
	 * 
	 * @param command - to be send to the server
	 * @return - results received from the server, return null if nothing to be returned
	 */
	public String[] runCommandOnTcpEchoServer(String command){
		//Send the message to the server and get a result
		return null;
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
		//shitdown the server here
	}

}
