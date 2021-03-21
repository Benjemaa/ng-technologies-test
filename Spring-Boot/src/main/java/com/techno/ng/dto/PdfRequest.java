package com.techno.ng.dto;

import org.springframework.web.multipart.MultipartFile;

public class PdfRequest {

	private MultipartFile pdf;
	private MultipartFile image;
	private long x;
	private long y;
	
	
	public MultipartFile getPdf() {
		return pdf;
	}
	public void setPdf(MultipartFile pdf) {
		this.pdf = pdf;
	}
	public MultipartFile getImage() {
		return image;
	}
	public void setImage(MultipartFile image) {
		this.image = image;
	}
	public long getX() {
		return x;
	}
	public void setX(long x) {
		this.x = x;
	}
	public long getY() {
		return y;
	}
	public void setY(long y) {
		this.y = y;
	}
	
	
	
}
