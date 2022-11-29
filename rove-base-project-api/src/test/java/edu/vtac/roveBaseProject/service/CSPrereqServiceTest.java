package edu.vtac.roveBaseProject.service;

import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.vtac.roveBaseProject.AbstractCourseSearchTest;
import edu.vtac.roveBaseProject.data.prereq.CSPrereqWrapper;

public class CSPrereqServiceTest extends AbstractCourseSearchTest {

	@Autowired
	private CSPrereqService csPrereqService;
	
	
	@Test
	public void testMe() {
		Map<Integer, CSPrereqWrapper> prerequisite = csPrereqService.getPrerequisite(2020);
	}
}
