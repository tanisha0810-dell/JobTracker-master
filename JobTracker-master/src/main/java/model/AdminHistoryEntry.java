package model;

import jakarta.persistence.*;

@Entity
@Table(name = "admin_history_entries")
public class AdminHistoryEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String jobTitle;
    private String companyName;
    private String status;

    public AdminHistoryEntry() {
    }

    public AdminHistoryEntry(String userName, String jobTitle, String companyName, String status) {
        this.userName = userName;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.status = status;
    }

    // Getters/setters omitted for brevity

    @Override
    public String toString() {
        return "User: " + userName + ", Job: " + jobTitle + ", Company: " + companyName + ", Status: " + status;
    }
}
