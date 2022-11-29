package edu.vtac.roveBaseProject.service;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.vtac.roveBaseProject.AbstractCourseSearchTest;
import edu.vtac.roveBaseProject.dto.MailShortlistCriteriaDto;

public class CourseDescriptionServiceTest extends AbstractCourseSearchTest {

	@Autowired
	private CourseDescriptionService courseDescriptionService;
	
	@Test
	public void test() throws Exception {
		MailShortlistCriteriaDto request = new MailShortlistCriteriaDto();
		request.setApplicationPeriod(19);
		request.setContent("1");
		request.setShortListedCourses(Arrays.asList(9555101));
		courseDescriptionService.sendShortListToEMail(request );
	
	}
	
}
