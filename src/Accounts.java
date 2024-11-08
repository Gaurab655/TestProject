import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Accounts {
    private Scanner scanner;
    private Connection connection;

    public Accounts(Connection connection,Scanner scanner){
        this.scanner=scanner;
        this.connection=connection;

    }
    public void open_account(String email){
        if (accountsExists(email)){
            System.out.print("Enter Your Full Name: ");
            String name = scanner.nextLine();
            scanner.nextLine();
            System.out.println("Enter balance : ");
            double balance = scanner.nextDouble();
            scanner.nextLine();
            System.out.println("Enter pin : ");
            String pin = scanner.next();

            String query ="INSERT INTO accounts(full_name,email,balance,pin)values(?,?,?,?)";

           try {
               PreparedStatement preparedStatement = connection.prepareStatement(query);
               preparedStatement.setString(1,name);
               preparedStatement.setString(2,email);
               preparedStatement.setDouble(3,balance);
               preparedStatement.setString(4,pin);
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows>0){
                    System.out.println("Account created successfully");
                }

           }catch (SQLException e){
               System.out.println(e.getMessage());
           }
        }

    }
    public boolean accountsExists(String email){
        String query = "SELECT * FROM accounts where email = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return false;
            }else {
                return true;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return true;
    }
    public void accountNumber(String email){
        String query = "SELECT account_number FROM accounts where email = ?";
       try {
           PreparedStatement preparedStatement = connection.prepareStatement(query);
           preparedStatement.setString(1,email);
           ResultSet resultSet= preparedStatement.executeQuery();
           if (resultSet.next()){
               int accNumber = resultSet.getInt("account_number");
               System.out.println("Account number is " +accNumber);
           }else {
               throw new RuntimeException("Account does not exists");
           }
       }catch (Exception e){
           System.out.println(e.getMessage());
       }


    }
}
