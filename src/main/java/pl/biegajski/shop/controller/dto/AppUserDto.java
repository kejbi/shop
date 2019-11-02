package pl.biegajski.shop.controller.dto;

import lombok.Getter;
import lombok.Setter;
import pl.biegajski.shop.model.AppUser;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AppUserDto {

    private long id;

    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
    private String role;

    @Min(value = 0)
    private float account;

    public AppUserDto(AppUser user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole();
        this.account = user.getAccount();
    }
}
