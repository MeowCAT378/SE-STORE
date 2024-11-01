import java.io.*;
import java.util.*;

public class FileManage {
    Map<String, String> category = new HashMap<>();
    List<Product> productList = new ArrayList<>();
    List<Member> membersList = new ArrayList<>();

    public void writeFile(String newLine, File file) throws IOException {
        BufferedWriter writeFile = new BufferedWriter(new FileWriter(file, true));
        writeFile.write(newLine);
        writeFile.close();
    }

    //Method แสดงรายการสินค้าทั้งหมด | รับไฟล์
    public void showCategory(File file) throws IOException {
        BufferedReader readFile = new BufferedReader(new FileReader(file));

        //ตัวแปรเอาไว้นับจำนวนรายการสินค้าทั้งหมด
        int count = 1;

        //อ่านไฟล์ category
        String line;
        while ((line = readFile.readLine()) != null){
            String[] data = line.split("\\s+");
            category.put(String.valueOf(count), data[1].trim());
            count++;
        }

        System.out.println("===== SE STORE's Product Categories =====");
        System.out.printf("%-7s %-15s \n", "#", "Category");
        for (String i : category.keySet()){
            System.out.printf("%-7s %-15s \n", i, category.get(i));
        }
        System.out.println("=========================================");
        readFile.close();
    }

    //Method แสดงรายการสินค้าที่เราเลือก | รับไฟล์, รับตัวเลือกที่เลือก
    public void showProductSelect(File file, String selectType) throws FileNotFoundException {
        Scanner readFile = new Scanner(file);
        productList.clear();
        //ตัวแปรไว้นับจำนวนรายการที่แสดง
        int countProduct = 1;

        //ลูปเอาไว้เช็คข้อมูลของ Product บรรทัดไหนบ้างที่ตรงกับ Category ที่เลือก
        while (readFile.hasNext()){
            Product product = new Product();
            product.setAmount(readFile.next().trim());
            product.setName(readFile.next().trim());
            product.setPrice(readFile.next().trim());
            product.setQuantity(readFile.next().trim());
            product.setId(readFile.next().trim());
            productList.add(product);
        }

        String checkTypeId = "";
        for (String i : category.keySet()){
            checkTypeId = i.equals(selectType) ? String.valueOf(((Integer.parseInt(i)-1)+3000)) : "" ;
            if (!checkTypeId.isEmpty()) break;
        }

        System.out.println("============ " + category.get(selectType) + " ============");
        System.out.printf("%-5s %-15s %-10s %6s \n", "#" ,"Name", "Price (฿)", "Quantity");
        for (Product i : productList){
            if (i.getId().equals(checkTypeId)){
                double price = Double.parseDouble(i.getPrice().substring(1)) * 34;
                System.out.printf("%-5s %-17s %-8.2f %5s \n", countProduct, i.getName(), price, i.getQuantity());
                countProduct++;
            }
        }

        System.out.println("================================");
    }

    //Method เอาไว้นับจำนวนรายการสินค้า | รับไฟล์
    public int getCountProduct() throws FileNotFoundException {
        return category.size();
    }

    //Method เอาไว้เช็คว่า Email กับ Password ถูกมั้ย | รับไฟล์, email, password
    public boolean checkCorrect(File file, String Email, String Password) throws FileNotFoundException {
        Scanner readFile = new Scanner(file);
        membersList.clear();

        while (readFile.hasNext()){
            Member member = new Member();

            member.setId(readFile.next().trim());
            member.setFirstName(readFile.next().trim());
            member.setLastName(readFile.next().trim());
            member.setEmail(readFile.next().trim());
            member.setPassCode(readFile.next().trim());
            member.setPhoneNum(readFile.next().trim());
            member.setPoint(readFile.next().trim());

            StringBuilder pass = new StringBuilder();
            pass.append(member.getPassCode().charAt(9));//เพื่อค่า passCode ตำแหน่งที่ 9 .append ฟีลเหมือน pass += passCode.charAt(9)
            pass.append(member.getPassCode().charAt(10));
            pass.append(member.getPassCode().charAt(13));
            pass.append(member.getPassCode().charAt(14));
            pass.append(member.getPassCode().charAt(15));
            pass.append(member.getPassCode().charAt(16));

            if (Password.equals(String.valueOf(pass)) && Email.equals(member.getEmail())){//เช็คว่า email กับ password ตรงกับ ข้อมูลในไฟล์ มั้ย
                membersList.add(member); //เก็บ email ที่ login ผ่านไว้
                readFile.reset();
                return true;
            }
        }
        return false;
    }

