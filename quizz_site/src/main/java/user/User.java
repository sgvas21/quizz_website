package user;

import java.util.StringTokenizer;

public class User {
    private int id;
    private String username;
    private String password;
    private String firstname;
    private String surname;
    private boolean adminPrivileges;

    public User() {}

    public User(int id, String username, String password, String firstname, String surname, boolean adminPrivileges) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.surname = surname;
        this.adminPrivileges = adminPrivileges;
    }

    public int getId() {return this.id; }

    public void setId(int id) { this.id = id; }

    public String getUsername() {return this.username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() {return this.password; }

    public void setPassword(String password) { this.password = password; }

    public String getFirstname() {return this.firstname; }

    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getSurname() {return this.surname; }

    public void setSurname(String surname) { this.surname = surname; }

    public String getFullName() {return this.firstname + ' ' + this.surname; }

    public void setFullName(String fullName) {
        StringTokenizer st = new StringTokenizer(fullName);
        StringBuilder sb = new StringBuilder();
        boolean firstnameDefined = false;
        boolean surnameDefined = false;
        while (st.hasMoreTokens()) {
            if(!firstnameDefined) {
                firstname = st.nextToken();
                firstnameDefined = true;
                continue;
            }
            if(!surnameDefined) {
                surname = st.nextToken();
                surnameDefined = true;
            }
            else{
                sb.append(' ').append(st.nextToken());
            }
        }

        if(!firstnameDefined) return;

        this.surname += sb.toString();
    }

    public boolean isAdminPrivileges() {return this.adminPrivileges; }

    public void setAdminPrivileges(boolean admin) { this.adminPrivileges = admin; }

    @Override
    public String toString() {
        return "MultipleQuestion {" +
                "id=" + this.id +
                ", username=" + this.username +
                ", password='" + this.password + '\'' +
                ", firstname='" + this.firstname + '\'' +
                ", surname=" + this.surname + '\'' +
                ", admin=" + this.adminPrivileges +
                '}';
    }
}
