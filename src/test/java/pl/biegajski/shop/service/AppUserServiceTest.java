package pl.biegajski.shop.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.biegajski.shop.model.AppUser;
import pl.biegajski.shop.repository.AppUserRepository;

import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppUserServiceTest {

    @Mock
    private AppUserRepository repository;

    private AppUserService appUserService;

    private Optional<AppUser> optionalTestUser;

    @BeforeEach
    void setUp() {
        appUserService = new AppUserService(repository, new BCryptPasswordEncoder());
        optionalTestUser = Optional.of(new AppUser(1,"test1","123","ROLE_USER",50.50f, new ArrayList<>()));
    }

    void mockFindUserById() {
        when(repository.findAppUserById(anyLong())).thenReturn(optionalTestUser);
    }

    @Test
    void whenAddingUser_thenItHasEncodedPassword() {
        String name = "test1";
        String password = "password";
        String role = "admin";

        when(repository.save(any(AppUser.class))).then(returnsFirstArg());

        AppUser user = appUserService.addUser(name, password, role);

        assertThat(user.getPassword(), not(password));
    }

    @Test
    void whenChargingAccount_thenAccountIsGreater() {
        mockFindUserById();

        float account = appUserService.chargeAccount(10f,1);

        assertThat(account, is(60.50f));
    }

    @Test
    void whenDebitingAccount_thenAccountIsLower() throws Exception {
        mockFindUserById();

        float account = appUserService.debitAccount(10.50f, 1);
        assertThat(account, is(40f));
    }

    @Test
    void whenDebitingAccountWithNotEnoughMoney_thenExceptionIsThrown() throws Exception {
        mockFindUserById();

        assertThrows(Exception.class, () -> appUserService.debitAccount(60f, 1));
    }

    @Test
    void whenNewOrderIsCreated_thenItIsAdded() {
        mockFindUserById();
        when(repository.save(any(AppUser.class))).then(returnsFirstArg());

        AppUser user = appUserService.createNewOrder(1L);
        assertThat(user.getOrders().size(), is(1));

    }
}
