public class Member {
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phone;
    private String points;
    private String role;

    // Constructor, getter และ setter

    public Member(String id, String firstname, String lastname, String email, String password, String phone, String points) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.points = points;
        this.role = role;
    }

    // Getter สำหรับ Firstname
    public String getFirstname() {
        return firstname;
    }

    // Setter สำหรับ Firstname
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    // Getter สำหรับ Lastname
    public String getLastname() {
        return lastname;
    }

    // Setter สำหรับ Lastname
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    // Getter และ Setter สำหรับฟิลด์อื่นๆ
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getRole() {
        return role;
    }
}
