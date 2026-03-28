package org.example.service;

import dao.ApplicationRepository;
import dao.JobRepository;
import dao.UserRepository;
import model.Application;
import model.Job;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;

    public Application saveApplication(Application application) {
        // Fetch user details
        Optional<User> userOpt = userRepository.findById(application.getUserId());
        userOpt.ifPresent(user -> application.setUserName(user.getName())); // assuming User has getName()

        // Fetch job details
        Optional<Job> jobOpt = jobRepository.findById(application.getJobId());
        jobOpt.ifPresent(job -> {
            application.setJobTitle(job.getTitle());
            application.setCompany(job.getCompany());
        });

        if (application.getStatus() == null) {
            application.setStatus("Applied");
        }

        return repository.save(application);
    }

    @Transactional
    public void updateApplicationStatus(Long userId, Long jobId, String status) {
        List<Application> applications = repository.findByUserId(userId);
        for (Application app : applications) {
            if (app.getJobId().equals(jobId)) {  // ✅ compare Longs properly
                app.setStatus(status);
                repository.save(app);
                break;
            }
        }
    }

    public List<Application> getApplicationsByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    public List<Application> getApplicationsByJobId(Long jobId) {
        return repository.findByJobId(jobId);
    }

    public void deleteApplication(Long id) {
        repository.deleteById(id);
    }
}
