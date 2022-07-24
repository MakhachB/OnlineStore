package person;

import java.util.Objects;

public class Employee extends AbstractPerson{
    private String title;

    public Employee(int id, String last_name, String first_name, String phone, String email, String country, String city, String password, String title) {
        super(id, last_name, first_name, phone, email, country, city, password);
        this.title = title;
    }

    @Override
    public int hashCode() {
        return Objects.hash(last_name, first_name, phone, email, country, city, password, title);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", title='" + title + '\'' +
                '}';
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
