package Model.Person;

public class Administrator implements IPerson{
    private String username;
    private String password;
    private String email;
    private String fullname;
    public Administrator(String username, String password, String email, String fullname) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullname = fullname;
    }

    public Administrator(String username, String password) {
        this.username = username;
        this.password = password;
    }
    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public void setUsername(String username) {
        this.username = username;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String getEmail() {
        return email;
    }
    @Override
    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String getFullname() {
        return fullname;
    }
    @Override
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
