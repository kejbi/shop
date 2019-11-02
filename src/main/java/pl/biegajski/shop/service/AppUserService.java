package pl.biegajski.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.biegajski.shop.model.AppUser;
import pl.biegajski.shop.model.Order;
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
        Order order = new Order();
        user.getOrders().add(order);
        order.setUser(user);
        return userRepository.save(user);
    }

    public float chargeAccount(float amount, String username) {
        AppUser user = userRepository.findAppUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.chargeAccount(amount);

        userRepository.save(user);
        return user.getAccount();
    }
}
