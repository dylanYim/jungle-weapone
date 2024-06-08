package jungle.dylan.api.service;

import jungle.dylan.api.dto.UserRequest;

public interface UserService {
    Long join(UserRequest userRequest);
    String login(UserRequest userRequest);

}
