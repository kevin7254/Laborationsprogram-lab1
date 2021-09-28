import java.util.*;

class State extends GlobalSimulation{
	
	public int numberInQueue = 0, accumulated = 0, noMeasurements = 0;

	public static int summa = 0;
	
	private EventList myEventList;

	Random slump = new Random();
	
	State(EventList x){
		myEventList = x;
	}
	
	private void InsertEvent(int event, double timeOfEvent){
		myEventList.InsertEvent(event, timeOfEvent);
	}
	
	
	public void TreatEvent(Event x){ //fler cases för A eller B
		switch (x.eventType) {
			case ARRIVAL_A, ARRIVAL_B -> arrival(x.eventType);
			case READY_A, READY_B -> ready(x.eventType);
			case MEASURE -> measure();
		}
	}
	
	private double generateMean(double mean){
		return 2*mean*slump.nextDouble();
	}
	
	private void arrival(int type){
		if (numberInQueue == 0) {
			numberInQueue++;
			if (type == ARRIVAL_A) {
				InsertEvent(READY_A, 0.002);
			} else if(type == ARRIVAL_B) {
				InsertEvent(READY_B, 0.004);
			}
		} else {
			numberInQueue++;
			if ((type == ARRIVAL_A) && myEventList.FetchEvent().eventType != ARRIVAL_B) {
				InsertEvent(ARRIVAL_A, 0.002);
			} else if (type == ARRIVAL_B) {
				InsertEvent(ARRIVAL_B, 0.004);
			}
		}
	}
	
	private void ready(int type){
		numberInQueue--;
		if (numberInQueue > 0) {
			if (type == READY_A) {
				InsertEvent(ARRIVAL_B, 1);
				InsertEvent(myEventList.FetchEvent().eventType, myEventList.FetchEvent().eventTime);
			}
		}
	}
	
	private void measure(){
		accumulated = accumulated + numberInQueue;
		noMeasurements++; //antal betjänta
		InsertEvent(MEASURE, 0.001);
	}
}