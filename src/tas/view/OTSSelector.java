/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tas.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import tas.controller.*;
import java.beans.*;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class OTSSelector extends JPanel{
    
    public static final int HOUR_MAX = 24;
    public static final int HOUR_MIN = 1;
    public static final int MIN_MAX = 59;
    public static final int MIN_MIN = 0;
    public static final int SEC_MAX = 59;
    public static final int SEC_MIN = 0;
    
    DateSelector dateSelector;
    
    JSpinner hourSpinner;
    JSpinner minSpinner;
    JSpinner secSpinner;
    
    GregorianCalendar ots;
    
    public OTSSelector(){
        ots = new GregorianCalendar();
        initComponents();
    }
    
    private void initComponents(){
        this.setLayout(new GridBagLayout());
        GridBagConstraints mainGBC = new GridBagConstraints();
        mainGBC.gridx = 0;
        mainGBC.gridy = 0;
        mainGBC.insets = new Insets(10,10,10,10);
        mainGBC.fill = GridBagConstraints.HORIZONTAL;
        
         dateSelector = new DateSelector();
        
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0,10,0,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        
        JLabel hourLabel = new JLabel("Hour");
        JLabel minLabel = new JLabel("Minutes");
        JLabel secLabel = new JLabel("Seconds");
        
        timePanel.add(hourLabel,gbc);
        gbc.gridx = 1;
        timePanel.add(minLabel,gbc);
        gbc.gridx = 2;
        timePanel.add(secLabel,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        
        SpinnerNumberModel hourModel = new SpinnerNumberModel(HOUR_MIN,HOUR_MIN,HOUR_MAX,1);
        hourSpinner = new JSpinner(hourModel);
        ((JSpinner.DefaultEditor)hourSpinner.getEditor()).getTextField().setEditable(false);
        hourSpinner.addMouseWheelListener(new MouseWheelListener(){
            @Override
            public void mouseWheelMoved(MouseWheelEvent e){
                
                int currentValue =((Integer)hourSpinner.getValue()).intValue();
                
                if((currentValue >= HOUR_MIN) && (currentValue <= HOUR_MAX)){
                    if(currentValue == HOUR_MIN){
                       if(e.getWheelRotation() < 0)
                            hourSpinner.setValue(new Integer(currentValue - e.getWheelRotation())); 
                    }
                    else if(currentValue == HOUR_MAX){
                        if(e.getWheelRotation() > 0)
                            hourSpinner.setValue(new Integer(currentValue - e.getWheelRotation())); 
                    }
                    else
                        hourSpinner.setValue(new Integer(currentValue - e.getWheelRotation())); 
                    
                }
            }
        });
        SpinnerNumberModel minModel = new SpinnerNumberModel(MIN_MIN,MIN_MIN,MIN_MAX,1);
        minSpinner = new JSpinner(minModel);
        minSpinner.addMouseWheelListener(new MouseWheelListener(){
            @Override
            public void mouseWheelMoved(MouseWheelEvent e){
                int currentValue = ((Integer) minSpinner.getValue()).intValue();
                
                if((currentValue >= MIN_MIN) && (currentValue <= MIN_MAX)){
                    if(currentValue == MIN_MIN){
                        if(e.getWheelRotation() < 0){
                            minSpinner.setValue(new Integer(currentValue - e.getWheelRotation()));
                        }
                    }
                    else if(currentValue == MIN_MAX){
                        if(e.getWheelRotation()> 0)
                            minSpinner.setValue(new Integer(currentValue - e.getWheelRotation()));
                    }
                    else
                        minSpinner.setValue(new Integer(currentValue - e.getWheelRotation()));
                }
            }
        });
        ((JSpinner.DefaultEditor)minSpinner.getEditor()).getTextField().setEditable(false);
        SpinnerNumberModel secModel = new SpinnerNumberModel(SEC_MIN,SEC_MIN,SEC_MAX,1);
        secSpinner = new JSpinner(secModel);
        ((JSpinner.DefaultEditor)secSpinner.getEditor()).getTextField().setEditable(false);
        secSpinner.addMouseWheelListener(new MouseWheelListener(){
            @Override
            public void mouseWheelMoved(MouseWheelEvent e){
               int currentValue = ((Integer) secSpinner.getValue()).intValue();
                
                if((currentValue >= SEC_MIN) && (currentValue <= SEC_MAX)){
                    if(currentValue == SEC_MIN){
                        if(e.getWheelRotation() < 0){
                            secSpinner.setValue(new Integer(currentValue - e.getWheelRotation()));
                        }
                    }
                    else if(currentValue == SEC_MAX){
                        if(e.getWheelRotation()> 0)
                            secSpinner.setValue(new Integer(currentValue - e.getWheelRotation()));
                    }
                    else
                        secSpinner.setValue(new Integer(currentValue - e.getWheelRotation()));
                }
            } 
        });
        
        timePanel.add(hourSpinner,gbc);
        gbc.gridx = 1;
        timePanel.add(minSpinner,gbc);
        gbc.gridx = 2;
        timePanel.add(secSpinner,gbc);
        
        this.add(dateSelector,mainGBC);
        mainGBC.gridy = 1;
        this.add(timePanel,mainGBC);
        
    }
    
    public void resetGUI(){
        dateSelector.resetGUI();
        
        this.hourSpinner.setValue(HOUR_MIN);
        this.minSpinner.setValue(MIN_MIN);
        this.secSpinner.setValue(SEC_MIN);
    }
    public GregorianCalendar getOTS(){
        
        GregorianCalendar date = dateSelector.getGregorianCalendar();
        
        ots.setTimeInMillis(date.getTimeInMillis());
        
        int hour = (Integer)hourSpinner.getValue();
        int min = (Integer)minSpinner.getValue();
        int sec = (Integer)secSpinner.getValue();
        
        ots.set(Calendar.HOUR_OF_DAY, hour);
        ots.set(Calendar.MINUTE,min);
        ots.set(Calendar.SECOND, sec);
        
        return ots;
    }
}
