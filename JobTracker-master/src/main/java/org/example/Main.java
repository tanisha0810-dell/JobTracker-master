package org.example;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import config.AppConfig;
import dao.*;
import model.*;

public class Main {

    public static void main(String[] args) {
        // Load Spring Application Context
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        Scanner scanner = new Scanner(System.in);

        // Get Beans from Spring IoC container
        UserDAO userDAO = context.getBean(UserDAO.class);
        JobDAO jobDAO = context.getBean(JobDAO.class);
        ApplicationDAO appDAO = context.getBean(ApplicationDAO.class);
        JobBST jobBST = context.getBean(JobBST.class);
        AdminHistory adminHistory = context.getBean(AdminHistory.class);

        System.out.println("Do you want to login as 'user' or 'admin'?");
        String role = scanner.nextLine().trim().toLowerCase();

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
                    adminMenu(scanner, jobDAO, appDAO, admin, jobBST, adminHistory);
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
            System.out.println("Enter your email: ");
            String email = scanner.nextLine();
            System.out.println("Enter your password: ");
            String password = scanner.nextLine();

            try {
                User currentUser = userDAO.getUserByEmail(email);


                if (currentUser == null) {
                    System.out.println("No account found. Would you like to register? (yes/no)");
                    String registerChoice = scanner.nextLine();

                    if (registerChoice.equalsIgnoreCase("yes")) {
                        System.out.println("Enter your name: ");
                        String name = scanner.nextLine();
                        System.out.println("Create a password: ");
                        String newPassword = scanner.nextLine();

                        User newUser = new User(0, name, email, newPassword, "user");
                        userDAO.createUser(newUser);
                        System.out.println("Account created successfully! Please login now.");

                        // Prompt user to login immediately after registration
                        performUserLogin(scanner, userDAO, jobDAO, appDAO, jobBST);
                    } else {
                        System.out.println("Exiting...");
                        return;
                    }


                } else {
                    int userAttempts = 0;
                    final int MAX_USER_ATTEMPTS = 3;

                    while (!currentUser.getPassword().equals(password) && userAttempts < MAX_USER_ATTEMPTS) {
                        System.out.println("Incorrect password. Try again.");
                        userAttempts++;
                        if (userAttempts == MAX_USER_ATTEMPTS) {
                            System.out.println("Too many failed attempts. Exiting...");
                            return;
                        }
                        System.out.println("Enter your password: ");
                        password = scanner.nextLine();
                    }


                    System.out.println("Login successful! Welcome, " + currentUser.getName());

                    // Load jobs into BST
                    List<Job> jobs = jobDAO.getAllJobs();
                    for (Job job : jobs) {
                        jobBST.insert(job);
                    }

                    userMenu(scanner, jobDAO, appDAO, currentUser, jobBST);
                }
            } catch (Exception e) {
                System.out.println("Database error: " + e.getMessage());
            }

        } else {
            System.out.println("Invalid role selection. Please restart and enter 'user' or 'admin'.");
        }

