package com.cancelo.identityservice.mapper;

import org.mapstruct.Mapper;

import com.cancelo.identityservice.dto.request.PermissionRequest;
import com.cancelo.identityservice.dto.response.PermissionResponse;
import com.cancelo.identityservice.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
