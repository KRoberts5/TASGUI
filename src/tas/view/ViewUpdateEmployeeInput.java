/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tas.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import tas.controller.DefaultController;

/**
 *
 * @author Brendan
 */
public class ViewUpdateEmployeeInput extends JPanel implements AbstractView{
    
    private String originalBadgeId;
    private DefaultController controller;
    
    private JPanel selectionPanel;
    private JPanel inputPanel;
    private ArrayList<JCheckBox> selectionBoxes;
    private ArrayList<JTextField> inputFields;
    private JTextField badgeIdField;
    private JTextField fnameField;
    private JTextField mnameField;
    private JTextField lnameField;
    private JTextField empTypeField;
    private JTextField depIdField;
    private JTextField shiftIdField;
    private JTextField activeField;
    private JTextField inactiveField;
    
    private JLabel badgeIdValue;
    private JLabel fnameValue;
    private JLabel mnameValue;
    
    public ViewUpdateEmployeeInput(DefaultController c){
        this.controller = c;
        initComponents();
    }
    
    private void initComponents(){
        selectionBoxes = new ArrayList();
        inputFields = new ArrayList();
        
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        
        inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(0,3));
        JLabel field = new JLabel("Field");
        JLabel currentValue = new JLabel("Current Value");
        JLabel updateValue = new JLabel("Update Value");
        inputPanel.add(field);
        inputPanel.add(currentValue);
        inputPanel.add(updateValue);
        
        JCheckBox badgeIdBox = new JCheckBox("Badge Id");
        badgeIdBox.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(badgeIdBox.isSelected()){
                    badgeIdField.setEditable(true);
                }
                else{
                    badgeIdField.setEditable(false);
                    badgeIdField.setText("");
                }
            }
        });
        badgeIdValue = new JLabel("Old Value");
        badgeIdField = new JTextField();
        badgeIdField.setActionCommand(DefaultController.BADGE_ID);
        badgeIdField.setEditable(false);
        
        inputPanel.add(badgeIdBox);
        inputPanel.add(badgeIdValue);
        inputPanel.add(badgeIdField);
        inputFields.add(badgeIdField);
        
        JCheckBox fnameBox = new JCheckBox("First Name");
        fnameBox.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(fnameBox.isSelected()){
                    fnameField.setEditable(true);
                }
                else{
                    fnameField.setEditable(false);
                    fnameField.setText("");
                }
            }
        });
        fnameValue = new JLabel("Old Value");
        fnameField = new JTextField();
        fnameField.setActionCommand(DefaultController.FIRSTNAME);
        fnameField.setEditable(false);
        
        inputPanel.add(fnameBox);
        inputPanel.add(fnameValue);
        inputPanel.add(fnameField);
        inputFields.add(fnameField);
        
        JCheckBox mnameBox = new JCheckBox("Middle Name");
        mnameBox.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(mnameBox.isSelected()){
                    mnameField.setEditable(true);
                }
                else{
                    mnameField.setEditable(false);
                    mnameField.setText("");
                }
            }
        });
        mnameValue = new JLabel("Old Value");
        mnameField = new JTextField();
        mnameField.setActionCommand(DefaultController.FIRSTNAME);
        mnameField.setEditable(false);
        
        inputPanel.add(mnameBox);
        inputPanel.add(mnameValue);
        inputPanel.add(mnameField);
        inputFields.add(mnameField);

        
        JPanel submitPanel = new JPanel();
        JButton submit = new JButton("Submit");
        submit.setPreferredSize(new Dimension(100,30));
        submit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                submit();
            }
        });
        submitPanel.add(submit);
        ReturnHomeButton home = new ReturnHomeButton(controller);
        home.setPreferredSize(new Dimension(100,30));
        submitPanel.add(home);
        
        //this.add(selectionPanel);
        this.add(inputPanel);
        this.add(submitPanel);
        
        
        
    }
    
    private void submit(){
        
    }
    
    private void resetGUI(){
        
    }
    public void modelPropertyChange(PropertyChangeEvent e){
        //Add RefreshBadgeIds
        
        if(e.getPropertyName().equals(DefaultController.RESET_GUI)){
            resetGUI();
        }
        else if(e.getPropertyName().equals(DefaultController.UPDATE_EMPLOYEE_INFO)){
            HashMap<String,String> values = (HashMap<String,String>)e.getNewValue();
            String badgeId = values.get(DefaultController.BADGE_ID);
            String fname = values.get(DefaultController.FIRSTNAME);
            String mname = values.get(DefaultController.MIDDLENAME);
            
            badgeIdValue.setText(badgeId);
            fnameValue.setText(fname);
            mnameValue.setText(mname);
        }
        else if(e.getPropertyName().equals(DefaultController.UPDATE_BADGE_ID)){
            this.originalBadgeId = (String)e.getNewValue();
        }
    }
}
