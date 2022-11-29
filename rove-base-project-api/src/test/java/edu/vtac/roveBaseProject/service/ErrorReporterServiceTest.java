package edu.vtac.roveBaseProject.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import edu.vtac.roveBaseProject.AbstractCourseSearchTest;
import edu.vtac.roveBaseProject.dto.CourseSearchCriteriaDto;

public class ErrorReporterServiceTest extends AbstractCourseSearchTest {

	@Mock
	private org.springframework.mail.javamail.JavaMailSender javaMailSender;
	
	@Autowired
	private ErrorReporterService errorReporterService;

	
	@Before
	public void init() {
		inject(errorReporterService, "javaMailSender", javaMailSender);
	}
	
	@Test
	public void test_sendExceptionWithObject() throws InterruptedException, ExecutionException {
		Future<Boolean> send = errorReporterService.send("", new RuntimeException(), new CourseSearchCriteriaDto());
		Boolean boolean1 = send.get();
		boolean1 = false;
	}
}
