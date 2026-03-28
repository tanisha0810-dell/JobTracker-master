package controller;

import model.Application;
import org.example.service.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<Application> apply(@RequestBody Application application) {
        return ResponseEntity.ok(applicationService.saveApplication(application));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Application>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(applicationService.getApplicationsByUserId(userId));
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<Application>> getByJob(@PathVariable Long jobId) {
        return ResponseEntity.ok(applicationService.getApplicationsByJobId(jobId));
    }

    @PutMapping("/status")
    public ResponseEntity<?> updateStatus(@RequestParam Long userId,
                                          @RequestParam Long jobId,
                                          @RequestParam String status) {
        applicationService.updateApplicationStatus(userId, jobId, status);
        return ResponseEntity.ok("Status updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.ok("Application deleted successfully");
    }
}