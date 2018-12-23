/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tas.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import tas.controller.*;
import java.beans.*;

public class BadgeIdSelector extends JComboBox {
    
    private ArrayList<String> badgeIds;
    
    public BadgeIdSelector(){
        badgeIds = new ArrayList();
        initComponents();
    }
    public BadgeIdSelector(ArrayList<String> ids){
        badgeIds = ids;
        initComponents();
    }
    
    
    private void initComponents(){
        for(String id: badgeIds){
            this.addItem(id);
        }
    }
    public void updateBadgeIds(ArrayList<String> ids){
        this.badgeIds = ids;
        this.removeAllItems();
        for(String id: badgeIds){
            this.addItem(id);
        }
    }
}
