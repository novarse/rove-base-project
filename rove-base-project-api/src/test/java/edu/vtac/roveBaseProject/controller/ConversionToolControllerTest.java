package edu.vtac.roveBaseProject.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.vtac.roveBaseProject.AbstractCourseSearchTest;
import edu.vtac.roveBaseProject.dto.CourseSearchCriteriaDto;

public class ConversionToolControllerTest extends AbstractCourseSearchTest {

	@Autowired
	private ConversionToolController controller;
	
	@Test
	public void test() {
		CourseSearchCriteriaDto criteriaDto = new CourseSearchCriteriaDto();
		criteriaDto.setKeyword("arts");
		get("/api/conversion/tool/ACT/22389");
	}
	
	
	@Test
	public void test_scoreConversion() {
		CourseSearchCriteriaDto criteriaDto = new CourseSearchCriteriaDto();
		criteriaDto.setKeyword("arts");
		get("/api/conversion/tool/scoreconversions/ACT");
	}
}
