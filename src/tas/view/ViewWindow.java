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
import java.util.*;

public class ViewWindow extends JFrame implements AbstractView {
    
    
    
    
    private DefaultController controller;
    
    ViewContainer c1,c2;
    HashMap<String,AbstractView> containers;
    JPanel container,cards;
    

    
    public ViewWindow(DefaultController controller, HashMap<String,AbstractView> views){
        super("TAS");
        this.controller = controller;
        
        containers = views;
        
        initComponents();
        showCard(DefaultController.LAUNCH);
    }
    
    private void initComponents(){
        
        
        container = new JPanel();
        container.setLayout(new BorderLayout());
        
        cards = new JPanel();
        cards.setLayout(new CardLayout());
        
        for(HashMap.Entry<String,AbstractView> e: containers.entrySet()){
            ViewContainer c = new ViewContainer((JPanel)e.getValue());
            JPanel card = new JPanel();
            card.setLayout(new BorderLayout());
            card.add(c,BorderLayout.CENTER);
            card.setVisible(false);
            
            cards.add(card,e.getKey());
            
        }
        
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
