import java.time.LocalDate;
import java.util.Date;

public class Developer {

    int id;
    String name;
    String surname;
    int age;
    LocalDate employment_date;
    LocalDate dismissal_date;
    double salary;
    String access_level;

    public Developer(int id, String name, String surname, int age, LocalDate employment_date, LocalDate dismissal_date, double salary, String access_level) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.employment_date = employment_date;
        this.dismissal_date = dismissal_date;
        this.salary = Math.round(salary * 100) / 100.0;
        this.access_level = access_level;
    }

    public void print() {

    }

    public String getInsertValue() {
        return "INSERT INTO developers VALUES(" + this.id + ",'" + this.name + "','" + this.surname + "','" + this.employment_date + "',"
                + ((this.dismissal_date == null) ? "null," : "'" + this.dismissal_date + "',") + this.salary + ",'" + this.access_level + "')" ;
    }

}
