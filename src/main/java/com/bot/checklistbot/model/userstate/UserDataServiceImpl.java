package com.bot.checklistbot.model.userstate;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bot.checklistbot.model.checklists.Checklist;
import com.bot.checklistbot.model.checklists.ChecklistService;

/**
 * Реализация {@link UserDataService}
 */
@Service
public class UserDataServiceImpl implements UserDataService
{
    private final UserDataRepository repository;
    private final ChecklistService checklistService;

    @Autowired
    public UserDataServiceImpl(UserDataRepository repository, ChecklistService checklistService)
    {
        this.repository = repository;
        this.checklistService = checklistService;
    }

    @Override
    @Transactional
    public UserData findOrSave(long id)
    {
        Optional<UserData> result = repository.findById(id);
        return result.orElseGet(() -> repository.save(new UserData(id)));
    }

    @Override
    public void update(UserData registration)
    {
        repository.save(registration);
    }

    @Override
    public Checklist addChecklist(UserData userData, Checklist checklist)
    {
        userData.addChecklists(checklist);
        checklist.setOwner(userData);

        repository.save(userData);
        return checklistService.save(checklist);
    }

    @Override
    public void deleteChecklist(UserData userData, Checklist checklist)
    {
        userData.deleteChecklist(checklist);

        repository.save(userData);
        checklistService.delete(checklist);
    }
}
