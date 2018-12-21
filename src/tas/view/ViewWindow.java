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
import javax.swing.*;
import java.beans.*;
import tas.controller.*;

public class ViewWindow extends JFrame implements AbstractView {
    
    public static final String LAUNCH = "Launch";
    public static final String TERMINAL = "Terminal";
    public static final String ADMIN = "Admin";
    public static final String ADMIN_CHOICE = "AdminChoice";
    public static final String ADMIN_RETRIEVE = "AdminRetrieve";
    public static final String ADMIN_UPDATE = "AdminUpdate";
    public static final String ADMIN_INSERT = "AdminInsert";
    
    private DefaultController controller;
    
    ViewContainer c1;
    JPanel container,cards;
    
    ViewLaunchPanel launchPanel;
    
    public ViewWindow(DefaultController controller, ViewLaunchPanel launchPanel){
        super("TAS");
        
        this.controller = controller;
        
        this.launchPanel = launchPanel;
        
        initComponents();
        
        showCard(LAUNCH);
    }
    
    private void initComponents(){
        container = new JPanel();
        container.setLayout(new BorderLayout());
        
        c1 = new ViewContainer(launchPanel);
        
        cards = new JPanel();
        cards.setLayout(new CardLayout());
        
        JPanel card1 = new JPanel();
        card1.setLayout(new BorderLayout());
        card1.add(c1,BorderLayout.CENTER);
        card1.setVisible(false);
        
        
        cards.add(card1,LAUNCH);
        
        container.add(cards,BorderLayout.CENTER);
        
        this.getContentPane().add(container);
        
    }
    public void showCard(String name){
        CardLayout c1 = (CardLayout)(cards.getLayout());
        c1.show(cards,name);
    }
    public void modelPropertyChange(PropertyChangeEvent e){
        
    }
}
