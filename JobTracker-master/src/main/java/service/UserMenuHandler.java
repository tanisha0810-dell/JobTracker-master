package service;

import model.Application;
import model.Job;
import model.User;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

@Component
public class UserMenuHandler {

    public static void handle(Scanner scanner, UserService userService,
                              JobService jobService, ApplicationService applicationService) throws SQLException {

        System.out.println("Enter your email: ");
        String email = scanner.nextLine();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();

        User currentUser = userService.getUserByEmail(email);

        if (currentUser == null) {
            System.out.println("No account found. Would you like to register? (yes/no)");
            String registerChoice = scanner.nextLine();

            if (registerChoice.equalsIgnoreCase("yes")) {
                System.out.println("Enter your name: ");
                String name = scanner.nextLine();
                System.out.println("Create a password: ");
                String newPassword = scanner.nextLine();

                User newUser = new User(0, name, email, newPassword, "user");
                userService.createUser(newUser);
                System.out.println("Registration successful. Please login.");

                // Restart the method after registration
                handle(scanner, userService, jobService, applicationService);
            } else {
                System.out.println("Exiting ...");
                return;
            }
        } else {
            // Password verification with 3 attempts
            final int MAX_ATTEMPTS = 3;
            int attempts = 0;
            while (!currentUser.getPassword().equals(password) && attempts < MAX_ATTEMPTS) {
                System.out.println("Incorrect password. Try again.");
                attempts++;
                if (attempts == MAX_ATTEMPTS) {
                    System.out.println("Too many failed attempts. Exiting...");
                    return;
                }
                System.out.println("Enter your password: ");
                password = scanner.nextLine();
            }

            System.out.println("Login successful! Welcome, " + currentUser.getName());

            // User menu
            while (true) {
                System.out.println("\nUser Menu:");
                System.out.println("1. View Available Jobs");
                System.out.println("2. Apply for a Job");
                System.out.println("3. View Your Applications");
                System.out.println("4. Search Jobs by Title");
                System.out.println("5. Exit");

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        List<Job> jobs = jobService.getAllJobsSortedByTitle();
                        if (jobs.isEmpty()) {
                            System.out.println("No jobs available currently.");
                        } else {
                            jobs.forEach(job -> System.out.println(job));
                        }
                        break;
                    case 2:
                        System.out.println("Enter Job ID to apply for: ");
                        int jobId = Integer.parseInt(scanner.nextLine());

                        Application application = new Application(
                                currentUser.getId(),
                                jobId,
                                currentUser.getName(),
                                "Applied"
                        );

                        applicationService.saveApplication(application);
                        System.out.println("Applied for job successfully!");
                        break;
                    case 3:
                        List<Application> applications = applicationService.getApplicationsByUserId(currentUser.getId());
                        if (applications.isEmpty()) {
                            System.out.println("You have not applied for any jobs yet.");
                        } else {
                            applications.forEach(app -> System.out.println(app));
                        }
                        break;
                    case 4:
                        System.out.println("Enter job title to search: ");
                        String searchTitle = scanner.nextLine();
                        List<Job> searchedJobs = jobService.findJobsByTitleIgnoreCase(searchTitle);
                        if (searchedJobs.isEmpty()) {
                            System.out.println("No job found with title: " + searchTitle);
                        } else {
                            searchedJobs.forEach(job -> System.out.println(job));
                        }
                        break;
                    case 5:
                        return; // Exit menu
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }
}
