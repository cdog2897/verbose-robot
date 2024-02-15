package com.gcu.registrationlogin.repository;

import com.gcu.registrationlogin.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}