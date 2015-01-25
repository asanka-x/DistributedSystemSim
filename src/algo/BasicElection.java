package algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import syssim.Participant;
import syssim.SimSystem;

/**
 * This is an election written using SystemSim framework. You create a node by extending 
 * Participant.EventListener interface. The interface provides callbacks when the system is 
 * Started, message received by the participant. 
 * @author srinath
 *
 */

public class BasicElection {
	public static class ElectionListener implements Participant.EventListener{
		final List<Integer> receivedTokens = new ArrayList<Integer>();
		private int participantCount; 
		private int participantIndex; 
		
		public ElectionListener(int participantCount, int participantIndex){
			this.participantCount = participantCount;
			this.participantIndex = participantIndex; 
		}

		@Override
		public void participantStarted(SimSystem simSystem) {
			//This is how you broadcast the message
			simSystem.broadcastMessage(new String[]{String.valueOf(participantIndex)});
		}
		@Override
		public void eventReceived(SimSystem simSystem, String[] event) {
			System.out.println(participantIndex + " received "+ Arrays.toString(event));
			receivedTokens.add(Integer.parseInt(event[0]));
			if(receivedTokens.size() == participantCount){
				int leader = Collections.max(receivedTokens);
				System.out.println(participantIndex + " selected "+ leader + " as the leader");
			}
			
		}
	}
	
	
	public static void main(String[] args) throws Exception{
		final SimSystem system = new SimSystem();
		final int participantCount = 4; 
		for(int i =0;i< participantCount; i++){
			final int participantIndex = i;
			Participant participant = system.createParticipant(new ElectionListener(participantCount, participantIndex) );
		}
		system.bootUp();
	}
}
