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
        this.budget = budget;
        this.technologyDesc = technologyDesc;
        this.startDate = startDate;
        this.endDate = endDate;
        this.expectedDevTime = expectedDevTime;
        this.projectOwnerId = projectOwnerId;
        this.devteamId = devteamId;
    }

    /* TODO */

}
