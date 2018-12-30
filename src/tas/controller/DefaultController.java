/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tas.controller;

import java.beans.PropertyChangeEvent;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 *
 * @author Brendan
 */
public class DefaultController extends AbstractController {
    
    //Badge and Employee Constants
    public static final String ABSENTEEISM = "Absenteeism";
    public static final String BADGE = "Badge";
    public static final String BADGE_ID = "badgeid";
    public static final String EMPLOYEE = "Employee";
    public static final String EMPLOYEE_TYPE_ID = "employeetypeid";
    public static final String FIRSTNAME = "firstname";
    public static final String LASTNAME = "lastname";
    public static final String MIDDLENAME = "middlename";
    
    //Daily Schedule Constants
    public static final String DAILY_SCHEDULE = "DailySchedule";
    
    //Department Constants
    public static final String DEPARTMENT_ID = "departmentid";
    
    //Shift Constants
    public static final String SHIFT = "Shift";
    public static final String SHIFT_ID = "shiftid";
    
    //Punch Constants
    public static final String PUNCH = "Punch";
    public static final String PUNCH_LIST = "PunchList";
    public static final String PUNCH_TYPE_ID = "punchtypeid";
    
    public static final String ORIGINAL_TIMESTAMP = "orgiginaltimestamp";
    public static final String TERMINAL_ID = "terminalid";
    
    //TASLogic Constants
    public static final String TASLOGIC = "TASLogic";
    
    
    //Insert Constants
    public static final String INSERT = "Insert";
    
    //Retrieve Constants
    public static final String RETRIEVE = "Retrieve";

    //Update Constants
    public static final String UPDATE = "Update";
    public static final String UPDATE_BADGE_IDS = "UpdateBadgeIds";
    public static final String UPDATE_BADGE_ID = "UpdateBadgeId";
    public static final String UPDATE_DAILY_PUNCH_LIST = "UpdateDailyPunchList";
    public static final String UPDATE_DEP_IDS = "UpdateDepIds";
    public static final String UPDATE_EMPLOYEE_INFO = "UpdateEmployeeInfo";
    public static final String UPDATE_EMP_TYPE_IDS = "UpdateEmpTypeIds";
    public static final String UPDATE_PAY_PERIOD_DATA = "UpdatePayPeriodData";
    public static final String UPDATE_PUNCH_TYPE_IDS = "UpdatePunchTypeIds";
    public static final String UPDATE_SHIFT_IDS = "UpdateShiftIds";
    public static final String UPDATE_VALUES = "UpdateValues";
    
    //Constants for View Class Names
    public static final String LAUNCH = "Launch";
    public static final String TERMINAL = "Terminal";
    public static final String ADMIN = "Admin";
    public static final String ADMIN_CHOICE = "AdminChoice";
    public static final String INSERT_EMPLOYEE = "InsertEmployee";
    public static final String INSERT_PUNCH = "InsertPunch";
    public static final String UPDATE_EMPLOYEE = "UpdateEmployee";
    public static final String UPDATE_EMPLOYEE_INPUT = "UpdateEmployeeInput";
    public static final String RETRIEVE_PUNCH_LIST = "RetrievePunchList";
    public static final String RETRIEVE_PAY_PERIOD_DATA = "RetrievePayPeriodData";
    
    //Constants for Communication with ViewTerminal
    public static final String PUNCH_IN = "PunchIn";
    public static final String PUNCH_OUT = "PunchOut";
    public static final String PUNCH_IN_FAIL_INVALID_BADGE_ID = "PunchInFailInvalidBadgeId";
    public static final String PUNCH_OUT_FAIL_INVALID_BADGE_ID = "PunchOutFailInvalidBadgeId";
    public static final String PUNCH_IN_SUCCESS = "PunchInSuccess";
    public static final String PUNCH_OUT_SUCCESS = "PunchOutSuccess";
    
    //Constants for Setting up and Reseting GUI
    public static final String RESET_GUI = "ResetGUI";
    public static final String RETURN_HOME = "ReturnHome";
    public static final String ADMIN_GUI = "AdminGUI";
    public static final String CREATE_TOOLBAR = "CreateToolbar";
    
    //Constants for Communicating with ViewUpdateEmployee and ViewUpdateEmployeeInput
    public static final String UPDATE_EMPLOYEE_SUCCESS = "UpdateEmployeeSuccess";
    public static final String UPDATE_EMPLOYEE_FAILED = "UpdateEmployeeFailed";
    
    //Constants for Communicating with ViewRetrievePunchList
    public static final String NO_DAILY_PUNCH_LIST_DATA = "NoDailyPunchListData";
    
    //Constants for Communicating with ViewRetrievePayPeriodData
    public static final String NO_PAY_PERIOD_DATA = "NoPayPeriodData";
    public static final String PAY_PERIOD_DATA = "PayPeriodData";
    public static final String PAY_PERIOD_OVERVIEW = "PayPeriodOverview";
    public static final String PUNCH_DATA = "PunchData";
    
    //Constants for Communicating with ViewInsertPunch
    public static final String INSERT_PUNCH_SUCCESS = "InsertPunchSuccess";
    public static final String INSERT_PUNCH_FAILED = "InsertPunchFailed";
    
    //Constants for Calling Model Methods
    public static final String EMPLOYEE_ENTRY = "EmployeeEntry";
    
    //Misc. Constants
    public static final String DATE = "Date";
    
    public void returnHome(){
        showCard(ADMIN_CHOICE);
        setModelProperty(RETURN_HOME,ADMIN_CHOICE);
    }
    public void initAdmin(){
        setModelProperty(ADMIN_GUI,ADMIN_GUI);
        showCard(ADMIN_CHOICE);
    }
    public void punchIn(String badgeId){
        setModelProperty(PUNCH_IN, badgeId);
    }
    public void punchOut(String badgeId){
        setModelProperty(PUNCH_OUT, badgeId);
    }
    public void getPunchList(String badgeId, GregorianCalendar date){
        HashMap<String,Object> newValues = new HashMap();
        newValues.put(DATE, date);
        newValues.put(BADGE_ID, badgeId);
        
        getModelProperty(PUNCH_LIST,newValues);
        
    }
    public void getPayPeriodData(String badgeId, GregorianCalendar date){
        HashMap<String,Object> newValues = new HashMap();
        newValues.put(DATE, date);
        newValues.put(BADGE_ID, badgeId);
        
        getModelProperty(PAY_PERIOD_DATA,newValues);
    }
    public void getEmployeeData(String badgeId){
        getModelProperty(UPDATE_EMPLOYEE_INFO,badgeId);
    }
    public void updateEmployeeEntry(HashMap<String,Object> values){
        updateDatabaseProperty(EMPLOYEE_ENTRY,values);
    }
    
    public void insertPunch(HashMap<String,Object> values){
        insertDatabaseProperty(PUNCH,values);
    }
}
