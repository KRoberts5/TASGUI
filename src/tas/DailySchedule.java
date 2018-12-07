/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tas;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.lang.Number;
import java.util.Calendar;
import java.util.HashMap;
import java.util.GregorianCalendar;
import static tas.Shift.LUNCH_START;
import static tas.Shift.LUNCH_STOP;
import static tas.Shift.SHIFT_START;
import static tas.Shift.SHIFT_STOP;
import static tas.Shift.TIME_FORMAT;

public class DailySchedule {
    
    private GregorianCalendar start;
    private GregorianCalendar stop;
    private int interval;
    private int gracePeriod;
    private int dock;
    private GregorianCalendar lunchStart;
    private GregorianCalendar lunchStop;
    private int lunchLength;
    private int lunchDeduct;
    private int shiftLength;
    
    public DailySchedule(GregorianCalendar start, GregorianCalendar stop, int interval, int gracePeriod, int dock, GregorianCalendar lunchStart, GregorianCalendar lunchStop,  int lunchDeduct){
        this.start = start;
        this.stop = stop;
        this.interval = interval;
        this.gracePeriod = gracePeriod;
        this.dock = dock;
        this.lunchStart = lunchStart;
        this.lunchStop = lunchStop;
        this.lunchDeduct = lunchDeduct;
        
        this.updateLengthVariables();
    }
    
    public String toString(String description){
        
        
        
        GregorianCalendar stop = TASLogic.forceXafterY(this.stop, start);
        GregorianCalendar lunchStart = TASLogic.forceXafterY(this.lunchStart, start);
        GregorianCalendar lunchStop = TASLogic.forceXafterY(this.lunchStop, start);
        
        long startMillis = this.start.getTimeInMillis();
        long stopMillis = stop.getTimeInMillis();
        long difference = (stopMillis - startMillis)/TASLogic.MILLIS_TO_MIN;
        
        long lunchStartMillis = lunchStart.getTimeInMillis();
        long lunchStopMillis = lunchStop.getTimeInMillis();
        long lunchDiff = (lunchStopMillis - lunchStartMillis)/TASLogic.MILLIS_TO_MIN;
        
        String startHour = (new SimpleDateFormat(TIME_FORMAT)).format(start.getTimeInMillis());
        String stopHour = (new SimpleDateFormat(TIME_FORMAT)).format(stop.getTimeInMillis());
        String lunchStartHour = (new SimpleDateFormat(TIME_FORMAT)).format(lunchStart.getTimeInMillis());
        String lunchStopHour = (new SimpleDateFormat(TIME_FORMAT)).format(lunchStop.getTimeInMillis());
        
        String output = description + ": " ;
        output += startHour + " - " + stopHour;
        output += " (" + difference + " minutes); ";
        output += "Lunch: " + lunchStartHour + " - " + lunchStopHour + " (" + lunchDiff + " minutes)";
        
        return output;
    }
    
