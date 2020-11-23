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
    @Transactional
    public void update(UserData userData)
    {
        repository.save(userData);
    }

    @Override
    @Transactional
    public void addChecklist(UserData userData, Checklist checklist)
    {
        userData.addChecklists(checklist);
        checklist.setOwner(userData);

        repository.save(userData);
        checklistService.save(checklist);
    }

    @Override
    @Transactional
    public void deleteChecklist(UserData userData, Checklist checklist)
    {
        userData.deleteChecklist(checklist);

        repository.save(userData);
        checklistService.delete(checklist);
    }

    @Override
    @Transactional
    public void changeUserState(UserData userData, UserState userState)
    {
        userData.setState(userState);
        repository.save(userData);
    }
}
