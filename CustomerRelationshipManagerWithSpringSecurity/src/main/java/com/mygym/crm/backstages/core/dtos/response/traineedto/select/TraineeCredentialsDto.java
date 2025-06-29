package com.mygym.crm.backstages.core.dtos.response.traineedto.select;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TraineeCredentialsDto {

    @NonNull
    private String userName;

    @NonNull
    private String password;

}
