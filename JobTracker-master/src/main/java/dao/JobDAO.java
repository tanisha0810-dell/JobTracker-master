package dao;

import model.Application;
import model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JobDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Insert Job and return generated ID
    public int createJob(Job job) {
        String sql = "INSERT INTO jobs (title, description, company, role_required, deadline) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, job.getTitle(), job.getDescription(), job.getCompany(), job.getRoleRequired(), job.getDeadline());

        // Return last inserted ID
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }

    // Get all jobs
    public List<Job> getAllJobs() {
        String sql = "SELECT * FROM jobs";
        return jdbcTemplate.query(sql, new JobRowMapper());
    }

    // Delete job by ID (including applications)
    public boolean deleteJobById(int jobId) {
        String deleteApplicationsSql = "DELETE FROM applications WHERE job_id = ?";
        jdbcTemplate.update(deleteApplicationsSql, jobId);

        String deleteJobSql = "DELETE FROM jobs WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(deleteJobSql, jobId);
        return rowsAffected > 0;
    }

    // Get applied jobs for a given jobId
    public List<Application> getAppliedJobs(int jobId) {
        String sql = "SELECT a.user_id, a.job_id, u.name AS user_name, j.title AS job_title, a.status, j.company " +
                "FROM applications a " +
                "JOIN users u ON a.user_id = u.id " +
                "JOIN jobs j ON a.job_id = j.id " +
                "WHERE a.job_id = ?";

        return jdbcTemplate.query(sql, new Object[]{jobId}, new ApplicationRowMapper());
    }

    // ----------------- RowMappers -----------------

    private static class JobRowMapper implements RowMapper<Job> {
        @Override
        public Job mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Job(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("company"),
                    rs.getString("role_required"),
                    rs.getDate("deadline")
            );
        }
    }

    private static class ApplicationRowMapper implements RowMapper<Application> {
        @Override
        public Application mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Application(
                    rs.getInt("user_id"),
                    rs.getInt("job_id"),
                    rs.getString("job_title"),
                    rs.getString("status"),
                    rs.getString("company")
            );
        }
    }
}
