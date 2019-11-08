package pl.biegajski.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.biegajski.shop.controller.dto.AppUserDto;
import pl.biegajski.shop.controller.dto.RegisterDto;
import pl.biegajski.shop.model.AppUser;
import pl.biegajski.shop.security.UserPrincipal;
import pl.biegajski.shop.service.AppUserService;
import pl.biegajski.shop.service.CustomUserDetailsService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.security.InvalidParameterException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/app_user")
public class AppUserController {

    private final AppUserService appUserService;

    @PermitAll
    @PostMapping("/add")
    @ResponseBody
    public AppUserDto addAppUser(@Valid @RequestBody RegisterDto registerDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            throw new ValidationException("Incorrect data");
        }

        AppUser user = appUserService.addUser(registerDto.getUsername(), registerDto.getPassword(), registerDto.getRole());
        return new AppUserDto(user);
    }

    @RolesAllowed({"ROLE_USER"})
    @PatchMapping("/charge")
    @ResponseBody
    public float chargeAccount(@RequestParam float amount) {
        if (amount <= 0) {
            throw new InvalidParameterException("Amount must be greater than zero!");
        }
        UserPrincipal user = CustomUserDetailsService.getCurrentUser();
        return appUserService.chargeAccount(amount, user.getId());
    }
}
