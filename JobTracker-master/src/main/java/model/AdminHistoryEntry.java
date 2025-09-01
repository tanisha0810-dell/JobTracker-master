package model;

public class AdminHistoryEntry {
    private String userName;
    private String jobTitle;
    private String companyName;
    private String status;
    public AdminHistoryEntry next; // acts like a linked list node

    public AdminHistoryEntry(String userName, String jobTitle, String companyName, String status) {
        this.userName = userName;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.status = status;
        this.next = null;
    }

    // Getters & Setters
    public String getUserName() {
        return userName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getStatus() {
        return status;
    }

    public AdminHistoryEntry getNext() {
        return next;
    }

    public void setNext(AdminHistoryEntry next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "User: " + userName +
                ", Job: " + jobTitle +
                ", Company: " + companyName +
                ", Status: " + status;
    }
}
