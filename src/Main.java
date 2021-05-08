import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BSONCallback;
import org.bson.BSONObject;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.simple.JSONObject;

import javax.print.Doc;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class Main {

    static final String postgreUrl = "jdbc:postgresql://localhost/bachelorTest";
    static final String mongodbUrl = "mongodb://localhost";
    static final String postgreFilename = "output/postgreInsert.sql";

    private static int devId = 0;
    private static int manId = 0;
    private static int teamId = 0;
    private static int companyId = 0;
    private static int comp_repsId = 0;
    private static int projectId = 0;

    public static int getDevId() { devId++; return devId; }
    public static int getManId() { manId++; return manId;}
    public static int getTeamId() { teamId++; return teamId; }
    public static int getCompanyId() { companyId++; return companyId; }
    public static int getComp_repsId() { comp_repsId++; return comp_repsId; }
    public static int getProjectId() { projectId++; return projectId; }

    public static int getCurrentCompanyId() { return companyId; }

    public static void main(String[] args) {

        Random random = new Random();

        Statistics insertStatistics = new Statistics();

        MongoClient client = new MongoClient(mongodbUrl);
        MongoDatabase bachelorTestMongo = client.getDatabase("bachelorTest");

        // Connect to all MongoDB collections
        MongoCollection<Document> developersMongo = bachelorTestMongo.getCollection("developers");
        MongoCollection<Document> managersMongo = bachelorTestMongo.getCollection("managers");
        MongoCollection<Document> companiesMongo = bachelorTestMongo.getCollection("companies");
        MongoCollection<Document> projectsMongo = bachelorTestMongo.getCollection("projects");
        MongoCollection<Document> teamsMongo = bachelorTestMongo.getCollection("teams");
        MongoCollection<Document> team_managersMongo = bachelorTestMongo.getCollection("team_managers");
        MongoCollection<Document> team_developersMongo = bachelorTestMongo.getCollection("team_developers");
        MongoCollection<Document> company_representativesMongo = bachelorTestMongo.getCollection("company_representatives");
        // Connect to all MongoDB collections

        //Developer d = new Developer(1,"Dmytro","Sokolovskyi",19, LocalDate.of(2002 , 8,19),null,2500.00, "strd");
        //Document document = new Document(d.getMongodbInsertObject());

        //developers.insertOne(document);

        /*Properties postgreProps = new Properties();
        postgreProps.setProperty("user","postgres");
        postgreProps.setProperty("password","Sokol");*/

        int approx_records = 23125000;
        int approx_loops = 125000; // each SQLSchema has 185 records

        try {
            //Connection postgreConn = DriverManager.getConnection(postgreUrl, postgreProps);
            //PreparedStatement preparedStatement = postgreConn.prepareStatement("SELECT 1");
            //BufferedWriter postgreOutput = new BufferedWriter(new FileWriter(postgreFilename));

            BufferedWriter postgreDevelopers = new BufferedWriter((new FileWriter("output/PostgreInsertTables/developers.sql")));
            BufferedWriter postgreManagers = new BufferedWriter((new FileWriter("output/PostgreInsertTables/managers.sql")));
            BufferedWriter postgreCompanies = new BufferedWriter((new FileWriter("output/PostgreInsertTables/companies.sql")));
            BufferedWriter postgreCompReps = new BufferedWriter((new FileWriter("output/PostgreInsertTables/compReps.sql")));
            BufferedWriter postgreTeams = new BufferedWriter((new FileWriter("output/PostgreInsertTables/teams.sql")));
            BufferedWriter postgreProjects = new BufferedWriter((new FileWriter("output/PostgreInsertTables/projects.sql")));
            BufferedWriter postgreTeam_Developers = new BufferedWriter((new FileWriter("output/PostgreInsertTables/team_developers.sql")));
            BufferedWriter postgreTeam_Managers = new BufferedWriter((new FileWriter("output/PostgreInsertTables/team_managers.sql")));

            System.out.println("Connection to MongoDB and PostgreSQL successfully established");

            postgreDevelopers.write("INSERT INTO developers VALUES");
            postgreManagers.write("INSERT INTO managers VALUES");
            postgreCompanies.write("INSERT INTO companies VALUES");
            postgreCompReps.write("INSERT INTO company_representatives VALUES");
            postgreTeams.write("INSERT INTO teams VALUES");
            postgreProjects.write("INSERT INTO projects VALUES");
            postgreTeam_Developers.write("INSERT INTO team_developers VALUES");
            postgreTeam_Managers.write("INSERT INTO team_managers VALUES");

            long timeStart = System.currentTimeMillis();

            long timeCheck1;
            long timeCheck2;
            long timeCheck3;

            for (int i = 0; i < approx_loops; i++){

                // Schema auto records init
                SQLSchema schema = new SQLSchema();
                schema.creaeteCompanyReps();
                schema.createRandomTeamsAndProjects(5);
                schema.createRandomManagers(5);
                schema.createRandomDevelopers(80);
                schema.connectDevelopersToTeams();
                schema.connectManagersToTeams();
                // Schema auto records init

                //System.out.println("Developers:");
                for (Developer dev: schema.devsList) {
                    postgreDevelopers.write(((i == 0) ? "" : ",") + dev.getInsertValue() + "\n");
                    //timeCheck1 = System.nanoTime();
                    //postgreConn.prepareStatement(dev.getInsertValue()).executeUpdate();
                    //timeCheck2 = System.nanoTime();
                    developersMongo.insertOne(dev.getMongodbInsertObject());
                    //timeCheck3 = System.nanoTime();

                    //insertStatistics.addPostgreSQLInsertTime(timeCheck2 - timeCheck1);
                    //insertStatistics.addMongoDBInsertTime(timeCheck3 - timeCheck2);
                }

                //System.out.println("Managers:");
                for (Manager man: schema.managerList) {
                    postgreManagers.write(((i == 0) ? "" : ",") + man.getInsertValue() + "\n");
                    //postgreConn.prepareStatement(man.getInsertValue()).executeUpdate();
                    managersMongo.insertOne(man.getMongodbInsertObject());

                    //System.out.println(man.getInsertValue());
                    //System.out.println("MongoId: " + man.getMongoId());
                }

                //System.out.println("Company:");
                postgreCompanies.write(((i == 0) ? "" : ",") + schema.getCompanyInsertValue() + "\n");
                //postgreConn.prepareStatement(schema.getCompanyInsertValue()).executeUpdate();
                companiesMongo.insertOne(schema.getMongodbInsertObject());

                //System.out.println("Company representatives:");
                for (CompanyRepresentatives cr : schema.compReps) {
                    postgreCompReps.write(((i == 0) ? "" : ",") + cr.getInsertValue() + "\n");
                    //postgreConn.prepareStatement(cr.getInsertValue()).executeUpdate();
                    company_representativesMongo.insertOne(cr.getMongodbInsertObject());
                }

                //System.out.println("Teams:");
                for (Team t : schema.teamList) {
                    postgreTeams.write(((i == 0) ? "" : ",") + t.getInsertValue() + "\n");
                    //postgreConn.prepareStatement(t.getInsertValue()).executeUpdate();
                    teamsMongo.insertOne(t.getMongodbInsertObject());
                }

                //System.out.println("Projects:");
                for (Project p : schema.projectList) {
                    postgreProjects.write(((i == 0) ? "" : ",") + p.getInsertValue() + "\n");
                    //postgreConn.prepareStatement(p.getInsertValue()).executeUpdate();
                    projectsMongo.insertOne(p.getMongodbInsertObject());
                }

                //System.out.println("Team_developers:");
                for (Map.Entry<Integer, Integer> team_developer : schema.teamDevelopers.entrySet()) {
                    postgreTeam_Developers.write(((i == 0) ? "(" : ",(") + team_developer.getKey() + "," + team_developer.getValue() + ")\n");
                    //postgreConn.prepareStatement("INSERT INTO team_developers VALUES(" + team_developer.getKey() + "," + team_developer.getValue() + ")").executeUpdate();
                }
                //System.out.println("Team_developers Mongo:");
                for (HashMap<String, Object> td : schema.teamDevelopersMongo) {
                    team_developersMongo.insertOne(new Document(td));
                }

                //System.out.println("Team_managers:");
                for (Map.Entry<Integer, Integer> team_manager : schema.teamManagers.entrySet()) {

                    postgreTeam_Managers.write(((i == 0) ? "(" : ",(") + team_manager.getKey() + " , " + team_manager.getValue() + ")\n");
                    //postgreConn.prepareStatement("INSERT INTO team_managers VALUES(" + team_manager.getKey() + " , " + team_manager.getValue() + ")").executeUpdate();
                }
                //System.out.println("Team_managers Mongo:");
                for (HashMap<String, Object> tm : schema.teamManagersMongo) {
                    team_managersMongo.insertOne(new Document(tm));
                    //System.out.println(tm.get("manager_id") + " , " + tm.get("team_id"));
                }

                if (i % 1000 == 0 || i == approx_loops - 1) {
                    System.out.println("Loop : " + i + ", Records inserted: " + ((i + 1) * 185) + "/" + approx_records);
                }
                if (i == approx_loops - 1) {
                    postgreDevelopers.write(";");
                    postgreManagers.write(";");
                    postgreCompanies.write(";");
                    postgreCompReps.write(";");
                    postgreTeams.write(";");
                    postgreProjects.write(";");
                    postgreTeam_Developers.write(";");
                    postgreTeam_Managers.write(";");
                }
            }
            //preparedStatement.close();
            //postgreConn.close();
            //postgreOutput.close();

            postgreDevelopers.close();
            postgreManagers.close();
            postgreCompanies.close();
            postgreCompReps.close();
            postgreTeams.close();
            postgreProjects.close();
            postgreTeam_Developers.close();
            postgreTeam_Managers.close();

            long timeEnd = System.currentTimeMillis();

            System.out.println("Process took " + (timeEnd - timeStart) + " ms to finish");
        }
        catch (/*SQLException |*/ IOException ex) {
            ex.printStackTrace();
        }

    }
}
