public class Member {

    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String pass;
    private String phonenum;
    private double point;
    private boolean active;

    // Constructor สำหรับกำหนดค่าเริ่มต้นให้กับสมาชิก
    public Member(String id, String firstname, String lastname, String email, String pass, String phonenum, double point, boolean active) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.pass = pass;
        this.phonenum = phonenum;
        this.point = point;
        this.active = active;
    }

    // Getter methods สำหรับการเข้าถึงข้อมูลของสมาชิก
    public String getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public double getPoint() {
        return point;
    }

    public boolean isActive() {
        return active;
    }

    // ฟังก์ชันสำหรับตรวจสอบรหัสผ่าน
    public boolean checkPassword(String inputPassword) {
        return pass.equals(inputPassword);
    }
    public class PasswordUtils {

        // ฟังก์ชันสำหรับสร้างรหัสผ่านจากตำแหน่ง index
        public static String createPasswordFromIndex(String[] parts, int... indexes) {
            StringBuilder passwordBuilder = new StringBuilder();
            for (int index : indexes) {
                if (index >= 0 && index < parts.length) {
                    passwordBuilder.append(parts[index]);
                } else {
                    throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for parts array.");
                }
            }
            return passwordBuilder.toString();
        }
    }

}
