package com.bot.checklistbot.model.checklists;

import java.util.Optional;

import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Реализация {@link ChecklistService}
 */
@Service
public class ChecklistServiceImpl implements ChecklistService {
    private final ChecklistRepository repository;
    private final ChecklistItemService itemService;

    @Autowired
    public ChecklistServiceImpl(ChecklistRepository repository, ChecklistItemService itemService)
    {
        this.repository = repository;
        this.itemService = itemService;
    }

    @Override
    @Transactional
    public Checklist find(long id) {
        Optional<Checklist> result = repository.findById(id);
        return result.orElse(null);
    }

    @Override
    @Nullable
    @Transactional
    public Checklist findByTitle(String title) {
        try
        {
            return repository.findByTitle(title);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Transactional
    public Checklist save(Checklist checklist) {
        return repository.save(checklist);
    }

    @Override
    @Transactional
    public void delete(Checklist checklist) {
        itemService.deleteFrom(checklist);
        repository.delete(checklist);
    }

    @Override
    @Transactional
    public void addItem(ChecklistItem checklistItem, Checklist checklist) {
        checklistItem.setChecklist(checklist);
        checklist.addItem(checklistItem);

        itemService.save(checklistItem);
        repository.save(checklist);
    }

    @Override
    public void deleteItem(ChecklistItem checklistItem, Checklist checklist) {
        checklist.deleteItem(checklistItem);

        itemService.delete(checklistItem);
        repository.save(checklist);
    }
}
