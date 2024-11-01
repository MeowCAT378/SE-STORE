import java.io.*;
import java.util.Scanner;

public class Main {

    //หน้าแรกสุด หน้า Login
    public static void displayLogin(){
        System.out.println("===== SE STORE =====");
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.println("====================");
        System.out.print("Select (1-2) : ");
    }


    public static void main(String[] args) throws IOException {
        Scanner kb = new Scanner(System.in);
        //ตัวแปรเก็บ Path File ทั้งหมด
        File productData = new File("C:\\Users\\yothi\\Downloads\\PRODUCT.txt");
        File categoryData = new File("C:\\Users\\yothi\\Downloads\\CATEGORY.txt");
        File memberData = new File("C:\\Users\\yothi\\Downloads\\MEMBER.txt");

        //ตัวแปรเอาไว้เก็บตัวเลือก Login
        String inputLogin;

        //ตัวแปรเอาไว้เก็บตัวเลือกรายการสินค้า
        String inputCategory;

        FileManage fileManage = new FileManage();

        do {
            //ตัวแปรเอาไว้เก็บตัวเลือก Show Category หรือ Logout
            String inputSelect = "";
            String inputSelectStaff = "";

            //รับตัวเลือก
            displayLogin();
            inputLogin = kb.nextLine();

            switch (inputLogin){
                case "1":
                    //ตัวแปรเอาไว้เก็บจำนวนครั้งที่ใส่รหัสผิด
                    int countError = 0;

                    do {
                        //รับ Email กับ Password
                        System.out.println("===== LOGIN =====");
                        System.out.print("Email : ");
                        String email = kb.nextLine();
                        System.out.print("Password : ");
                        String password = kb.nextLine();

                        //เช็คว่า Email กับ Password ถูกมั้ย
                        if (fileManage.checkCorrect(memberData, email, password)){ //ถ้าถูก

                            //เช็คว่า Expired (บัญชีหมดอายุ) มั้ย
                            if (fileManage.checkExpired()){ //ถ้าถูก

                                if (fileManage.checkRole().equals("0")){
                                    fileManage.showDataMember();
                                    do {
                                        System.out.println("====================");
                                        System.out.println("1. Show Category");
                                        System.out.println("2. Add Member");
                                        System.out.println("3. Logout");
                                        System.out.println("====================");
                                        System.out.print("Select (1-3) : ");
                                        inputSelectStaff = kb.nextLine();
                                        switch (inputSelectStaff) {
                                            case "1":
                                                do {
                                                    //เรียกใช้ Method แสดงข้อมูล รายการสินค้า
                                                    fileManage.showCategory(categoryData);

                                                    //ตัวแปรเอาไว้เก็บจำนวน รายการสินค้า
                                                    int count = fileManage.getCountProduct();

                                                    System.out.println("Select Category to Show Product (1-" + count + ") or Q for exit");
                                                    System.out.print("Select : ");
                                                    inputSelect = kb.nextLine();

                                                    switch (inputSelect) {
                                                        case "1", "2", "3", "4", "5", "6", "7", "8", "9", "10":
                                                            do {
                                                                //เรียกใช้ Method แสดงข้อมูล รายการสินค้าที่เราเลือก
                                                                fileManage.showProductSelect(productData, inputSelect);

                                                                System.out.print("Press Q to Exit ");
                                                                inputCategory = kb.nextLine();

                                                            } while (!inputCategory.equalsIgnoreCase("Q"));//ถ้าที่เลือกมาไม่ใช่ q ให้วนลูปต่อ
                                                            break;
                                                    }

                                                } while (!inputSelect.equalsIgnoreCase("Q"));//ถ้าที่เลือกมาไม่ใช่ q ให้วนลูปต่อ
                                                break;

                                            case "2":
                                                addMember(kb,memberData);
                                                break;

                                        }
                                    }while (!inputSelectStaff.equals("3"));

                                }else {
                                    fileManage.showDataMember();
                                    do {//ลูปแสดงตัวเลือกสินค้า
                                        System.out.println("====================");
                                        System.out.println("1. Show Category");
                                        System.out.println("2. Logout");
                                        System.out.println("====================");
                                        System.out.print("Select (1-2) : ");
                                        inputSelect = kb.nextLine();
                                        switch (inputSelect){
                                            case "1":
                                                do {
                                                    //เรียกใช้ Method แสดงข้อมูล รายการสินค้า
                                                    fileManage.showCategory(categoryData);

                                                    //ตัวแปรเอาไว้เก็บจำนวน รายการสินค้า
                                                    int count = fileManage.getCountProduct();

                                                    System.out.println("Select Category to Show Product (1-" + count + ") or Q for exit");
                                                    System.out.print("Select : ");
                                                    inputSelect = kb.nextLine();

                                                    switch (inputSelect) {
                                                        case "1", "2", "3", "4", "5", "6", "7", "8", "9", "10":
                                                            do {
                                                                //เรียกใช้ Method แสดงข้อมูล รายการสินค้าที่เราเลือก
                                                                fileManage.showProductSelect(productData, inputSelect);

                                                                System.out.print("Press Q to Exit ");
                                                                inputCategory = kb.nextLine();

                                                            } while (!inputCategory.equalsIgnoreCase("Q"));//ถ้าที่เลือกมาไม่ใช่ q ให้วนลูปต่อ
                                                            break;
                                                    }

                                                }while (!inputSelect.equalsIgnoreCase("Q"));//ถ้าที่เลือกมาไม่ใช่ q ให้วนลูปต่อ
                                                break;

                                            case "2":
                                                System.out.println("===== SE STORE =====");
                                                System.out.println("Thank you for using our service :3");
                                                break;
                                        }

                                    }while (!inputSelect.equals("2"));
                                }
                            }else {//ถ้า Expired (บัญชีหมดอายุ)
                                System.out.println("===== LOGIN =====");
                                System.out.println("Email : " + email);
                                System.out.println("Password : " + password);
                                System.out.println("====================");
                                System.out.println("Error! - Your Account are Expired!");
                                break;
                            }

                        }else {//ถ้าใส่ Email หรือ Password ผิด
                            countError++;
                            System.out.println("===== LOGIN =====");
                            System.out.println("Email : " + email);
                            System.out.println("Password : " + password);
                            System.out.println("====================");
                            System.out.println("Error! - Email or Password is Incorrect (" + countError + ")");
                        }

                    }while (countError != 3 && !inputSelect.equals("2") && !inputSelectStaff.equals("3"));//ถ้าใส่รหัสผิดไม่ถึง 3 ครั้ง และที่รับค่าเข้ามาไม่ใช่ 2 ให้วนลูปต่อ

                    //ถ้าใส่รหัสผิด 3 ครั้ง
                    if (countError == 3){
                        System.out.println("Sorry, Please try again later :(");
                    }
                    break;

                case "2":
                    System.out.println("===== SE STORE =====");
                    System.out.println("Thank you for using our service :3");
                    break;

            }

        }while (!inputLogin.equals("2"));//ถ้าที่รับค่าเข้ามาไม่ใช่ 2 ให้วนลูปต่อ

    }

