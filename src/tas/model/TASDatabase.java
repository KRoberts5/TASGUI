/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tas.model;


import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TASDatabase {
    
    private Connection conn = null;
    
    public TASDatabase(){
        
        try{
           
            
            String url = "jdbc:mysql://localhost/tas";
            String username = "tasuser";
            String password = "CS310";
            conn = DriverManager.getConnection(url,username,password);
        }
        catch(Exception e){System.err.println(e.getMessage());}
    }
    

    
    public void close(){
        
        try{
            if(conn != null)
                conn.close();
        }
        catch(Exception e){System.err.println(e.getMessage());}
    }
    
    public Punch getPunch(int id){
        Punch punch = null;
        
        try{
            PreparedStatement pst = conn.prepareStatement("SELECT *, UNIX_TIMESTAMP(originaltimestamp) AS ts FROM punch WHERE id = ?;");
            pst.setInt(1,id);
            
            ResultSet result = pst.executeQuery();
            if(result.next()){
                String badgeId = result.getString("badgeid");
            
                int terminalId = result.getInt("terminalid");
                int ptid = result.getInt("punchtypeid");
                long ts = result.getLong("ts");
                Badge badge = this.getBadge(badgeId);
            
                ts = ts*1000;
                GregorianCalendar ots = new GregorianCalendar();
                ots.setTimeInMillis(ts);
            
                punch = new Punch(badge,id, terminalId,ots,ptid);
            }
            
            result.close();
            pst.close();
        }
        catch(Exception e){System.err.println(e.getMessage());}
        
        
        return punch;
    }
    public Badge getBadge(String id){
        Badge badge = null;
        
        try{
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM badge WHERE id=?;");
            pst.setString(1,id);
            
            ResultSet result = pst.executeQuery();
            if(result.next()){
                String badgeDesc = result.getString("description");
                badge = new Badge(id, badgeDesc);
            }   
            
            result.close();
            pst.close();
        }
        catch(Exception e){System.err.println(e.getMessage());}
        
        return badge;
    }
    
    public ArrayList<String> getBadgeIdList(){
        ArrayList<String> badgeIds = new ArrayList();
        
        try{
            PreparedStatement pst = conn.prepareStatement("Select `id` FROM badge;");
            
            ResultSet result = pst.executeQuery();
            while(result.next()){
                badgeIds.add(result.getString("id"));
            }
            
            result.close();
            pst.close();
        }
        catch(Exception e){
            System.err.println(e.toString());
        }
        
        return badgeIds;
    }
    
    public Shift getShift(int id){
        Shift shift = null;
        try{
            PreparedStatement pst = conn.prepareStatement("SELECT shift.id, shift.description, dailyschedule.graceperiod, dailyschedule.dock, dailyschedule.`interval`,\n" +
                "UNIX_TIMESTAMP(dailyschedule.`start` ) AS `start`, UNIX_TIMESTAMP(dailyschedule.`stop` ) AS `stop`,\n" +
                "UNIX_TIMESTAMP(dailyschedule.lunchstart) AS lunchstart, UNIX_TIMESTAMP(dailyschedule.lunchstop) AS lunchstop, dailyschedule.lunchdeduct\n" +
                "FROM shift\n" +
                "INNER JOIN dailyschedule\n" +
                "ON shift.dailyscheduleid = dailyschedule.id\n" +
                "WHERE shift.id = ?;");
            pst.setInt(1, id);
            
            ResultSet result = pst.executeQuery();
            if(result.next()){
                String desc = result.getString("description");
            
                GregorianCalendar start = new GregorianCalendar();
                start.setTimeInMillis(result.getLong("start") *1000);
                
                GregorianCalendar stop = new GregorianCalendar();
                stop.setTimeInMillis(result.getLong("stop") *1000);
                
                int interval = result.getInt("interval");
                int gracePeriod = result.getInt("graceperiod");
                int dock = result.getInt("dock");
                
                GregorianCalendar lunchStart = new GregorianCalendar();
                lunchStart.setTimeInMillis(result.getLong("lunchstart") *1000);
                
                GregorianCalendar lunchStop = new GregorianCalendar();
                lunchStop.setTimeInMillis(result.getLong("lunchstop") *1000);
                
                
                int lunchDeduct = result.getInt("lunchdeduct");
                DailySchedule defaultschedule = new DailySchedule(start,stop,interval,gracePeriod,dock,lunchStart,lunchStop,lunchDeduct );
                shift = new Shift(id,desc,defaultschedule);
            }
            
            result.close();
            pst.close();
        }
        catch(Exception e){System.err.println(e.getMessage());}
        
        return shift;
    }
    public Shift getShift(Badge badge){
        Shift shift = null;
        try{
        String badgeID = badge.getId();
        
        PreparedStatement pst = conn.prepareStatement("SELECT shiftid FROM employee WHERE badgeid =?;");
        pst.setString(1, badgeID);
        
        ResultSet result = pst.executeQuery();
        if(result.next()){
        
            int shiftId = result.getInt("shiftid");
        
            shift = this.getShift(shiftId);
        }
        
        result.close();
        pst.close();
        }
        catch(Exception e){System.err.println(e.getMessage());}
        
        return shift;
    }
    
    public Shift getShift(Badge badge, long ts){
        Shift shift = this.getShift(badge);
        
        String badgeId = badge.getId();
        
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(ts);
        gc.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
        gc.set(Calendar.HOUR_OF_DAY, 0);
        gc.set(Calendar.MINUTE, 0);
        gc.set(Calendar.SECOND, 0);
        gc.set(Calendar.MILLISECOND, 0);
        
        String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(gc.getTimeInMillis());
        
        try{
            
            //Checking for recurring overrides for all employees
            String sql = "SELECT * FROM scheduleoverride "
                + "WHERE badgeid IS NULL AND `start` <= ? AND `end` IS NULL;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, date);
            
            ResultSet result = pst.executeQuery();
            while(result.next()){
                int scheduleId = result.getInt("dailyscheduleid");
                int day = result.getInt("day");
                
                this.updateDailySchedule(shift, scheduleId, day);
                
            }
            result.close();
            pst.close();
        }
        catch(Exception e){System.err.println(e.toString());}
            
        try{
            //Checking for recurring overrides for a singular employee
            String sql = "SELECT * FROM scheduleoverride "
                    + "WHERE badgeid = ? and `start` <= ? AND `end` IS NULL;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, badgeId);
            pst.setString(2, date);
            
            ResultSet result = pst.executeQuery();
            while(result.next()){
                
                
                
                int scheduleId = result.getInt("dailyscheduleid");
                int day = result.getInt("day");
                
                this.updateDailySchedule(shift, scheduleId, day);
            }
            result.close();
            pst.close();
        }
        catch(Exception e){System.err.println(e.toString());}
        
        try{
            String sql = "SELECT * FROM scheduleoverride "
                    + "WHERE badgeid IS NULL AND `start` <= ? AND `end` >= ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, date);
            pst.setString(2, date);
            
            ResultSet result = pst.executeQuery();
            
            
            while(result.next()){
                
                
                int scheduleId = result.getInt("dailyscheduleid");
                int day = result.getInt("day");
                
                this.updateDailySchedule(shift, scheduleId, day);
            }
            result.close();
            pst.close();
        }
        catch(Exception e){System.err.println(e.toString());}
        
        try{
            String sql = "SELECT * FROM scheduleoverride "
                    + "WHERE badgeid = ? AND `start` <= ? AND `end` >= ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, badgeId);
            pst.setString(2, date);
            pst.setString(3, date);
            
            ResultSet result = pst.executeQuery();
            while(result.next()){
                int scheduleId = result.getInt("dailyscheduleid");
                int day = result.getInt("day");
                
                this.updateDailySchedule(shift, scheduleId, day);
            }
            result.close();
            pst.close();
        }
        catch(Exception e){System.err.println(e.toString());}
        
        return shift;
    }
    public Shift getShift(Badge badge, Punch punch){
        long ts = punch.getOriginaltimestamp().getTimeInMillis();
        
        Shift shift = this.getShift(badge,ts);
        
        return shift;
    }
    
    public void updateDailySchedule(Shift shift, int dailyScheduleId, int day){
        
        //System.out.println("Day: " + day);
        //System.out.println(shift.getShiftLength(4));
        
        String sql = "SELECT `interval`, graceperiod,dock,lunchdeduct,\n" +
"UNIX_TIMESTAMP(`start`) AS `start`, UNIX_TIMESTAMP(stop) AS stop,\n" +
"UNIX_TIMESTAMP(lunchstart) AS lunchstart, UNIX_TIMESTAMP(lunchstop) AS lunchstop\n" +
"FROM dailyschedule WHERE id = ?;";
        
        try{
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, dailyScheduleId);
            
            ResultSet result = pst.executeQuery();
            //System.out.println("Update Schedule try");
            if(result.next()){
                //System.out.println("Update Schedule if");
                
                
                
                GregorianCalendar start = new GregorianCalendar();
                start.setTimeInMillis(result.getLong("start") * TASLogic.MILLIS_TO_SECS);
                
                
                GregorianCalendar stop = new GregorianCalendar();
                stop.setTimeInMillis(result.getLong("stop") * TASLogic.MILLIS_TO_SECS);
                
                int interval = result.getInt("interval");
                int graceperiod = result.getInt("graceperiod");
                int dock = result.getInt("dock");
                
                GregorianCalendar lunchStart = new GregorianCalendar();
                lunchStart.setTimeInMillis(result.getLong("lunchstart") * TASLogic.MILLIS_TO_SECS);
                
                GregorianCalendar lunchStop = new GregorianCalendar();
                lunchStop.setTimeInMillis(result.getLong("lunchstop") * TASLogic.MILLIS_TO_SECS);
                
                
                int lunchDeduct = result.getInt("lunchdeduct");
                
                shift.setStart(day, start);
                shift.setStop(day, stop);
                shift.setInterval(day, interval);
                shift.setGracePeriod(day, graceperiod);
                shift.setDock(day, dock);
                shift.setLunchStart(day, lunchStart);
                shift.setLunchStop(day, lunchStop);
                shift.setLunchDeduct(day, lunchDeduct);
            }
            result.close();
            pst.close();
        }
        catch(Exception e){System.err.println(e.toString());}
        
        //System.out.println(shift.getShiftLength(4));
        
        
    }
    
    public int insertPunch(Punch punch){
        String badgeId = punch.getBadge().getId();
        int terminalId = punch.getTerminalid();
        GregorianCalendar ots = punch.getOriginaltimestamp();
        int ptid = punch.getPunchtypeid();
        
        try{
            int punchId = 0;
            int results = 0;
            ResultSet keys;
            String sql = "INSERT INTO punch (terminalid,badgeid,originaltimestamp,punchtypeid) VALUES (?,?,?,?);";
            PreparedStatement pst = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
                      
            pst.setInt(1,terminalId);
            pst.setString(2, badgeId);
            pst.setString(3, (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(ots.getTimeInMillis()));
            pst.setInt(4,ptid);
            
            results = pst.executeUpdate();
                       
            if(results == 1){
                keys = pst.getGeneratedKeys();
                if(keys.next()){
                    punchId = keys.getInt(1);
                    punch.setId(punchId);
                }
            }
            
            pst.close();
        }
        catch(Exception e){System.err.println(e.getMessage());}
        
        return punch.getId();
    }
    public ArrayList getDailyPunchList(Badge b, long ts){
        ArrayList<Punch> punchList = new ArrayList();
        
        String badgeId = b.getId();
        
        long followingTimestamp = ts + 24*TASLogic.MILLIS_TO_HOURS;
        String date = new SimpleDateFormat("yyyy-MM-dd").format(ts);
        String followingDate = new SimpleDateFormat("yyyy-MM-dd").format(followingTimestamp);
        
  
        try{
            PreparedStatement pst1 = conn.prepareStatement("SELECT id FROM punch WHERE badgeid = ? AND originaltimestamp LIKE ? ORDER BY originaltimestamp;"); 
            pst1.setString(1, badgeId);
            pst1.setString(2, date + "%"); 
            
            ResultSet result1 = pst1.executeQuery();
            while(result1.next()){
                int punchId = result1.getInt("id");
                Punch punch = this.getPunch(punchId);
                punchList.add(punch);
            }
            
            PreparedStatement pst2 = conn.prepareStatement("SELECT * FROM punch WHERE badgeid = ? AND originaltimestamp LIKE ? LIMIT 1;");
            pst2.setString(1, badgeId);
            pst2.setString(2, followingDate + "%");
            
            ResultSet result2 = pst2.executeQuery();
            if(result2.next()){
                if(result2.getInt("punchtypeid") == Punch.CLOCKED_OUT){
                    int punchId = result2.getInt("id");
                    Punch punch = this.getPunch(punchId);
                    punchList.add(punch);
                }
            }
                
            
            result1.close();
            pst1.close();
            result2.close();
            pst2.close();
        }
        catch(Exception e){System.err.println(e.getMessage());}
        
        return punchList;
    }
    public ArrayList<Punch> getPayPeriodPunchList(Badge b, long ts){
        ArrayList<Punch> punchList = new ArrayList();
        
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(ts);
        gc.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
        gc.set(Calendar.HOUR_OF_DAY, 0);
        gc.set(Calendar.MINUTE, 0);
        gc.set(Calendar.SECOND, 0);
        gc.set(Calendar.MILLISECOND, 0);
        
        for(int i = 0; i < TASLogic.NUM_DAYS; ++i){
            ArrayList<Punch> dayPunches = getDailyPunchList(b,gc.getTimeInMillis());
            punchList.addAll(dayPunches);

            gc.roll(Calendar.DAY_OF_WEEK, true);
        }
        
        return punchList;
    }
    public Absenteeism getAbsenteeism(String badgeId, long ts){
         Absenteeism absenteeism = null;
        String sql = "SELECT * FROM absenteeism WHERE badgeid = ? AND payperiod = ?;";
        
        long payPeriodStart = Absenteeism.getPayPeriodStart(ts);
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(payPeriodStart);
        
        try{
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, badgeId);
            pst.setString(2, date);
            
            ResultSet result = pst.executeQuery();
            if(result.next()){
                double percentage = result.getDouble("percentage");
                absenteeism = new Absenteeism(badgeId,ts,percentage);
            }
            result.close();
            pst.close();
        }
        catch(Exception e){System.err.println(e.toString());}
        
        
        return absenteeism;
    }
    
    public void insertAbsenteeism(Absenteeism a){
        String check = "SELECT * FROM absenteeism WHERE badgeid =? AND payperiod = ?;";
        String update = "UPDATE absenteeism SET percentage = ?, payperiod = payperiod WHERE badgeid = ? AND payperiod = ?;";
        String newRecord = "INSERT INTO absenteeism (badgeid,payperiod,percentage) VALUES (?,?,?);";
        
        String badgeId = a.getBadgeId();
        long ts = a.getPayPeriod().getTimeInMillis();
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ts);
        
        String decimal = new DecimalFormat("#0.00").format(a.getPercentage());
        double percentage = Double.valueOf(decimal);
        
         //System.out.println(date);
        try{
            PreparedStatement pst1 = conn.prepareStatement(check);
            pst1.setString(1, badgeId);
            pst1.setString(2, date);
            
            ResultSet result = pst1.executeQuery();
            
            if(result.next()){
                PreparedStatement pst2 = conn.prepareStatement(update);
                pst2.setDouble(1,percentage);
                pst2.setString(2, badgeId);
                pst2.setString(3, date);
                
                pst2.executeUpdate();
                pst2.close();
            }
            else{
                PreparedStatement pst2 = conn.prepareStatement(newRecord);
                pst2.setString(1, badgeId);
                pst2.setString(2, date);
                pst2.setDouble(3, percentage);

                pst2.executeUpdate();
                pst2.close();
            }
            
            result.close();
            pst1.close();
        }
        catch(Exception e){System.err.println(e.toString());}
    }
    
}
