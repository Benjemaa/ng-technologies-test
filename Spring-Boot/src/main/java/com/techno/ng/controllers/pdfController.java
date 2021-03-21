package com.techno.ng.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.techno.ng.dto.Response;
import com.techno.ng.services.FilesStorageService;

@RestController
@RequestMapping("/pdf")
@CrossOrigin("*")
public class pdfController {

	@Autowired
	FilesStorageService filesStorageService;

	@PostMapping("/image")
	public ResponseEntity<Response> uploadImage(@RequestParam("image") MultipartFile file,@RequestParam("pdf") MultipartFile pdf,
			@RequestParam("page") String page,@RequestParam("xCoordinate") String xCoordinate,@RequestParam("yCoordinate") String yCoordinate) {
		String message = "";

		try {
			File res = filesStorageService.mergeImageAndPdf(file,pdf,Integer.parseInt(page),Integer.parseInt(xCoordinate),Integer.parseInt(yCoordinate));
			message = "Uploaded the file successfully: " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.OK).body(new Response(message,Base64.getEncoder().encodeToString(new FileInputStream(res).readAllBytes())));
		} catch (Exception e) {
			e.printStackTrace();
			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Response(message));

		}
	}

}
