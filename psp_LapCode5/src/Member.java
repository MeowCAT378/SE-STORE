public class Member {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    public String passCode;
    private String phoneNum;
    private String point;  // ใช้เป็น double เพราะอาจใช้ในการคำนวณ

    // Getter and Setter for id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter and Setter for firstName
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Getter and Setter for lastName
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Getter and Setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and Setter for passCode
    public String getPassCode() {
        return passCode;
    }

    public void setPassCode(String passCode) {
        this.passCode = passCode;  // แก้ไขให้ถูกต้อง
    }

    // Getter and Setter for phoneNum
    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    // Getter and Setter for point
    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;  // เปลี่ยนให้ตรงกับประเภทของฟิลด์ point
    }
}
