import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.*;

// basically a company instance
public class SQLSchema {

    Random random;

    List<Developer> devsList;
    List<Manager> managerList;
    List<Team> teamList;
    List<CompanyRepresentatives> compReps;
    List<Project> projectList;

    Map<Integer, Integer> teamDevelopers;
    Map<Integer, Integer> teamManagers;

    List<HashMap<String, Object>> teamDevelopersMongo;
    List<HashMap<String, Object>> teamManagersMongo;

    private final int companyId;
    private final String companyTitle;
    private final LocalDate dateOfFoundation;
    private final String description;
    private final int companyFoundationYear;

    private HashMap<String, Object> mongodbInsertObject;

    public SQLSchema() {
        this.random = new Random();

        this.devsList = new ArrayList<>();
        this.managerList = new ArrayList<>();
        this.teamList = new ArrayList<>();
        this.compReps = new ArrayList<>();
        this.projectList = new ArrayList<>();

        this.teamDevelopers = new HashMap<>();
        this.teamManagers = new HashMap<>();

        this.teamDevelopersMongo = new ArrayList<>();
        this.teamManagersMongo = new ArrayList<>();

        this.companyId = Main.getCompanyId();
        this.companyTitle = this.getRandomString(random.nextInt(118) + 10);
        this.companyFoundationYear = new Random().nextInt(34) + 1980; // 1980 - 2014
        this.dateOfFoundation = LocalDate.of(this.companyFoundationYear, random.nextInt(11) + 1, random.nextInt(25) + 1);
        this.description = this.getRandomString(random.nextInt(1200) + 100);

        // cast to mongodb Object
        this.mongodbInsertObject = new HashMap<>();
        this.mongodbInsertObject.put("_id", new ObjectId());
        this.mongodbInsertObject.put("title", this.companyTitle);
        this.mongodbInsertObject.put("date_of_foundation", this.dateOfFoundation);
        this.mongodbInsertObject.put("description", this.description);
        // set companyId
    }

    public String getRandomString (int length) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomLetterNumber = random.nextInt(rightLimit - leftLimit) + leftLimit;
            sb.append((char) randomLetterNumber);
        }
        return sb.toString();
    }

    public void createRandomDevelopers(int size) {
        for (int i = 0; i < size; i++) {
            Developer dev = new Developer(Main.getDevId(), this.getRandomString(random.nextInt(20)+ 8), this.getRandomString(random.nextInt(30) + 12),
                    random.nextInt(30) + 18, LocalDate.of(random.nextInt(2020 - this.companyFoundationYear) + this.companyFoundationYear, random.nextInt(12) + 1, random.nextInt(28) + 1),
                    null,random.nextDouble() * 10000, this.getRandomString(10));
            this.devsList.add(dev);
        }
    }

    public void createRandomManagers(int size) {
        for (int i = 0; i < size; i++) {
            Manager man = new Manager(Main.getManId(), this.getRandomString(random.nextInt(20)+ 8), this.getRandomString(random.nextInt(30) + 12),
                    random.nextInt(30) + 18, LocalDate.of(random.nextInt(2020 - this.companyFoundationYear) + this.companyFoundationYear, random.nextInt(12) + 1, random.nextInt(25) + 1),
                    null,random.nextDouble() * 12000);
            this.managerList.add(man);
        }
    }

    public void createRandomTeamsAndProjects(int size) {
        for (int i = 0; i < size; i++) {
            Team t = new Team(Main.getTeamId(),this.getRandomString(random.nextInt(32) + 16),getRandomString(random.nextInt(200) + 25), this.companyId);
            t.setCompanyMongoId(this.getMongoId());
            Project p = new Project(Main.getProjectId(),this.getRandomString(random.nextInt(250) + 30), random.nextDouble() * 1000000, this.getRandomString(random.nextInt(500) + 30),
                    LocalDate.of(random.nextInt(2020 - this.companyFoundationYear) + this.companyFoundationYear, random.nextInt(12) + 1, random.nextInt(25) + 1), null,
                    random.nextInt(5) + 1 + "-" + (random.nextInt(11) + 1), this.compReps.get(1).id , t.id);
            p.setDevteamIdMongoId(t.getMongoId());
            p.setProjectOwnerIdMongoId(this.compReps.get(1).getMongoId());
            this.teamList.add(t);
            this.projectList.add(p);
        }
    }

    public void creaeteCompanyReps() {
        CompanyRepresentatives c1 = new CompanyRepresentatives(Main.getComp_repsId(),this.getRandomString(random.nextInt(20)+ 8), this.getRandomString(random.nextInt(30) + 12), this.dateOfFoundation, "CEO", this.companyId);
        CompanyRepresentatives c2 = new CompanyRepresentatives(Main.getComp_repsId(),this.getRandomString(random.nextInt(20)+ 8), this.getRandomString(random.nextInt(30) + 12), this.dateOfFoundation, "Co-founder", this.companyId);
        CompanyRepresentatives c3 = new CompanyRepresentatives(Main.getComp_repsId(),this.getRandomString(random.nextInt(20)+ 8), this.getRandomString(random.nextInt(30) + 12),
                LocalDate.of(random.nextInt(2020 - this.companyFoundationYear) + this.companyFoundationYear, random.nextInt(12) + 1, random.nextInt(25) + 1), "HR", this.companyId);
        CompanyRepresentatives c4 = new CompanyRepresentatives(Main.getComp_repsId(),this.getRandomString(random.nextInt(20)+ 8), this.getRandomString(random.nextInt(30) + 12),
                LocalDate.of(random.nextInt(2020 - this.companyFoundationYear) + this.companyFoundationYear, random.nextInt(12) + 1, random.nextInt(25) + 1), "HR", this.companyId);
        this.compReps.add(c1);
        this.compReps.add(c2);
        this.compReps.add(c3);
        this.compReps.add(c4);
        for (CompanyRepresentatives cr : this.compReps) {
            cr.setCompanyMongoId(this.getMongoId());
        }
    }

    public void connectDevelopersToTeams() {
        for (int i = 0; i < this.teamList.size(); i++) {
            for (int j = i * 14; j < (i + 1) * 14; j++) {
                this.teamDevelopers.put(this.devsList.get(j).id, this.teamList.get(i).id);
                HashMap<String, Object> insert = new HashMap<>();
                insert.put("team_id", this.teamList.get(i).getMongoId());
                insert.put("developer_id", this.devsList.get(j).getMongoId());
                this.teamDevelopersMongo.add(insert);
            }
        }
    }

    public void connectManagersToTeams() {
        for (int i = 0; i < this.teamList.size(); i++) {
            for (int j = i * 3; j < (i + 1) * 3; j++) {
                this.teamManagers.put(this.managerList.get(j).id, this.teamList.get(i).id);
                HashMap<String, Object> insert = new HashMap<>();
                insert.put("team_id", this.teamList.get(i).getMongoId());
                insert.put("manager_id", this.managerList.get(j).getMongoId());
                this.teamManagersMongo.add(insert);
            }
        }
    }

    public String getCompanyInsertValue() {
        return "INSERT INTO companies VALUES(" + this.companyId + ",'" + this.companyTitle + "','" + this.dateOfFoundation + "','" + this.description + "')";
    }

    public Document getMongodbInsertObject() { return new Document(this.mongodbInsertObject); }

    public ObjectId getMongoId() { return (ObjectId) this.mongodbInsertObject.get("_id"); }

}

