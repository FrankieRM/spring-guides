package com.example.uploadingfiles.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseErrorBody {

    private int status;
    private String error;
    private String message;
}