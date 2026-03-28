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

    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }

    // Get all jobs sorted by title ascending
    public List<Job> getAllJobsSortedByTitle() {
        return jobRepository.findAll(Sort.by(Sort.Direction.ASC, "title"));
    }

    // Search for a job by title
    public List<Job> findByTitle(String title) {
        return jobRepository.findByTitleIgnoreCase(title);
    }

    public void deleteJobById(Long id) {
        jobRepository.deleteById(id);
    }

    public List<Job> findJobsByTitleIgnoreCase(String title) {
        return jobRepository.findByTitleIgnoreCase(title);
    }
}
