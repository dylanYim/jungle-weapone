package jungle.dylan.api.service.impl;

import jungle.dylan.api.auth.JwtProvider;
import jungle.dylan.api.domain.user.User;
import jungle.dylan.api.dto.UserRequest;
import jungle.dylan.api.exception.DuplicateUsernameException;
import jungle.dylan.api.repository.UserRepository;
import jungle.dylan.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public Long join(UserRequest userRequest) {
        Optional<User> findUser = userRepository.findUserByUsername(userRequest.getUsername());
        if (findUser.isPresent()) {
            throw new DuplicateUsernameException();
        }
        User user = userRequest.createUser(passwordEncoder);
        User saveUser = userRepository.save(user);
        return saveUser.getId();
    }

    @Override
    public String login(UserRequest userRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequest.getUsername(),
                        userRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return jwtProvider.generateToken(authenticate.getName());
    }
}
