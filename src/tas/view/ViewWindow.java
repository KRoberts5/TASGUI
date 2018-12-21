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
    
    ViewContainer c1,c2;
    JPanel container,cards;
    
    ViewLaunchPanel launchPanel;
    ViewTerminal terminal;
    
    public ViewWindow(DefaultController controller, ViewLaunchPanel launchPanel, ViewTerminal terminal){
        super("TAS");
        
        this.controller = controller;
        
        this.launchPanel = launchPanel;
        this.terminal = terminal;
        initComponents();
        
        showCard(LAUNCH);
    }
    
    private void initComponents(){
        container = new JPanel();
        container.setLayout(new BorderLayout());
        
        cards = new JPanel();
        cards.setLayout(new CardLayout());
        
        c1 = new ViewContainer(launchPanel);
        JPanel card1 = new JPanel();
        card1.setLayout(new BorderLayout());
        card1.add(c1,BorderLayout.CENTER);
        card1.setVisible(false);
        
        c2 = new ViewContainer(terminal);
        JPanel card2 = new JPanel();
        card2.setLayout(new BorderLayout());
        card2.add(c2,BorderLayout.CENTER);
        card2.setVisible(false);
        
        
        cards.add(card1,LAUNCH);
        cards.add(card2,TERMINAL);
        
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
