package com.cancelo.identityservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cancelo.identityservice.dto.request.RoleRequest;
import com.cancelo.identityservice.dto.response.RoleResponse;
import com.cancelo.identityservice.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
