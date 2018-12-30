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
        
        //Font buttonFont = this.getFont().deriveFont(15f);
        //Font labelFont = this.getFont().deriveFont(15f);
        
        Font buttonFont = this.getFont();
        Font labelFont = this.getFont();
        
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
        
        
        for(JRadioButton b: updateSelections){
            b.setFont(buttonFont);
        }
        
        JButton submitUpdate = new JButton("Submit");
        submitUpdate.setFont(buttonFont);
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
        insertEmployee.setActionCommand(DefaultController.INSERT_EMPLOYEE); // Change This
        insertSelections.add(insertEmployee);
        insertButtons.add(insertEmployee);
        insertTab.add(insertEmployee);
        
        JRadioButton insertPunch = new JRadioButton("Punch");
        insertPunch.setActionCommand(DefaultController.INSERT_PUNCH);
        insertSelections.add(insertPunch);
        insertButtons.add(insertPunch);
        insertTab.add(insertPunch);
        
        for(JRadioButton b: insertSelections){
            b.setFont(buttonFont);
        }
        
        JButton submitInsert = new JButton("Submit");
        submitInsert.setFont(buttonFont);
        submitInsert.addActionListener(new ActionListener(){
            @Override 
            public void actionPerformed(ActionEvent e){
                for(JRadioButton b: insertSelections){
                    if(b.isSelected()){
                        controller.showCard(b.getActionCommand());
                    }
                }
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
        
        for(JRadioButton b: retrieveSelections){
            b.setFont(buttonFont);
        }
        
        JButton submitRetrieve = new JButton("Submit");
        submitRetrieve.setFont(buttonFont);
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
        
        
        JLabel updateLabel = new JLabel("Update");
        updateLabel.setFont(labelFont);
        updateLabel.setPreferredSize(new Dimension(200,30));
        tabPanel.setTabComponentAt(0, updateLabel);
        JLabel insertLabel = new JLabel("Insert");
        insertLabel.setFont(labelFont);
        insertLabel.setPreferredSize(new Dimension(200,30));
        tabPanel.setTabComponentAt(1, insertLabel);
        JLabel retrieveLabel = new JLabel("Retrieve");
        retrieveLabel.setFont(labelFont);
        retrieveLabel.setPreferredSize(new Dimension(200,30));
        tabPanel.setTabComponentAt(2, retrieveLabel);
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
