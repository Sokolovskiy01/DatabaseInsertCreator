public class Team {

    int id;
    String name;
    String description;
    int company_id; // need to create company at first

    public Team(int id, String name, String description, int company_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.company_id = company_id;
    }

    public String getInsertValue() {
        return "INSERT INTO teams VALUES (" + this.id + ",'" + this.name + "','" + this.description + "'," + this.company_id + ")";
    }

}
