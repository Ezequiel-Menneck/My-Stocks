package com.minhasacoes.Service;

import com.minhasacoes.Entities.Roles;
import com.minhasacoes.Enums.RoleName;
import com.minhasacoes.Repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;

@Service
public class RolesService {

    @Autowired
    private RolesRepository rolesRepository;

    public Roles findByRoleName(RoleName roleName) {
        return rolesRepository.findByRoleName(roleName)
                .orElseThrow(() -> new ResolutionException("Role not found " + roleName));
    }


}
