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
            Shift s = db.getShift(badge);
        
            for(Punch p: punchList){
                p.adjust(s);
            }

            firePropertyChange(DefaultController.UPDATE_DAILY_PUNCH_LIST,null,punchList);
        }
        
    }
    
    public void init(){
        ArrayList<String> badgeIds = db.getBadgeIdList();
        firePropertyChange(DefaultController.UPDATE_BADGE_IDS,null, badgeIds);
        
    }
    
}
