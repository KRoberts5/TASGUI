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
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.DefaultCaret;
import tas.model.Punch;

public class ViewRetrievePayPeriodData extends JPanel implements AbstractView{
    private DefaultController controller;
    private MonthSelector monthSelector;
    private BadgeIdSelector badgeSelector;
    private JTextArea output;
    private JTable overview;
    private JTable punchData;
    private JPanel outputPanel;
    private JScrollPane scrollPanel;
    //private JScrollPane outputPanel;
    
    public ViewRetrievePayPeriodData(DefaultController c){
        controller = c;
        
        initComponents();
        
    }
    
    private void initComponents(){
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(0,1));
        
        JPanel badgePanel = new JPanel();
        JLabel badgeLabel = new JLabel("Badge Id: ");
        badgePanel.add(badgeLabel);
        badgeSelector = new BadgeIdSelector();
        badgePanel.add(badgeSelector);
        
        
        inputPanel.add(badgePanel);
        
        monthSelector = new MonthSelector();
        inputPanel.add(monthSelector);
        
        this.add(inputPanel);
        
        /*output = new JTextArea("");
        output.setEditable(false);
        output.setMargin(new Insets(10,10,10,10));
        DefaultCaret caret = (DefaultCaret) output.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        outputPanel = new JScrollPane(output);
        outputPanel.setPreferredSize(new Dimension(700,400));
        this.add(outputPanel);*/
        
        
        
        outputPanel = new JPanel();
        outputPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20,20,20,20);
        
        String[] overviewCol = {"Badge ID", "Pay Period Start", "Absenteeism", "Salary"};
        String[][] overviewRow = {{"Badge ID", "Pay Period Start", "Absenteeism", "Salary"},{"","","",""}};
        
        overview = new JTable(overviewRow,overviewCol);
        overview.setRowHeight(30);
        int colCount = overview.getColumnModel().getColumnCount();
        for(int i = 0; i < colCount; ++i){
            overview.getColumnModel().getColumn(i).setPreferredWidth(150);
        }
        outputPanel.add(overview,gbc);
        
        gbc.gridy = 1;
        
        String[] punchDataCol = {"Punch Type", "Date", "Original Time", "Adjusted Time"};
        String[][] punchDataRow = {{"Punch Type", "Date", "Original Time", "Adjusted Time"},{"","","",""}};
        punchData = new JTable(punchDataRow,punchDataCol);
        punchData.setRowHeight(30);
        colCount = punchData.getColumnModel().getColumnCount();
        for(int i = 0; i < colCount; ++i){
            punchData.getColumnModel().getColumn(i).setPreferredWidth(150);
        }
        outputPanel.add(punchData,gbc);
        
        scrollPanel = new JScrollPane(outputPanel);
        
        this.add(scrollPanel);
        
        
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
        badgeSelector.setSelectedIndex(0);
        monthSelector.resetGUI();
        resetTables();
    }
    private void resetTables(){
        String[] overviewCol = {"Badge ID", "Pay Period Start", "Absenteeism", "Salary"};
        String[][] overviewRow = {{"Badge ID", "Pay Period Start", "Absenteeism", "Salary"},{"","","",""}};
        DefaultTableModel overviewModel = new DefaultTableModel(overviewRow,overviewCol);
        
        String[] punchDataCol = {"Punch Type", "Date", "Original Time", "Adjusted Time"};
        String[][] punchDataRow = {{"Punch Type", "Date", "Original Time", "Adjusted Time"},{"","","",""}};
        DefaultTableModel punchDataModel = new DefaultTableModel(punchDataRow,punchDataCol);
        
        updateOverview(overviewModel);
        updatePunchData(punchDataModel);
    }
    
    private void updateOverview(DefaultTableModel m){
        overview.setModel(m);
        
        overview.setRowHeight(30);
        int colCount = overview.getColumnModel().getColumnCount();
        for(int i = 0; i < colCount; ++i){
            overview.getColumnModel().getColumn(i).setPreferredWidth(150);
        }
        
    }
    private void updatePunchData(DefaultTableModel m){
        punchData.setModel(m);
        
        punchData.setRowHeight(30);
        int colCount = punchData.getColumnModel().getColumnCount();
        for(int i = 0; i < colCount; ++i){
            punchData.getColumnModel().getColumn(i).setPreferredWidth(150);
        }
    }
    
    public void modelPropertyChange(PropertyChangeEvent e){
        
        if(e.getPropertyName().equals(DefaultController.UPDATE_BADGE_IDS)){
            ArrayList<String> badgeIds = (ArrayList<String>)e.getNewValue();
            badgeSelector.updateBadgeIds(badgeIds);
        }
        
        else if(e.getPropertyName().equals(DefaultController.UPDATE_PAY_PERIOD_DATA)){
            //String outputText = (String)e.getNewValue();
            
            //output.setText(outputText);
            //outputPanel.set
            HashMap<String,DefaultTableModel> models = (HashMap<String,DefaultTableModel>)e.getNewValue();
            DefaultTableModel overviewModel = (DefaultTableModel)models.get(DefaultController.PAY_PERIOD_OVERVIEW);
            DefaultTableModel punchDataModel = (DefaultTableModel)models.get(DefaultController.PUNCH_DATA);
            
            this.updateOverview(overviewModel);
            this.updatePunchData(punchDataModel);
        }
        else if(e.getPropertyName().equals(DefaultController.NO_PAY_PERIOD_DATA)){
            JOptionPane.showMessageDialog(this, "No Punch Data for the Given Selections.");
        }
        else if(e.getPropertyName().equals(DefaultController.RESET_GUI)){
            resetGUI();
        }
        
    }
}
