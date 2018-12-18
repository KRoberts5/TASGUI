package tas.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class AbstractModel
{
 
    protected PropertyChangeSupport propertyChangeSupport;
    
    /*
     * These methods add and remove an AbstractController subclass as a
     * a PropertyChangeListener, and ensure that changes to the Model are
     * announced to the controller in the form of a PropertyChange event.
     */
 
    public AbstractModel()
    {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }
 
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
 
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
 
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
 
}