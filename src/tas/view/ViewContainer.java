/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tas.view;

import java.awt.*;
import javax.swing.*;

public class ViewContainer extends JPanel{
    private final JPanel panel;
    
    public ViewContainer(JPanel panel){
        super();
        this.panel = panel;
        initComponents();
    }
    
    private void initComponents(){
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        add(panel, BorderLayout.CENTER);
    }
}