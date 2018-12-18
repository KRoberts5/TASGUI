package tas.model;

/**
 *
 * @author Brendan
 */
public class Badge {
    private String id;
    private String description;
    
    public Badge(String id, String description){
        this.id = id;
        this.description = description;
    }
    
    public String toString(){
        String output = "#" + id + " ";
        output += "(" + description + ")";
        
        return output;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}