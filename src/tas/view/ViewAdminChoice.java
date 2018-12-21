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
import tas.controller.*;

public class ViewAdminChoice extends JPanel implements AbstractView {
    
    private DefaultController controller;
    
    public ViewAdminChoice(DefaultController controller){
        this.controller = controller;
        initComponents();
    }
    
    private void initComponents(){
        this.setLayout(new GridLayout(0,1));
        
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new FlowLayout());
        JLabel label = new JLabel("Please Choose an Operation:");
        labelPanel.add(label);
        
        this.add(labelPanel);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        
        JButton insert = new JButton("Insert");
        insert.setActionCommand(ViewWindow.ADMIN_INSERT);
        insert.addActionListener(new ActionListener(){
            @Override public void actionPerformed(ActionEvent e){
                selectOperation(e.getActionCommand());
            }
        });
        buttonPanel.add(insert);
        
        JButton update = new JButton("Update");
        update.setActionCommand(ViewWindow.ADMIN_UPDATE);
        update.addActionListener(new ActionListener(){
            @Override 
            public void actionPerformed(ActionEvent e){
                selectOperation(e.getActionCommand());
            }
        });
        buttonPanel.add(update);
        
        JButton retrieve = new JButton("Retrieve");
        retrieve.setActionCommand(ViewWindow.ADMIN_RETRIEVE);
        retrieve.addActionListener(new ActionListener(){
            @Override public void actionPerformed(ActionEvent e){
                selectOperation(e.getActionCommand());
            }
        });
        buttonPanel.add(retrieve);
        
        this.add(buttonPanel);
        
    }
    
    private void selectOperation(String op){
        controller.showCard(op);
    }
    
    
    public void modelPropertyChange(PropertyChangeEvent e){
        
    }
}
