/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tas.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import tas.controller.*;
public class ReturnHomeButton extends JButton{
    
    public ReturnHomeButton(DefaultController c){
        super("Return Home");
        this.setPreferredSize(new Dimension(100,75));
        
        this.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                c.returnHome();
                
            }
        });
    }
}
