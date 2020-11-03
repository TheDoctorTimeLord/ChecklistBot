package com.bot.checklistbot.model.userstate;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Реализация {@link UserService}
 */
@Service
public class UserServiceImpl implements UserService
{
    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository)
    {
        this.repository = repository;
    }

    @Override
    @Transactional
    public User findOrSave(long id)
    {
        Optional<User> result = repository.findById(id);
        return result.orElseGet(() -> repository.save(new User(id)));
    }

    @Override
    public void update(User registration)
    {
        repository.save(registration);
    }
}
