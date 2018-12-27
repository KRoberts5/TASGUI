/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tas.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.beans.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import tas.controller.*;

public class ViewUpdateEmployee extends JPanel implements AbstractView{
    
    DefaultController controller;
    
    private BadgeIdSelector badgeSelector;
    private JPanel titlePanel;
    private JPanel comboPanel;
    private JPanel textFieldPanel;
    private JTabbedPane tabPanel;
    
    private JTextField badgeIdInput;
    
    private ArrayList<String> badgeIds;
    
    public ViewUpdateEmployee(DefaultController controller){
        this.controller = controller;
        badgeIds = new ArrayList();
        
        initComponents();
    }
    
    private void initComponents(){
        this.setLayout(new BorderLayout());
        
        titlePanel = new JPanel();
        comboPanel = new JPanel();
        textFieldPanel = new JPanel();
        tabPanel = new JTabbedPane();
        
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setPreferredSize(new Dimension(800,100));
       // titlePanel.setBorder(new EmptyBorder(new Insets(10,10,10,10)));
        
        JLabel instruction = new JLabel("Select a method for finding an employee:");
        instruction.setBorder(new EmptyBorder(new Insets(10,10,10,10)));
        titlePanel.add(instruction);
        this.add(titlePanel,BorderLayout.NORTH);
        
        comboPanel.setLayout(new FlowLayout());
        badgeSelector = new BadgeIdSelector();
        for(String id: badgeIds){
            badgeSelector.addItem(id);
        }
        comboPanel.add(badgeSelector);
        
        JButton submit2 = new JButton("Submit");
        submit2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String badgeId = (String)badgeSelector.getSelectedItem();
                submit(badgeId);
                resetGUI();
            }
        });
        comboPanel.add(submit2);
        
        tabPanel.add("Select Badge Id" , comboPanel);
        
        textFieldPanel.setLayout(new FlowLayout());
        JLabel badgeId = new JLabel("Badge ID:");
        textFieldPanel.add(badgeId);
        badgeIdInput = new JTextField();
        badgeIdInput.setPreferredSize(new Dimension(150,50));
        textFieldPanel.add(badgeIdInput);
        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String badgeId = badgeIdInput.getText();
                submit(badgeId);
                resetGUI();
            }
        });
        textFieldPanel.add(submit);
        
        tabPanel.add("Search Manually",textFieldPanel);
        
        JLabel comboLabel = new JLabel("Select ID");
        comboLabel.setPreferredSize(new Dimension(200,30));
        tabPanel.setTabComponentAt(0, comboLabel);
        JLabel textFieldLabel = new JLabel("Search Manually");
        textFieldLabel.setPreferredSize(new Dimension(200,30));
        tabPanel.setTabComponentAt(1, textFieldLabel);
        
        
        this.add(tabPanel);
    }
    private void submit(String badgeId){
        
        controller.getEmployeeData(badgeId);
        controller.showCard(DefaultController.UPDATE_EMPLOYEE_INPUT);
    }
    private void resetGUI(){
        this.badgeIdInput.setText("");
        this.badgeSelector.setSelectedIndex(0);
    }
    public void modelPropertyChange(PropertyChangeEvent e){
        //Add RefreshBadgeIds
        
        if(e.getPropertyName().equals(DefaultController.UPDATE_BADGE_IDS)){
            
            ArrayList<String> badgeIds = (ArrayList<String>)e.getNewValue();
            
           this.badgeSelector.updateBadgeIds(badgeIds);
            
        }
        
    }
}
