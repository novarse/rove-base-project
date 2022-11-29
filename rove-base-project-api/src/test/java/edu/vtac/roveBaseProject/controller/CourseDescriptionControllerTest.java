package edu.vtac.roveBaseProject.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.vtac.roveBaseProject.AbstractCourseSearchTest;
import edu.vtac.roveBaseProject.dto.CourseSearchCriteriaDto;

public class CourseDescriptionControllerTest extends AbstractCourseSearchTest {

	@Autowired
	private CourseDescriptionController controller;

	@Test
	public void test() {
		CourseSearchCriteriaDto criteriaDto = new CourseSearchCriteriaDto();
		criteriaDto.setKeyword("arts");
		get("/api/course/description/coursenbr/22/9550151012");
		assertOk();
	}

}
