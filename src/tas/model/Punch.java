package tas.model;

/**
 *
 * @author Brendan
 */
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.sql.Time;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class Punch {
    
    
    public final static int CLOCKED_OUT = 0;
    public final static int CLOCKED_IN = 1;
    public final static int TIMED_OUT = 2;
    
    public final static String EVENT_DATA_SHIFT_START = "Shift Start";
    public final static String EVENT_DATA_SHIFT_STOP = "Shift Stop";
    public final static String EVENT_DATA_LUNCH_START = "Lunch Start";
    public final static String EVENT_DATA_LUNCH_STOP = "Lunch Stop";
    public final static String EVENT_DATA_SHIFT_DOCK = "Shift Dock";
    public final static String EVENT_DATA_INTERVAL_ROUND = "Interval Round";
    public final static String EVENT_DATA_NONE = "None";
    
    public final static int SUN = 0;
    public final static int SAT = 6;
    
    public static final String[] PUNCH_TYPES = { "CLOCKED OUT","CLOCKED IN", "TIMED OUT"}; 
    public static final String[] DAYS_OF_WEEK = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
    public static final String DATE_FORMAT = "MM/dd/yyyy HH:mm:ss";
    
    private int id;
    private int terminalId;
    private Badge badge;
    private GregorianCalendar originalTimestamp;
    private GregorianCalendar adjustedTimestamp;
    private int punchTypeId;
    private String eventData;
    
    public Punch(Badge badge, int terminalId, int punchTypeId ){
        this.badge = badge;
        this.terminalId = terminalId;
        this.punchTypeId = punchTypeId;
        
        this.id = 0;
         originalTimestamp = new GregorianCalendar();
        
        this.adjustedTimestamp = null;
        this.eventData = "";
        
    }
    
    public Punch(Badge badge, int terminalId, GregorianCalendar ots,int ptid){
        this.badge = badge;
        this.id = 0;
        this.terminalId = terminalId;
        this.originalTimestamp = ots;
        
        this.adjustedTimestamp = null;
        this.eventData = "";
    }
    
    public Punch(Badge badge,int id, int terminalId, GregorianCalendar ots, int ptid ){
        this.badge = badge;
        this.id = id;
        this.terminalId = terminalId;
        
        this.originalTimestamp = ots;
        this.adjustedTimestamp = null;
        this.punchTypeId = ptid;
        this.eventData = "";
    }
    
    public void adjust(Shift s){
        
        GregorianCalendar ots = originalTimestamp;
        int day = originalTimestamp.get(Calendar.DAY_OF_WEEK);
        
        HashMap<String,GregorianCalendar> shiftValues = s.getParticularShiftValues(day,ots);
        
        GregorianCalendar shiftStart = shiftValues.get(Shift.SHIFT_START);
        GregorianCalendar lunchStart = shiftValues.get(Shift.LUNCH_START);
        GregorianCalendar lunchStop = shiftValues.get(Shift.LUNCH_STOP);
        GregorianCalendar shiftStop = shiftValues.get(Shift.SHIFT_STOP);

        if(day != shiftStart.get(Calendar.DAY_OF_WEEK))
            day = shiftStart.get(Calendar.DAY_OF_WEEK);
        
        int interval = s.getInterval(day) * TASLogic.MILLIS_TO_MIN;
        int gracePeriod = s.getGracePeriod(day) * TASLogic.MILLIS_TO_MIN;
        int dock = s.getDock(day) * TASLogic.MILLIS_TO_MIN;  
        
        GregorianCalendar shiftStartEarly = new GregorianCalendar();
        shiftStartEarly.setTimeInMillis(shiftStart.getTimeInMillis() - interval);
        
        GregorianCalendar shiftStartLate = new GregorianCalendar();
        shiftStartLate.setTimeInMillis(shiftStart.getTimeInMillis() + interval);
        
        GregorianCalendar shiftStartGracePeriod = new GregorianCalendar();
        shiftStartGracePeriod.setTimeInMillis(shiftStart.getTimeInMillis() + gracePeriod);
        
        GregorianCalendar shiftStopEarly = new GregorianCalendar();
        shiftStopEarly.setTimeInMillis(shiftStop.getTimeInMillis() - interval);
        
        GregorianCalendar shiftStopLate = new GregorianCalendar();
        shiftStopLate.setTimeInMillis(shiftStop.getTimeInMillis() + interval);
        
        GregorianCalendar shiftStopGracePeriod = new GregorianCalendar();
        shiftStopGracePeriod.setTimeInMillis(shiftStop.getTimeInMillis() - gracePeriod);

        if((day == Calendar.SUNDAY) || (day == Calendar.SATURDAY)){
            
            /*
            Punch Occurs on a Weekend and Does Not Pertain to a Rule Set.
            Punch Will be Adjusted to the Closest Interval
            */
            
            GregorianCalendar nearestBefore = new GregorianCalendar(ots.get(Calendar.YEAR),ots.get(Calendar.MONTH),ots.get(Calendar.DAY_OF_MONTH),ots.get(Calendar.HOUR_OF_DAY),0,0);
            GregorianCalendar nearestAfter = new GregorianCalendar();
            nearestAfter.setTimeInMillis(nearestBefore.getTimeInMillis() + interval);
            
            GregorianCalendar otsNoSecs = new GregorianCalendar(ots.get(Calendar.YEAR),ots.get(Calendar.MONTH),ots.get(Calendar.DAY_OF_MONTH),ots.get(Calendar.HOUR_OF_DAY),ots.get(Calendar.MINUTE),0);
            
            while(nearestAfter.before(ots)){
                
                nearestBefore.setTimeInMillis(nearestBefore.getTimeInMillis() + interval);
                nearestAfter.setTimeInMillis(nearestAfter.getTimeInMillis() + interval);   
            }
            
            long beforeDiff = ots.getTimeInMillis() - nearestBefore.getTimeInMillis();
            long afterDiff = nearestAfter.getTimeInMillis() - ots.getTimeInMillis();
            
            if(otsNoSecs.equals(nearestBefore) || ots.equals(nearestAfter)){
                    adjustedTimestamp = new GregorianCalendar();
                    adjustedTimestamp.setTimeInMillis(otsNoSecs.getTimeInMillis());
                    eventData = EVENT_DATA_NONE;
            }
            else if(beforeDiff < afterDiff){
                adjustedTimestamp = new GregorianCalendar();
                adjustedTimestamp.setTimeInMillis(nearestBefore.getTimeInMillis());
                eventData = EVENT_DATA_INTERVAL_ROUND;
            }
            else{
                adjustedTimestamp = new GregorianCalendar();
                adjustedTimestamp.setTimeInMillis(nearestAfter.getTimeInMillis());
                eventData = EVENT_DATA_INTERVAL_ROUND;
            }  
        }
        else{
            
            /*
            Punch Occurs on a Weekday and Either Falls Within a Rule Set or
            Will be Rounded to the Nearest Interval
            */
            
            if((ots.after(shiftStartEarly) && ots.before(shiftStartLate)) || (ots.equals(shiftStartEarly)) || (ots.equals(shiftStartLate))){
                
                //Punch Occurs Within the Shift Start Period
                
                if(ots.before(shiftStart)){     //Punch is in the Early Interval
                    adjustedTimestamp = new GregorianCalendar();
                    adjustedTimestamp.setTimeInMillis(shiftStart.getTimeInMillis());
                    eventData = EVENT_DATA_SHIFT_START;
                }
                else{   //Punch is in the Late Interval
                    if(ots.before(shiftStartGracePeriod) || ots.equals(shiftStartGracePeriod)){     //Punch is Within the Grace Period
                        adjustedTimestamp = new GregorianCalendar();
                        adjustedTimestamp.setTimeInMillis(shiftStart.getTimeInMillis());
                        eventData = EVENT_DATA_SHIFT_START;
                    }
                    else{   //Punch is Late and Will be Docked
                        adjustedTimestamp = new GregorianCalendar();
                        adjustedTimestamp.setTimeInMillis(shiftStart.getTimeInMillis() + dock);
                        eventData = EVENT_DATA_SHIFT_DOCK;
                    }
                }
            }
            else if((ots.after(shiftStopEarly)  && ots.before(shiftStopLate)) || (ots.equals(shiftStopEarly)) || (ots.equals(shiftStopLate))){
                
                //Punch Occurs Within the Shift Stop Period
                
                if(ots.after(shiftStop)){   //Punch is in the Late Interval
                    adjustedTimestamp = new GregorianCalendar();
                    adjustedTimestamp.setTimeInMillis(shiftStop.getTimeInMillis());
                    eventData = EVENT_DATA_SHIFT_STOP;
                    
                }
                else{   //Punch is in the Early Interval
                    if((ots.after(shiftStopGracePeriod)) || ots.equals(shiftStopGracePeriod)){  //Punch is Within the Grace Period
                        adjustedTimestamp = new GregorianCalendar();
                        adjustedTimestamp.setTimeInMillis(shiftStop.getTimeInMillis());
                        eventData = EVENT_DATA_SHIFT_STOP;
                    }
                    else{   //Punch-Out is Early and is Docked
                        adjustedTimestamp = new GregorianCalendar();
                        adjustedTimestamp.setTimeInMillis(shiftStop.getTimeInMillis() - dock);
                        eventData = EVENT_DATA_SHIFT_DOCK;
                    }
                }
            }
            else if((ots.after(lunchStart) && ots.before(lunchStop)) || (ots.equals(lunchStart)) || (ots.equals(lunchStop))){
                
                //Punch Occurs Within the Lunch Period
                
                if(ots.equals(lunchStart)){     
                    adjustedTimestamp = new GregorianCalendar();
                    adjustedTimestamp.setTimeInMillis(originalTimestamp.getTimeInMillis());
                    eventData = EVENT_DATA_LUNCH_START;
                }
                else if(ots.equals(lunchStop)){
                    adjustedTimestamp = new GregorianCalendar();
                    adjustedTimestamp.setTimeInMillis(originalTimestamp.getTimeInMillis());
                    eventData = EVENT_DATA_LUNCH_STOP;
                }
                else{    
                    if(punchTypeId == CLOCKED_OUT){     //Punch is a Clock-Out and is Pushed Back to Lunch Start
                        adjustedTimestamp = new GregorianCalendar();
                        adjustedTimestamp.setTimeInMillis(lunchStart.getTimeInMillis());
                        eventData = EVENT_DATA_LUNCH_START;
                    }
                    else{   //Punch is a Clock-In and is Pushed Back to Lunch Stop
                        adjustedTimestamp = new GregorianCalendar();
                        adjustedTimestamp.setTimeInMillis(lunchStop.getTimeInMillis());
                        eventData = EVENT_DATA_LUNCH_STOP;
                    }
                }
                
            }
            else{
                
                //Punch Does Not Occur Within a Designated Rule Set
                //and is Set to the Nearest Interval
                
                GregorianCalendar nearestBefore = new GregorianCalendar(ots.get(Calendar.YEAR),ots.get(Calendar.MONTH),ots.get(Calendar.DAY_OF_MONTH),ots.get(Calendar.HOUR_OF_DAY),0,0);
                GregorianCalendar nearestAfter = new GregorianCalendar();
                nearestAfter.setTimeInMillis(nearestBefore.getTimeInMillis() + interval);
                
                GregorianCalendar otsNoSecs = new GregorianCalendar(ots.get(Calendar.YEAR),ots.get(Calendar.MONTH),ots.get(Calendar.DAY_OF_MONTH),ots.get(Calendar.HOUR_OF_DAY),ots.get(Calendar.MINUTE),0);

                while(nearestAfter.before(ots)){
                    nearestBefore.setTimeInMillis(nearestBefore.getTimeInMillis() + interval);
                    nearestAfter.setTimeInMillis(nearestAfter.getTimeInMillis() + interval);
                }

                long beforeDiff = ots.getTimeInMillis() - nearestBefore.getTimeInMillis();
                long afterDiff = nearestAfter.getTimeInMillis() - ots.getTimeInMillis();
               
                if(otsNoSecs.equals(nearestBefore) || ots.equals(nearestAfter)){
                    adjustedTimestamp = new GregorianCalendar();
                    adjustedTimestamp.setTimeInMillis(otsNoSecs.getTimeInMillis());
                    eventData = EVENT_DATA_NONE;
                }
                
               else if(beforeDiff < afterDiff){
                    adjustedTimestamp = new GregorianCalendar();
                    adjustedTimestamp.setTimeInMillis(nearestBefore.getTimeInMillis());
                    eventData = EVENT_DATA_INTERVAL_ROUND;
                }
                else{
                    adjustedTimestamp = new GregorianCalendar();
                    adjustedTimestamp.setTimeInMillis(nearestAfter.getTimeInMillis());
                    eventData = EVENT_DATA_INTERVAL_ROUND;
                }
            } 
        }
    }
      
    public String printOriginalTimestamp(){
        
        String pt = this.PUNCH_TYPES[punchTypeId];
        //String dow = this.DAYS_OF_WEEK[];
        String dow = DAYS_OF_WEEK[originalTimestamp.get(Calendar.DAY_OF_WEEK) - 1];
        
        String date = (new SimpleDateFormat(DATE_FORMAT)).format(originalTimestamp.getTimeInMillis());
        
        String output = "#" + badge.getId() + " ";
        output+= pt + ": " + dow + " " + date;
        
        
        return output;
    }
    public String printAdjustedTimestamp(){
        String pt = this.PUNCH_TYPES[punchTypeId];
        String dow = this.DAYS_OF_WEEK[adjustedTimestamp.get(Calendar.DAY_OF_WEEK) - 1];
        String date = (new SimpleDateFormat(DATE_FORMAT)).format(adjustedTimestamp.getTimeInMillis());
        
        String output = "#" + badge.getId() + " ";
        output+= pt + ": " + dow + " " + date + " ";
        output+= "(" + this.eventData + ")";
        
        
        return output;
    }
    
    public String getOriginalformatteddate(){
        String date = (new SimpleDateFormat(DATE_FORMAT)).format(originalTimestamp);
        
        return date;
    }

    public int getId() {
        return id;
    }

    public int getTerminalid() {
        return terminalId;
    }

    public Badge getBadge() {
        return badge;
    }
    public String getBadgeid(){
        return this.badge.getId();
    }
    public String getBadgeDesc(){
        return this.badge.getDescription();
    }

    public GregorianCalendar getOriginaltimestamp() {
        return originalTimestamp;
    }

    public GregorianCalendar getAdjustedtimestamp() {
        return adjustedTimestamp;
    }

    public int getPunchtypeid() {
        return punchTypeId;
    }
    
    public String getEventdata(){
        return this.eventData;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTerminalid(int terminalId) {
        this.terminalId = terminalId;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    public void setOriginaltimestamp(GregorianCalendar originalTimestamp) {
        this.originalTimestamp = originalTimestamp;
    }

    public void setAdjustedtimestamp(GregorianCalendar adjustedTimeStamp) {
        this.adjustedTimestamp = adjustedTimeStamp;
    }

    public void setPunchtypeid(int punchTypeId) {
        this.punchTypeId = punchTypeId;
    }
    
    
}
