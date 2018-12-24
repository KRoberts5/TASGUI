/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tas.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.beans.*;
import java.util.ArrayList;
import tas.controller.*;

public class ViewAdminChoice extends JPanel implements AbstractView {
    
    private DefaultController controller;
    
    private JTabbedPane tabPanel;
    private ArrayList<JRadioButton> updateSelections;
    private ArrayList<JRadioButton> insertSelections;
    private ArrayList<JRadioButton> retrieveSelections;
   
    
    public ViewAdminChoice(DefaultController controller){
        
        this.controller = controller;
        
        updateSelections = new ArrayList();
        insertSelections = new ArrayList();
        retrieveSelections = new ArrayList();
        
        
        initComponents();
    }
    
    private void initComponents(){
        tabPanel = new JTabbedPane();
        tabPanel.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        
        JPanel updateTab = new JPanel();
        updateTab.setPreferredSize(new Dimension(700,800));
        updateTab.setLayout(new BoxLayout(updateTab, BoxLayout.Y_AXIS));
        
        //update radio buttons
        ButtonGroup updateButtons = new ButtonGroup();
        
        JRadioButton updateEmployee = new JRadioButton("Employee");
        updateEmployee.setActionCommand(DefaultController.UPDATE_EMPLOYEE); //Change this
        updateSelections.add(updateEmployee);
        updateButtons.add(updateEmployee);
        updateTab.add(updateEmployee);
        
        JButton submitUpdate = new JButton("Submit");
        submitUpdate.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                for(JRadioButton b: updateSelections){
                    if(b.isSelected()){
                        controller.showCard(b.getActionCommand());
                    }
                }
            }
        });
        updateTab.add(submitUpdate);
        
        tabPanel.add("Update", updateTab);
        
        JPanel insertTab = new JPanel();
        insertTab.setPreferredSize(new Dimension(700,800));
        insertTab.setLayout(new BoxLayout(insertTab, BoxLayout.Y_AXIS));
        ButtonGroup insertButtons = new ButtonGroup();
        
        JRadioButton insertEmployee = new JRadioButton("Employee");
        insertEmployee.setActionCommand(""); // Change This
        insertSelections.add(insertEmployee);
        insertButtons.add(insertEmployee);
        insertTab.add(insertEmployee);
        
        JRadioButton insertPunch = new JRadioButton("Punch");
        insertPunch.setActionCommand("");
        insertSelections.add(insertPunch);
        insertButtons.add(insertPunch);
        insertTab.add(insertPunch);
        
        JButton submitInsert = new JButton("Submit");
        submitInsert.addActionListener(new ActionListener(){
            @Override 
            public void actionPerformed(ActionEvent e){
                //Finish
            }
        });
        insertTab.add(submitInsert);
        
        tabPanel.add("Insert", insertTab);
        
        JPanel retrieveTab = new JPanel();
        retrieveTab.setPreferredSize(new Dimension(700,800));
        retrieveTab.setLayout(new BoxLayout(retrieveTab, BoxLayout.Y_AXIS));
        ButtonGroup retrieveButtons = new ButtonGroup();
        
        JRadioButton retrievePunchList = new JRadioButton("Punch List");
        retrievePunchList.setActionCommand(DefaultController.RETRIEVE_PUNCH_LIST); // change this
        retrieveSelections.add(retrievePunchList);
        retrieveButtons.add(retrievePunchList);
        retrieveTab.add(retrievePunchList);
        
        JRadioButton retrievePayPeriodData = new JRadioButton("Pay Period Data");
        retrievePayPeriodData.setActionCommand(DefaultController.RETRIEVE_PAY_PERIOD_DATA);
        retrieveSelections.add(retrievePayPeriodData);
        retrieveButtons.add(retrievePayPeriodData);
        retrieveTab.add(retrievePayPeriodData);
        
        JButton submitRetrieve = new JButton("Submit");
        submitRetrieve.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                for(JRadioButton b: retrieveSelections){
                    if(b.isSelected()){
                        controller.showCard(b.getActionCommand());
                    }
                }
            }
        });
        retrieveTab.add(submitRetrieve);
        
        tabPanel.add("Retrieve",retrieveTab);
        
        this.add(tabPanel);
    }
    
    private void resetGUI(){
        
    }

    private void selectOperation(String op){
        controller.showCard(op);
    }
    
    
    public void modelPropertyChange(PropertyChangeEvent e){
        
    }
}
