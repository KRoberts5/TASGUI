/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tas;

/**
 *
 * @author Brendan
 */
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;

public class Absenteeism {
    
    public static boolean ROLL_BACK = false;
    public static String DATE_FORMAT = "MM-dd-yyyy";
    
    private String badgeId;
    private GregorianCalendar payPeriod;
    private double percentage;
    
    public Absenteeism(String badgeId, long ts, double percentage){
        this.badgeId = badgeId;
        
        payPeriod = new GregorianCalendar(); 
        payPeriod.setTimeInMillis(getPayPeriodStart(ts));
        
        this.percentage = percentage;
    }
    
    public String toString(){
        String output = "";
        String date = new SimpleDateFormat(DATE_FORMAT).format(payPeriod.getTimeInMillis());
        String decimal = String.format("%.2f", percentage);
        
        output += "#" + badgeId;
        output += " (Pay Period Starting " + date + "): ";
        output += decimal + "%";
        
        return output;
    }

    public String getBadgeId() {
        return badgeId;
    }

    public GregorianCalendar getPayPeriod() {
        return payPeriod;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setBadgeId(String badgeId) {
        this.badgeId = badgeId;
    }

    public void setPayPeriod(GregorianCalendar payPeriod) {
        this.payPeriod = payPeriod;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
    public static long getPayPeriodStart(long ts){
        GregorianCalendar payPeriod = new GregorianCalendar();
        payPeriod.setTimeInMillis(ts);
        
        payPeriod.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
        payPeriod.set(Calendar.HOUR_OF_DAY, 0);
        payPeriod.set(Calendar.MINUTE, 0);
        payPeriod.set(Calendar.SECOND, 0);
        payPeriod.set(Calendar.MILLISECOND, 0);
        
        return payPeriod.getTimeInMillis();
    }

    
}
