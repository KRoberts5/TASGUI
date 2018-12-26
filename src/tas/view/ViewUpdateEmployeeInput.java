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
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10,10,10,10);
        
        
        JLabel field = new JLabel("Field");
        JLabel currentValue = new JLabel("Current Value");
        JLabel updateValue = new JLabel("Update Value");
        inputPanel.add(field,gbc);
        gbc.gridx = 1;
        inputPanel.add(currentValue,gbc);
        gbc.gridx = 2;
        inputPanel.add(updateValue,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        
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
        
        inputPanel.add(badgeIdBox,gbc);
        gbc.gridx = 1;
        inputPanel.add(badgeIdValue,gbc);
        gbc.gridx = 2;
        inputPanel.add(badgeIdField,gbc);
        inputFields.add(badgeIdField);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        
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
        
        inputPanel.add(fnameBox,gbc);
        gbc.gridx = 1;
        inputPanel.add(fnameValue,gbc);
        gbc.gridx = 2;
        inputPanel.add(fnameField,gbc);
        inputFields.add(fnameField);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        
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
        
        inputPanel.add(mnameBox,gbc);
        gbc.gridx = 1;
        inputPanel.add(mnameValue,gbc);
        gbc.gridx = 2;
        inputPanel.add(mnameField,gbc);
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
