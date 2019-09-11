package com.aboo.movie.springcloud.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class SysRole /*implements Serializable*/ {

    private Long id;
    private String name;

    private List<SysPrivilege> privileges;

}
