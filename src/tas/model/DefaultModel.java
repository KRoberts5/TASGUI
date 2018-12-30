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
import java.text.SimpleDateFormat;
import tas.controller.DefaultController;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;

public class DefaultModel extends AbstractModel {
    
    private TASDatabase db;
    private int terminalId = 0;
    
    public DefaultModel(){
        db = new TASDatabase();
    }
    
    public void setAdminGUI(String s){
        init();
        firePropertyChange(DefaultController.CREATE_TOOLBAR,null,null);
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
        
        long start = TASLogic.getPayPeriodStartInMillis(ts);
        String startString = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(start);
        
        ArrayList<Punch> payPeriodPunchList = db.getPayPeriodPunchList(b, ts);
        
        
        
        if(payPeriodPunchList.isEmpty()){
            firePropertyChange(DefaultController.NO_PAY_PERIOD_DATA,null,null);
        }
        else{
            HashMap<String,DefaultTableModel> output = new HashMap();
            
            Shift s = db.getShift(b);
            double wage = db.getEmployeeWage(badgeId);
            double overtime = db.getEmployeeOvertime(badgeId);

            double salary = TASLogic.calculatePayPeriodSalary(payPeriodPunchList, s, wage, overtime);
            double absenteeismPercentage = TASLogic.calculateAbsenteeism(payPeriodPunchList, s);

            Absenteeism absenteeism = new Absenteeism(badgeId,ts,absenteeismPercentage);
            String percentage = String.format("%.2f", absenteeism.getPercentage());
            
            String[] overviewCol = {"Badge ID", "Pay Period Start", "Absenteeism", "Salary"};
            String[][] overviewData = new String[2][overviewCol.length];
            
            for(int i = 0; i< overviewCol.length; ++i){
                overviewData[0][i] = overviewCol[i];
            }
            
            overviewData[1][0] = badgeId;
            overviewData[1][1] = startString;
            overviewData[1][2] = percentage + "%";
            overviewData[1][3] = "$" + salary;
            
            DefaultTableModel overviewModel = new DefaultTableModel(overviewData,overviewCol);
            
            String[] punchDataCol = {"Punch Type", "Date", "Original Time", "Adjusted Time"};
            String[][] punchData = new String[payPeriodPunchList.size() + 1][punchDataCol.length];
            
            for(int i = 0; i< punchDataCol.length; ++i){
                punchData[0][i] = punchDataCol[i];
            }
            
            for(int i = 0; i <payPeriodPunchList.size();++i){
                Punch p = payPeriodPunchList.get(i);
                p.adjust(s);
                
                String punchType = Punch.PUNCH_TYPES[p.getPunchtypeid()];
                String punchDay = Punch.DAYS_OF_WEEK[p.getOriginaltimestamp().get(Calendar.DAY_OF_WEEK) - 1];
                String punchDate = new SimpleDateFormat("yyyy-MM-dd").format(p.getOriginaltimestamp().getTimeInMillis());
                String ots = new SimpleDateFormat("hh:mm:ss").format(p.getOriginaltimestamp().getTimeInMillis());
                String ats = new SimpleDateFormat("hh:mm:ss").format(p.getAdjustedtimestamp().getTimeInMillis());
                
                
                punchData[i + 1][0] = punchType;
                punchData[i + 1][1] = punchDay + " " + punchDate;
                punchData[i + 1][2] = ots;
                punchData[i + 1][3] = ats;
            }
            
            DefaultTableModel punchDataModel = new DefaultTableModel(punchData,punchDataCol);
            
            output.put(DefaultController.PAY_PERIOD_OVERVIEW,overviewModel );
            output.put(DefaultController.PUNCH_DATA, punchDataModel);
            
            firePropertyChange(DefaultController.UPDATE_PAY_PERIOD_DATA,null,output);
            
        }
    }
    
    public void getUpdateEmployeeInfo(String badgeId){
        
        firePropertyChange(DefaultController.UPDATE_BADGE_ID,null,badgeId);
        
        HashMap<String,String> data = db.getEmployeeData(badgeId);
        
        if(data.isEmpty()){
            //Add Later
        }
        else{
            firePropertyChange(DefaultController.UPDATE_EMPLOYEE_INFO,null,data);
        }
        
    }
    
