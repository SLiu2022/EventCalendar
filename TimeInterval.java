/**
 * @author Shaoyue Liu
 * @version 9/28/2020
 * Time interval class holds the time interval for all events
 * It also checks for overlapping when creating events
 */
public class TimeInterval {

	private int startTime;
	private int endTime;
	private String startTimeString;
	private String endTimeString;
	
	/**
	 * Constructor for the time interval class
	 * It holds start time and end time as both integers and strings
	 * @param startTime - start time as integer
	 * @param endTime - end time as integer
	 * @param startTimeString - start time as string
	 * @param endTimeString - end time as string
	 */
	public TimeInterval(int startTime, int endTime, String startTimeString, String endTimeString) {
			this.startTime = startTime;
			this.endTime = endTime;
			this.startTimeString = startTimeString;
			this.endTimeString = endTimeString;
	}
	
	/**
	 * gets start time as an integer
	 * @return startTime - integer start time
	 */
	public int getStartTimeInt() {
		return startTime;
	}
	
	/**
	 * gets end time as an integer
	 * @return endTime - integer end time
	 */
	public int getEndTimeInt()	{
		return endTime;
	}
	
	/**
	 * gets start time as a string
	 * @return startTimeString - string start time
	 */
	public String getStartTimeString() {
		return startTimeString;
	}
	
	/**
	 * gets end time as a string
	 * @return endTimeString - string end time
	 */
	public String getEndTimeString() {
		return endTimeString;
	}

	/**
	 * Checks for overlapping when creating an event
	 * @param e - event to be created
	 * @return overlap - whether the time of event is overlapping or not
	 */
	public boolean overlap(Event e) {
		boolean overlap = false;
		
		//if event start time is within another event
		if(e.getTimeInterval().getStartTimeInt() >= getStartTimeInt() 
				&& e.getTimeInterval().getStartTimeInt() <= getEndTimeInt()) {
					overlap = true;
		}
		//if event end time is within another event
		else if(e.getTimeInterval().getEndTimeInt() >= getStartTimeInt() 
				&& e.getTimeInterval().getEndTimeInt() <= getEndTimeInt()) {
					overlap = true;
		}
		//if event/end time is the same as other event's start/end time
		else if(e.getTimeInterval().getStartTimeInt() == getStartTimeInt() 
				|| e.getTimeInterval().getStartTimeInt() == getEndTimeInt()
				|| e.getTimeInterval().getEndTimeInt() == getStartTimeInt()
				|| e.getTimeInterval().getEndTimeInt() == getEndTimeInt()) {
					overlap = true;
		}
		return overlap;
	}
}
