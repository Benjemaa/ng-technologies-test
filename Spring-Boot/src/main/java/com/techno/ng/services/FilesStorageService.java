package com.techno.ng.services;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {

	public void deleteAll();
	
	public void init();

	public void save(MultipartFile file);

	public File mergeImageAndPdf(MultipartFile file, MultipartFile pdf, int page, int xCoordinate,
			int yCoordinate)throws Exception;
}
