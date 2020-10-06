package com.k1ts.dao;

import com.k1ts.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername( String username );
}
