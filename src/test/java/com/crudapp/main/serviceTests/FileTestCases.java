package com.crudapp.main.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.crudapp.main.exception.CustomException;
import com.crudapp.main.model.FileData;
import com.crudapp.main.repository.FilesDatarepository;
import com.crudapp.main.service.FileService;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FileTestCases {

	@Autowired
	private FileService fileService;

	@Autowired
	private FilesDatarepository fileRepo;

	@Mock
	FilesDatarepository mockFileRepo;

	// Saving File happy testcase
	@Test
	public void saveFile() throws IOException {

		Path path = Paths.get("C:/Users/Dev/Documents/Kpi Stuff/test.txt");
		byte[] data = Files.readAllBytes(path);

		FileData fd = new FileData("test.txt", "text/plain", data);

		FileData fd1 = fileRepo.save(fd);
		assertEquals("text/plain", fd1.getType());

	}

	// Getting the file happy test
	// @Test
	// public void getFileTest() throws IOException {
	// Path path = Paths.get("C:/Users/Dev/Documents/Kpi Stuff/test.txt");
	// byte[] data = Files.readAllBytes(path);

	// when(mock.getFile("C:/Users/Dev/Documents/Kpi Stuff/test.txt"))
	// .thenReturn(new FileData("test.txt", "text/plain", data));

	// FileData file = mock.getFile("C:/Users/Dev/Documents/Kpi Stuff/test.txt");
	// assertEquals("text/plain", file.getType());
	// }

	@Test // Getting a file by name //mockito
	public void getFileNameTest() throws IOException {

		Path path = Paths.get("C:/Users/Dev/Documents/Kpi Stuff/test.txt");
		byte[] data = Files.readAllBytes(path);

		FileData file1 = new FileData("test.txt", "text/plain", data);

		when(mockFileRepo.getByName("test.txt")).thenReturn(file1);

		fileRepo.save(file1);
		assertEquals(fileService.getByName("test.txt").getName(), file1.getName());
	}
}
