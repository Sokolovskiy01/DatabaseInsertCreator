import java.time.LocalDate;
import java.util.Date;

public class Manager {

    int id;
    String name;
    String surname;
    int age;
    LocalDate employment_date;
    LocalDate dismissal_date;
    double salary;

    public Manager(int id, String name, String surname, int age, LocalDate employment_date, LocalDate dismissal_date, double salary) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.employment_date = employment_date;
        this.dismissal_date = dismissal_date;
        this.salary = Math.round(salary * 100) / 100.0;
    }

    public String getInsertValue() {
        return "INSERT INTO managers VALUES(" + this.id + ",'" + this.name + "','" + this.surname + "','" + this.employment_date + "',"
                + ((this.dismissal_date == null) ? "null," : "'" + this.dismissal_date + "',") + this.salary + ")" ;
    }

}
