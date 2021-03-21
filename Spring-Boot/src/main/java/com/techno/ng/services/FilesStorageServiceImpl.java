package com.techno.ng.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FilesStorageServiceImpl implements FilesStorageService {

	private final Path root = Paths.get("uploads");

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(root.toFile());
	}

	@Override
	public void init() {
		try {
			Files.createDirectory(root);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize folder for upload!");
		}
	}

	@Override
	public void save(MultipartFile file) {
		try {
			Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
	}

	@Override
	public File mergeImageAndPdf(MultipartFile file, MultipartFile pdf, int page, int xCoordinate, int yCoordinate)
			throws Exception {
		save(file);
		save(pdf);
		System.out.println(page);
		System.out.println(xCoordinate);
		System.out.println(yCoordinate);

		File pdfFile = new File(this.root + "/_NEW_" + pdf.getOriginalFilename());
		try (OutputStream os = new FileOutputStream(pdfFile)) {
			os.write(pdf.getBytes());
		}
//		PDDocument doc= PDDocument.load(pdfFile);

		PDDocument doc = PDDocument.load(pdf.getBytes());
		if (doc.getNumberOfPages() < page || page == 0)
			page = 1;
		PDPage pagePdf = doc.getPage(page-1);
		PDPageContentStream contents = new PDPageContentStream(doc, pagePdf);
		contents.drawImage(PDImageXObject.createFromFile(this.root.resolve(file.getOriginalFilename()).toString(), doc),
				xCoordinate, yCoordinate,500,500);
		contents.close();
		doc.save(this.root + "/_NEW_" + pdf.getOriginalFilename());
		doc.close();
		return pdfFile;
	}
}
