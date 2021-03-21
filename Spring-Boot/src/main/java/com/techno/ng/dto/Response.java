package com.techno.ng.dto;


import org.springframework.web.multipart.MultipartFile;

public class Response {

	private String message;
	private String pdf;
	
	public Response() {
	}
	
	public Response(String message) {
		this.message = message;
	}
	public Response(String message,String res) {
		this.message = message;
		this.pdf = res;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getPdf() {
		return pdf;
	}

	public void setPdf(String pdf) {
		this.pdf = pdf;
	}

	
}
