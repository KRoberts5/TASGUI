package UnitTests;

import tas.Shift;
import tas.Badge;
import tas.TASDatabase;
import tas.Punch;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.GregorianCalendar;
import java.sql.Timestamp;

public class TestShift3{
	private TASDatabase db;

	
	@Before
	public void setup(){
		db = new TASDatabase();
                
	}
	
	@Test
	public void testFirstDay(){
            
            Badge badge = db.getBadge("TESTMAN1");
            Shift s4 = db.getShift(badge);
		
                GregorianCalendar gc1 = new GregorianCalendar(2018,9,29,22,29);
                
                GregorianCalendar gc2 = new GregorianCalendar(2018,9,30,7,4);
                
		
		Punch p1 = new Punch(badge,3342,103,gc1,1);
		Punch p2 = new Punch(badge,3345,103,gc2,0);  
		
		p1.adjust(s4);
		p2.adjust(s4);
		
		assertEquals("#TESTMAN1 CLOCKED IN: MON 10/29/2018 22:29:00", p1.printOriginalTimestamp());
		assertEquals("#TESTMAN1 CLOCKED IN: MON 10/29/2018 22:30:00 (Shift Start)", p1.printAdjustedTimestamp());
		
		assertEquals("#TESTMAN1 CLOCKED OUT: TUE 10/30/2018 07:04:00", p2.printOriginalTimestamp());
		assertEquals("#TESTMAN1 CLOCKED OUT: TUE 10/30/2018 07:00:00 (Shift Stop)", p2.printAdjustedTimestamp());
	}
	@Test
	public void testSecondDay(){
            
            Badge badge = db.getBadge("TESTMAN1");
            Shift s4 = db.getShift(badge);
	
		GregorianCalendar gc1 = new GregorianCalendar(2018,9,30,22,30);
                
                GregorianCalendar gc2 = new GregorianCalendar(2018,9,31,7,0);
                
		
		Punch p1 = new Punch(badge,3342,103,gc1,1);
		Punch p2 = new Punch(badge,3345,103,gc2,0);  
		
		p1.adjust(s4);
		p2.adjust(s4);
				
		assertEquals("#TESTMAN1 CLOCKED IN: TUE 10/30/2018 22:30:00", p1.printOriginalTimestamp());
		assertEquals("#TESTMAN1 CLOCKED IN: TUE 10/30/2018 22:30:00 (Shift Start)", p1.printAdjustedTimestamp());
		
		assertEquals("#TESTMAN1 CLOCKED OUT: WED 10/31/2018 07:00:00", p2.printOriginalTimestamp());
		assertEquals("#TESTMAN1 CLOCKED OUT: WED 10/31/2018 07:00:00 (Shift Stop)", p2.printAdjustedTimestamp());
		
	}
	
	@Test
	public void testThirdDay(){
            
            Badge badge = db.getBadge("TESTMAN1");
            Shift s4 = db.getShift(badge);
            
		GregorianCalendar gc1 = new GregorianCalendar(2018,9,31,22,31);
                GregorianCalendar gc2 = new GregorianCalendar(2018,10,1,6,56);
		
		Punch p1 = new Punch(badge,3342,103,gc1,1);
		Punch p2 = new Punch(badge,3345,103,gc2,0);  
		
		p1.adjust(s4);
		p2.adjust(s4);
		
		assertEquals("#TESTMAN1 CLOCKED IN: WED 10/31/2018 22:31:00", p1.printOriginalTimestamp());
		assertEquals("#TESTMAN1 CLOCKED IN: WED 10/31/2018 22:30:00 (Shift Start)", p1.printAdjustedTimestamp());
		
		assertEquals("#TESTMAN1 CLOCKED OUT: THU 11/01/2018 06:56:00", p2.printOriginalTimestamp());
		assertEquals("#TESTMAN1 CLOCKED OUT: THU 11/01/2018 07:00:00 (Shift Stop)", p2.printAdjustedTimestamp());
	}
	
	@Test
	public void testFourthDay(){
            
            Badge badge = db.getBadge("TESTMAN1");
            Shift s4 = db.getShift(badge);
            
		GregorianCalendar gc1 = new GregorianCalendar(2018,10,1,22,40);
                GregorianCalendar gc2 = new GregorianCalendar(2018,10,2,6,50);
		
		Punch p1 = new Punch(badge,3342,103,gc1,1);
		Punch p2 = new Punch(badge,3345,103,gc2,0);  
		
		p1.adjust(s4);
		p2.adjust(s4);
		
		assertEquals("#TESTMAN1 CLOCKED IN: THU 11/01/2018 22:40:00", p1.printOriginalTimestamp());
		assertEquals("#TESTMAN1 CLOCKED IN: THU 11/01/2018 22:45:00 (Shift Dock)", p1.printAdjustedTimestamp());
		
		assertEquals("#TESTMAN1 CLOCKED OUT: FRI 11/02/2018 06:50:00", p2.printOriginalTimestamp());
		assertEquals("#TESTMAN1 CLOCKED OUT: FRI 11/02/2018 06:45:00 (Shift Dock)", p2.printAdjustedTimestamp());
	}
	
	@Test
	public void testFifthDay(){
            
            Badge badge = db.getBadge("TESTMAN1");
            Shift s4 = db.getShift(badge);
            
		GregorianCalendar gc1 = new GregorianCalendar(2018,10,2,22,30);
                
                GregorianCalendar gc2 = new GregorianCalendar(2018,10,3,2,31);
                
                GregorianCalendar gc3 = new GregorianCalendar(2018,10,3,2,54);
                
                GregorianCalendar gc4 = new GregorianCalendar(2018,10,3,7,0);
                
                
		
		Punch p1 = new Punch(badge,3342,103,gc1,1);
                Punch p2 = new Punch(badge,3343,103,gc2,0);
                Punch p3 = new Punch(badge,3344,103,gc3,1);
		Punch p4 = new Punch(badge,3345,103,gc4,0);  
		
		p1.adjust(s4);
		p2.adjust(s4);
                p3.adjust(s4);
                p4.adjust(s4);
		
		assertEquals("#TESTMAN1 CLOCKED IN: FRI 11/02/2018 22:30:00", p1.printOriginalTimestamp());
		assertEquals("#TESTMAN1 CLOCKED IN: FRI 11/02/2018 22:30:00 (Shift Start)", p1.printAdjustedTimestamp());
		
		assertEquals("#TESTMAN1 CLOCKED OUT: SAT 11/03/2018 02:31:00", p2.printOriginalTimestamp());
		assertEquals("#TESTMAN1 CLOCKED OUT: SAT 11/03/2018 02:30:00 (Lunch Start)", p2.printAdjustedTimestamp());
		
		assertEquals("#TESTMAN1 CLOCKED IN: SAT 11/03/2018 02:54:00", p3.printOriginalTimestamp());
		assertEquals("#TESTMAN1 CLOCKED IN: SAT 11/03/2018 03:00:00 (Lunch Stop)", p3.printAdjustedTimestamp());
		
		assertEquals("#TESTMAN1 CLOCKED OUT: SAT 11/03/2018 07:00:00", p4.printOriginalTimestamp());
		assertEquals("#TESTMAN1 CLOCKED OUT: SAT 11/03/2018 07:00:00 (Shift Stop)", p4.printAdjustedTimestamp());
	}
}