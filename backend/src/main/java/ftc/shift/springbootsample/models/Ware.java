package ftc.shift.springbootsample.models;

public class Ware {
    //here will be photo soon

    private String name;
    private String id;
    private String category;
    private String ownerId;
    private String status;
    private String description;
    private String exploitation;

    public Ware(){

    }

    public Ware(String name, String id, String category, String ownerId, String exploitation){
        this.setExploitation(exploitation);
        this.setCategory(category);
        this.setStatus("Свободный");
        this.setOwnerId(ownerId);
        this.setName(name);
        this.setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExploitation() {
        return exploitation;
    }

    public void setExploitation(String exploitation) {
        this.exploitation = exploitation;
    }

    public Ware change (Ware changed_Ware){

        this.name = changed_Ware.name;
        this.id = changed_Ware.id;
        this.category = changed_Ware.category;
        this.ownerId = changed_Ware.ownerId;
        this.status = changed_Ware.status;
        this.description = changed_Ware.description;
        this.exploitation = changed_Ware.exploitation;
        
        return this;

    }
}
