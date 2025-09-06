package org.example.service;

import dao.ApplicationRepository;
import model.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository repository;

    public Application saveApplication(Application application) {
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
