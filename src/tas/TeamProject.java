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

public class TeamProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {   
        
        DefaultModel m = new DefaultModel();
        DefaultController c = new DefaultController();
        
        ViewLaunchPanel launchPanel = new ViewLaunchPanel(c);
        
        
        c.addModel(m);
        c.addView(launchPanel);
 
        
        EventQueue.invokeLater(() -> {
        
            ViewWindow window = new ViewWindow(c, launchPanel);
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
