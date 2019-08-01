package com.academy.project.demo.repository;

import com.academy.project.demo.entity.Role;
import com.academy.project.demo.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName roleName);
}
