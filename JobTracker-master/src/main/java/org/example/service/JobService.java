package org.example.service;

import dao.JobRepository;
import model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    // Save or update a job
    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }

    // Get all jobs sorted by title ascending
    public List<Job> getAllJobsSortedByTitle() {
        return jobRepository.findAll(Sort.by(Sort.Direction.ASC, "title"));
    }

    // Search for a job by title
    public List<Job> findByTitle(String title) {
        // Assuming you add a method in JobRepository to find by title
        return jobRepository.findByTitleIgnoreCase(title);
    }

    // Delete a job by id
    public void deleteJobById(int id) {
        jobRepository.deleteById(id);
    }

    public List<Job> findJobsByTitleIgnoreCase(String title) {
        return jobRepository.findByTitleIgnoreCase(title);
    }
}
