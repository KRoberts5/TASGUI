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
    
}
