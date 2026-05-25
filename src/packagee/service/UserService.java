package packagee.service;

import packagee.*;
import packagee.repository.IUserRepository;
import packagee.response.ServiceResponse;
import packagee.util.PasswordUtil;
import packagee.util.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class UserService implements IUserService {

    private final IUserRepository userRepo;

    public UserService(IUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public ServiceResponse registerPatient(
            String idStr, String username, String firstname, String lastname,
            String password, String confirm, String email,
            String birthdateStr, String genderStr, String phoneStr, String address,
            ArrayList<User> users) {

        if (!Validator.isValidId(idStr))
            return ServiceResponse.badRequest("El ID debe tener exactamente 12 dígitos.");
        if (!Validator.isValidUsername(username))
            return ServiceResponse.badRequest("Username inválido (no puede estar vacío ni tener espacios).");
        if (!Validator.notBlank(firstname) || !Validator.notBlank(lastname))
            return ServiceResponse.badRequest("Nombre y apellido son obligatorios.");
        if (!Validator.passwordsMatch(password, confirm))
            return ServiceResponse.badRequest("Las contraseñas no coinciden.");
        if (!Validator.isValidEmail(email))
            return ServiceResponse.badRequest("Email inválido. Formato: usuario@dominio.ext");
        if (!Validator.isValidDate(birthdateStr))
            return ServiceResponse.badRequest("Fecha de nacimiento inválida. Formato: AAAA-MM-DD");
        if (!Validator.isValidPhone(phoneStr))
            return ServiceResponse.badRequest("Teléfono debe tener exactamente 10 dígitos.");
        if (!Validator.notBlank(address))
            return ServiceResponse.badRequest("Dirección es obligatoria.");

        long id = Long.parseLong(idStr);
        long phone = Long.parseLong(phoneStr);

        for (User u : users) {
            if (u.getId() == id)
                return ServiceResponse.conflict("Ya existe un usuario con ID " + id + ".");
            if (u.getUsername().equals(username))
                return ServiceResponse.conflict("El username '" + username + "' ya está en uso.");
        }

        boolean gender = "Male".equalsIgnoreCase(genderStr) || "1".equals(genderStr) || "true".equalsIgnoreCase(genderStr);
        LocalDate birthdate;
        try {
            birthdate = LocalDate.parse(birthdateStr);
        } catch (DateTimeParseException e) {
            return ServiceResponse.badRequest("Fecha inválida: " + birthdateStr);
        }

        String hashedPassword = PasswordUtil.hash(password);
        Patient patient = new Patient(id, username, firstname, lastname, hashedPassword, email, birthdate, gender, phone, address);
        users.add(patient);
        userRepo.saveAll(users);

        return ServiceResponse.ok("Paciente registrado exitosamente.");
    }

    public ServiceResponse registerDoctor(
            String idStr, String username, String firstname, String lastname,
            String password, String confirm, String specialtyStr,
            String licenseNumber, String assignedOffice,
            ArrayList<User> users) {

        if (!Validator.isValidId(idStr))
            return ServiceResponse.badRequest("El ID debe tener exactamente 12 dígitos.");
        if (!Validator.isValidUsername(username))
            return ServiceResponse.badRequest("Username inválido.");
        if (!Validator.notBlank(firstname) || !Validator.notBlank(lastname))
            return ServiceResponse.badRequest("Nombre y apellido son obligatorios.");
        if (!Validator.passwordsMatch(password, confirm))
            return ServiceResponse.badRequest("Las contraseñas no coinciden.");
        if (!Validator.isValidLicense(licenseNumber))
            return ServiceResponse.badRequest("Licencia médica inválida. Formato: L-XXXXXXXXXX MTL");
        if (!Validator.isValidOffice(assignedOffice))
            return ServiceResponse.badRequest("Oficina inválida. Formato: O-XXX");

        long id = Long.parseLong(idStr);

        for (User u : users) {
            if (u.getId() == id)
                return ServiceResponse.conflict("Ya existe un usuario con ID " + id + ".");
            if (u.getUsername().equals(username))
                return ServiceResponse.conflict("El username '" + username + "' ya está en uso.");
        }

        Specialty specialty;
        try {
            specialty = Specialty.parse(specialtyStr);
        } catch (Exception e) {
            return ServiceResponse.badRequest("Especialidad inválida: " + specialtyStr);
        }

        String hashedPassword = PasswordUtil.hash(password);
        Doctor doctor = new Doctor(id, username, firstname, lastname, hashedPassword, specialty, licenseNumber, assignedOffice);
        users.add(doctor);
        userRepo.saveAll(users);

        return ServiceResponse.ok("Doctor registrado exitosamente.");
    }

    public ServiceResponse updatePatient(
            Patient patient, String firstname, String lastname,
            String email, String birthdateStr, String genderStr,
            String phoneStr, String address, String username,
            String password, String confirm,
            ArrayList<User> users) {

        if (!Validator.notBlank(firstname) || !Validator.notBlank(lastname))
            return ServiceResponse.badRequest("Nombre y apellido son obligatorios.");
        if (!Validator.isValidEmail(email))
            return ServiceResponse.badRequest("Email inválido.");
        if (!Validator.isValidDate(birthdateStr))
            return ServiceResponse.badRequest("Fecha de nacimiento inválida. Formato: AAAA-MM-DD");
        if (!Validator.isValidPhone(phoneStr))
            return ServiceResponse.badRequest("Teléfono debe tener 10 dígitos.");
        if (!Validator.isValidUsername(username))
            return ServiceResponse.badRequest("Username inválido.");
        if (!Validator.passwordsMatch(password, confirm))
            return ServiceResponse.badRequest("Las contraseñas no coinciden.");

        for (User u : users) {
            if (u.getId() != patient.getId() && u.getUsername().equals(username))
                return ServiceResponse.conflict("El username '" + username + "' ya está en uso.");
        }

        patient.setFirstname(firstname);
        patient.setLastname(lastname);
        patient.setEmail(email);
        patient.setBirthdate(LocalDate.parse(birthdateStr));
        boolean gender = "Male".equalsIgnoreCase(genderStr) || "1".equals(genderStr) || "true".equalsIgnoreCase(genderStr);
        patient.setGender(gender);
        patient.setPhone(Long.parseLong(phoneStr));
        patient.setAddress(address);
        patient.setUsername(username);
        patient.setPassword(PasswordUtil.hash(password));

        userRepo.saveAll(users);
        return ServiceResponse.ok("Paciente actualizado exitosamente.");
    }

    public ServiceResponse updateDoctor(
            Doctor doctor, String firstname, String lastname,
            String specialtyStr, String licenseNumber, String assignedOffice,
            String username, String password, String confirm,
            ArrayList<User> users) {

        if (!Validator.notBlank(firstname) || !Validator.notBlank(lastname))
            return ServiceResponse.badRequest("Nombre y apellido son obligatorios.");
        if (!Validator.isValidLicense(licenseNumber))
            return ServiceResponse.badRequest("Licencia médica inválida. Formato: L-XXXXXXXXXX MTL");
        if (!Validator.isValidOffice(assignedOffice))
            return ServiceResponse.badRequest("Oficina inválida. Formato: O-XXX");
        if (!Validator.isValidUsername(username))
            return ServiceResponse.badRequest("Username inválido.");
        if (!Validator.passwordsMatch(password, confirm))
            return ServiceResponse.badRequest("Las contraseñas no coinciden.");

        for (User u : users) {
            if (u.getId() != doctor.getId() && u.getUsername().equals(username))
                return ServiceResponse.conflict("El username '" + username + "' ya está en uso.");
        }

        Specialty specialty;
        try {
            specialty = Specialty.parse(specialtyStr);
        } catch (Exception e) {
            return ServiceResponse.badRequest("Especialidad inválida: " + specialtyStr);
        }

        doctor.setFirstname(firstname);
        doctor.setLastname(lastname);
        doctor.setSpecialty(specialty);
        doctor.setLicenceNumber(licenseNumber);
        doctor.setAssignedOffice(assignedOffice);
        doctor.setUsername(username);
        doctor.setPassword(PasswordUtil.hash(password));

        userRepo.saveAll(users);
        return ServiceResponse.ok("Doctor actualizado exitosamente.");
    }

}
