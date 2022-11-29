package edu.vtac.roveBaseProject.service;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.vtac.roveBaseProject.AbstractCourseSearchTest;
import edu.vtac.roveBaseProject.dto.CourseExplorerCriteriaDto;
import edu.vtac.roveBaseProject.dto.searchdata.SearchDataDto;

public class PrereqCheckServiceTest  extends AbstractCourseSearchTest {

	
	private static final Logger log = LoggerFactory.getLogger(PrereqCheckServiceTest.class);

	
	@Autowired
	private PrereqCheckService prereqCheckService;
	
	@Test
	public void testme() throws Exception {
		CourseExplorerCriteriaDto criteria = new CourseExplorerCriteriaDto();
		criteria.setExcludeCoursesWithoutPrereq(false);
		criteria.setYear(2021);
		criteria.setIsSingleSubjectMatch(false);
		criteria.setIncludeUnit12Subjects(true);
		criteria.setSubjectCodeList(Arrays.asList("AG25", "MA16", "NF"));
		Collection<SearchDataDto> data = prereqCheckService.findCourseSearchData(criteria);
		log.debug("############### " + data.size());
	}
	
	
}
