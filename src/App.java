import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class App {
    private static final String url = "jdbc:postgresql://localhost:5432/TestProject";
    private static final String name ="postgres";
    private static final String password ="password";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
       try {
           Class.forName("org.postgresql.Driver");
           System.out.println("Driver loaded successfully");
       }catch (ClassNotFoundException e){
           System.out.println(e.getMessage());
       }
       try {
           Connection connection = DriverManager.getConnection(url,name,password);
           User user = new User(connection,scanner);
           Accounts accounts = new Accounts(connection,scanner);
           if (!Objects.isNull(connection)){
               mainMenu(user,accounts);
           }
       }catch (SQLException e){
           System.out.println(e.getMessage());
       }
    }
    public static void mainMenu(User user,Accounts accounts){
        String email;
        while (true){
            System.out.println("1: Login");
            System.out.println("2: Register");
            System.out.println("0: Exit");

            int choose =scanner.nextInt();
            switch (choose){
                case 1:
                    email=user.login();
                    if (email!=null){
                        System.out.println("Logged in");
                        if (accounts.accountsExists(email)){
                            System.out.println("Choose 1 of the following :");
                            System.out.println("1: Open account ");
                            int choose1=scanner.nextInt();
                            if (choose1==1){
                                accounts.open_account(email);
                                accounts.accountNumber(email);
                                break;

                            }
                        }


                    }
                    break;
                case 2:
                    user.register();
                    break;
                case 0 :
                    user.exit();
                   break;
            }
        }
    }
}