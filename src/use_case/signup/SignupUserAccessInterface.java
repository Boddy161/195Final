package use_case.signup;

import entity.User;

/**
 * Application Business Rules layer interface for accessing user data.
 */

public interface SignupUserAccessInterface {
    /**
     * @param username string username of the User to check if it has been taken.
     * @return true if and only if the username has not been taken and the user can register with this name.
     */

    boolean checkValidEmail(String username);


    boolean checkValidUsername(String username);

    /**
     * Saves a new user to the database.
     * @param user newly created User object
     */
    void save(User user);
}
