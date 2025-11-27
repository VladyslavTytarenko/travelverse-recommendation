package com.travelverse.recommendation.model;

import com.travelverse.recommendation.entity.Role;
import com.travelverse.recommendation.enums.AccountType;
import com.travelverse.recommendation.enums.Status;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Data
public final class UserPrincipal implements UserDetails {

    @Serial
    private static final long serialVersionUID = -7216022870013833324L;

    private Long id;

    private String nickname;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private Status status;

    private String locale;

    private String profileImage;

    private AccountType accountType;

    private Set<Role> roles;

    private LocalDateTime created;

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