        context.close(); // close Spring context
    }

    private static void performUserLogin(Scanner scanner, UserDAO userDAO, JobDAO jobDAO,
                                         ApplicationDAO appDAO, JobBST jobBST) {
        try {
            System.out.println("Enter your email: ");
            String email = scanner.nextLine();
            System.out.println("Enter your password: ");
            String password = scanner.nextLine();

            User currentUser = userDAO.getUserByEmail(email);

            if (currentUser == null) {
                System.out.println("No account found with this email.");
                return;
            }

            int loginAttempts = 0;
            final int MAX_LOGIN_ATTEMPTS = 3;
            while (!currentUser.getPassword().equals(password) && loginAttempts < MAX_LOGIN_ATTEMPTS) {
                System.out.println("Incorrect password. Try again.");
                loginAttempts++;
                if (loginAttempts == MAX_LOGIN_ATTEMPTS) {
                    System.out.println("Too many failed attempts. Access denied.");
                    return;
                }
                System.out.println("Enter your password: ");
                password = scanner.nextLine();
            }


            System.out.println("Login successful! Welcome, " + currentUser.getName());

            // Load jobs into BST
            List<Job> jobs = jobDAO.getAllJobs();
            for (Job job : jobs) {
                jobBST.insert(job);
            }

            userMenu(scanner, jobDAO, appDAO, currentUser, jobBST);

        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }


    private static void adminMenu(Scanner scanner, JobDAO jobDAO, ApplicationDAO appDAO,
                                  User currentUser, JobBST jobBST, AdminHistory adminHistory) {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add a Job");
            System.out.println("2. View Applied Jobs by the Users");
            System.out.println("3. View All Jobs");
            System.out.println("4. Manage Job Application (Accept/Reject)");
            System.out.println("5. View Application History");
            System.out.println("6. Delete a Job");
            System.out.println("7. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    try {
                        System.out.println("Enter Job Title: ");
                        String title = scanner.nextLine();
                        System.out.println("Enter Job Description: ");
                        String description = scanner.nextLine();
                        System.out.println("Enter Company Name: ");
                        String company = scanner.nextLine();
                        System.out.println("Enter Required Role: ");
                        String roleRequired = scanner.nextLine();
                        System.out.println("Enter Job Deadline (yyyy-mm-dd): ");
                        String deadline = scanner.nextLine();

                        Job newJob = new Job(0, title, description, company, roleRequired, java.sql.Date.valueOf(deadline));
                        jobDAO.createJob(newJob);
                        jobBST.insert(newJob);
                        System.out.println("Job added successfully!");
                    } catch (Exception e) {
                        System.out.println("Error adding job: " + e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        System.out.println("Enter Job ID to view applied users: ");
                        int jobId = scanner.nextInt();
                        scanner.nextLine();

                        List<Application> applications = jobDAO.getAppliedJobs(jobId);
                        if (applications.isEmpty()) {
                            System.out.println("No applications for this job.");
                        } else {
                            for (Application app : applications) {
                                System.out.println("User ID: " + app.getUserId() +
                                        ", Job Title: " + app.getJobTitle() +
                                        ", Job ID: " + app.getJobId() +
                                        ", Company: " + app.getCompany() +
                                        ", Status: " + app.getStatus());
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Error fetching applications: " + e.getMessage());
                    }
                    break;

                case 3:
                    try {
                        List<Job> jobs = jobDAO.getAllJobs();
                        System.out.println("\nAll Jobs:");
                        for (Job job : jobs) {
                            System.out.println(job);
                        }
                    } catch (Exception e) {
                        System.out.println("Error fetching jobs: " + e.getMessage());
                    }
                    break;

                case 4:
                    try {
                        System.out.println("Enter User ID of the applicant: ");
                        int userId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Enter Job ID for the application: ");
                        int jobId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Enter new status (Accepted/Rejected): ");
                        String status = scanner.nextLine();

                        appDAO.updateApplicationStatus(userId, jobId, status);

                        String jobTitle = appDAO.getJobTitle(jobId);
                        String companyName = appDAO.getCompanyName(jobId);

                        adminHistory.addEntry("User" + userId, jobTitle, companyName, status);
                        System.out.println("History updated successfully.");
                    } catch (SQLException e) {
                        System.out.println("Error updating status: " + e.getMessage());
                    }
                    break;

                case 5:
                    adminHistory.showHistory();
                    break;

                case 6:
                    try {
                        System.out.println("Enter Job ID to delete: ");
                        int deleteJobId = scanner.nextInt();
                        scanner.nextLine();

                        boolean deleted = jobDAO.deleteJobById(deleteJobId);
                        if (deleted) {
                            System.out.println("Job deleted successfully!");
                        } else {
                            System.out.println("Job ID not found.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error deleting job: " + e.getMessage());
                    }
                    break;

                case 7:
                    System.out.println("Exiting admin menu...");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void userMenu(Scanner scanner, JobDAO jobDAO, ApplicationDAO appDAO,
                                 User currentUser, JobBST jobBST) {
        while (true) {
            System.out.println("\nUser Menu:");
            System.out.println("1. View Available Jobs");
            System.out.println("2. Apply for a Job");
            System.out.println("3. Your Applications");
            System.out.println("4. Search Jobs by Title");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    try {
                        List<Job> jobs = jobDAO.getAllJobs();
                        System.out.println("\nAll Jobs:");
                        for (Job job : jobs) {
                            System.out.println(job);
                        }
                    } catch (Exception e) {
                        System.out.println("Error fetching jobs: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("Enter Job ID to apply for: ");
                    int jobIdToApply = scanner.nextInt();
                    scanner.nextLine();

                    Application newApplication = new Application(
                            currentUser.getId(),
                            jobIdToApply,
                            currentUser.getName(),
                            "Applied"
                    );

                    try {
                        appDAO.applyForJob(newApplication);
                        System.out.println("Job application submitted successfully!");
                    } catch (SQLException e) {
                        System.out.println("Error applying for job: " + e.getMessage());
                    }
                    break;

                case 3:
                    try {
                        System.out.println("=== Your Applications ===");
                        List<Application> userApps = appDAO.getApplicationsByUser(currentUser.getId());
                        if (userApps.isEmpty()) {
                            System.out.println("You have not applied for any jobs yet.");
                        } else {
                            System.out.printf("%-10s %-30s %-20s %-15s\n", "Job ID", "Job Title", "Company", "Status");
                            for (Application app : userApps) {
                                System.out.printf("%-10d %-30s %-20s %-15s\n",
                                        app.getJobId(), app.getJobTitle(), app.getCompany(), app.getStatus());
                            }
                        }
                    } catch (RuntimeException e) {
                        System.out.println("Error fetching applications: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.println("Enter the job title you are looking for: ");
                    String searchTitle = scanner.nextLine();

                    Job job = jobBST.search(searchTitle.toLowerCase());
                    if (job != null) {
                        System.out.println("Job Found: " + job);
                    } else {
                        System.out.println("Job not found.");
                    }
                    break;

                case 5:
                    System.out.println("Exiting user menu...");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
