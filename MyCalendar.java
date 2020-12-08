import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;


/**
 * @author Shaoyue Liu
 * @version 9/29/2020
 *This class contains methods for all functions in a calendar
 */
public class MyCalendar {
	private HashMap<LocalDate, ArrayList<Event>> calendar;
	private LocalDate today;

	/**
	 * Constructor for my calendar.
	 * The start date is today
	 * @param today - highlighted date for the calendar
	 */
	public MyCalendar(LocalDate today) {
		this.calendar = new HashMap<LocalDate, ArrayList<Event>>();
		this.today = today;
	}

	/**
	 * This method check for existing event in that date
	 * And add the event
	 * @param date - date of the event
	 * @param event - event to add
	 */
	public void addEvent(LocalDate date, Event event) {
		//Check if there are already events in that day
		if(calendar.containsKey(date)) {
			ArrayList<Event> eventList = calendar.get(date);
			eventList.add(event);
			calendar.put(date,  eventList);
		}
		else {
			ArrayList<Event> events = new ArrayList<Event>();
			events.add(event);
			calendar.put(date, events);
		}
	}

	/**
	 * Return today's date
	 * @return today - today's date
	 */
	public LocalDate currentDay() {
		return today;
	}

	/**
	 * Prints the month of the calendar with a given date
	 * Highlights all event dates with brackets
	 * @param date - the date to print from
	 */
	public void printCalendar(LocalDate date) {
		LocalDate startDate = LocalDate.of(date.getYear(), date.getMonth(), 1);
		String startDateS = startDate.getDayOfWeek().toString();
		//Assign numbers from 1-7 to each day of the week
		int startDateInt = 0;
		switch(startDateS){
		case "SUNDAY":
			startDateInt = 1;
			break;
		case "MONDAY":
			startDateInt = 2;
			break;
		case "TUESDAY":
			startDateInt = 3;
			break;
		case "WEDNESDAY":
			startDateInt = 4;
			break;
		case "THURSDAY":
			startDateInt = 5;
			break;
		case "FRIDAY":
			startDateInt = 6;
			break;
		case "SATURDAY":
			startDateInt = 7;
			break;
		}
		//Print month and year
		System.out.println("             " + date.getMonth() + " " + date.getYear());
    	System.out.println(" Su    Mo    Tu    We    Th    Fr    Sa");
    	int dayNum = 0;
    	//Calculate the correct spot to print the first day of the month
    	//Each day of week are 6 spaces apart
    	for(int space = 1; space < startDateInt; space++) {
    		System.out.print("      ");
    		dayNum++;
    	}
    	LocalDate todayDate = LocalDate.now();
    	for (int day = 1; day <= date.lengthOfMonth(); day++){
    		if ((day == todayDate.getDayOfMonth() 
    				&& date.getMonthValue() == todayDate.getMonthValue() 
    				&& date.getYear() == today.getYear())
    				|| calendar.containsKey(startDate)){
        		if (day < 10) {
        			System.out.print(" [" + day + "]");
        		}
        		else {
        			System.out.print("[" + day + "]");
        		}
        		dayNum++;
        		if(dayNum % 7 == 0) {
        			System.out.println();
        		}
        		else {
        			System.out.print("  ");
        		}
    		}
    		else{
        		if (day < 10) {
        			System.out.print("  " + day);
        		}
        		else {
        			System.out.print(" " + day); 
        		}
        		dayNum++;
        		if(dayNum % 7 == 0) {
        			System.out.println();
        		}
        		else {
        			System.out.print("   ");
        		}
    		}
    		startDate = startDate.plusDays(1);
    	}
    	System.out.println("\n");
	}

