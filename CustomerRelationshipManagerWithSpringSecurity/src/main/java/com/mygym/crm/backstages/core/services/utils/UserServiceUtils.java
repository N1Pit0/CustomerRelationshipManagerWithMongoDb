package com.mygym.crm.backstages.core.services.utils;

import com.mygym.crm.backstages.core.dtos.request.common.UserDto;

public interface UserServiceUtils {

    <T> void validateDto(T Dto);

    String generateUserName(UserDto userDto);

    String generatePassword();

    String encodePassword(String plainTextPassword);

    String getPassword();
}
