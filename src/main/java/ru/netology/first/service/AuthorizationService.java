package ru.netology.first.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.netology.first.enumerable.Authorities;
import ru.netology.first.exception.InvalidCredentials;
import ru.netology.first.exception.UnauthorizedUser;
import ru.netology.first.repository.UserRepository;

import java.util.List;

@Service
@Component
public class AuthorizationService {

    private final UserRepository userRepository;

    public AuthorizationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Authorities> getAuthorities(String user, String password) {
        if (isEmpty(user) || isEmpty(password)) {
            throw new InvalidCredentials("User name or password is empty");
        }
        List<Authorities> userAuthorities = userRepository.getUserAuthorities(user, password);
        if (isEmpty(userAuthorities)) {
            throw new UnauthorizedUser("Unknown user " + user);
        }
        return userAuthorities;
    }

    private boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private boolean isEmpty(List<?> str) {
        return str == null || str.isEmpty();
    }
}