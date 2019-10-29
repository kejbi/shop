package pl.biegajski.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.biegajski.shop.model.AppUser;
import pl.biegajski.shop.repository.AppUserRepository;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AppUser addUser(String name, String password, String role) {
        AppUser user = new AppUser();
        user.setUsername(name);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);

        return userRepository.save(user);
    }
}
