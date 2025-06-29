package com.mygym.crm.backstages.core.dtos.response.trainerdto.select;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class TrainerCredentials {

    @NonNull
    private String userName;

    @NonNull
    private String password;

}
