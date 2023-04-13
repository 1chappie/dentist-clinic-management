package domain;

import java.io.Serializable;
import java.util.Objects;

public class Patient implements Identifiable<String>, Serializable {

    public static final long serialVersionUID = 1L;
    private String name;
    private String email;
    private String phone;
    private String id;

    public Patient(){
        this.name = null;
        this.email = null;
        this.phone = null;
        this.id = null;
    }

    public Patient(String id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public void setID(String s) {
        this.id = s;
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "[" + this.getID() + "] " + this.name + " " + this.email + " " + this.phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(id, patient.id);
    }

}
