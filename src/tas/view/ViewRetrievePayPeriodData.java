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
import javax.swing.text.DefaultCaret;
import tas.model.Punch;

public class ViewRetrievePayPeriodData extends JPanel implements AbstractView{
    private DefaultController controller;
    private MonthSelector monthSelector;
    private BadgeIdSelector badgeSelector;
    private JTextArea output;
    private JScrollPane outputPanel;
    
    public ViewRetrievePayPeriodData(DefaultController c){
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
        
        output = new JTextArea("");
        output.setEditable(false);
        output.setMargin(new Insets(10,10,10,10));
        DefaultCaret caret = (DefaultCaret) output.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        outputPanel = new JScrollPane(output);
        outputPanel.setPreferredSize(new Dimension(700,400));
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
        
        this.add(buttonPanel);
        
    }
    
    private void submit(){
        
        String badgeId = (String)badgeSelector.getSelectedItem();
        GregorianCalendar date = monthSelector.getGregorianCalendar();
        
        controller.getPayPeriodData(badgeId,date);
    }
    private void resetGUI(){
        output.setText("");
        badgeSelector.setSelectedIndex(0);
        monthSelector.resetGUI();
    }
    
    public void modelPropertyChange(PropertyChangeEvent e){
        
        if(e.getPropertyName().equals(DefaultController.UPDATE_BADGE_IDS)){
            ArrayList<String> badgeIds = (ArrayList<String>)e.getNewValue();
            badgeSelector.updateBadgeIds(badgeIds);
        }
        
        else if(e.getPropertyName().equals(DefaultController.UPDATE_PAY_PERIOD_DATA)){
            String outputText = (String)e.getNewValue();
            
            output.setText(outputText);
            //outputPanel.set
        }
        else if(e.getPropertyName().equals(DefaultController.NO_PAY_PERIOD_DATA)){
            output.setText("No Pay Period Data to Report.");
        }
        else if(e.getPropertyName().equals(DefaultController.RESET_GUI)){
            resetGUI();
        }
        
    }
}
