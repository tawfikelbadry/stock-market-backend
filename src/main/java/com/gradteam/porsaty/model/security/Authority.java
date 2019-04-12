package com.gradteam.porsaty.model.security;


import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * Created by tawfik on 4/22/2018.
 */
public class Authority  implements GrantedAuthority,Serializable {

    private static final long serialVersionUID=456465l;

    private final String authority;

    public Authority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
