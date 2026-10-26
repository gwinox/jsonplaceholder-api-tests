package api;

import models.users.User;
import utils.Endpoints;
import java.util.List;

public class UsersApi extends BaseApi {

    private UsersApi() {
    }

    public static ApiResponse<List<User>> getAllUsers() {
        return getList(Endpoints.USERS, User.class);
    }

    public static ApiResponse<User> getUserById(int id) {
        String endpoint = String.format("%s/%d", Endpoints.USERS, id);
        return get(endpoint, User.class);
    }
}