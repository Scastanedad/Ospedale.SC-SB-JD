package packagee.service;

import packagee.*;
import packagee.response.ServiceResponse;
import java.util.ArrayList;

public interface IUserService {
    ServiceResponse registerPatient(
            String idStr, String username, String firstname, String lastname,
            String password, String confirm, String email,
            String birthdateStr, String genderStr, String phoneStr, String address,
            ArrayList<User> users);

    ServiceResponse registerDoctor(
            String idStr, String username, String firstname, String lastname,
            String password, String confirm, String specialtyStr,
            String licenseNumber, String assignedOffice,
            ArrayList<User> users);

    ServiceResponse updatePatient(
            Patient patient, String firstname, String lastname,
            String email, String birthdateStr, String genderStr,
            String phoneStr, String address, String username,
            String password, String confirm,
            ArrayList<User> users);

    ServiceResponse updateDoctor(
            Doctor doctor, String firstname, String lastname,
            String specialtyStr, String licenseNumber, String assignedOffice,
            String username, String password, String confirm,
            ArrayList<User> users);
}
