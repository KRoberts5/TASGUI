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



public class DateSelector extends JPanel{
    
    private static final int FIRST_YEAR = 2018;
    private static final int LAST_YEAR = 2020;
    
    private GregorianCalendar gc;
    
    private String[] monthNames = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    
    private ArrayList<Integer> months;
    private ArrayList<Integer> daysOfMonth;
    private ArrayList<Integer> years;
    
    private JComboBox monthComboBox;
    private JComboBox dayComboBox;
    private JComboBox yearComboBox;
    
    public DateSelector(){
        gc = new GregorianCalendar(2018,Calendar.JANUARY,1);
        months = new ArrayList();
        daysOfMonth = new ArrayList();
        years = new ArrayList();
        
        months.add(Calendar.JANUARY);
        months.add(Calendar.FEBRUARY);
        months.add(Calendar.MARCH);
        months.add(Calendar.APRIL);
        months.add(Calendar.MAY);
        months.add(Calendar.JUNE);
        months.add(Calendar.JULY);
        months.add(Calendar.AUGUST);
        months.add(Calendar.SEPTEMBER);
        months.add(Calendar.OCTOBER);
        months.add(Calendar.NOVEMBER);
        months.add(Calendar.DECEMBER);
        
        for(int i = 1; i <= gc.getActualMaximum(Calendar.DAY_OF_MONTH); ++i){
            daysOfMonth.add(i);
        }
        
        for(int i = FIRST_YEAR; i <= LAST_YEAR; ++i){
            years.add(i);
        }
        
        
        initComponents();
    }
    
    private void initComponents(){
        this.setLayout(new GridLayout(0,3));
        
        JLabel monthLabel = new JLabel("Month");
        JLabel dayLabel = new JLabel("Day");
        JLabel yearLabel = new JLabel("Year");
        
        this.add(monthLabel);
        this.add(dayLabel);
        this.add(yearLabel);
        
        monthComboBox = new JComboBox();
        for(Integer i: months){
            monthComboBox.addItem(monthNames[i]);
        }
        monthComboBox.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String monthName = (String)monthComboBox.getSelectedItem();
                int month = 0;
                
                for(int i = 0; i <monthNames.length;++i){
                    if(monthName.equals(monthNames[i])){
                        month = i;
                    }
                }
                
                gc.set(Calendar.MONTH, month );
                refreshDays();
                
                //printGC();
            }
        });
        this.add(monthComboBox);
        
        dayComboBox = new JComboBox();
        for(Integer i: daysOfMonth){
            dayComboBox.addItem(i);
        }
        dayComboBox.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                
                if(dayComboBox.getSelectedItem() != null){
                    int day = (Integer)dayComboBox.getSelectedItem();
                    gc.set(Calendar.DAY_OF_MONTH, day);
                }
                else{
                    gc.set(Calendar.DAY_OF_MONTH, 1);
                }
               //printGC(); 
            }
            
        });
        this.add(dayComboBox);
        
        yearComboBox = new JComboBox();
        for(Integer i: years){
            yearComboBox.addItem(i);
        }
        yearComboBox.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                int year = (Integer)yearComboBox.getSelectedItem();
                gc.set(Calendar.YEAR, year);
                
                if(gc.get(Calendar.MONTH) == Calendar.FEBRUARY)
                    refreshDays();
            }
        });
        this.add(yearComboBox);
        
    }
    private void refreshDays(){
        
        int numDays = dayComboBox.getItemCount();
        int daysInMonth = gc.getActualMaximum(Calendar.DAY_OF_MONTH);
        
        if(numDays != daysInMonth){
            dayComboBox.removeAllItems();
            
            for(int i = 1; i<= daysInMonth; ++i){
                dayComboBox.addItem(i);
            }
        }
    }
    
    public void resetGUI(){
        monthComboBox.setSelectedIndex(0);
        dayComboBox.setSelectedIndex(0);
        yearComboBox.setSelectedIndex(0);
    }
   
    
    public GregorianCalendar getGregorianCalendar(){
        return gc;
    }
    
    public void printGC(){
        System.out.println("Year: " + gc.get(Calendar.YEAR));
        System.out.println("Month: " + gc.get(Calendar.MONTH));
        System.out.println("Day: " + gc.get(Calendar.DAY_OF_MONTH));
        System.out.println();
    }
}
