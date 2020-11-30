package com.bot.checklistbot.model.checklists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Реализация {@link ChecklistItemService}
 */
@Service
public class ChecklistItemServiceImpl implements ChecklistItemService {
    private final ChecklistItemRepository repository;

    @Autowired
    public ChecklistItemServiceImpl(ChecklistItemRepository repository)
    {
        this.repository = repository;
    }

    @Override
    @Transactional
    public ChecklistItem find(long id) {
        Optional<ChecklistItem> result = repository.findById(id);
        return result.orElse(null);
    }

    @Override
    @Transactional
    public ChecklistItem findByCapture(String capture) {
        return repository.findByCapture(capture);
    }

    @Override
    @Transactional
    public void save(ChecklistItem checklistItem) {
        repository.save(checklistItem);
    }

    @Override
    @Transactional
    public void delete(ChecklistItem checklistItem) {
        repository.delete(checklistItem);
    }

    @Override
    @Transactional
    public void deleteFrom(Checklist checklist) {
        repository.deleteAll(checklist.getItems());
    }

    @Override
    public Iterable<ChecklistItem> getAll() {
        return repository.findAll();
    }
}