    //Method เอาไว้เช็คว่า Email กับ Password ถูกมั้ย
    public boolean checkExpired() throws IOException {
        //ตัวแปรเอาไว้เก็บว่า บัญชีหมดอายุมั้ย ถ้าหมดเป็น false ถ้ายังไม่หมดเป็น true
        boolean check = false;

        for (Member i : membersList) {
            check = i.passCode.charAt(2) == '1';
        }

        return check;
    }

    //Method เอาไว้แสดงข้อมูล member
    public void showDataMember() throws FileNotFoundException {
        //ตัวแปรเอาไว้เก็บข้อมูลที่ดัดแปลงแล้ว เพื่อเอาไปแสดง | **สังเกตุความแตกต่างที่พิมพ์ใหญ่ตัวแรก**
        StringBuilder FirstName = new StringBuilder();
        StringBuilder FullName = new StringBuilder("Hello, ");
        StringBuilder Email = new StringBuilder();
        StringBuilder Phone = new StringBuilder();
        StringBuilder Point = new StringBuilder();

        String role = "";

        for (Member i : membersList){
            //ชื่อ
            FirstName.append(i.getLastName().charAt(0));//เอานามสกุลตัวแรก
            FirstName.append(".");//เพิ่มจุด

            if (i.getPassCode().charAt(6) == '0'){
                role = "STAFF";
            }if(i.getPassCode().charAt(6) == '1'){
                role = "REGULAR";
            }if (i.getPassCode().charAt(6) == '2'){
                role = "SILVER";
            }if (i.getPassCode().charAt(6) == '3'){
                role = "GOLD";
            }

            FullName.append(FirstName).append(" ").append(i.getFirstName()).append(" (").append(role).append(")");//เอาชื่อมารวมกัน

            //email
            Email.append(i.getEmail());//เอา email ในไฟล์ไปใส่ในตัวแปรชนิด StringBuilder เพื่อง่ายต่อการดัดแปลง
            Email.replace(2, i.getEmail().indexOf("@"), "***");//แทนที่ ตัวแหน่งที่ 2 ถึง ตำแหน่งที่ @ อยู่ ด้วย ***
            Email.replace(Email.indexOf("@")+3, Email.length(), "***");//แทนที่ ตำแหน่งที่ @ อยู่ ถัดไป 3 ตัว ถึง ตำแหน่งสุดท้าย ด้วย ***

            //เบอร์
            Phone.append(i.getPhoneNum());
            Phone.insert(3,"-");//แทรก (เพิ่ม) - ในตำแหน่งที่ 3
            Phone.insert(7,"-");//แทรก (เพิ่ม) - ในตำแหน่งที่ 7

            //point
            Point.append(i.getPoint());//เอาตั้งแต่ตำแหน่งที่ 0 ไปจนถึงตำแหน่งก่อน ตำแหน่ง .
        }

        System.out.println("===== SE STORE =====");
        System.out.println(FullName);
        System.out.println("Email: " + Email);
        System.out.println("Phone: " + Phone);
        System.out.println("You have " + Point + " Point");

    }

    public String checkRole() throws FileNotFoundException {
        String role = "";

        for (Member i : membersList){
            role = String.valueOf(i.getPassCode().charAt(6));
        }

        return role;
    }

    public String randomPassword(){
        StringBuilder pass = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int randomInt = random.nextInt(10);
            pass.append(randomInt);
        }
        return String.valueOf(pass);
    }

    public String randomChar(String pass){
        String character = "ABCDEFGHIJKLMNOPQRSPUVWUSYZ";
        StringBuilder passWord = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 19 ; i++) {
            int randomInt = random.nextInt(26);
            passWord.append(character.charAt(randomInt));
        }

        passWord.replace(2,3, "1");
        passWord.replace(6,7,"1");
        passWord.replace(9,10,String.valueOf(pass.charAt(0)));
        passWord.replace(10,11,String.valueOf(pass.charAt(1)));
        passWord.replace(13,14,String.valueOf(pass.charAt(2)));
        passWord.replace(14,15,String.valueOf(pass.charAt(3)));
        passWord.replace(15,16,String.valueOf(pass.charAt(4)));
        passWord.replace(16,17,String.valueOf(pass.charAt(5)));

        return String.valueOf(passWord);
    }
}