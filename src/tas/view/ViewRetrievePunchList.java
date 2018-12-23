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
import java.util.GregorianCalendar;
import tas.model.Punch;

public class ViewRetrievePunchList extends JPanel implements AbstractView {
    
    private DefaultController controller;
    private MonthSelector monthSelector;
    private BadgeIdSelector badgeSelector;
    private JTextArea output;
    
    public ViewRetrievePunchList(DefaultController c){
        controller = c;
        
        initComponents();
        
    }
    
    private void initComponents(){
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        
        JPanel badgePanel = new JPanel();
        JLabel badgeLabel = new JLabel("Badge Id: ");
        badgePanel.add(badgeLabel);
        badgeSelector = new BadgeIdSelector();
        badgePanel.add(badgeSelector);
        this.add(badgePanel);
        
        monthSelector = new MonthSelector();
        this.add(monthSelector);
        
        JPanel outputPanel = new JPanel();
        output = new JTextArea();
        output.setEditable(false);
        output.setPreferredSize(new Dimension(700,400));
        outputPanel.add(output);
        this.add(outputPanel);
        
        JPanel buttonPanel = new JPanel();
        JButton submit = new JButton("Submit Search");
        submit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                submit();
            }
        });
        buttonPanel.add(submit);
        ReturnHomeButton home = new ReturnHomeButton(controller);
        home.setPreferredSize(submit.getPreferredSize());
        buttonPanel.add(home);
        
        this.add(buttonPanel);
        
    }
    
    private void submit(){
        
        String badgeId = (String)badgeSelector.getSelectedItem();
        GregorianCalendar date = monthSelector.getGregorianCalendar();
        
        controller.getPunchList(badgeId,date);
    }
    
    public void modelPropertyChange(PropertyChangeEvent e){
        
        if(e.getPropertyName().equals(DefaultController.UPDATE_BADGE_IDS)){
            ArrayList<String> badgeIds = (ArrayList<String>)e.getNewValue();
            badgeSelector.updateBadgeIds(badgeIds);
        }
        
        else if(e.getPropertyName().equals(DefaultController.UPDATE_DAILY_PUNCH_LIST)){
            ArrayList<Punch> punchList = (ArrayList<Punch>)e.getNewValue();
            
            String outputText = "";
            
            for(Punch p: punchList){
                outputText += p.printOriginalTimestamp() +"\n";
                outputText += p.printAdjustedTimestamp() +"\n\n";
                
            }
            
            output.setText(outputText);
        }
        else if(e.getPropertyName().equals(DefaultController.NO_DAILY_PUNCH_LIST_DATA)){
            output.setText("No Punch Data to Report.");
        }
        
    }
}
