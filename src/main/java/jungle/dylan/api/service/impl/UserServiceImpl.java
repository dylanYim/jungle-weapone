package jungle.dylan.api.service.impl;

import jungle.dylan.api.auth.JwtProvider;
import jungle.dylan.api.domain.user.User;
import jungle.dylan.api.dto.UserRequest;
import jungle.dylan.api.exception.DuplicateUsernameException;
import jungle.dylan.api.exception.InvalidPasswordException;
import jungle.dylan.api.exception.UserNotFoundException;
import jungle.dylan.api.repository.UserRepository;
import jungle.dylan.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    public Long join(UserRequest userRequest) {
        Optional<User> findUser = userRepository.findUserByUsername(userRequest.getUsername());
        if (findUser.isPresent()) {
            throw new DuplicateUsernameException();
        }
        User user = userRequest.createUser();
        User saveUser = userRepository.save(user);
        return saveUser.getId();
    }

    @Override
    public String login(UserRequest userRequest) {
        User user = userRepository.findUserByUsername(userRequest.getUsername())
                .orElseThrow(UserNotFoundException::new);
        if (!user.getPassword().equals(userRequest.getPassword())) {
            throw new InvalidPasswordException();
        }
        return jwtProvider.generateToken(user.getUsername());
    }
}
