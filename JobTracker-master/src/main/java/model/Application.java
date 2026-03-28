package model;

import jakarta.persistence.*;

@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long jobId;

    private String userName;
    private String jobTitle;
    private String company;
    private String status;

    public Application() {}

    public Application(Long userId, Long jobId, String userName, String status) {
        this.userId = userId;
        this.jobId = jobId;
        this.userName = userName;
        this.status = status;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getJobId() { return jobId; }
    public void setJobId(Long jobId) { this.jobId = jobId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

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
