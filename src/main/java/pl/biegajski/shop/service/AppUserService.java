package pl.biegajski.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.biegajski.shop.model.AppUser;
import pl.biegajski.shop.model.Order;
import pl.biegajski.shop.repository.AppUserRepository;

import javax.persistence.EntityNotFoundException;
import java.nio.file.attribute.UserPrincipalNotFoundException;

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

    public float chargeAccount(float amount, long id) {
        AppUser user = userRepository.findAppUserById(id).orElseThrow(EntityNotFoundException::new);

        user.chargeAccount(amount);

        userRepository.save(user);
        return user.getAccount();
    }

    public float debitAccount(float amount, long id) throws Exception {
        AppUser user = userRepository.findAppUserById(id).orElseThrow(EntityNotFoundException::new);
        if(user.getAccount() < amount) {
            throw new Exception("Not enough money");
        }
        user.debitAccount(amount);

        userRepository.save(user);
        return user.getAccount();
    }

    public AppUser createNewOrder(long id) {
        AppUser user = userRepository.findAppUserById(id).orElseThrow(EntityNotFoundException::new);
        Order order = new Order();
        user.getOrders().add(order);
        order.setUser(user);
        return userRepository.save(user);
    }
}
