package com.venkatesh.main.entity;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "users_db")
public class UsersDb {


    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String password;

    private String  roles;

    private Integer age;

    private String gender;

    @Column(name = "tenantID", updatable = false, nullable = false, unique = true)
    private UUID tenantID;

    // 🚀 LifeCycle Hook: Automatically injects a fresh UUID right before database insertion
    @PrePersist
    protected void onCreate() {
        if (this.tenantID == null) {
            this.tenantID = UUID.randomUUID();
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public UUID getTenantID() {
        return tenantID;
    }

    public void setTenantID(UUID tenantID) {
        this.tenantID = tenantID;
    }
}
