package it.macgood.opus.user.model;

import com.fasterxml.jackson.annotation.JsonView;
import it.macgood.opus.views.View;
import it.macgood.opus.works.Work;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "_user")
@Table(name = "_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    @JsonView({View.GetProfileInfo.class, View.GetShortInfo.class})
    private Integer id;

    @JsonView(View.GetProfileInfo.class)
    @Column(nullable = false)
    private String firstname;

    @JsonView(View.GetProfileInfo.class)
    @Column(nullable = false)
    private String lastname;

    @JsonView({View.GetProfileInfo.class, View.GetShortInfo.class})
    private String nickname;

    @JsonView(View.GetProfileInfo.class)
    @Column(unique = true, nullable = false)
    private String email;

    @JsonView({View.GetProfileInfo.class, View.GetShortInfo.class})
    private String photo;

    private String password;

    @JsonView(View.GetProfileInfo.class)
    private String dateOfBirth;

    private String currentToken;

    @JsonView(View.GetProfileInfo.class)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Career> career;

    @JsonView(View.GetProfileInfo.class)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Teams> teams;

    @JsonView(View.GetProfileInfo.class)
    @OneToMany(fetch = FetchType.EAGER)
    private List<Work> works;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
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
