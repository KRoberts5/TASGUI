/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tas.view;

/**
 *
 * @author Brendan
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.beans.*;
import tas.controller.*;

public class ViewTerminal extends JPanel implements AbstractView{
    
    public static final String PUNCH_IN = "PunchIn";
    public static final String PUNCH_OUT = "PunchOut";
    
    private DefaultController controller;
    
    JTextField badgeField;
    
    public ViewTerminal(DefaultController controller){
        this.controller = controller;
        initComponents();
    }
    
    private void initComponents(){
        this.setLayout(new GridLayout(0,1));
        
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        
        JLabel badgeLabel = new JLabel("Badge ID:");
        inputPanel.add(badgeLabel);
        
        badgeField = new JTextField();
        badgeField.setPreferredSize(new Dimension(150,30));
        inputPanel.add(badgeField);
        
        this.add(inputPanel);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        
        JButton punchIn = new JButton("Punch In");
        punchIn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                punchIn();
            }
        });
        buttonPanel.add(punchIn);
        
        JButton punchOut = new JButton("Punch Out");
        punchOut.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                punchOut();
            }
        });
        buttonPanel.add(punchOut);
        
        this.add(buttonPanel);
               
        
    }
    
    private void punchIn(){
        String badgeId = badgeField.getText();
        controller.punchIn(badgeId);
        resetComponents();
    }
    private void punchOut(){
        String badgeId = badgeField.getText();
        controller.punchOut(badgeId);
        resetComponents();
    }
    
    private void resetComponents(){
        badgeField.setText("");
    }
    
    public void modelPropertyChange(PropertyChangeEvent e){
        
        if(e.getPropertyName().equals(DefaultController.PUNCH_IN_SUCCESS)){
            JOptionPane.showMessageDialog(this, "Punch In was Successful");
        }
        else if(e.getPropertyName().equals(DefaultController.PUNCH_IN_FAIL_INVALID_BADGE_ID)){
            JOptionPane.showMessageDialog(this, "Punch In Failed. Invalid Badge ID.");
        }
        else if(e.getPropertyName().equals(DefaultController.PUNCH_OUT_FAIL_INVALID_BADGE_ID)){
            JOptionPane.showMessageDialog(this, "Punch Out Failed. Invalid Badge ID.");
        }
        else if(e.getPropertyName().equals(DefaultController.PUNCH_OUT_SUCCESS)){
            JOptionPane.showMessageDialog(this, "Punch Out was Successful.");
        }
    }
}
