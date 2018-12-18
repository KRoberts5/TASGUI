/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tas.model;

/**
 *
 * @author Brendan
 */
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.*;
import java.lang.Number;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class TASLogic {
    
    public static final int MILLIS_TO_MIN = 60000;
    public static final long MILLIS_TO_HOURS = 3600000;
    public static final int MILLIS_TO_SECS = 1000;
    
    public static final int NUM_DAYS = 7;
    public static final int WORK_WEEK = 5;
    
    public static int calculateTotalMinutes(ArrayList<Punch> punchList,Shift shift){
        
        /*
        *Calculates the total minutes an employee work in a day
        *Does the bulk of the calcualtion in terms of Milliseconds
        *Before converting the total of milliseconds to minutes
        */
        
        
        int totalMinutes = 0;
        Long totalMillis = new Long(0);
        Punch previous = null;
        boolean inPair = false;
        boolean lunchOut = false;
        boolean lunchIn = false;

        
        long currentDayMillis = 0;
        int day = punchList.get(0).getOriginaltimestamp().get(Calendar.DAY_OF_WEEK);
        //boolean multipleDays = false;
        
        long lunchDeduct =0;
        long lunchLength = 0;
        
        for(Punch p: punchList){
            
            int currentDay = p.getOriginaltimestamp().get(Calendar.DAY_OF_WEEK);
            
            if(currentDay != day){
                
                day = currentDay;
                if(!lunchOut || !lunchIn){
                    if(currentDayMillis> lunchDeduct){
                        totalMillis -= lunchLength;
                    }
                }
                lunchOut = lunchIn = false;
                currentDayMillis = 0;
            }
            
            lunchDeduct = shift.getLunchDeduct(day) * TASLogic.MILLIS_TO_MIN;
            lunchLength = shift.getLunchLength(day) * TASLogic.MILLIS_TO_MIN;
            
            p.adjust(shift);
            
            
            if(p.getPunchtypeid() == Punch.CLOCKED_IN && !inPair){
                previous = p;
                inPair = true;
            }
            else if(inPair ){
                
                if(p.getPunchtypeid() == Punch.CLOCKED_OUT){
                    totalMillis += p.getAdjustedtimestamp().getTimeInMillis() - previous.getAdjustedtimestamp().getTimeInMillis();
                    currentDayMillis += p.getAdjustedtimestamp().getTimeInMillis() - previous.getAdjustedtimestamp().getTimeInMillis();
                    inPair = false;
                }
                else if(p.getPunchtypeid() == Punch.TIMED_OUT)
                    inPair = false;
            }
            
            if(p.getEventdata().equals(Punch.EVENT_DATA_LUNCH_START))
                lunchOut = true;
            else if(p.getEventdata().equals(Punch.EVENT_DATA_LUNCH_STOP))
                lunchIn = true;
        }    
        
        if(!lunchOut && !lunchIn){
            if(currentDayMillis > lunchDeduct)
                totalMillis -= lunchLength;
        }
        
        totalMinutes = (new Long(totalMillis/TASLogic.MILLIS_TO_MIN)).intValue();
        
        return totalMinutes;
    }
    
    public static GregorianCalendar forceXafterY(GregorianCalendar x, GregorianCalendar y){
        if(x.before(y)){
            x.setTimeInMillis(x.getTimeInMillis() + 24*TASLogic.MILLIS_TO_HOURS);
        }
        
        return x;
    }
    
    public static String getPunchListAsJSON(ArrayList<Punch> dailypunchlist){
        
        ArrayList<HashMap<String, String>> jsonData = getPunchListData(dailypunchlist);
        
        String json = JSONValue.toJSONString(jsonData);
        return json;
    }
    public static String getPunchListPlusTotalsAsJSON(ArrayList<Punch> punchList,Shift s){
        ArrayList<HashMap<String, String>> jsonData = getPunchListData(punchList);
        
        HashMap<String, String> totalData = new HashMap();
        
        double absenteeism = calculateAbsenteeism(punchList,s);
        String absenteeismString = new DecimalFormat("#0.00").format(absenteeism) + "%";
        totalData.put("absenteeism", absenteeismString);
        
        int totalMinutes = calculateTotalMinutes(punchList,s);
        totalData.put("totalminutes", String.valueOf(totalMinutes));
        jsonData.add(totalData);
        
        String json = JSONValue.toJSONString(jsonData);
        return json;
    }
    public static ArrayList<HashMap<String,String>> getPunchListData(ArrayList<Punch> punchList){
        ArrayList<HashMap<String,String>> punchListData = new ArrayList<>();
        HashMap<String,String> punchData;
        
        for(int i = 0; i < punchList.size(); ++i){
            Punch punch = punchList.get(i);
            punchData = new HashMap<>();
            
            punchData.put("id", String.valueOf(punch.getId()));
            punchData.put("badgeid", punch.getBadgeid());
            punchData.put("terminalid", String.valueOf(punch.getTerminalid()));
            punchData.put("punchtypeid",String.valueOf(punch.getPunchtypeid()));
            punchData.put("punchdata", punch.getEventdata());
            punchData.put("originaltimestamp", String.valueOf(punch.getOriginaltimestamp().getTimeInMillis()));
            punchData.put("adjustedtimestamp", String.valueOf(punch.getAdjustedtimestamp().getTimeInMillis()));
            
            punchListData.add(punchData);
        }
        
        return punchListData;
    }
    public static double calculateAbsenteeism(ArrayList<Punch> punchList, Shift s){
        
        double percentage = 0;
        int shiftLength = s.getShiftLength();
        double minutesRequired = 0;
        
        for(int i = Calendar.MONDAY; i < Calendar.SATURDAY; ++i){
            minutesRequired += s.getShiftLength(i);
        }
        
        double minutesWorked = calculateTotalMinutes(punchList,s);
        
        if(minutesRequired > 0){
            percentage = 100 - ((minutesWorked/minutesRequired)*100);
        }
        
        return percentage; 
    }
}
