import java.time.LocalDate;
import java.util.Date;

public class CompanyRepresentatives {

    int id;
    String name;
    String surname;
    LocalDate dateOfJoin;
    String role;
    int companyId;

    public CompanyRepresentatives(int id, String name, String surname, LocalDate dateOfJoin, String role, int companyId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dateOfJoin = dateOfJoin;
        this.role = role;
        this.companyId = companyId;
    }

    public String getInsertValue() {
        return "INSER INTO company_representatives VALUES(" + this.id + ",'" + this.name + "','" + this.surname + "','" + this.dateOfJoin + "','" + this.role + "'," + this.companyId +");";
    }

}
