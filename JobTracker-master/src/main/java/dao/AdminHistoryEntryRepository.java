package dao;

import model.AdminHistoryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminHistoryEntryRepository extends JpaRepository<AdminHistoryEntry, Long> {

    List<AdminHistoryEntry> findByUserName(String userName);

    List<AdminHistoryEntry> findByCompanyName(String companyName);

    @Query("SELECT a FROM AdminHistoryEntry a WHERE LOWER(a.status) LIKE LOWER(CONCAT('%', :statusKeyword, '%'))")
    List<AdminHistoryEntry> findByStatusContainingIgnoreCase(@Param("statusKeyword") String statusKeyword);
}
