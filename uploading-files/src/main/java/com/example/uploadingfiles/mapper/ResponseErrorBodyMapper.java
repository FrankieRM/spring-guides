package com.example.uploadingfiles.mapper;

import com.example.uploadingfiles.response.ResponseErrorBody;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ResponseErrorBodyMapper {

    public ResponseErrorBody map(String message) {
        ResponseErrorBody responseErrorBody = new ResponseErrorBody();
        responseErrorBody.setMessage(message);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        responseErrorBody.setStatus(httpStatus.value());
        responseErrorBody.setError(httpStatus.getReasonPhrase());
        return responseErrorBody;
    }
}