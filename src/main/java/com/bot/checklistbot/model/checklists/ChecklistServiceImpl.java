package com.bot.checklistbot.model.checklists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Реализация {@link ChecklistService}
 */
@Service
public class ChecklistServiceImpl implements ChecklistService {
    private final ChecklistRepository repository;
    private final ChecklistItemRepository itemRepository;

    @Autowired
    public ChecklistServiceImpl(ChecklistRepository repository, ChecklistItemRepository itemRepository)
    {
        this.repository = repository;
        this.itemRepository = itemRepository;
    }

    @Override
    @Transactional
    public Checklist find(long id) {
        Optional<Checklist> result = repository.findById(id);
        return result.orElse(null);
    }

    @Override
    public void save(Checklist checklist) {
        repository.save(checklist);
    }

    @Override
    public void delete(Checklist checklist) {
        repository.delete(checklist);
    }

    @Override
    public void addItem(ChecklistItem checklistItem, Checklist checklist) {
        checklistItem.setChecklist(checklist);
        checklist.addItem(checklistItem);

        itemRepository.save(checklistItem);
        repository.save(checklist);
    }

    @Override
    public void deleteItem(ChecklistItem checklistItem, Checklist checklist) {
        checklist.deleteItem(checklistItem);

        itemRepository.delete(checklistItem);
        repository.save(checklist);
    }
}
