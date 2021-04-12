import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import java.sql.*;

public class Main {

    private static int devId = 0;
    private static int manId = 0;
    private static int teamId = 0;
    private static int companyId = 0;
    private static int comp_repsId = 0;
    private static int projectId = 0;
    //public static SimpleDateFormat standardFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static int getDevId() { devId++; return devId; }
    public static int getManId() { manId++; return manId;}
    public static int getTeamId() { teamId++; return teamId; }
    public static int getCompanyId() { companyId++; return companyId; }
    public static int getComp_repsId() { comp_repsId++; return comp_repsId; }
    public static int getProjectId() { projectId++; return projectId; }

    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost/";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","Sokol");
        //props.setProperty("ssl","true");
        try {
            Connection conn = DriverManager.getConnection(url, props);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM test");
            ResultSetMetaData rsmd = rs.getMetaData();
            /*PreparedStatement pst = conn.prepareStatement("INSERT INTO test VALUES (2,'Jan','Kowalski')");
            int inserted = pst.executeUpdate();
            System.out.println(inserted + " rows inserted");*/

            while (rs.next())
            {
                for (int i = 0; i< rsmd.getColumnCount(); i++){
                    System.out.print(rs.getString(i+1));
                }
                System.out.println();
                //System.out.print("Column 1 returned ");
                //System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3));
            }
            rs.close();
            st.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

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

        /*CompanyRepresentatives c1 = new CompanyRepresentatives(1,"Dmytro", "Sokolovskyi", LocalDate.now(), "CEO", 12);

        System.out.println(c1.getInsertValue());

        SQLSchema schema = new SQLSchema(getCompanyId(),"t","desc");

        schema.createRandomTeams(5);
        schema.createRandomManagers(15);
        schema.createRandomDevelopers(70);
        schema.connectDevelopersToTeams();
        schema.connectManagersToTeams();

        schema.createRandomDevelopers(8);

        for (Developer dev : schema.devsList) {
            System.out.println(dev.getInsertValue());
        }
        for (Map.Entry<Integer, Integer> elem : schema.teamDevelopers.entrySet()) {
            System.out.println(elem.getKey() + " : " + elem.getValue());
        }*/

    }
}
