import java.util.Date;

public class Manager {

    int id;
    String name;
    String surname;
    int age;
    Date employment_date;
    Date dismissal_date;
    double salary;

    public Manager(int id, String name, String surname, int age, Date employment_date, Date dismissal_date, double salary) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.employment_date = employment_date;
        this.dismissal_date = dismissal_date;
        this.salary = salary;
    }

    public String getInsertValue() {
        return "INSERT INTO managers VALUES(" + this.id + ",'" + this.name + "','" + this.surname + "','" + Main.standardFormat.format(this.employment_date) + "',"
                + ((this.dismissal_date == null) ? "null" : "'" + Main.standardFormat.format(this.dismissal_date) + "'") + "," + this.salary + ");" ;
    }

}
