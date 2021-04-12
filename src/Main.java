import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    private static int devId = 0;
    private static int manId = 0;
    private static int teamId = 0;
    private static int companyId = 0;
    private static int comp_repsId = 0;
    private static int projectId = 0;
    public static SimpleDateFormat standardFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static int getDevId() {
        devId++;
        return devId;
    }

    public static int getManId() {
        manId++;
        return manId;
    }

    public static int getTeamId() {
        teamId++;
        return teamId;
    }

    public static int getCompanyId() {
        companyId++;
        return companyId;
    }

    public static int getComp_repsId() {
        comp_repsId++;
        return comp_repsId;
    }

    public static int getProjectId() {
        projectId++;
        return projectId;
    }

    public static void main(String[] args) {

        // product license create manually

        /*BufferedWriter fileoutput;

        try {
            fileoutput = new BufferedWriter(new FileWriter("ouput.txt"));

            fileoutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        /*Developer d = new Developer(1,"Dmytro","Sokolovskyi",19, new Date(2002 - 1900, 8,19),null,2500.00, "strd");
        Manager m = new Manager(1,"Adrian","Kowalski",25, new Date(2009 - 1900, 2, 25), null, 3200.0);

        System.out.println(d.getInsertValue());
        System.out.println(m.getInsertValue());*/

        SQLSchema schema = new SQLSchema(getCompanyId(),"t","desc");

        schema.createRandomTeams(5);
        schema.createRandomManagers(15);
        schema.createRandomDevelopers(70);
        schema.connectDevelopersToTeams();
        schema.connectManagersToTeams();

        /*schema.createRandomDevelopers(8);

        for (Developer dev : schema.devsList) {
            System.out.println(dev.getInsertValue());
        }
        for (Map.Entry<Integer, Integer> elem : schema.teamDevelopers.entrySet()) {
            System.out.println(elem.getKey() + " : " + elem.getValue());
        }*/



    }
}
