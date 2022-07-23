package person;

public class Client extends AbstractPerson{


    public Client(String last_name, String first_name, String phone, String email, String country, String city, String password) {
        super(last_name, first_name, phone, email, country, city, password);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
