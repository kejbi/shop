package pl.biegajski.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.biegajski.shop.model.AppUser;
import pl.biegajski.shop.repository.UserRepository;
import pl.biegajski.shop.security.UserPrincipal;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AppUser user = userRepository.findAppUserByUsername(s).orElseThrow(() -> new UsernameNotFoundException("Username "+ s + " not found"));
        return new UserPrincipal(user);
    }
}
