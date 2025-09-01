package dao;

import java.sql.*;
import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ArrayList;

@Component
public class ApplicationDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void updateApplicationStatus(int userId, int jobId, String status) throws SQLException {
        String sql = "UPDATE applications SET status = ? WHERE user_id = ? AND job_id = ?";
        int rowsUpdated = jdbcTemplate.update(sql, status, userId, jobId);
        if (rowsUpdated > 0) {
            System.out.println("Application status updated successfully.");
        } else {
            System.out.println("No application found for the given user ID and job ID.");
        }

    }

    public void applyForJob(Application application) throws SQLException {
        String sql = "INSERT INTO applications (user_id, job_id,status) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, application.getUserId(), application.getJobId(), application.getStatus());
    }


    public List<Application> getApplicationsByUser(int userId) {
        String sql = "SELECT a.job_id, a.status, j.title, j.company " +
                "FROM applications a JOIN jobs j ON a.job_id = j.id " +
                "WHERE a.user_id = ?";

        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) ->
                new Application(
                        userId,
                        rs.getInt("job_id"),
                        rs.getString("title"),
                        rs.getString("status"),
                        rs.getString("company")
                )
        );
    }


    public String getCompanyName(int jobId) {
        String sql = "SELECT company FROM jobs WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{jobId}, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;  // No company found
        }
    }

    public String getJobTitle(int jobId) {
        String sql = "SELECT title FROM jobs WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{jobId}, String.class);
        } catch (EmptyResultDataAccessException e) {
            // No record found with the given jobId
            return null;
        }
    }

}