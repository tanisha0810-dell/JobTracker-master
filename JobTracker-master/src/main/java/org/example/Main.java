package org.example;

import model.Application;
import model.Job;
import model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import service.ApplicationService;
import service.JobService;
import service.UserService;
import service.AdminMenuHandler;
import service.UserMenuHandler;


import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);

        ApplicationService applicationService = context.getBean(ApplicationService.class);
        JobService jobService = context.getBean(JobService.class);
        UserService userService = context.getBean(UserService.class);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Do you want to login as 'user' or 'admin'?");
        String role = scanner.nextLine().trim().toLowerCase();

        try{
            if (role.equals("admin")) {
                int adminAttempts = 0;
                final int MAX_ADMIN_ATTEMPTS = 3;

                while (adminAttempts < MAX_ADMIN_ATTEMPTS) {
                    System.out.println("Enter admin email: ");
                    String email = scanner.nextLine();
                    System.out.println("Enter admin password: ");
                    String password = scanner.nextLine();

                    if (email.equals("admin@example.com") && password.equals("admin123")) {
                        User admin = new User(0, "Admin", email, password, "admin");
                        System.out.println("Login successful as Admin!");
                        AdminMenuHandler.handle(scanner, jobService, applicationService, admin);
                        break;
                    } else {
                        adminAttempts++;
                        System.out.println("Invalid admin credentials. Try again.");
                    }
                }
                if (adminAttempts == MAX_ADMIN_ATTEMPTS) {
                    System.out.println("Too many failed attempts. Exiting...");
                    return;
                }

            } else if (role.equals("user")) {
                UserMenuHandler.handle(scanner, userService, jobService, applicationService);
            }
        }catch(SQLException e){
            System.out.println("Database error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

