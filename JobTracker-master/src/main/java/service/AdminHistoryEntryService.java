package service;

import dao.AdminHistoryEntryRepository;
import model.AdminHistoryEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminHistoryEntryService {

    @Autowired
    private AdminHistoryEntryRepository repository;

    // Save or update an AdminHistoryEntry
    public AdminHistoryEntry saveEntry(AdminHistoryEntry entry) {
        return repository.save(entry);
    }

    // Find entries by userName
    public List<AdminHistoryEntry> findByUserName(String userName) {
        return repository.findByUserName(userName);
    }

    // Find entries by companyName
    public List<AdminHistoryEntry> findByCompanyName(String companyName) {
        return repository.findByCompanyName(companyName);
    }

    // Find entries with status containing keyword (case insensitive)
    public List<AdminHistoryEntry> findByStatusKeyword(String keyword) {
        return repository.findByStatusContainingIgnoreCase(keyword);
    }

    // Delete an entry by ID
    public void deleteEntry(Long id) {
        repository.deleteById(id);
    }
}
