import java.time.LocalDate;

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
    }

    public String getInsertValue() {
        return "INSER INTO projects VALUES(" + this.id + ",'" + this.title + "'," + this.budget + ",'" + this.technologyDesc + "','" + this.startDate + "',"
                + ((this.endDate == null) ? "null" : ("'" + this.endDate + "'")) + ",'" + this.expectedDevTime + "'," + this.projectOwnerId + "," + this.devteamId + ")";
    }

}
