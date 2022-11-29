package edu.vtac.roveBaseProject.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import edu.vtac.roveBaseProject.service.ErrorReporterService;
import edu.vtac.roveBaseProject.service.ErrorReporterServiceImpl.ErrorReporterData;
import edu.vtac.roveBaseProject.util.CSBadRequestException;

@ControllerAdvice
class CSDefaultExceptionHandler implements HandlerExceptionResolver {

	private static final Logger log = LoggerFactory.getLogger(CSDefaultExceptionHandler.class);

	@Autowired
	private ErrorReporterService errorReporterService;

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		if (!request.getServerName().equalsIgnoreCase("localhost") || !request.getLocalAddr().equals("127.0.0.1")) {
			ErrorReporterData data = errorReporterService.createData(request, ex);
			errorReporterService.send(data);
		}
		return null;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleContraintViolationException(ConstraintViolationException e) {
		List<ErrorMessage> list = e.getConstraintViolations().stream().map(ee -> new ErrorMessage(ee))
				.collect(Collectors.toList());
		return ResponseEntity.badRequest().body(list);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(CSBadRequestException.class)
	public ResponseEntity<Object> handleCSException(CSBadRequestException e) {
		List<ErrorMessage> list = Arrays.asList(new ErrorMessage(e.getMessage()));
		return ResponseEntity.badRequest().body(list);
	}	
	
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		BindingResult result = e.getBindingResult();
		List<org.springframework.validation.FieldError> fieldErrors = result.getFieldErrors();
		List<ErrorMessage> list = new ArrayList<>();
		for (org.springframework.validation.FieldError fieldError: fieldErrors) {
			list.add(new ErrorMessage(fieldError));
		}
		return ResponseEntity.badRequest().body(list);
	}		
	
	public class ErrorMessage {

		private Object invalidValue;

		private String message;

		private String field;

		public ErrorMessage(String message) {
			this.message = message;
			invalidValue = null;

		}

		

		public ErrorMessage(FieldError e) {
			message = e.getDefaultMessage();
			invalidValue = e.getRejectedValue();
			field = e.getField();

		}

		
		public ErrorMessage(ConstraintViolation e) {
			message = e.getMessage();
			invalidValue = e.getInvalidValue();
			field = ((PathImpl) e.getPropertyPath()).getLeafNode().getName();
		}

		public Object getInvalidValue() {
			return invalidValue;
		}

		public void setInvalidValue(Object invalidValue) {
			this.invalidValue = invalidValue;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}



		public String getField() {
			return field;
		}



		public void setField(String field) {
			this.field = field;
		}
	}
}