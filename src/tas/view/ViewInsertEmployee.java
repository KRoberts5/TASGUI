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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import javax.swing.*;
import tas.controller.DefaultController;

public class ViewInsertEmployee extends JPanel implements AbstractView{
    
    private DefaultController controller;
    
    private ArrayList<JTextField> nameFields;
    private ArrayList<IdSelector> selectors;
    private JTextField badgeIdField;
    private JTextField fnameField;
    private JTextField mnameField;
    private JTextField lnameField;
    private IdSelector empTypeField;
    private IdSelector depIdField;
    private IdSelector shiftIdField;
    private DateTimeSelector activeSelector;
    
    public ViewInsertEmployee(DefaultController c){
        this.controller = c;
        
        nameFields = new ArrayList();
        selectors = new ArrayList();
        
        initComponents();
    }
    
    private void initComponents(){
        
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        int y = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        JLabel badgeIdLabel = new JLabel("Badge ID:");
        badgeIdField = new JTextField();
        this.add(badgeIdLabel,gbc);
        gbc.gridx = 1;
        this.add(badgeIdField,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = ++y;
        
        JLabel fnameLabel = new JLabel("First Name:");
        fnameField = new JTextField();
        nameFields.add(fnameField);
        this.add(fnameLabel,gbc);
        gbc.gridx = 1;
        this.add(fnameField,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = ++y;
        
        JLabel mnameLabel = new JLabel("Middle Name:");
        mnameField = new JTextField();
        nameFields.add(mnameField);
        this.add(mnameLabel,gbc);
        gbc.gridx = 1;
        this.add(mnameField,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = ++y;
        
        JLabel lnameLabel = new JLabel("Last Name:");
        lnameField = new JTextField();
        nameFields.add(lnameField);
        this.add(lnameLabel,gbc);
        gbc.gridx = 1;
        this.add(lnameField,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = ++y;
        
        JLabel empTypeLabel = new JLabel("Employee Type:");
        empTypeField = new IdSelector();
        empTypeField.setSelectedIndex(-1);
        selectors.add(empTypeField);
        this.add(empTypeLabel,gbc);
        gbc.gridx = 1;
        this.add(empTypeField,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = ++y;
        
        JLabel depLabel = new JLabel("Department:");
        depIdField = new IdSelector();
        depIdField.setSelectedIndex(-1);
        selectors.add(depIdField);
        this.add(depLabel,gbc);
        gbc.gridx = 1;
        this.add(depIdField,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = ++y;
        
        JLabel shiftIdLabel = new JLabel("Shift:");
        shiftIdField = new IdSelector();
        shiftIdField.setSelectedIndex(-1);
        selectors.add(shiftIdField);
        this.add(shiftIdLabel,gbc);
        gbc.gridx = 1;
        this.add(shiftIdField,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = ++y;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        
        JLabel activeLabel = new JLabel("Active Since:");
        activeSelector = new DateTimeSelector();
        this.add(activeLabel,gbc);
        gbc.gridy = ++y;
        this.add(activeSelector,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = ++y;
        
        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                submit();
            }
        });
        
        this.add(submit,gbc);
        
    }
    private void submit(){
        HashMap<String,String> insertValues = new HashMap();

        boolean validInput = true;
        
        if(badgeIdField.getText().isEmpty()){
            validInput = false;
        }
        else{
            String id = badgeIdField.getText();
            
            if(id.matches("[A-Z0-9]+")){
                insertValues.put(DefaultController.BADGE_ID, id);
            }
            else
                validInput = false;
        }
        
        for(JTextField f: nameFields){
            if(f.getText().isEmpty()){
                validInput = false;
            }
            else{
                String text = f.getText();
                if(text.matches("[a-zA-Z]+")){
                    
                    text = formatName(text);
                    
                    if(f.equals(fnameField)){
                        insertValues.put(DefaultController.FIRSTNAME, text);
                    }
                    else if(f.equals(mnameField)){
                        insertValues.put(DefaultController.MIDDLENAME, text);
                    }
                    else if(f.equals(lnameField)){
                        insertValues.put(DefaultController.LASTNAME, text);
                    }
                }
                else
                    validInput = false;
            }
        }
        for(IdSelector i : selectors){
            if(i.getSelectedIndex() == -1){
                validInput = false;
            }
            else{
                int id = i.getSelectedItemId();
                
                if(i.equals(empTypeField)){
                    insertValues.put(DefaultController.EMPLOYEE_TYPE_ID, String.valueOf(id));
                }
                else if(i.equals(depIdField)){
                    insertValues.put(DefaultController.DEPARTMENT_ID, String.valueOf(id));
                }
                else if(i.equals(shiftIdField)){
                    insertValues.put(DefaultController.SHIFT_ID, String.valueOf(id));
                }
            }
        }
        
        GregorianCalendar active = activeSelector.getOTS();
        String activeString = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(active.getTimeInMillis());
        insertValues.put(DefaultController.ACTIVE, activeString);
        
        if(!validInput){
            JOptionPane.showMessageDialog(this, "Invalid Input");
        }
        else{
            controller.insertEmployee(insertValues);
        }
    }
    private void resetGUI(){
        badgeIdField.setText("");
        for(JTextField f : nameFields){
            f.setText("");
        }
        for(IdSelector i : selectors){
            i.setSelectedIndex(-1);
        }
        activeSelector.resetGUI();
        
    }
    private void updateIds(IdSelector i, HashMap<String,Integer> values){
        i.updateElements(values);
        i.setSelectedIndex(-1);
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
    
    public void modelPropertyChange(PropertyChangeEvent e){
        if(e.getPropertyName().equals(DefaultController.RESET_GUI)){
            resetGUI();
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
        else if(e.getPropertyName().equals(DefaultController.INSERT_BADGE_FAILED)){
            JOptionPane.showMessageDialog(this, "There was an error inserting Badge ID.");
        }
        else if(e.getPropertyName().equals(DefaultController.INSERT_EMPLOYEE_FAILED)){
            JOptionPane.showMessageDialog(this, "There was an error inserting employee.");
        }
        else if(e.getPropertyName().equals(DefaultController.BADGE_ID_TAKEN)){
            JOptionPane.showMessageDialog(this, "Badge ID not available.");
        }
        else if(e.getPropertyName().equals(DefaultController.INSERT_EMPLOYEE_SUCCESS)){
            JOptionPane.showMessageDialog(this, "Employee Inserted Successfully.");
            controller.returnHome();
        }
        
    }
}
