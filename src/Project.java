import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.HashMap;

public class Project {

    int id;
    String title;
    double budget;
    String technologyDesc;
    LocalDate startDate;
    LocalDate endDate;
    String expectedDevTime;
    int projectOwnerId;
    int devteamId;

    private HashMap<String, Object> mongodbInsertObject;

    public Project(int id, String title, double budget, String technologyDesc, LocalDate startDate, LocalDate endDate, String expectedDevTime, int projectOwnerId, int devteamId) {
        this.id = id;
        this.title = title;
        this.budget = Math.round(budget * 100) / 100.0;
        this.technologyDesc = technologyDesc;
        this.startDate = startDate;
        this.endDate = endDate;
        this.expectedDevTime = expectedDevTime;
        this.projectOwnerId = projectOwnerId;
        this.devteamId = devteamId;

        // cast to mongodb Object
        this.mongodbInsertObject = new HashMap<>();
        this.mongodbInsertObject.put("_id", new ObjectId());
        this.mongodbInsertObject.put("title", this.title);
        this.mongodbInsertObject.put("budget", this.budget);
        this.mongodbInsertObject.put("technologyDesc", this.technologyDesc);
        this.mongodbInsertObject.put("startDate", this.startDate);
        this.mongodbInsertObject.put("endDate", this.endDate);
        this.mongodbInsertObject.put("expectedDevTime", this.expectedDevTime);
        // set companyId
    }

    public String getInsertValue() {
        return "(" + this.id + ",'" + this.title + "'," + this.budget + ",'" + this.technologyDesc + "','" + this.startDate + "',"
                + ((this.endDate == null) ? "null" : ("'" + this.endDate + "'")) + ",'" + this.expectedDevTime + "'," + this.projectOwnerId + "," + this.devteamId + ")";
    }

    public Document getMongodbInsertObject() { return new Document(this.mongodbInsertObject); }

    public ObjectId getMongoId() { return (ObjectId) this.mongodbInsertObject.get("_id"); }
    public void setProjectOwnerIdMongoId(ObjectId _id) { this.mongodbInsertObject.put("project_owner_id", _id); }
    public void setDevteamIdMongoId(ObjectId _id) { this.mongodbInsertObject.put("devteam_id", _id); }

}
