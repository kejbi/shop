package pl.biegajski.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.biegajski.shop.model.AppUser;
import pl.biegajski.shop.security.UserPrincipal;
import pl.biegajski.shop.service.AppUserService;

import java.security.InvalidParameterException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/app_user")
public class AppUserController {

    private final AppUserService appUserService;

    @PostMapping("/add")
    @ResponseBody
    public AppUser addAppUser(@RequestParam String name, @RequestParam String password, @RequestParam String role) {
        return appUserService.addUser(name, password, role);
    }

    @PatchMapping("/charge")
    @ResponseBody
    public float chargeAccount(@RequestParam float amount) {
        if (amount <= 0) {
            throw new InvalidParameterException("Amount must be greater than zero!");
        }
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return appUserService.chargeAccount(amount, ((UserPrincipal)user).getUsername());
    }
}
