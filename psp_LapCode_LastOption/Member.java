public class Member {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private double points;
    private String password;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Member(String id, String firstName, String lastName, String email, String phone, double points) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.points = points;
        this.password = password;
    }

    public String getId(){
        return id;
    }

    public String getMaskedName() {
        return lastName.charAt(0) + ". " + firstName;
    }

    public String getMaskedEmail() {
        int atIndex = email.indexOf('@');
        return email.substring(0, 2) + "***@" + email.substring(atIndex - 3, atIndex) + "***";
    }

    public String getFormattedPhone() {
        return phone.substring(0, 3) + "-" + phone.substring(3, 6) + "-" + phone.substring(6);
    }

    public double getPoints() {
        return points;
    }

}