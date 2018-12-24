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

import java.beans.*;
import tas.controller.DefaultController;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class DefaultModel extends AbstractModel {
    
    private TASDatabase db;
    private int terminalId = 0;
    
    public DefaultModel(){
        db = new TASDatabase();
    }
    
    public void setPunchIn(String badgeId){
        
        badgeId = badgeId.toUpperCase();
        
        Badge b = db.getBadge(badgeId);
        
        if(b == null){
            firePropertyChange(DefaultController.PUNCH_IN_FAIL_INVALID_BADGE_ID,null,null);
        }
        else{
            Punch p = new Punch(b,terminalId,Punch.CLOCKED_IN);
            db.insertPunch(p);
            firePropertyChange(DefaultController.PUNCH_IN_SUCCESS,null,null);
        }
        
        
    }
    public void setPunchOut(String badgeId){
        badgeId = badgeId.toUpperCase();
        
        Badge b = db.getBadge(badgeId);
        
        if(b == null){
            firePropertyChange(DefaultController.PUNCH_OUT_FAIL_INVALID_BADGE_ID,null,null);
        }
        else{
            Punch p = new Punch(b,terminalId,Punch.CLOCKED_OUT);
            db.insertPunch(p);
            firePropertyChange(DefaultController.PUNCH_OUT_SUCCESS,null,null);
        }
    }
    
    public void getPunchList(HashMap<String,Object> values){

        String badgeId = (String)values.get(DefaultController.BADGE_ID);
        GregorianCalendar date = (GregorianCalendar)values.get(DefaultController.DATE);
        
        Badge badge = db.getBadge(badgeId);
        long ts = date.getTimeInMillis();
        
        ArrayList<Punch> punchList = db.getDailyPunchList(badge, ts);
        
        if(punchList.isEmpty()){
            firePropertyChange(DefaultController.NO_DAILY_PUNCH_LIST_DATA,null,null);
        }
        else{
            String output = "Punch Data: \n\n";
            
            Shift s = db.getShift(badge);
            for(Punch p: punchList){
                p.adjust(s);
                output += p.printOriginalTimestamp() + "\n";
                output += p.printAdjustedTimestamp() + "\n\n";
            }

            firePropertyChange(DefaultController.UPDATE_DAILY_PUNCH_LIST,null,output);
        }
        
    }
    
    public void getPayPeriodData(HashMap<String,Object> values){
        String badgeId = (String)values.get(DefaultController.BADGE_ID);
        GregorianCalendar date = (GregorianCalendar)values.get(DefaultController.DATE);
        
        Badge b = db.getBadge(badgeId);
        long ts = date.getTimeInMillis();
        
        ArrayList<Punch> payPeriodPunchList = db.getPayPeriodPunchList(b, ts);
        
        if(payPeriodPunchList.isEmpty()){
            firePropertyChange(DefaultController.NO_PAY_PERIOD_DATA,null,null);
        }
        else{
        
            Shift s = db.getShift(b);
            double wage = db.getEmployeeWage(badgeId);
            double overtime = db.getEmployeeOvertime(badgeId);

            double salary = TASLogic.calculatePayPeriodSalary(payPeriodPunchList, s, wage, overtime);
            double absenteeismPercentage = TASLogic.calculateAbsenteeism(payPeriodPunchList, s);

            Absenteeism absenteeism = new Absenteeism(badgeId,ts,absenteeismPercentage);



            String output = absenteeism.toString() + "\n";
            output += "Gross Salary: $" + salary + "\n";
            output += "Punch Data:\n\n";
            for(Punch p: payPeriodPunchList){
                p.adjust(s);
                output += p.printOriginalTimestamp() + "\n";
                output += p.printAdjustedTimestamp() + "\n\n";
            }
            
            firePropertyChange(DefaultController.UPDATE_PAY_PERIOD_DATA,null,output);
        }
    }
    
    public void setReturnHome(String homeName){
        firePropertyChange(DefaultController.RESET_GUI,null,null);
    }
    
    public void init(){
        ArrayList<String> badgeIds = db.getBadgeIdList();
        firePropertyChange(DefaultController.UPDATE_BADGE_IDS,null, badgeIds);
        
    }
    
}
