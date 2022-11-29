package edu.vtac.roveBaseProject.util;

import org.springframework.http.HttpStatus;

public class CSBadRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String message = "System error occured. Please contact administrator";
	private HttpStatus status = HttpStatus.BAD_REQUEST;

	public CSBadRequestException() {
		super();
	}

	public CSBadRequestException(String message) {
		super();
		this.message = message;
		this.status = HttpStatus.BAD_REQUEST;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

}
