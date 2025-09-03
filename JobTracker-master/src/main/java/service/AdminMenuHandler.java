package service;

import model.Application;
import model.Job;
import model.User;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

@Component
public class AdminMenuHandler {

    public static void handle(Scanner scanner, JobService jobService,
                              ApplicationService applicationService, User admin) {
        try{
            while (true) {
                System.out.println("\nAdmin Menu:");
                System.out.println("1. Add a Job");
                System.out.println("2. View Applied Jobs by Users");
                System.out.println("3. View All Jobs");
                System.out.println("4. Manage Job Application (Accept/Reject)");
                System.out.println("5. Exit");

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1: // Add Job
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
                            jobService.saveJob(newJob);
                            System.out.println("Job added successfully!");
                        } catch (Exception e) {
                            System.out.println("Error adding job: " + e.getMessage());
                        }
                        break;
                    case 2: // View Applied Jobs for a job
                        try {
                            System.out.println("Enter Job ID to view applied users: ");
                            int jobId = Integer.parseInt(scanner.nextLine());
                            List<Application> applications = applicationService.getApplicationsByJobId(jobId);
                            if (applications.isEmpty()) {
                                System.out.println("No applications for this job.");
                            } else {
                                applications.forEach(app ->
                                        System.out.println(app));
                            }
                        } catch (Exception e) {
                            System.out.println("Error fetching applications: " + e.getMessage());
                        }
                        break;
                    case 3: // View All Jobs
                        try {
                            List<Job> jobs = jobService.getAllJobsSortedByTitle();
                            jobs.forEach(job -> System.out.println(job));
                        } catch (Exception e) {
                            System.out.println("Error fetching jobs: " + e.getMessage());
                        }
                        break;
                    case 4: // Manage Job Application
                        try {
                            System.out.println("Enter User ID of applicant: ");
                            int userId = Integer.parseInt(scanner.nextLine());
                            System.out.println("Enter Job ID for application: ");
                            int jobId = Integer.parseInt(scanner.nextLine());
                            System.out.println("Enter new status (Accepted/Rejected): ");
                            String status = scanner.nextLine();

                            applicationService.updateApplicationStatus(userId, jobId, status);
                            System.out.println("Application status updated.");
                        } catch (Exception e) {
                            System.out.println("Error updating application: " + e.getMessage());
                        }
                        break;
                    case 5: // Exit
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }catch (Exception e) {
            // log exception details
            System.err.println("Exception: " + e.getMessage());
            // optionally rethrow as unchecked exception
            throw new RuntimeException("Failed to update application status", e);
        }
    }
}
