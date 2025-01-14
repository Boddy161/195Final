package use_case.user_list;
import java.util.*;

import entity.User;
import use_case.UserListOutputBoundary;


public class UserListInteractor implements UserListInputBoundary {

    private User currentUser;
    final UserListDataAccessInterface userDataAccessObject;
    final UserListOutputBoundary userListPresenter;

    public UserListInteractor(
                              UserListDataAccessInterface userDataAccessObject,
                              UserListOutputBoundary userListPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.userListPresenter = userListPresenter;
        this.currentUser = null;

    }
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public List<User> execute() {
        // I will add to the map everytime the usecase is executed, and I would not store this anywhere
        // this means the map gets updated per login.

        Map<User, Integer> userSimilarityScore = new HashMap<>();
        for(User u:userDataAccessObject.getAllUsers()){
            if (!u.getId().equals(currentUser.getId())) {
                int similarity = calculateSimilarity(currentUser, u);
                userSimilarityScore.put(u, similarity);
        }    }
        // sort the Map based on the scores and output an arrayList of users
        List<User> sorted = new ArrayList<>(userSimilarityScore.keySet()); // unsorted list
        // for any two users, define them to be comparable based on simiarlity score, and sort based on way to compare them
        sorted.sort((user1, user2) -> userSimilarityScore.get(user2) - userSimilarityScore.get(user1));

        return sorted;
    }

    private int calculateSimilarity(User currentUser, User u) {
        // for now define the score as the number of common courses that two users both take.
        Set<String> commonCourses = new HashSet<>(currentUser.getCourses());
        commonCourses.retainAll(u.getCourses()); // keep only the courses that the other user also take
        return commonCourses.size();

    }
    }
