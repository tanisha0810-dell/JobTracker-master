package dao;

import model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;



@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    // You can add custom query methods here if needed, e.g.,
    // List<Job> findByCompany(String company);
    List<Job> findByTitleIgnoreCase(String title);
}
