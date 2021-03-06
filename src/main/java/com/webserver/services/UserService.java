package com.webserver.services;

import com.webserver.domain.UserDTO;
import com.webserver.exceptions.UserException;
import com.webserver.models.User;
import com.webserver.transactions.MultichainManager;
import com.webserver.repositories.UserRepository;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Autowired
    private UserRepository usrRepository;

    @Autowired
    private MultichainManager multichainManager;

    public UserDTO findUserById(int userId) throws IOException, JSONException {
        User usr = usrRepository.findById(userId);
        if (usr == null) {
            throw new UserException("No user with id: " + userId);
        }

        UserDTO dto = new UserDTO.Builder()
                .id(usr.getId())
                .dollars(usr.getDollars())
                .email(usr.getEmail())
                .password(usr.getPassword())
                .userName(usr.getUsername())
                .coins(multichainManager.getUserBalance(usr.getMulticoinAddress()))
                .create();
        return dto;
    }

    public UserDTO findUserByUsernamePassword(String username, String password) {
        User usr = usrRepository.findByUsernameAndPassword(username, password);
        if (usr == null) {
            throw new UserException("Wrong credentials!");
        }

        UserDTO dto = new UserDTO.Builder()
                .id(usr.getId())
                .email(usr.getEmail())
                .password(usr.getPassword())
                .userName(usr.getUsername())
                .create();
        return dto;
    }

    public int create(UserDTO userDTO) {
        List<String> validationErrors = validateUser(userDTO);
        if (!validationErrors.isEmpty()) {
            throw new UserException("Wrong data!");
        }

        User user = new User();
        user.setDollars(0d);
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());

        MultichainManager multichainManager = new MultichainManager();
        try {
            String multichainAddress = multichainManager.createNewAddress();
            user.setMulticoinAddress(multichainAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
        User usr = usrRepository.save(user);
        return usr.getId();
    }

    private List<String> validateUser(UserDTO usr) {
        List<String> validationErrors = new ArrayList<String>();

        if (usr.getUsername() == null || "".equals(usr.getUsername())) {
            validationErrors.add("Username field should not be empty");
        }

        if (usr.getEmail() == null || !validateEmail(usr.getEmail())) {
            validationErrors.add("Email does not have the correct format.");
        }

        return validationErrors;
    }

    private static boolean validateEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

}
