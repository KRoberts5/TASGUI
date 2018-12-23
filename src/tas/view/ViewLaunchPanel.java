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

public class ViewLaunchPanel extends JPanel implements AbstractView {
    
    private DefaultController controller;
    
    public ViewLaunchPanel(DefaultController controller){
        this.controller = controller;
        initComponents();
    }
    
    private void initComponents(){
        this.setLayout(new GridLayout(0,1));
        
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new FlowLayout());
        JLabel label = new JLabel("Please Select Mode of Application:");
        labelPanel.add(label);
        this.add(labelPanel);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        
        JButton terminal = new JButton("Terminal");
        terminal.setActionCommand(DefaultController.TERMINAL);
        terminal.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                setApplicationMode(e.getActionCommand());
            }
        });
        buttonPanel.add(terminal);
        
        JButton admin = new JButton("Admin");
        admin.setActionCommand(DefaultController.ADMIN_CHOICE);
        admin.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                setApplicationMode(e.getActionCommand());
            }
        });
        buttonPanel.add(admin);
        
        this.add(buttonPanel);
    }
    
    private void setApplicationMode(String mode){
        controller.showCard(mode);
    }
    
    public void modelPropertyChange(PropertyChangeEvent e){
        
    }
    
}
