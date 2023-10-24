package ru.netology.first.repository;

import org.springframework.stereotype.Repository;
import ru.netology.first.enumerable.Authorities;

import java.util.ArrayList;
import java.util.List;

import static ru.netology.first.enumerable.Authorities.*;

@Repository
public class UserRepository {
    public List<Authorities> getUserAuthorities(String user, String password) {
        List<Authorities> authorities = new ArrayList<>();
        if (user.equals("admin")  && password.equals("admin")) {
            authorities.add(READ);
            authorities.add(WRITE);
            authorities.add(DELETE);
            return authorities;
        }
        return authorities;
    }
}