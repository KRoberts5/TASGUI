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
import java.util.Locale;
import javax.swing.*;
import tas.controller.DefaultController;

/**
 *
 * @author Brendan
 */
public class ViewUpdateEmployeeInput extends JPanel implements AbstractView{
    
    private String originalBadgeId;
    private DefaultController controller;
    
    private JPanel inputPanel;
    private JCheckBox selectAll;
    private ArrayList<JCheckBox> selectionBoxes;
    private ArrayList<JTextField> inputFields;
    private ArrayList<IdSelector> selectors;
    private JTextField badgeIdField;
    private JTextField fnameField;
    private JTextField mnameField;
    private JTextField lnameField;
    private IdSelector empTypeField;
    private IdSelector depIdField;
    private IdSelector shiftIdField;
    private JTextField activeField;
    private JTextField inactiveField;
    
    private JLabel badgeIdValue;
    private JLabel fnameValue;
    private JLabel mnameValue;
    private JLabel lnameValue;
    private JLabel empTypeValue;
    private JLabel depIdValue;
    private JLabel shiftIdValue;
    
    
    public ViewUpdateEmployeeInput(DefaultController c){
        this.controller = c;
        initComponents();
    }
    
    private void initComponents(){
        selectionBoxes = new ArrayList();
        inputFields = new ArrayList();
        selectors = new ArrayList();
        
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        
        inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        int y = gbc.gridy = 0;
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
        gbc.gridy = ++y;
        
         selectAll = new JCheckBox("Select All");
        selectAll.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                for(JCheckBox b: selectionBoxes){
                    if(selectAll.isSelected())
                        b.setSelected(true);                
                    else
                        b.setSelected(false);
                }
                for(JTextField f : inputFields){
                    if(selectAll.isSelected()){
                        f.setEditable(true);
                    }
                    else{
                        f.setEditable(false);
                        f.setText("");
                    }
                }
                for(IdSelector i : selectors){
                    if(selectAll.isSelected()){
                        i.setEnabled(true);
                    }
                    else{
                        i.setEnabled(false);
                    }
                }
            }
        });
        inputPanel.add(selectAll,gbc);
        
        /*gbc.gridx = 0;
        gbc.gridy = ++y;
        
        JCheckBox badgeIdBox = new JCheckBox("Badge Id");
        selectionBoxes.add(badgeIdBox);
        badgeIdBox.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(badgeIdBox.isSelected()){
                    badgeIdField.setEditable(true);
                }
                else{
                    badgeIdField.setEditable(false);
                    badgeIdField.setText("");
                    
                    if(selectAll.isSelected())
                        selectAll.setSelected(false);
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
        inputFields.add(badgeIdField);*/
        
        gbc.gridx = 0;
        gbc.gridy = ++y;
        
        JCheckBox fnameBox = new JCheckBox("First Name");
        selectionBoxes.add(fnameBox);
        fnameBox.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(fnameBox.isSelected()){
                    fnameField.setEditable(true);
                }
                else{
                    fnameField.setEditable(false);
                    fnameField.setText("");
                    
                    if(selectAll.isSelected())
                        selectAll.setSelected(false);
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
        gbc.gridy = ++y;
        
        JCheckBox mnameBox = new JCheckBox("Middle Name");
        selectionBoxes.add(mnameBox);
        mnameBox.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(mnameBox.isSelected()){
                    mnameField.setEditable(true);
                }
                else{
                    mnameField.setEditable(false);
                    mnameField.setText("");
                    
                    if(selectAll.isSelected())
                        selectAll.setSelected(false);
                }
            }
        });
        mnameValue = new JLabel("Old Value");
        mnameField = new JTextField();
        mnameField.setActionCommand(DefaultController.MIDDLENAME);
        mnameField.setEditable(false);
        
        inputPanel.add(mnameBox,gbc);
        gbc.gridx = 1;
        inputPanel.add(mnameValue,gbc);
        gbc.gridx = 2;
        inputPanel.add(mnameField,gbc);
        inputFields.add(mnameField);
        
        gbc.gridx = 0;
        gbc.gridy = ++y;
        
        JCheckBox lnameBox = new JCheckBox("Last Name");
        selectionBoxes.add(lnameBox);
        lnameBox.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(lnameBox.isSelected()){
                    lnameField.setEditable(true);
                }
                else{
                    lnameField.setEditable(false);
                    lnameField.setText("");
                    
                    if(selectAll.isSelected())
                        selectAll.setSelected(false);
                }
            }
        });
        lnameValue = new JLabel("Old Value");
        lnameField = new JTextField();
        lnameField.setActionCommand(DefaultController.LASTNAME);
        lnameField.setEditable(false);
        
        inputPanel.add(lnameBox,gbc);
        gbc.gridx = 1;
        inputPanel.add(lnameValue,gbc);
        gbc.gridx = 2;
        inputPanel.add(lnameField,gbc);
        inputFields.add(lnameField);
        
        gbc.gridx = 0;
        gbc.gridy = ++y;
        
        JCheckBox empTypeBox = new JCheckBox("Employee Type");
        selectionBoxes.add(empTypeBox);
        empTypeBox.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(empTypeBox.isSelected()){
                    empTypeField.setEnabled(true);
                }
                else{
                    empTypeField.setEnabled(false);
                    //empTypeField.setText("");
                    
                    if(selectAll.isSelected())
                        selectAll.setSelected(false);
                }
            }
        });
        empTypeValue = new JLabel("Old Value");
        empTypeField = new IdSelector();
        empTypeField.setActionCommand(DefaultController.EMPLOYEE_TYPE_ID);
        empTypeField.setEnabled(false);
        
        inputPanel.add(empTypeBox,gbc);
        gbc.gridx = 1;
        inputPanel.add(empTypeValue,gbc);
        gbc.gridx = 2;
        inputPanel.add(empTypeField,gbc);
        selectors.add(empTypeField);
        
        gbc.gridx = 0;
        gbc.gridy = ++y;
        
        JCheckBox depIdBox = new JCheckBox("Department ID");
        selectionBoxes.add(depIdBox);
        depIdBox.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(depIdBox.isSelected()){
                    depIdField.setEnabled(true);
                }
                else{
                    depIdField.setEnabled(false);
                    //depIdField.setText("");
                    
                    if(selectAll.isSelected())
                        selectAll.setSelected(false);
                }
            }
        });
        depIdValue = new JLabel("Old Value");
        depIdField = new IdSelector();
        depIdField.setActionCommand(DefaultController.DEPARTMENT_ID);
        depIdField.setEnabled(false);
        
        inputPanel.add(depIdBox,gbc);
        gbc.gridx = 1;
        inputPanel.add(depIdValue,gbc);
        gbc.gridx = 2;
        inputPanel.add(depIdField,gbc);
        selectors.add(depIdField);
        
        gbc.gridx = 0;
        gbc.gridy = ++y;
        
        JCheckBox shiftIdBox = new JCheckBox("Shift ID");
        selectionBoxes.add(shiftIdBox);
        shiftIdBox.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(shiftIdBox.isSelected()){
                    shiftIdField.setEnabled(true);
                }
                else{
                    shiftIdField.setEnabled(false);
                    //shiftIdField.setText("");
                    
                    if(selectAll.isSelected())
                        selectAll.setSelected(false);
                }
            }
        });
        shiftIdValue = new JLabel("Old Value");
        shiftIdField = new IdSelector();
        shiftIdField.setActionCommand(DefaultController.SHIFT_ID);
        shiftIdField.setEnabled(false);
        
        inputPanel.add(shiftIdBox,gbc);
        gbc.gridx = 1;
        inputPanel.add(shiftIdValue,gbc);
        gbc.gridx = 2;
        inputPanel.add(shiftIdField,gbc);
        selectors.add(shiftIdField);
        
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
        HashMap<String,Object> update = new HashMap();
        HashMap<String,String> updateValues = new HashMap();

        boolean validInput = true;
        
        update.put(DefaultController.BADGE_ID, this.originalBadgeId);
        
        for(JTextField f: inputFields){
            if(f.isEditable()){
                String text = f.getText();
                if(text.matches("[a-zA-Z]+")){
                    
                    text = formatName(text);
                    
                    if(f.equals(fnameField)){
                        updateValues.put(DefaultController.FIRSTNAME, text);
                    }
                    else if(f.equals(mnameField)){
                        updateValues.put(DefaultController.MIDDLENAME, text);
                    }
                    else if(f.equals(lnameField)){
                        updateValues.put(DefaultController.LASTNAME, text);
                    }
                }
                else
                    validInput = false;
            }
        }
        for(IdSelector i: selectors){
            if(i.isEnabled()){
                int id = i.getSelectedItemId();
                
                if(i.equals(empTypeField)){
                    updateValues.put(DefaultController.EMPLOYEE_TYPE_ID, String.valueOf(id));
                }
                else if(i.equals(depIdField)){
                    updateValues.put(DefaultController.DEPARTMENT_ID, String.valueOf(id));
                }
                else if(i.equals(shiftIdField)){
                    updateValues.put(DefaultController.SHIFT_ID, String.valueOf(id));
                }
            }
        }
        
        update.put(DefaultController.UPDATE_VALUES, updateValues);
        
        if(validInput){
            controller.updateEmployeeEntry(update);
        }
        else{
            JOptionPane.showMessageDialog(this, "Invalid Input.");
        }
    }
    
    private String formatName(String text){
        String name = "";
        
        text = text.toUpperCase();
        char[] characters = text.toCharArray();
        
        name += characters[0];
        for(int i = 1; i < characters.length; ++i){
           Character c = characters[i];
           name += c.toString().toLowerCase();
        }
        return name;
    }
    
    private void resetGUI(){
        selectAll.setSelected(false);
        for(JCheckBox b: selectionBoxes){
            b.setSelected(false);
        }
        for(JTextField f: inputFields){
            f.setText("");
            f.setEditable(false);
        }
        for(IdSelector i: selectors){
            i.setSelectedIndex(0);
            i.setEnabled(false);
        }
    }
    private void updateIds(IdSelector i, HashMap<String,Integer> values){
        i.updateElements(values);
    }
    public void modelPropertyChange(PropertyChangeEvent e){
        //Add RefreshBadgeIds
        
        if(e.getPropertyName().equals(DefaultController.RESET_GUI)){
            resetGUI();
        }
        else if(e.getPropertyName().equals(DefaultController.UPDATE_EMPLOYEE_INFO)){
            HashMap<String,String> values = (HashMap<String,String>)e.getNewValue();
            //String badgeId = values.get(DefaultController.BADGE_ID);
            String fname = values.get(DefaultController.FIRSTNAME);
            String mname = values.get(DefaultController.MIDDLENAME);
            String lname = values.get(DefaultController.LASTNAME);
            String empType = values.get(DefaultController.EMPLOYEE_TYPE_ID);
            String depId = values.get(DefaultController.DEPARTMENT_ID);
            String shiftId = values.get(DefaultController.SHIFT_ID);
            
            //badgeIdValue.setText(badgeId);
            fnameValue.setText(fname);
            mnameValue.setText(mname);
            lnameValue.setText(lname);
            empTypeValue.setText(empType);
            depIdValue.setText(depId);
            shiftIdValue.setText(shiftId);
        }
        else if(e.getPropertyName().equals(DefaultController.UPDATE_BADGE_ID)){
            this.originalBadgeId = (String)e.getNewValue();
        }
        else if(e.getPropertyName().equals(DefaultController.UPDATE_DEP_IDS)){
            HashMap<String,Integer> values = (HashMap<String,Integer>)e.getNewValue();
            this.updateIds(depIdField, values);
        }
        else if(e.getPropertyName().equals(DefaultController.UPDATE_EMP_TYPE_IDS)){
            HashMap<String,Integer> values = (HashMap<String,Integer>)e.getNewValue();
            this.updateIds(empTypeField, values);
        }
        else if(e.getPropertyName().equals(DefaultController.UPDATE_SHIFT_IDS)){
            HashMap<String,Integer> values = (HashMap<String,Integer>)e.getNewValue();
            this.updateIds(shiftIdField, values);
        }
        else if(e.getPropertyName().equals(DefaultController.UPDATE_EMPLOYEE_SUCCESS)){
            JOptionPane.showMessageDialog(this, "Update Successful.");
            controller.returnHome();
        }
        else if(e.getPropertyName().equals(DefaultController.UPDATE_EMPLOYEE_FAILED)){
            ArrayList<String> failures = (ArrayList<String>)e.getNewValue();
            
            String message = "The following fields failed to update:\n(";
            for(int i = 0; i < failures.size(); ++i){
                String f = failures.get(i);
                message += f;
                
                if((i + 1) == failures.size()){
                    message+= ")";
                }
                else
                    message += ", ";
                
            }
            JOptionPane.showMessageDialog(this, message);
        }
    }
}
