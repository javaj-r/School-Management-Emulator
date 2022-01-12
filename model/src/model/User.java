package model;

import constant.Permit;
import util.GenericList;

public class User extends BaseEntity {

    private String username;
    private String password;
    private GenericList<Permit> permissions = new GenericList<>();

    public User() {
        permissions = new GenericList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public GenericList<Permit> getPermissions() {
        return permissions;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User addGrant(Permit grant) {
        this.permissions.add(grant);
        return this;
    }

    public User addGrants(Permit grant, Permit... grants) {
        this.permissions.add(grant, grants);
        return this;
    }

    public User addGrants(Permit[] grants) {
        this.permissions.addAll(grants);
        return this;
    }

    public User removeGrant(int index) {
        this.permissions.remove(index);
        return this;
    }

    protected void setPermissions(GenericList<Permit> permissions) {
        this.permissions = permissions;
    }
}
