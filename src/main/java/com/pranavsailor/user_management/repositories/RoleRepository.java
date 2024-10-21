package com.pranavsailor.user_management.repositories;

import com.pranavsailor.user_management.entities.Role;
import com.pranavsailor.user_management.entities.RoleEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByName(RoleEnum name);
}
