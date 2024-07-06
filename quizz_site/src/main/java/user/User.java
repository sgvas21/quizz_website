package user;

import java.util.Objects;
import java.util.StringTokenizer;

public class User {
    private int id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private boolean adminPrivileges;

    public User() {}

    public User(User user) {
        this.id = user.id;
        this.username = user.username;
        this.password = user.password;
        this.firstname = user.firstname;
        this.lastname = user.lastname;
        this.adminPrivileges = user.adminPrivileges;
    }

    public User(String username, String password, String firstname, String lastname, boolean adminPrivileges) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.adminPrivileges = adminPrivileges;
    }

    public User(int id, String username, String password, String firstname, String lastname, boolean adminPrivileges) {
        this(username, password, firstname, lastname, adminPrivileges);
        this.id = id;
    }

    public int getId() {return this.id; }

    public void setId(int id) { this.id = id; }

    public String getUsername() {return this.username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return this.password; }

    public void setPassword(String password) { this.password = password; }

    public String getFirstname() {return this.firstname; }

    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getLastname() {return this.lastname; }

    public void setLastname(String lastname) { this.lastname = lastname; }

    public boolean hasAdminPrivileges() { return this.adminPrivileges; }

    public void setAdminPrivileges(boolean isAdmin) { this.adminPrivileges = isAdmin; }

    @Override
    public String toString() {
        return "User {" +
                "id=" + this.id +
                ", username='" + this.username + '\'' +
                ", password='" + this.password + '\'' +
                ", firstname='" + this.firstname + '\'' +
                ", lastname=" + this.lastname + '\'' +
                ", isAdmin=" + this.adminPrivileges +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if(!(o instanceof User that)) return false;

        return (this.id == that.id) &&
                Objects.equals(this.username, that.username) &&
                Objects.equals(this.password, that.password) &&
                Objects.equals(this.firstname, that.firstname) &&
                Objects.equals(this.lastname, that.lastname) &&
                Objects.equals(this.adminPrivileges, that.adminPrivileges);
    }
}
