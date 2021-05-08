import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.HashMap;

public class Developer {

    int id;
    String name;
    String surname;
    int age;
    LocalDate employment_date;
    LocalDate dismissal_date;
    double salary;
    String access_level;

    private HashMap<String, Object> mongodbInsertObject;

    public Developer(int id, String name, String surname, int age, LocalDate employment_date, LocalDate dismissal_date, double salary, String access_level) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.employment_date = employment_date;
        this.dismissal_date = dismissal_date;
        this.salary = Math.round(salary * 100) / 100.0;
        this.access_level = access_level;

        // cast to mongodb Object
        this.mongodbInsertObject = new HashMap<>();
        this.mongodbInsertObject.put("_id", new ObjectId());
        this.mongodbInsertObject.put("name", this.name);
        this.mongodbInsertObject.put("surname", this.surname);
        this.mongodbInsertObject.put("age", this.age);
        this.mongodbInsertObject.put("employment_date", this.employment_date);
        this.mongodbInsertObject.put("dismissal_date", this.dismissal_date);
        this.mongodbInsertObject.put("salary", this.salary);
        this.mongodbInsertObject.put("access_level", this.access_level);
    }

    public String getInsertValue() {
        return "(" + this.id + ",'" + this.name + "','" + this.surname + "'," + this.age + ",'" + this.employment_date.toString() + "',"
                + ((this.dismissal_date == null) ? "null," : "'" + this.dismissal_date.toString() + "',") + this.salary + ",'" + this.access_level + "')" ;
    }

    public Document getMongodbInsertObject() { return new Document(this.mongodbInsertObject); }

    public ObjectId getMongoId() { return (ObjectId) this.mongodbInsertObject.get("_id"); }

}
