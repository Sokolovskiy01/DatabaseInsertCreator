import java.util.*;

// basically a company instance
public class SQLSchema {

    Random random;

    List<Developer> devsList;
    List<Manager> managerList;
    List<Team> teamList;

    Map<Integer, Integer> teamDevelopers;
    Map<Integer, Integer> teamManagers;

    private final int companyId;
    private final String companytitle;
    private final Date dateOfFoundation;
    private final String description;
    private final int companyFoundationYear;

    public SQLSchema(int companyId, String companytitle, String description) {
        this.random = new Random();

        this.devsList = new ArrayList<>();
        this.managerList = new ArrayList<>();
        this.teamList = new ArrayList<>();
        this.teamDevelopers = new HashMap<>();
        this.teamManagers = new HashMap<>();

        this.companyId = companyId;
        this.companytitle = companytitle;
        this.companyFoundationYear = new Random().nextInt(34) + 1980; // 1980 - 2014
        this.dateOfFoundation = new Date(this.companyFoundationYear - 1900, random.nextInt(11), random.nextInt(28));
        this.description = description;
    }

    public String getRandomString (int length) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomLetterNumber = random.nextInt(122 - 97) + 97;
            sb.append((char) randomLetterNumber);
        }
        String result = sb.toString();
        return result;
    }

    public void createRandomDevelopers(int size) {
        for (int i = 0; i < size; i++) {
            Developer dev = new Developer(Main.getDevId(), this.getRandomString(random.nextInt(20)+ 8), this.getRandomString(random.nextInt(30) + 12),
                    random.nextInt(30) + 18, new Date(random.nextInt(2020 - this.companyFoundationYear) + this.companyFoundationYear - 1900, random.nextInt(12), random.nextInt(28)),
                    null,random.nextDouble() * 10000, this.getRandomString(10));
            this.devsList.add(dev);
        }
    }

    public void createRandomManagers(int size) {
        for (int i = 0; i < size; i++) {
            Manager man = new Manager(Main.getManId(), this.getRandomString(random.nextInt(20)+ 8), this.getRandomString(random.nextInt(30) + 12),
                    random.nextInt(30) + 18, new Date(random.nextInt(2020 - this.companyFoundationYear) + this.companyFoundationYear - 1900, random.nextInt(12), random.nextInt(25)),
                    null,random.nextDouble() * 12000);
            this.managerList.add(man);
        }
    }

    public void createRandomTeams(int size) {
        for (int i = 0; i < size; i++) {
            Team t = new Team(Main.getTeamId(),this.getRandomString(random.nextInt(32) + 16),getRandomString(random.nextInt(200) + 25), this.companyId);
            this.teamList.add(t);
        }
    }

    public void connectDevelopersToTeams() {
        for (int i = 0; i < this.teamList.size(); i++) {
            for (int j = i * 14; j < (i + 1) * 14; j++) {
                this.teamDevelopers.put(this.devsList.get(j).id, this.teamList.get(i).id);
            }
        }
    }

    public void connectManagersToTeams() {
        for (int i = 0; i < this.teamList.size(); i++) {
            for (int j = i * 3; j < (i + 1) * 3; j++) {
                this.teamManagers.put(this.managerList.get(j).id, this.teamList.get(i).id);
            }
        }
    }

}

