import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.print.Doc;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class Main {

    static final String postgreUrl = "jdbc:postgresql://localhost/bachelorTest";
    static final String mongodbUrl = "mongodb://localhost";

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

        Properties postgreProps = new Properties();
        postgreProps.setProperty("user","postgres");
        postgreProps.setProperty("password","Sokol");

        int approx_records = 20000165;
        int approx_loops = 108109; // each SQLSchema has 185 records

        try {
            Connection postgreConn = DriverManager.getConnection(postgreUrl, postgreProps);
            PreparedStatement preparedStatement = postgreConn.prepareStatement("SELECT 1");

            System.out.println("Connection to MongoDB and PostgreSQL successfully established");

            long timeCheck1;
            long timeCheck2;
            long timeCheck3;

            for (int i = 0; i < approx_loops; i++){

                /* Schema auto records init */
                SQLSchema schema = new SQLSchema();
                schema.creaeteCompanyReps();
                schema.createRandomTeamsAndProjects(5);
                schema.createRandomManagers(15);
                schema.createRandomDevelopers(70);
                schema.connectDevelopersToTeams();
                schema.connectManagersToTeams();
                /* Schema auto records init */

                //System.out.println("Developers:");
                for (Developer dev: schema.devsList) {
                    timeCheck1 = System.nanoTime();
                    preparedStatement = postgreConn.prepareStatement(dev.getInsertValue());
                    preparedStatement.executeUpdate();
                    timeCheck2 = System.nanoTime();
                    developersMongo.insertOne(dev.getMongodbInsertObject());
                    timeCheck3 = System.nanoTime();

                    insertStatistics.addPostgreSQLInsertTime(timeCheck2 - timeCheck1);
                    insertStatistics.addMongoDBInsertTime(timeCheck3 - timeCheck2);
                    //System.out.println(dev.getInsertValue());
                    //System.out.println("MongoId: " + dev.getMongoId());
                }

                //System.out.println("Managers:");
                for (Manager man: schema.managerList) {
                    timeCheck1 = System.nanoTime();
                    preparedStatement = postgreConn.prepareStatement(man.getInsertValue());
                    preparedStatement.executeUpdate();
                    timeCheck2 = System.nanoTime();
                    managersMongo.insertOne(man.getMongodbInsertObject());
                    timeCheck3 = System.nanoTime();

                    insertStatistics.addPostgreSQLInsertTime(timeCheck2 - timeCheck1);
                    insertStatistics.addMongoDBInsertTime(timeCheck3 - timeCheck2);
                    //System.out.println(man.getInsertValue());
                    //System.out.println("MongoId: " + man.getMongoId());
                }

                //System.out.println("Company:");
                timeCheck1 = System.nanoTime();
                preparedStatement = postgreConn.prepareStatement(schema.getCompanyInsertValue());
                preparedStatement.executeUpdate();
                timeCheck2 = System.nanoTime();
                companiesMongo.insertOne(schema.getMongodbInsertObject());
                timeCheck3 = System.nanoTime();

                insertStatistics.addPostgreSQLInsertTime(timeCheck2 - timeCheck1);
                insertStatistics.addMongoDBInsertTime(timeCheck3 - timeCheck2);
                //System.out.println(schema.getCompanyInsertValue());
                //System.out.println("Mongoid: " + schema.getMongoId());

                //System.out.println("Company representatives:");
                for (CompanyRepresentatives cr : schema.compReps) {
                    timeCheck1 = System.nanoTime();
                    preparedStatement = postgreConn.prepareStatement(cr.getInsertValue());
                    preparedStatement.executeUpdate();
                    timeCheck2 = System.nanoTime();
                    company_representativesMongo.insertOne(cr.getMongodbInsertObject());
                    timeCheck3 = System.nanoTime();

                    insertStatistics.addPostgreSQLInsertTime(timeCheck2 - timeCheck1);
                    insertStatistics.addMongoDBInsertTime(timeCheck3 - timeCheck2);
                    //System.out.println(cr.getInsertValue());
                    //.out.println("Mongoid: " + cr.getMongoId());
                    //System.out.println("MongoCompanyId: " + cr.getMongodbInsertObject().get("company_id").toString());
                }

                //System.out.println("Teams:");
                for (Team t : schema.teamList) {
                    timeCheck1 = System.nanoTime();
                    preparedStatement = postgreConn.prepareStatement(t.getInsertValue());
                    preparedStatement.executeUpdate();
                    timeCheck2 = System.nanoTime();
                    teamsMongo.insertOne(t.getMongodbInsertObject());
                    timeCheck3 = System.nanoTime();

                    insertStatistics.addPostgreSQLInsertTime(timeCheck2 - timeCheck1);
                    insertStatistics.addMongoDBInsertTime(timeCheck3 - timeCheck2);
                    //System.out.println(t.getInsertValue());
                    //System.out.println("Mongoid: " + t.getMongoId());
                    //System.out.println("MongoCompanyId: " + t.getMongodbInsertObject().get("company_id").toString());
                }

                //System.out.println("Projects:");
                for (Project p : schema.projectList) {
                    timeCheck1 = System.nanoTime();
                    preparedStatement = postgreConn.prepareStatement(p.getInsertValue());
                    preparedStatement.executeUpdate();
                    timeCheck2 = System.nanoTime();
                    projectsMongo.insertOne(p.getMongodbInsertObject());
                    timeCheck3 = System.nanoTime();

                    insertStatistics.addPostgreSQLInsertTime(timeCheck2 - timeCheck1);
                    insertStatistics.addMongoDBInsertTime(timeCheck3 - timeCheck2);
                    //System.out.println(p.getInsertValue());
                    //System.out.println("Mongoid: " + p.getMongoId());
                    //System.out.println("MongoProjectOwnerId: " + p.getMongodbInsertObject().get("projectOwnerId").toString());
                    //System.out.println("MongoTeamId: " + p.getMongodbInsertObject().get("devteamId").toString());
                }

                //System.out.println("Team_developers:");
                timeCheck1 = System.nanoTime();
                for (Map.Entry<Integer, Integer> team_developer : schema.teamDevelopers.entrySet()) {
                    preparedStatement = postgreConn.prepareStatement("INSERT INTO team_developers VALUES(" + team_developer.getKey() + "," + team_developer.getValue() + ")");
                    preparedStatement.executeUpdate();
                    //System.out.println("INSERT INTO team_developers VALUES(" + team_developer.getKey() + " , " + team_developer.getValue() + ")");
                }
                timeCheck2 = System.nanoTime();
                //System.out.println("Team_developers Mongo:");
                for (HashMap<String, Object> td : schema.teamDevelopersMongo) {
                    team_developersMongo.insertOne(new Document(td));
                    //System.out.println(td.get("developer_id") + " , " + td.get("team_id"));
                }
                timeCheck3 = System.nanoTime();

                insertStatistics.addPostgreSQLInsertTime(timeCheck2 - timeCheck1);
                insertStatistics.addMongoDBInsertTime(timeCheck3 - timeCheck2);

                //System.out.println("Team_managers:");
                timeCheck1 = System.nanoTime();
                for (Map.Entry<Integer, Integer> team_manager : schema.teamManagers.entrySet()) {
                    preparedStatement = postgreConn.prepareStatement("INSERT INTO team_managers VALUES(" + team_manager.getKey() + " , " + team_manager.getValue() + ")");
                    preparedStatement.executeUpdate();
                    //System.out.println("INSERT INTO team_managers VALUES(" + team_manager.getKey() + " , " + team_manager.getValue() + ")");
                }
                timeCheck2 = System.nanoTime();
                //System.out.println("Team_managers Mongo:");
                for (HashMap<String, Object> tm : schema.teamManagersMongo) {
                    team_managersMongo.insertOne(new Document(tm));
                    //System.out.println(tm.get("manager_id") + " , " + tm.get("team_id"));
                }
                timeCheck3 = System.nanoTime();

                insertStatistics.addPostgreSQLInsertTime(timeCheck2 - timeCheck1);
                insertStatistics.addMongoDBInsertTime(timeCheck3 - timeCheck2);

                if (i % 1000 == 0 || i == approx_loops - 1) {
                    System.out.println("Loop : " + i + ", Records inserted: " + ((i + 1) * 185) + "/" + approx_records);
                }
            }
            preparedStatement.close();
        }
        catch (SQLException SQLex) {
            SQLex.printStackTrace();
        }

        insertStatistics.print();

    }
}
