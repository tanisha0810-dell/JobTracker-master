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

    // Save application with auto-filled details
    public Application saveApplication(Application application) {
        // Fetch user details
        Optional<User> userOpt = userRepository.findById(application.getUserId());
        userOpt.ifPresent(user -> application.setUserName(user.getName())); // assuming User has getName()

        // Fetch job details
        Optional<Job> jobOpt = jobRepository.findById(application.getJobId());
        jobOpt.ifPresent(job -> {
            application.setJobTitle(job.getTitle());   // assuming Job has getTitle()
            application.setCompany(job.getCompany()); // assuming Job has getCompany()
        });

        // Default status if not set
        if (application.getStatus() == null) {
            application.setStatus("Applied");
        }

        return repository.save(application);
    }

    @Transactional
    public void updateApplicationStatus(int userId, int jobId, String status) {
        List<Application> applications = repository.findByUserId(userId);
        for (Application app : applications) {
            if (app.getJobId() == jobId) {
                app.setStatus(status);
                repository.save(app);
                break;
            }
        }
    }

    public List<Application> getApplicationsByUserId(int userId) {
        return repository.findByUserId(userId);
    }

    public List<Application> getApplicationsByJobId(int jobId) {
        return repository.findByJobId(jobId);
    }

    public void deleteApplication(int id) {
        repository.deleteById(id);
    }
}
