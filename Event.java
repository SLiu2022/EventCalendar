/**
 * @author Shaoyue Liu
 * @version 9/28/2020
 * This class handles basic information of events
 * The 3 properties of an event are name, time interval, and is it regular
 */
public class Event {

	private String eventName;
	private TimeInterval interval;
	private boolean regular;
	
	/**
	 * Constructor for the event class
	 * Defines the 3 properties of an event
	 * @param eventName - name of an event
	 * @param interval - time interval of an event
	 * @param regular - is the event regular(recurring)?
	 */
	public Event(String eventName, TimeInterval interval, boolean regular) {
		this.eventName = eventName;
		this.interval = interval;
		this.regular = regular;
	}
	
	/**
	 * gets the name of an event
	 * @return eventNmae - name of the event
	 */
	public String getEventName() {
		return eventName;
	}
	
	/**
	 * gets the time interval of an event
	 * @return interval - interval of an event
	 */
	public TimeInterval getTimeInterval() {
		return interval;
	}
	
	/**
	 * gets the boolean output of whether the event is regular(recurring)
	 * @return regular - if the event is regular(recurring)
	 */
	public boolean isRegular() {
		return regular;
	}

}
