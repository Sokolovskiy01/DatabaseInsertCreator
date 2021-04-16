import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;

public class CompanyRepresentatives {

    int id;
    String name;
    String surname;
    LocalDate dateOfJoin;
    String role;
    int companyId;

    private HashMap<String, Object> mongodbInsertObject;

    public CompanyRepresentatives(int id, String name, String surname, LocalDate dateOfJoin, String role, int companyId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dateOfJoin = dateOfJoin;
        this.role = role;
        this.companyId = companyId;

        // cast to mongodb Object
        this.mongodbInsertObject = new HashMap<>();
        this.mongodbInsertObject.put("_id", new ObjectId());
        this.mongodbInsertObject.put("name", this.name);
        this.mongodbInsertObject.put("surname", this.surname);
        this.mongodbInsertObject.put("dateOfJoin", this.dateOfJoin);
        this.mongodbInsertObject.put("role", this.role);
        // set companyId
    }

    public String getInsertValue() {
        return "INSERT INTO company_representatives VALUES(" + this.id + ",'" + this.name + "','" + this.surname + "','" + this.dateOfJoin + "','" + this.role + "'," + this.companyId +")";
    }

    public Document getMongodbInsertObject() { return new Document(this.mongodbInsertObject); }

    public ObjectId getMongoId() { return (ObjectId) this.mongodbInsertObject.get("_id"); }
    public void setCompanyMongoId(ObjectId _id) { this.mongodbInsertObject.put("company_id", _id); }

}
