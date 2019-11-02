package pl.biegajski.shop.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.biegajski.shop.model.AppUser;
import pl.biegajski.shop.repository.AppUserRepository;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppUserServiceTest {

    @Mock
    private AppUserRepository repository;

    private AppUserService appUserService;

    @BeforeEach
    void initUserService() {
        appUserService = new AppUserService(repository, new BCryptPasswordEncoder());
    }

    @Test
    void addedUserHasEncodedPassword() {
        String name = "test1";
        String password = "password";
        String role = "admin";

        when(repository.save(any(AppUser.class))).then(returnsFirstArg());

        AppUser user = appUserService.addUser(name, password, role);

        assertThat(user.getPassword(), not(password));
    }

    @Test
    void whenChargingAccount_thenAccountIsGreater() {
        AppUser user = new AppUser(1,"test1","123","ROLE_USER",50.50f, Collections.emptyList());
        Optional<AppUser> optionalAppUser = Optional.of(user);

        when(repository.findAppUserByUsername(anyString())).thenReturn(optionalAppUser);

        float account = appUserService.chargeAccount(10f,"test1");

        assertThat(account, is(60.50f));
    }
}
