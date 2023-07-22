package com.springsecurity.securityjwt.entity;



import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Role {
    @Id
    private String roleName;
    private String roleDiscription;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDiscription() {
        return roleDiscription;
    }

    public void setRoleDiscription(String roleDiscription) {
        this.roleDiscription = roleDiscription;
    }
}