	/**
	 * gets the String of all events in the given date
	 * calls sort method to sort events in order
	 * @param date - user given date
	 * @return events - list of events in the given date
	 */
	public String getEvents(LocalDate date) {
		String eventList = "";
		//check for events
		//if events exist, call sort method and output event list.
		if(!calendar.containsKey(date)) {
			eventList = "No events in the given date.";
		}
		else {
			sortEventsByTime(calendar.get(date));
			ArrayList<Event> list = calendar.get(date);
			for(Event e: list) {
				eventList = eventList + "  " + e.getEventName() + " | " + 
						e.getTimeInterval().getStartTimeString() + " - " +
						e.getTimeInterval().getEndTimeString() + "\n";
			}
		}
		return eventList;
	}

	/**
	 * Sorts an arraylist of events by time
	 * Uses the comparator interface
	 * @param events - arraylist of events to sort
	 */
	private void sortEventsByTime(ArrayList<Event> events) {
		//Compare 2 events and sort them by time
		class sortByTime implements Comparator<Event>{
			public int compare(Event e1, Event e2) {
				int event1 = e1.getTimeInterval().getStartTimeInt();
				int event2 = e2.getTimeInterval().getEndTimeInt();
				return Integer.compare(event1, event2);
			}
		}
		Collections.sort(events, new sortByTime());
	}

	/**
	 * returns the day before today
	 * @return today - the previous day
	 */
	public LocalDate previsouDay() {
		return today = today.minusDays(1);
		
	}

	/**
	 * return the day after today
	 * @return today - the next day
	 */
	public LocalDate nextDay() {
		return today = today.plusDays(1);
		
	}

	/**
	 * return date in last month
	 * @return today - a month before
	 */
	public LocalDate previousMonth() {
		return today = today.minusMonths(1);
		
	}

	/**
	 * return date in next month
	 * @return today - next month
	 */
	public LocalDate nextMonth() {
		return today = today.plusMonths(1);
		
	}

	/**
	 * Get the list of all events in that date
	 * check if time is overlapping
	 * @param date - given date of event
	 * @param event - event to add
	 * @return overlap - boolean value of whether the event overlap
	 */
	public boolean isOverlapped(LocalDate date, Event event) {
		boolean overlap = false;
		//check if date has event
		if(calendar.containsKey(date)) {
			ArrayList<Event> events = calendar.get(date);
			for(Event e : events) {
				if(e.getTimeInterval().overlap(event)) {
					overlap = true;
				}
			}
		}
		return overlap;
	}

	/**
	 * return the list of all events in order of time
	 * @return - eventList - list of all events
	 */
	public String getEventList() {
		String eventList = new String();
		TreeMap<LocalDate, ArrayList<Event>> cal = new TreeMap<>();
		cal.putAll(calendar);
		for(LocalDate day : cal.keySet()) {
			ArrayList<Event> events = calendar.get(day);
			
			if(!eventList.contains(Integer.toString(day.getYear()))) {
				eventList = eventList + day.getYear() + "\n";
			}
			eventList = eventList + "  " + day.getMonth()  + " " 
					+ day.getDayOfMonth() + " " + day.getDayOfWeek() + "\n";
			//sort all events by time
			sortEventsByTime(events);
			//input all events into event list
			for(Event e: events) {
				eventList = eventList + "    "  +  
						e.getTimeInterval().getStartTimeString() + " - " +
						e.getTimeInterval().getEndTimeString() + " " +  e.getEventName() + "\n";
			}
		}
		return eventList;
	}

	/**
	 * gets an event by date and name
	 * @param date - date of event
	 * @param eventName - name of event
	 * @return event - selected event
	 */
	public Event findEvent(LocalDate date, String eventName) {
		Event event = null;
		//check if the given date has any event
		if(!calendar.containsKey(date)) {
			return event;
		}
		//compare event name
		ArrayList<Event> events = calendar.get(date);
		for(Event e : events) {
			if(eventName.equals(e.getEventName())) {
				event = e;
			}
		}
		return event;
	}

