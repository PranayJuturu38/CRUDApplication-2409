package com.crudapp.main.service;

import java.io.IOException;

import java.util.stream.Stream;

import com.crudapp.main.model.FileData;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileService {

    FileData store(MultipartFile file) throws IOException;

    FileData getFile(String id);

    Stream<FileData> getAllFile();

    FileData getByName(String name);
}
