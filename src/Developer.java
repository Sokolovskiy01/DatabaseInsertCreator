import java.util.Date;

public class Developer {

    int id;
    String name;
    String surname;
    int age;
    Date employment_date;
    Date dismissal_date;
    double salary;
    String access_level;

    public Developer(int id, String name, String surname, int age, Date employment_date, Date dismissal_date, double salary, String access_level) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.employment_date = employment_date;
        this.dismissal_date = dismissal_date;
        this.salary = salary;
        this.access_level = access_level;
    }

    public void print() {

    }

    public String getInsertValue() {
        return "INSERT INTO developers VALUES(" + this.id + ",'" + this.name + "','" + this.surname + "','" + Main.standardFormat.format(this.employment_date) + "',"
                + ((this.dismissal_date == null) ? "null" : "'" + Main.standardFormat.format(this.dismissal_date) + "'") + "," + this.salary + ",'" + this.access_level + "');" ;
    }

}