	/**
	 * delete selected one time event in the given date
	 * @param date - user given date
	 * @param event - event to delete
	 * @return delete - boolean value of whether the event is deleted or not
	 */
	public boolean deleteSelectedEvent(LocalDate date, Event event) {
		boolean delete = false;
		//check if date has event
		if(calendar.containsKey(date)) {
			ArrayList<Event> events = calendar.get(date);
			Event e = findEvent(date, event.getEventName());
			//check if event is regular
			if(!e.isRegular()) {
				events.remove(event);
			}
			calendar.put(date,  events);
			if(events.isEmpty()) {
				calendar.remove(date);
			}
			delete = true;
		}
		return delete;
	}

	/**
	 * remove all non-regular events on a given date
	 * @param date - given date of the events
	 * @return delete - boolean value of whether all events are deleted or not
	 */
	public boolean deleteAllEvents(LocalDate date) {
		boolean delete = false;
		//check if date has events
		if(calendar.containsKey(date)) {
			ArrayList<Event> events = calendar.get(date);
			//iterates through all events
			java.util.Iterator<Event> i = events.iterator();
			while(i.hasNext()) {
				Event e = i.next();
				//remove if event is not regular
				if(!e.isRegular()) {
					i.remove();
				}
			}
			calendar.put(date, events);
			if(events.isEmpty()) {
				calendar.remove(date);
			}
			delete = true;
		}
		return delete;
	}

	/**
	 * Return a string of all regular events in the given date
	 * @param date - given date of events
	 * @return eventList - list of all regular events
	 */
	public String getRegularEvents(LocalDate date) {
		String eventList = "";
		//if date does not contain any events
		if(!calendar.containsKey(date)) {
			eventList = "No events on this date.";
		}
		else {
			//sort events
			sortEventsByTime(calendar.get(date));
			ArrayList<Event> events = calendar.get(date);
			//input regular events into event list 
			for(Event e : events) {
				if(e.isRegular()) {
					eventList = eventList + "  " + e.getEventName() + " | " + 
							e.getTimeInterval().getStartTimeString() + " - " +
							e.getTimeInterval().getEndTimeString() + "\n";
				}
			}
		}
		return eventList;
	}

	/**
	 * Delete a regular event on that date
	 * Required to delete all recurring events in the calendar
	 * I was unable to implement it in time.
	 * @param date - date of event
	 * @param event - regular event to delete
	 * @return delete - boolean value of whether the regular event is deleted or not
	 */
	public boolean deleteRegularEvent(LocalDate date, Event event) {
		boolean delete = false;
		//check of date contains any events
		if(calendar.containsKey(date)) {
			ArrayList<Event> events = calendar.get(date);
			//remove if event is regular
			if(event.isRegular()) {
				events.remove(event);
			}
			calendar.put(date, events);
			if(events.isEmpty()) {
				calendar.remove(date);
			}
			delete = true;
		}
		return delete;
	}

	/**
	 * Writes the output into file by using a print writer
	 * @param output - print writer object to output date
	 */
	public void printOutput(PrintWriter output) {
		String eventList = new String();
		TreeMap<LocalDate, ArrayList<Event>> cal = new TreeMap<>();
		cal.putAll(calendar);
		for(LocalDate day : cal.keySet())
		{
			ArrayList<Event> events = calendar.get(day);
			
			if (!eventList.contains(Integer.toString(day.getYear())))
			{
				eventList = eventList + day.getYear() + "\n";
				output.println(day.getYear());
			}
			
			eventList = eventList + "  " + day.getMonth() + "\n";
			output.println("  " + day.getMonth());
			
			eventList = eventList + "    " + day.getDayOfMonth() + " " + day.getDayOfWeek() + "\n";
			output.println("    " + day.getDayOfMonth() + " " + day.getDayOfWeek());
			
			sortEventsByTime(events);
			for(Event e : events)
			{
				eventList = eventList + "      " + e.getEventName() + " | " + 
							e.getTimeInterval().getStartTimeString() + " - " +
							e.getTimeInterval().getEndTimeString() + "\n";
				output.println("      " + e.getEventName() + " | " + 
						e.getTimeInterval().getStartTimeString() + " - " +
						e.getTimeInterval().getEndTimeString());
			}
		}
	}

}
