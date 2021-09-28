import java.io.*;


public class MainSimulation extends GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
    	Event actEvent;
    	EventList myEventList = new EventList();
    	State actState = new State(myEventList);
        myEventList.InsertEvent(ARRIVAL_A, 0); //jobb A skickas
        myEventList.InsertEvent(MEASURE, 5);
    	while (time < 50000){ //simuleringstid
    		actEvent = myEventList.FetchEvent();
    		time = actEvent.eventTime;
    		actState.TreatEvent(actEvent);
    	}
    	System.out.println("Mean number of customers: " + 1.0*actState.accumulated/actState.noMeasurements);
    	System.out.println("Number of measurements done: " + actState.noMeasurements);
		System.out.println(time);
		System.out.println(State.summa);
		System.out.println("lambda: " + State.summa/time);
		System.out.println("betj: " + time/State.summa);
    }
}