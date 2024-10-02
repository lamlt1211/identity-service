package com.cancelo.identityservice.mapper;


import com.cancelo.identityservice.dto.request.RoleRequest;
import com.cancelo.identityservice.dto.response.RoleResponse;
import com.cancelo.identityservice.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);


}
