package com.aboo.movie.springcloud.domain;

import lombok.Data;

import java.util.List;


@Data
public class SysUser /*implements UserDetails*/ {

    private Long id;
    private String username;
    private String password;


    private List<SysRole> roles;

   /* @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();
        List<SysRole> roles = this.getRoles();
        for (SysRole role : roles) {
            for (SysPrivilege sysPrivilege : role.getPrivileges()) {
                auths.add(new SimpleGrantedAuthority(sysPrivilege.getPermission()));
            }
        }
        return auths;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
    }*/

 /*   保存到数据库之前执行
 @PrePersist
    void placedAt() {
        this.placedAt = new Date();
    }*/
}
