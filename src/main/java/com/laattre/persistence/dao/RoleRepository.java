package com.laattre.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.laattre.persistence.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    @Override
    void delete(Role role);

}
