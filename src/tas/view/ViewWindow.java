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
    
    private DefaultController controller;
    
    public void modelPropertyChange(PropertyChangeEvent e){
        
    }
}
