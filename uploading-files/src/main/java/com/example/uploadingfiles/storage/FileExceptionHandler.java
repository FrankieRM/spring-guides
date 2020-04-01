package com.example.uploadingfiles.storage;

import com.example.uploadingfiles.mapper.ResponseErrorBodyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class FileExceptionHandler {

    @Autowired
    private ResponseErrorBodyMapper responseErrorBodyMapper;

    @ExceptionHandler
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(MaxUploadSizeExceededException e) {
        return ResponseEntity.badRequest().body(responseErrorBodyMapper.map(e.getMessage()));
    }
}