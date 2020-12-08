import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.time.LocalDate;


/**
 * @author Shaoyue Liu
 * @version 9/28/2020
 * This class loads the input file at the beginning of the program to add
 * in initial events.
 */
public class FileLoader {

	private String fileName;
	
	/**
	 * This is the constructor for the file loader class
	 * It takes the file name and load that file
	 * @param fileName - input file name
	 */
	public FileLoader(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * Reads and loads the given file into the MyCalendar class
	 * @param calendar - the Calendar object that I am building for this project
	 * @throws FileNotFoundException - throws an exception when the file is not found
	 */
	public void fileLoader(MyCalendar calendar) throws FileNotFoundException{
		File events = new File(fileName);
		Scanner input = new Scanner(events);
		TimeInterval interval;
		Event event;
		
		//Starts scanning the file
		while(input.hasNextLine()) {
			String eventName = input.nextLine();
			String word = input.next().toUpperCase();
			//If the input are letters of days of week, create regular events
			if(Character.isLetter(word.charAt(0))) {
				//each letter represents one day of the week
				String[] days = word.split("");
				//Start and end time string
				String startTimeString = input.next();
				String endTimeString = input.next();
				
				//Convert time string into integers
				String startT = startTimeString.replaceAll(":", "");
				int startTime = Integer.parseInt(startT);
				String endT = endTimeString.replaceAll(":", "");
				int endTime = Integer.parseInt(endT);
				
				//Calls time interval class to create a new interval
				interval = new TimeInterval(startTime, endTime, startTimeString, endTimeString);
				
				//Start and end day of events
				//if user enters the last two digit of year, assume that the event happens after 2000
				String[] startDay = input.next().split("/");
				if(startDay[2].length() == 2) {
					startDay[2] = "20" + startDay[2];
				}
				String[] endDay = input.next().split("/");
				if(endDay[2].length() == 2) {
					endDay[2] = "20" + endDay[2];
				}
				
				//Calls event class to create a new regular event
				event = new Event(eventName, interval, true);
				
				//Format of the date is: year, month, day
				LocalDate newStartDate = LocalDate.of(Integer.parseInt(startDay[2]), 
						Integer.parseInt(startDay[0]), Integer.parseInt(startDay[1]));
				LocalDate newEndDate = LocalDate.of(Integer.parseInt(endDay[2]), 
						Integer.parseInt(endDay[0]), Integer.parseInt(endDay[1])+1);
				
				int dayOfWeek = 0;
				
				//while the start and end dates are not equal
				while(!newStartDate.isEqual(newEndDate)) {
					for(int i = 0; i < days.length; i++) {
						switch(days[i]) {
						case "M": dayOfWeek = 1;
							break;
						case "T": dayOfWeek = 2;
							break;
						case "W": dayOfWeek = 3;
							break;
						case "H": dayOfWeek = 4;
							break;
						case "F": dayOfWeek = 5;
							break;
						case "A": dayOfWeek = 6;
							break;
						case "U": dayOfWeek = 7;
							break;
						}
						
						if(newStartDate.getDayOfWeek().getValue() == dayOfWeek) {
							calendar.addEvent(newStartDate, event);
						}
					}
					newStartDate = newStartDate.plusDays(1);
				}
				
				if(input.hasNextLine()) {
					input.nextLine();
				}
			}
			//Create a non-regular event
			else {
				String[] day = word.split("/");
				if(day[2].length() == 2) {
					day[2] = "20" + day[2];
				}
				//Start and end time string
				String startTimeString = input.next();
				String endTimeString = input.next();
				
				//Convert time string into integers
				String startT = startTimeString.replaceAll(":", "");
				int startTime = Integer.parseInt(startT);
				String endT = endTimeString.replaceAll(":", "");
				int endTime = Integer.parseInt(endT);
				
				//Calls time interval class to create a new interval
				interval = new TimeInterval(startTime, endTime, startTimeString, endTimeString);
				
				//Calls event class to create a new regular event
				event = new Event(eventName, interval, false);
				
				//Format of the date is: year, month, day
				LocalDate eventDate = LocalDate.of(Integer.parseInt(day[2]), 
						Integer.parseInt(day[0]), Integer.parseInt(day[1]));
				
				//add new non-regular event
				calendar.addEvent(eventDate, event);
				if(input.hasNextLine()) {
					input.nextLine();
				}
				
			}
		}
		System.out.println("Loading is done!\n");
		input.close();
	}
}
