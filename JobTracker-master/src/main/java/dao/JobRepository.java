package dao;

import model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;



@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    // You can add custom query methods here if needed, e.g.,
    // List<Job> findByCompany(String company);
    Optional<Job> findByTitleIgnoreCase(String title);
}
