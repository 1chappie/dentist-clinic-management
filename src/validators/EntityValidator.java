package validators;

import domain.Appointment;
import domain.Dentist;
import domain.Patient;
import exception.InvalidParametersException;

import java.util.regex.Pattern;

public class EntityValidator {
    private static void validateName(String name) throws InvalidParametersException {
        if (name == null || name.equals(""))
            throw new InvalidParametersException("Name is null or empty");
        if (name.length() > 50)
            throw new InvalidParametersException("Name is too long");
    }

    private static void validatePhone(String phone) throws InvalidParametersException {
        if (phone == null || phone.equals(""))
            throw new InvalidParametersException("Phone is null or empty");
        if (phone.length() > 12)
            throw new InvalidParametersException("Phone is too long");
        if (!Pattern.matches("^\\d+$", phone))
            throw new InvalidParametersException("Phone is not valid");
    }

    private static void validateEmail(String email) throws InvalidParametersException {
        if (email == null || email.equals(""))
            throw new InvalidParametersException("Email is null or empty");
        if (email.length() > 50)
            throw new InvalidParametersException("Email is too long");
        if (!Pattern.matches("^\\S+@\\S+\\.\\S+$", email))
            throw new InvalidParametersException("Email is not valid");
    }

    static public void check(Dentist d) throws InvalidParametersException {
        if (d == null)
            throw new InvalidParametersException("Dentist is null");
        validateName(d.getName());
        validatePhone(d.getPhone());
        validateEmail(d.getEmail());
    }

    static public void check(Patient p) throws InvalidParametersException {
        if (p == null)
            throw new InvalidParametersException("Object is null");
        validateName(p.getName());
        validatePhone(p.getPhone());
        validateEmail(p.getEmail());
    }

    static public void check(Appointment a) throws InvalidParametersException {
        if (a == null)
            throw new InvalidParametersException("Object is null");
        // only validation to be done here is the LocalDate one,
        // which is already handled by the LocalDate class and the exceptions
        // are caught in the UI
    }
}