    public static void addMember(Scanner kb, File memberData) throws IOException {
        FileManage fileManage = new FileManage();
        System.out.println("====Add Member====");
        System.out.print("Firstname : ");
        String firstName = kb.nextLine();
        System.out.print("Lastname : ");
        String lastName = kb.nextLine();
        System.out.print("Email : ");
        String email = kb.nextLine();
        System.out.print("Phone : ");
        String phone = kb.nextLine();

        if (firstName.length() > 2 && lastName.length() > 2 && email.contains("@") && phone.length() == 10){
            BufferedReader read = new BufferedReader(new FileReader(memberData));
            String line;
            String ID = "";
            while ((line = read.readLine()) != null){
                String[] data = line.split("\\s+");
                ID = data[0].trim();
            }

            String newID = String.valueOf(Integer.parseInt(ID)+1);

            StringBuilder inputFileWriter = new StringBuilder();
            String passNum = fileManage.randomPassword();

            inputFileWriter.append(newID).append("\t")
                    .append(firstName).append("\t")
                    .append(lastName).append("\t")
                    .append(email).append("\t")
                    .append(fileManage.randomChar(passNum)).append(" ")
                    .append(phone).append("  ").append("0.00");

            System.out.println("Success - New Member has been created!");
            System.out.println(firstName + " Password is " + passNum);

            fileManage.writeFile("\n" + inputFileWriter, memberData);

        }else{
            System.out.println("Error! = - Your Information are Incorrect!");
        }
    }

}