    public void updateEmployeeEntry(HashMap<String,Object> update){
        String badgeId = (String)update.get(DefaultController.BADGE_ID);
        HashMap<String,String> updateValues = (HashMap<String,String>)update.get(DefaultController.UPDATE_VALUES);
        
        boolean allSuccessful = true;
        boolean success = true;
        ArrayList<String> failedUpdates = new ArrayList();
        
        for(HashMap.Entry<String,String> e: updateValues.entrySet()){
            success = true;
            switch(e.getKey()){
                case DefaultController.FIRSTNAME: 
                    success = db.updateEmployeeFirstName(badgeId, e.getValue());
                    break;
                case DefaultController.MIDDLENAME: 
                    success = db.updateEmployeeMiddleName(badgeId, e.getValue());
                    break;
                case DefaultController.LASTNAME:
                    success = db.updateEmployeeLastName(badgeId, e.getValue());
                    break;
                case DefaultController.EMPLOYEE_TYPE_ID: 
                    int empTypeId = Integer.parseInt(e.getValue());
                    success = db.updateEmployeeEmployeeTypeId(badgeId, empTypeId);
                    break;
                case DefaultController.DEPARTMENT_ID: 
                    int depId = Integer.parseInt(e.getValue());
                    success = db.updateEmployeeDepartmentId(badgeId, depId);
                    break;
                case DefaultController.SHIFT_ID:
                    int shiftId = Integer.parseInt(e.getValue());
                    success = db.updateEmployeeShiftId(badgeId, shiftId);
                    break;
            }
            
            if(!success){
                allSuccessful = false;
                failedUpdates.add(e.getKey());
            }
        }
        
        if(allSuccessful){
            firePropertyChange(DefaultController.UPDATE_EMPLOYEE_SUCCESS,null,null);
        }
        else{
            firePropertyChange(DefaultController.UPDATE_EMPLOYEE_FAILED,null,failedUpdates);
        }
    }
    
    public void insertPunch(HashMap<String,Object> values){
        String badgeId = (String)values.get(DefaultController.BADGE_ID);
        Integer termId = (Integer)values.get(DefaultController.TERMINAL_ID);
        GregorianCalendar ots = (GregorianCalendar)values.get(DefaultController.ORIGINAL_TIMESTAMP);
        Integer ptid = (Integer)values.get(DefaultController.PUNCH_TYPE_ID);
        
        
        
        Badge b = db.getBadge(badgeId);
        Punch p = new Punch(b,termId,ots,ptid);
        
        int records = db.insertPunch(p);
        
        if(records > 0){
            firePropertyChange(DefaultController.INSERT_PUNCH_SUCCESS,null,null);
        }
        else
            firePropertyChange(DefaultController.INSERT_PUNCH_FAILED,null,null);
            
    }
    
    public void insertEmployee(HashMap<String,String> values){
        String badgeId = values.get(DefaultController.BADGE_ID);
        String fname = values.get(DefaultController.FIRSTNAME);
        String mname = values.get(DefaultController.MIDDLENAME);
        String lname = values.get(DefaultController.LASTNAME);
        int empTypeId = Integer.parseInt(values.get(DefaultController.EMPLOYEE_TYPE_ID));
        int depId = Integer.parseInt(values.get(DefaultController.DEPARTMENT_ID));
        int shiftId = Integer.parseInt(values.get(DefaultController.SHIFT_ID));
        String active = values.get(DefaultController.ACTIVE);
        
        String badgeDesc = lname + ", " + fname + " " + mname;
        
        if(db.checkBadgeIdAvailability(badgeId)){
            
            boolean success = true;
            
            if(!db.insertBadge(badgeId, badgeDesc)){
                success = false;
                firePropertyChange(DefaultController.INSERT_BADGE_FAILED,null,null);
            }
            
            if(!db.insertEmployee(badgeId, fname, mname, lname, empTypeId, depId, shiftId, active)){
                success = false;
                firePropertyChange(DefaultController.INSERT_EMPLOYEE_FAILED,null,null);
            }
            
            if(success)
                firePropertyChange(DefaultController.INSERT_EMPLOYEE_SUCCESS,null,null);
        }
        else{
            firePropertyChange(DefaultController.BADGE_ID_TAKEN,null,null);
        }
    }
    
    public void setReturnHome(String homeName){
        firePropertyChange(DefaultController.RESET_GUI,null,null);
    }
    
    public void init(){
        ArrayList<String> badgeIds = db.getBadgeIdList();
        firePropertyChange(DefaultController.UPDATE_BADGE_IDS,null, badgeIds);
        
        HashMap<String,Integer> shiftIds = db.getShiftIds();
        firePropertyChange(DefaultController.UPDATE_SHIFT_IDS,null,shiftIds);
        
        HashMap<String,Integer> depIds = db.getDepartmentIds();
        firePropertyChange(DefaultController.UPDATE_DEP_IDS,null,depIds);
        
        HashMap<String,Integer> empTypeIds = db.getEmployeeTypeIds();
        firePropertyChange(DefaultController.UPDATE_EMP_TYPE_IDS,null,empTypeIds);
        
        HashMap<String,Integer> punchTypeIds = db.getPunchTypeIds();
        firePropertyChange(DefaultController.UPDATE_PUNCH_TYPE_IDS,null,punchTypeIds);
    }
    
    
    
}
