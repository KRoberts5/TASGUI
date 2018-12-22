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
import tas.controller.*;

public class ViewUpdateEmployee extends JPanel implements AbstractView{
    
    DefaultController controller;
    
    private JComboBox comboBox;
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
        
        titlePanel.setLayout(new GridLayout(0,1));
        titlePanel.setPreferredSize(new Dimension(800,100));
        
        JLabel instruction = new JLabel("Select a method for finding an employee:");
        titlePanel.add(instruction);
        this.add(titlePanel,BorderLayout.NORTH);
        
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
                resetGUI();
            }
        });
        textFieldPanel.add(submit);
        
        tabPanel.add("Search Manually",textFieldPanel);
        
        
        comboPanel.setLayout(new FlowLayout());
        comboBox = new JComboBox();
        for(String id: badgeIds){
            comboBox.addItem(id);
        }
        comboPanel.add(comboBox);
        
        JButton submit2 = new JButton("Submit");
        submit2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                resetGUI();
            }
        });
        comboPanel.add(submit2);
        
        tabPanel.add("Select Badge Id" , comboPanel);
        
        this.add(tabPanel);
    }
    
    private void resetGUI(){
        this.badgeIdInput.setText("");
    }
    public void modelPropertyChange(PropertyChangeEvent e){
        //Add RefreshBadgeIds
        
        if(e.getPropertyName().equals(DefaultController.UPDATE_BADGE_IDS)){
            
            ArrayList<String> badgeIds = (ArrayList<String>)e.getNewValue();
            
            this.comboBox = new JComboBox();
            for(String id: badgeIds){
                this.comboBox.addItem(id);
            }
            
        }
        
    }
}
