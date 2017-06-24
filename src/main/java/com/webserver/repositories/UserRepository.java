package com.webserver.repositories;

import com.webserver.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {

    User findById(int id);

    User findByUsernameAndPassword(String username, String password);

}