    public HashMap<String,GregorianCalendar> getParticularShiftValues(GregorianCalendar ts){
        HashMap<String,GregorianCalendar> shiftValues = new HashMap();
        
        long shiftLength = this.stop.getTimeInMillis() - this.start.getTimeInMillis();
        int lunchLength = this.lunchLength;
        long lunchStarts = this.lunchStart.getTimeInMillis() - this.start.getTimeInMillis();
        
        long startDiff = 0;
        long stopDiff = 0;
        
        int year = ts.get(Calendar.YEAR);
        int month = ts.get(Calendar.MONTH);
        int date = ts.get(Calendar.DAY_OF_MONTH);
        
        int startHour = this.start.get(Calendar.HOUR_OF_DAY);
        int startMin = this.start.get(Calendar.MINUTE);
        GregorianCalendar shiftStart = new GregorianCalendar(year,month,date,startHour,startMin,0);
        
        
        int lunchStartHour = this.lunchStart.get(Calendar.HOUR_OF_DAY);
        int lunchStartMin = this.lunchStart.get(Calendar.MINUTE);
        GregorianCalendar lunchStart = new GregorianCalendar(year,month,date,lunchStartHour,lunchStartMin,0);
        GregorianCalendar lunchStop = new GregorianCalendar();
        lunchStop.setTimeInMillis(lunchStart.getTimeInMillis() + lunchLength*TASLogic.MILLIS_TO_MIN);
        
        int stopHour = this.stop.get(Calendar.HOUR_OF_DAY);
        int stopMin = this.stop.get(Calendar.MINUTE);
        GregorianCalendar shiftStop = new GregorianCalendar(year,month,date,stopHour,stopMin,0);
        
        
        if(shiftStop.after(shiftStart)){
            shiftValues.put(SHIFT_START, shiftStart);
            shiftValues.put(LUNCH_START, lunchStart);
            shiftValues.put(LUNCH_STOP, lunchStop);
            shiftValues.put(SHIFT_STOP, shiftStop);
        }
        else{
            if(ts.after(shiftStart) || ts.equals(shiftStart))
                startDiff = ts.getTimeInMillis() - shiftStart.getTimeInMillis();
            else
                startDiff = shiftStart.getTimeInMillis() - ts.getTimeInMillis();

            if(ts.after(shiftStop) || ts.equals(shiftStop))
                stopDiff = ts.getTimeInMillis() - shiftStop.getTimeInMillis();
            else
                stopDiff = shiftStop.getTimeInMillis() - ts.getTimeInMillis();

            if(startDiff < stopDiff){
                shiftStop = new GregorianCalendar();
                shiftStop.setTimeInMillis(shiftStop.getTimeInMillis() + 24*TASLogic.MILLIS_TO_HOURS);
            }
                
            else{
                shiftStart = new GregorianCalendar();
                shiftStart.setTimeInMillis(shiftStart.getTimeInMillis() - 24*TASLogic.MILLIS_TO_HOURS);
            }
                
            
            lunchStart = TASLogic.forceXafterY(lunchStart, shiftStart);
            lunchStop = TASLogic.forceXafterY(lunchStop, lunchStart);
            
            
            shiftValues.put(SHIFT_START, shiftStart);
            shiftValues.put(LUNCH_START, lunchStart);
            shiftValues.put(LUNCH_STOP, lunchStop);
            shiftValues.put(SHIFT_STOP, shiftStop);
        }
        
        
        return shiftValues;
    }
    
    private void updateLengthVariables(){
        Long lunchLength = (lunchStop.getTimeInMillis() - lunchStart.getTimeInMillis())/TASLogic.MILLIS_TO_MIN;
        this.lunchLength = lunchLength.intValue();
        
        GregorianCalendar shiftStop = TASLogic.forceXafterY(stop, start);
        Long shiftLength = (shiftStop.getTimeInMillis() - start.getTimeInMillis())/TASLogic.MILLIS_TO_MIN;
        shiftLength -= lunchLength;
        this.shiftLength = shiftLength.intValue();
    }
    
    public GregorianCalendar getStart() {
        return start;
    }

    public GregorianCalendar getStop() {
        return stop;
    }

    public int getInterval() {
        return interval;
    }

    public int getGracePeriod() {
        return gracePeriod;
    }

    public int getDock() {
        return dock;
    }

    public GregorianCalendar getLunchStart() {
        return lunchStart;
    }

    public GregorianCalendar getLunchStop() {
        return lunchStop;
    }

    public int getLunchLength() {
        return lunchLength;
    }

    public int getLunchDeduct() {
        return lunchDeduct;
    }
    public int getShiftLength(){
        return shiftLength;
    }
    
    public void setStart(GregorianCalendar start) {
        this.start = start;
        
        this.updateLengthVariables();
    }

    public void setStop(GregorianCalendar stop) {
        this.stop = stop;
        this.updateLengthVariables();
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void setGracePeriod(int gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public void setDock(int dock) {
        this.dock = dock;
    }

    public void setLunchStart(GregorianCalendar lunchStart) {
        this.lunchStart = lunchStart;
        this.updateLengthVariables();
    }

    public void setLunchStop(GregorianCalendar lunchStop) {
        this.lunchStop = lunchStop;
        this.updateLengthVariables();
    }

    public void setLunchLength(int lunchLength) {
        this.lunchLength = lunchLength;
    }

    public void setLunchDeduct(int lunchDeduct) {
        this.lunchDeduct = lunchDeduct;
    }
}
