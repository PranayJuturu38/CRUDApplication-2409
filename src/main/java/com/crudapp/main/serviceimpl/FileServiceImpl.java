package com.crudapp.main.serviceimpl;

import java.io.IOException;

import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import com.crudapp.main.exception.CustomException;
import com.crudapp.main.model.FileData;
import com.crudapp.main.repository.FilesDatarepository;
import com.crudapp.main.service.FileService;

@Service
@Transactional
public class FileServiceImpl implements FileService {
    @Autowired
    private FilesDatarepository filerepo;

    @Override
    public FileData store(MultipartFile file) throws IOException {
        // Cleaning the path
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        // Storing the file details
        FileData FileData = new FileData(fileName, file.getContentType(), file.getBytes());
        return filerepo.save(FileData);
    }

    @Override
    public FileData getFile(String id) {
        return filerepo.findById(id).get();
    }

    @Override
    public Stream<FileData> getAllFile() {
        return filerepo.findAll().stream();
    }

    @Override
    public FileData getByName(String name) throws CustomException {
        FileData file = filerepo.getByName(name);
        if (file != null) {
            return file;
        } else {
            throw new CustomException("File not found with name:" + name);
        }
    }

}