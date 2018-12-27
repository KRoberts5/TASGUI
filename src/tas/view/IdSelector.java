/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tas.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.HashMap;

public class IdSelector extends JComboBox {
    
    private HashMap<String,Integer> elementIds;
    
    public IdSelector(){
        elementIds = new HashMap();
    }
    
    public void updateElements(HashMap<String, Integer> values){
        elementIds = values;
        this.removeAllItems();
        
        for(HashMap.Entry<String,Integer> e: values.entrySet()){
            this.addItem(e.getKey());
        }
    }
    public int getSelectedItemId(){
        String selected = (String)this.getSelectedItem();
        int id = elementIds.get(selected);
        
        return id;
    }
}
