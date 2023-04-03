package com.minhasacoes.Repository;

import com.minhasacoes.Entities.Roles;
import com.minhasacoes.Enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles, Integer> {

    Optional<Roles> findByRoleName(RoleName roleName);

}
