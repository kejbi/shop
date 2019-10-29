package pl.biegajski.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.biegajski.shop.model.AppUser;
import pl.biegajski.shop.service.AppUserService;

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
}
