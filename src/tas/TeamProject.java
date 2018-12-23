/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tas;

/**
 *
 * @author bwats
 */

import tas.controller.*;
import tas.model.*;
import tas.view.*;
import java.awt.EventQueue;
import java.awt.Dimension;
import java.util.HashMap;
import javax.swing.UIManager;

public class TeamProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {   
        
        try{
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e){
            System.err.println(e.toString());
        }
 
        
        HashMap<String,AbstractView> views = new HashMap<>();
        
        DefaultModel m = new DefaultModel();
        DefaultController c = new DefaultController();
        
        ViewLaunchPanel launchPanel = new ViewLaunchPanel(c);
        ViewTerminal terminal = new ViewTerminal(c);
        ViewAdminChoice adminChoice = new ViewAdminChoice(c);
        ViewUpdateEmployee updateEmployee = new ViewUpdateEmployee(c);
        
        views.put(DefaultController.TERMINAL, terminal);
        views.put(DefaultController.LAUNCH,launchPanel);
        views.put(DefaultController.ADMIN_CHOICE, adminChoice);
        views.put(DefaultController.UPDATE_EMPLOYEE, updateEmployee);
        
        
        c.addModel(m);
        c.addView(launchPanel);
        c.addView(terminal);
        c.addView(adminChoice);
        c.addView(updateEmployee);
        
        
        
        EventQueue.invokeLater(() -> {
        
            ViewWindow window = new ViewWindow(c, views);
            c.addView(window);
            
            /* Set JFrame Properties */
            
            window.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            window.setPreferredSize(new Dimension(800,700));
            
            window.setResizable(false);
            window.pack();
            window.setVisible(true);
        });
        
    }
}
