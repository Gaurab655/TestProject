import com.sun.source.tree.PackageTree;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
    private Connection connection;
    private Scanner scanner;

    public User(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }
    public String login(){
        System.out.println("Enter email : ");
        String email = scanner.next();
        System.out.println("Enter password : ");
        String password = scanner.next();
        String query = "select * from users where email = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,email);
            ResultSet resultSet= preparedStatement.executeQuery();
            if (resultSet.next()){
                String checkPassword = resultSet.getString("password");
                if (checkPassword.equals(password)){
                    System.out.println("Login success");
                    return email;

                }else {
                    System.out.println("enter correct email/password");
                }
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void register(){
        System.out.println("Enter Name : ");
        String name = scanner.next();
        System.out.print("Enter Email : ");
        String email = scanner.next();
        System.out.print("Enter Password : ");
        String password = scanner.next();

        String query ="INSERT INTO users(name,email,password)VALUES (?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,password);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows>0){
                System.out.println("Data inserted successfully");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void exit(){
        System.out.println("exiting...");

    }

}
