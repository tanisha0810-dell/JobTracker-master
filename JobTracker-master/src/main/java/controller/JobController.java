package controller;

import model.Job;
import org.example.service.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobsSortedByTitle());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Job>> searchByTitle(@RequestParam String title) {
        return ResponseEntity.ok(jobService.findByTitle(title));
    }

    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        return ResponseEntity.ok(jobService.saveJob(job));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable Long id) {
        jobService.deleteJobById(id);
        return ResponseEntity.ok("Job deleted successfully");
    }
}