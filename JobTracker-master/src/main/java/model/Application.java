package model;

import jakarta.persistence.*;

@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "job_id", nullable = false)
    private int jobId;

    @Column(name = "user_name")
    private String userName;

    @Column(nullable = false)
    private String status;

    @Column(name = "job_title")
    private String jobTitle;

    @Column
    private String company;

    // Default constructor
    public Application() {
    }

    // Constructor with company
    public Application(int userId, int jobId, String jobTitle, String status, String company) {
        this.userId = userId;
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.status = status;
        this.company = company;
    }

    // Constructor with userName
    public Application(int userId, int jobId, String userName, String status) {
        this.userId = userId;
        this.jobId = jobId;
        this.userName = userName;
        this.status = status;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", userId=" + userId +
                ", jobId=" + jobId +
                ", userName='" + userName + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", company='" + company + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
