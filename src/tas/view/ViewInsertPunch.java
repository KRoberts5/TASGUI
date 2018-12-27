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
import java.util.GregorianCalendar;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.DefaultCaret;

public class ViewInsertPunch extends JPanel implements AbstractView {
    
    public static final int TERMINAL = 0;

    DefaultController controller;
    
    private OTSSelector otsSelector;
    private BadgeIdSelector badgeIdSelector;
    private IdSelector punchTypeSelector;
    
    public ViewInsertPunch(DefaultController c){
        this.controller = c;
        
        initComponents();
    }
    
    private void initComponents(){
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        int y = 0;
        
        JPanel idPanel = new JPanel();

        idPanel.setLayout(new GridBagLayout());
        GridBagConstraints idGBC = new GridBagConstraints();
        idGBC.gridx = 0;
        idGBC.gridy = 0;
        idGBC.anchor = GridBagConstraints.WEST;
        idGBC.fill = GridBagConstraints.HORIZONTAL;
        idGBC.insets = new Insets(5,5,5,5);
        
        
        JLabel badgeLabel = new JLabel("Badge ID:");
        badgeIdSelector = new BadgeIdSelector();
        

        
        idPanel.add(badgeLabel,idGBC);
        idGBC.gridx = 1;
        idPanel.add(badgeIdSelector,idGBC);
        
        idGBC.gridx = 0;
        idGBC.gridy = 1;
        
        JLabel punchLabel = new JLabel("Punch Type:");
        punchTypeSelector = new IdSelector();
        
        idPanel.add(punchLabel,idGBC);
        idGBC.gridx = 1;
        idPanel.add(punchTypeSelector,idGBC);
        
        this.add(idPanel,gbc);
        
        gbc.gridy = ++y;
        
        otsSelector = new OTSSelector();
        this.add(otsSelector,gbc);
        
        gbc.gridy = ++y;
        
        JPanel submitPanel = new JPanel();
        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                submit();
            }
        });
        submitPanel.add(submit);
        
        this.add(submitPanel,gbc);
        
        
    }
    
    
    private void submit(){
        
        HashMap<String,Object> punchValues = new HashMap();
        
        String badgeId = (String)badgeIdSelector.getSelectedItem();
        Integer ptid = (Integer)punchTypeSelector.getSelectedItemId();
        GregorianCalendar ots = otsSelector.getOTS();
        Integer terminalId = TERMINAL;
        
        punchValues.put(DefaultController.BADGE_ID, badgeId);
        punchValues.put(DefaultController.PUNCH_TYPE_ID, ptid);
        punchValues.put(DefaultController.ORIGINAL_TIMESTAMP, ots);
        punchValues.put(DefaultController.TERMINAL_ID, terminalId);
        
        controller.insertPunch(punchValues);
        
        
    }
    private void resetGUI(){
        
        badgeIdSelector.setSelectedIndex(0);
        otsSelector.resetGUI();
        punchTypeSelector.setSelectedIndex(0);
        
    }
    public void modelPropertyChange(PropertyChangeEvent e){
        
        if(e.getPropertyName().equals(DefaultController.UPDATE_BADGE_IDS)){
            ArrayList<String> badgeIds = (ArrayList<String>)e.getNewValue(); 
            badgeIdSelector.updateBadgeIds(badgeIds);
        }
        else if(e.getPropertyName().equals(DefaultController.RESET_GUI)){
            resetGUI();
        }
        
        else if(e.getPropertyName().equals(DefaultController.UPDATE_PUNCH_TYPE_IDS)){
            HashMap<String,Integer> ids = (HashMap<String,Integer>)e.getNewValue();
            
            punchTypeSelector.updateElements(ids);
        }
        
        else if(e.getPropertyName().equals(DefaultController.INSERT_PUNCH_SUCCESS)){
            JOptionPane.showMessageDialog(this, "Punch Inserted Successfully.");
            controller.returnHome();
        }
        else if(e.getPropertyName().equals(DefaultController.INSERT_PUNCH_FAILED)){
            JOptionPane.showMessageDialog(this, "Insert Punch Failed.");
        }
        
        
    }
    
}
