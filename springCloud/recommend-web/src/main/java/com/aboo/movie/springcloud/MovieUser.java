package com.aboo.movie.springcloud;

import com.aboo.movie.springcloud.domain.SysPrivilege;
import com.aboo.movie.springcloud.domain.SysRole;
import com.aboo.movie.springcloud.domain.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @description:
 * @author: zhegong
 * @create: 2019-09-10 21:17
 **/
public class MovieUser implements UserDetails {

    private SysUser sysUser;

    public MovieUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }


    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();
        List<SysRole> roles = sysUser.getRoles();
        for (SysRole role : roles) {
            for (SysPrivilege sysPrivilege : role.getPrivileges()) {
                auths.add(new SimpleGrantedAuthority(sysPrivilege.getPermission()));
            }
        }
        return auths;
    }

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getPassword();
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

