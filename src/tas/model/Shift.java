package tas.model;

/**
 *
 * @author Brendan
 */

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.lang.Number;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class Shift {
    
    public static final String TIME_FORMAT = "HH:mm";
    public static final String SHIFT_START = "Shift Start";
    public static final String LUNCH_START = "Lunch Start";
    public static final String LUNCH_STOP = "Lunch Stop";
    public static final String SHIFT_STOP = "Shift Stop";
    
    private int id;
    private String description;
    private DailySchedule defaultschedule;
    private HashMap<Integer, DailySchedule> scheduleList;
    
    
    
    public Shift(int id, String description, DailySchedule defaultschedule){
        this.id = id;
        this.description = description;
        this.defaultschedule = defaultschedule;
        
        
        
        this.initScheduleList(defaultschedule);
       
    }
    
    private void initScheduleList(DailySchedule ds){
        scheduleList = new HashMap<>();
        
        DailySchedule monday = new DailySchedule(ds.getStart(),ds.getStop(),ds.getInterval(),ds.getGracePeriod(),ds.getDock(),ds.getLunchStart(),ds.getLunchStop(),ds.getLunchDeduct());
        DailySchedule tuesday = new DailySchedule(ds.getStart(),ds.getStop(),ds.getInterval(),ds.getGracePeriod(),ds.getDock(),ds.getLunchStart(),ds.getLunchStop(),ds.getLunchDeduct());
        DailySchedule wednesday = new DailySchedule(ds.getStart(),ds.getStop(),ds.getInterval(),ds.getGracePeriod(),ds.getDock(),ds.getLunchStart(),ds.getLunchStop(),ds.getLunchDeduct());
        DailySchedule thursday = new DailySchedule(ds.getStart(),ds.getStop(),ds.getInterval(),ds.getGracePeriod(),ds.getDock(),ds.getLunchStart(),ds.getLunchStop(),ds.getLunchDeduct());
        DailySchedule friday = new DailySchedule(ds.getStart(),ds.getStop(),ds.getInterval(),ds.getGracePeriod(),ds.getDock(),ds.getLunchStart(),ds.getLunchStop(),ds.getLunchDeduct());
        DailySchedule saturday = new DailySchedule(ds.getStart(),ds.getStop(),ds.getInterval(),ds.getGracePeriod(),ds.getDock(),ds.getLunchStart(),ds.getLunchStop(),ds.getLunchDeduct());
        DailySchedule sunday = new DailySchedule(ds.getStart(),ds.getStop(),ds.getInterval(),ds.getGracePeriod(),ds.getDock(),ds.getLunchStart(),ds.getLunchStop(),ds.getLunchDeduct());
        
        scheduleList.put(Calendar.MONDAY, monday);
        scheduleList.put(Calendar.TUESDAY, tuesday);
        scheduleList.put(Calendar.WEDNESDAY, wednesday);
        scheduleList.put(Calendar.THURSDAY, thursday);
        scheduleList.put(Calendar.FRIDAY, friday);
        scheduleList.put(Calendar.SATURDAY, saturday);
        scheduleList.put(Calendar.SUNDAY, sunday);
        
    }
    
    public String toString(){
        
        return defaultschedule.toString(description);
    }
    public void printWeeklySchedule(){
        for(HashMap.Entry<Integer, DailySchedule> e: scheduleList.entrySet()){
            System.out.println(e.getValue().toString(description));
        }
    }
    
    public HashMap<String,GregorianCalendar> getParticularShiftValues(GregorianCalendar ts){
        HashMap<String,GregorianCalendar> shiftValues = defaultschedule.getParticularShiftValues(ts);
        
        return shiftValues;
    }
    
    public HashMap<String,GregorianCalendar> getParticularShiftValues(int day, GregorianCalendar ts){
        
        DailySchedule dailySchedule = scheduleList.get(day);
        HashMap<String,GregorianCalendar> shiftValues = dailySchedule.getParticularShiftValues(ts); 
        
        return shiftValues;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public GregorianCalendar getStart() {
        return defaultschedule.getStart();
    }
    
    public GregorianCalendar getStart(int day){
        return scheduleList.get(day).getStart();
    }

    public GregorianCalendar getStop() {
        return defaultschedule.getStop();
    }
    
    public GregorianCalendar getStop(int day){
        return scheduleList.get(day).getStop();
    }

    public int getInterval() {
        return defaultschedule.getInterval();
    }
    
    public int getInterval(int day){
        return scheduleList.get(day).getInterval();
    }

    public int getGracePeriod() {
        return defaultschedule.getGracePeriod();
    }
    public int getGracePeriod(int day){
        return scheduleList.get(day).getGracePeriod();
    }

    public int getDock() {
        return defaultschedule.getDock();
    }
    public int getDock(int day){
        return scheduleList.get(day).getDock();
    }

    public GregorianCalendar getLunchStart() {
        return defaultschedule.getLunchStart();
    }
    
    public GregorianCalendar getLunchStart(int day){
        return scheduleList.get(day).getLunchStart();
    }

    public GregorianCalendar getLunchStop() {
        return defaultschedule.getLunchStop();
    }
    
    public GregorianCalendar getLunchStop(int day){
        return scheduleList.get(day).getLunchStop();
    }

    public int getLunchLength() {
        return defaultschedule.getLunchLength();
    }
    
    public int getLunchLength(int day){
        return scheduleList.get(day).getLunchLength();
    }

    public int getLunchDeduct() {
        return defaultschedule.getLunchDeduct();
    }
    
    public int getLunchDeduct(int day){
        return scheduleList.get(day).getLunchDeduct();
    }
    public int getShiftLength(){
        return defaultschedule.getShiftLength();
    }
    
    public int getShiftLength(int day){
        return scheduleList.get(day).getShiftLength();
    }
    
    public void setStart(GregorianCalendar start) {
        defaultschedule.setStart(start);
    }
    
    public void setStart(int day, GregorianCalendar start){
        scheduleList.get(day).setStart(start);
    }

    public void setStop(GregorianCalendar stop) {
        defaultschedule.setStop(stop);
    }
    public void setStop(int day, GregorianCalendar stop){
        scheduleList.get(day).setStop(stop);
    }

    public void setInterval(int interval) {
        defaultschedule.setInterval(interval);
    }
    
    public void setInterval(int day, int interval){
        scheduleList.get(day).setInterval(interval);
    }

    public void setGracePeriod(int gracePeriod) {
        defaultschedule.setGracePeriod(gracePeriod);
    }
    public void setGracePeriod(int day, int gracePeriod){
        scheduleList.get(day).setGracePeriod(gracePeriod);
    }

    public void setDock(int dock) {
        defaultschedule.setDock(dock);
    }
    
    public void setDock(int day,int dock){
        scheduleList.get(day).setDock(dock);
    }

    public void setLunchStart(GregorianCalendar lunchStart) {
        defaultschedule.setLunchStart(lunchStart);
    }
    public void setLunchStart(int day, GregorianCalendar lunchStart){
        scheduleList.get(day).setLunchStart(lunchStart);
    }

    public void setLunchStop(GregorianCalendar lunchStop) {
        defaultschedule.setLunchStop(lunchStop);
    }
    
    public void setLunchStop(int day, GregorianCalendar lunchStop){
        scheduleList.get(day).setLunchStop(lunchStop);
    }

    public void setLunchLength(int lunchLength) {
        defaultschedule.setLunchLength(lunchLength);
    }
    public void setLunchLength(int day, int lunchLength){
        scheduleList.get(day).setLunchLength(lunchLength);
    }

    public void setLunchDeduct(int lunchDeduct) {
        defaultschedule.setLunchDeduct(lunchDeduct);
    }
    
    public void setLunchDeduct(int day, int lunchDeduct){
        scheduleList.get(day).setLunchDeduct(lunchDeduct);
    }

}