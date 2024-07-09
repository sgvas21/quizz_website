package user;

import java.util.Objects;

public class User {
    private int id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private boolean adminPrivileges;

    // Default constructor
    public User() {}

    // Copy constructor
    public User(User user) {
        this.id = user.id;
        this.username = user.username;
        this.password = user.password;
        this.firstname = user.firstname;
        this.lastname = user.lastname;
        this.adminPrivileges = user.adminPrivileges;
    }

    // Constructor with all fields except id
    public User(String username, String password, String firstname, String lastname, boolean adminPrivileges) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.adminPrivileges = adminPrivileges;
    }

    // Full constructor including id
    public User(int id, String username, String password, String firstname, String lastname, boolean adminPrivileges) {
        this(username, password, firstname, lastname, adminPrivileges);
        this.id = id;
    }

    // Getters and Setters
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean hasAdminPrivileges() {
        return this.adminPrivileges;
    }

    public void setAdminPrivileges(boolean isAdmin) {
        this.adminPrivileges = isAdmin;
    }

    // toString method for debugging and logging
    @Override
    public String toString() {
        return "User {" +
                "id=" + this.id +
                ", username='" + this.username + '\'' +
                ", password='" + this.password + '\'' +
                ", firstname='" + this.firstname + '\'' +
                ", lastname='" + this.lastname + '\'' +
                ", isAdmin=" + this.adminPrivileges +
                '}';
    }

    // equals method for comparing User objects
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id &&
                adminPrivileges == user.adminPrivileges &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(firstname, user.firstname) &&
                Objects.equals(lastname, user.lastname);
    }

    // hashCode method for hashing User objects
    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, firstname, lastname, adminPrivileges);
    }
}
