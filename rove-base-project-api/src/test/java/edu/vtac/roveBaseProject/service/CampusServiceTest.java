package edu.vtac.roveBaseProject.service;

import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.vtac.roveBaseProject.AbstractCourseSearchTest;

public class CampusServiceTest extends AbstractCourseSearchTest  {

	@Autowired
	private CampusService campusService;
	
	@Test
	public void test() {
		Map<Integer, Set<String>> map = campusService.getMapOfCampuseNameUsingCourseId(24);
	}
}
