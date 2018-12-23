/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tas.controller;

/**
 *
 * @author Brendan
 */
public class DefaultController extends AbstractController {
    
    public static final String PUNCH = "Punch";
    public static final String EMPLOYEE = "Employee";
    public static final String DAILY_SCHEDULE = "DailySchedule";
    public static final String ABSENTEEISM = "Absenteeism";
    public static final String SHIFT = "Shift";
    public static final String TASLOGIC = "TASLogic";
    public static final String BADGE = "Badge";
    
    public static final String UPDATE = "Update";
    public static final String INSERT = "Insert";
    public static final String RETRIEVE = "Retrieve";
    
    public static final String LAUNCH = "Launch";
    public static final String TERMINAL = "Terminal";
    public static final String ADMIN = "Admin";
    public static final String ADMIN_CHOICE = "AdminChoice";
    public static final String INSERT_EMPLOYEE = "InsertEmployee";
    public static final String UPDATE_EMPLOYEE = "UpdateEmployee";
    
    public static final String PUNCH_IN = "PunchIn";
    public static final String PUNCH_OUT = "PunchOut";
    public static final String PUNCH_IN_FAIL_INVALID_BADGE_ID = "PunchInFailInvalidBadgeId";
    public static final String PUNCH_OUT_FAIL_INVALID_BADGE_ID = "PunchOutFailInvalidBadgeId";
    public static final String PUNCH_IN_SUCCESS = "PunchInSuccess";
    public static final String PUNCH_OUT_SUCCESS = "PunchOutSuccess";
    
    
    public static final String UPDATE_BADGE_IDS = "UpdateBadgeIds";
    
    
    public void punchIn(String badgeId){
        setModelProperty(PUNCH_IN, badgeId);
    }
    public void punchOut(String badgeId){
        setModelProperty(PUNCH_OUT, badgeId);
    }
    
    
    
    
}
