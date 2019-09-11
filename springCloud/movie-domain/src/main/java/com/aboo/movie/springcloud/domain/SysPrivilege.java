package com.aboo.movie.springcloud.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysPrivilege /*implements Serializable*/ {
    private Long id;
    private String permission;
}
