package com.nhk.thesis.repository;

import com.nhk.thesis.entity.User;
import com.nhk.thesis.entity.constant.UserRole;
import com.nhk.thesis.entity.constant.UserStatus;
import com.nhk.thesis.entity.constant.UserTitle;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    public User findUserByAccount(String account);

    public boolean existsByAccount(String account);

    public List<User> findUserByStatus(UserStatus status);

    User findUserByNameAndTitle(String name, UserTitle title);

    List<User> findByRoleContains(UserRole role);
}
