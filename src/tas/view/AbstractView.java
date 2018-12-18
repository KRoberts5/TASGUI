/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tas.view;

import java.beans.PropertyChangeEvent;

public interface AbstractView {
    
    /*
     * This class is an interface instead of an abstract class because, in this
     * example, the Views already extend one of the Swing GUI classes, and
     * multiple inheritance is not permitted in Java.
    
     * This interface is simple: it specifies an abstract method called
     * "modelPropertyChange()", which receives a PropertyChange event from a
     * controller and updates the View accordingly.  Each View object which
     * implements this interface must implement "modelPropertyChange()" in its
     * own way, since each View may render the corresponding property of the
     * Model differently.
     */
 
    public abstract void modelPropertyChange(final PropertyChangeEvent e);
    
}