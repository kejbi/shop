package pl.biegajski.shop.security;

import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.biegajski.shop.model.AppUser;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserPrincipal extends AppUser implements UserDetails {

    public UserPrincipal(AppUser appUser){
        super(appUser);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return super.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
