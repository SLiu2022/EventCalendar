import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Shaoyue Liu
 * @version 9/29/2020
 * This class handles user inputs and communicates with other classes
 * User can view, create or delete events
 * when user inputs q, the outputs are written into the output file and program exits
 *
 */
public class MyCalendarTester {

	public static void main(String[] args) throws IOException{
		LocalDate today = LocalDate.now();
		MyCalendar calendar = new MyCalendar(today);
		calendar.printCalendar(calendar.currentDay());
		//Loads events.txt
		FileLoader file = new FileLoader("events.txt");
		file.fileLoader(calendar);
		
		//write all output to output file
		PrintWriter output = new PrintWriter(new File("C:\\Users\\shaoy\\Desktop\\CS151\\HW2Final\\src\\output.txt"), "UTF-8");
        calendar.printOutput(output);
        output.close();
	}	
}

