package person;

import java.util.Objects;

public abstract class AbstractPerson {
    protected String last_name;
    protected String first_name;
    protected String phone;
    protected String email;
    protected String country;
    protected String city;
    protected String password;

    public AbstractPerson(String last_name, String first_name, String phone, String email, String country, String city, String password) {
        this.last_name = last_name;
        this.first_name = first_name;
        this.phone = phone;
        this.email = email;
        this.country = country;
        this.city = city;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractPerson that = (AbstractPerson) o;
        return Objects.equals(last_name, that.last_name)
                && Objects.equals(first_name, that.first_name)
                && Objects.equals(phone, that.phone)
                && Objects.equals(email, that.email)
                && Objects.equals(country, that.country)
                && Objects.equals(city, that.city)
                && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(last_name, first_name, phone, email, country, city, password);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "last_name='" + last_name + '\'' +
                ", first_name='" + first_name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", password='" + password + '\'';
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
