package com.gradteam.porsaty.model.security;

import com.gradteam.porsaty.model.NormalUser;
import com.gradteam.porsaty.model.User;

import javax.persistence.*;

/**
 * Created by tawfik on 4/22/2018.
 */

@Entity
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userRoleId;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="role_id")
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    public UserRole() {
    }


    public UserRole( User user,Role role) {
        this.role = role;
        this.user = user;
    }

    public long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(long userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
