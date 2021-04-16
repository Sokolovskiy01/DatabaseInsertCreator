import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.HashMap;

public class Team {

    int id;
    String name;
    String description;
    int company_id; // need to create company at first

    private HashMap<String, Object> mongodbInsertObject;

    public Team(int id, String name, String description, int company_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.company_id = company_id;

        // cast to mongodb Object
        this.mongodbInsertObject = new HashMap<>();
        this.mongodbInsertObject.put("_id", new ObjectId());
        this.mongodbInsertObject.put("name", this.name);
        this.mongodbInsertObject.put("description", this.description);
        // set companyId
    }

    public String getInsertValue() {
        return "INSERT INTO teams VALUES (" + this.id + ",'" + this.name + "','" + this.description + "'," + this.company_id + ")";
    }

    public Document getMongodbInsertObject() { return new Document(this.mongodbInsertObject); }

    public ObjectId getMongoId() { return (ObjectId) this.mongodbInsertObject.get("_id"); }
    public void setCompanyMongoId(ObjectId _id) { this.mongodbInsertObject.put("company_id", _id); }

}
