package edu.vtac.roveBaseProject.service;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.vtac.roveBaseProject.AbstractCourseSearchTest;
import edu.vtac.roveBaseProject.dto.CourseSearchCriteriaDto;
import edu.vtac.roveBaseProject.dto.searchdata.SearchDataCourseDto;
import edu.vtac.roveBaseProject.dto.searchdata.SearchDataDto;

public class CourseSearchServiceTest extends AbstractCourseSearchTest {

	
	private static final Logger log = LoggerFactory.getLogger(CourseSearchServiceTest.class);

	
	@Autowired
	private CourseSearchService courseSearchService;
	
	@Test
	public void test() throws Exception {
		CourseSearchCriteriaDto criteria = new CourseSearchCriteriaDto();
		criteria.setKeyword("Arts");
		criteria.setApplicationPeriod(23);
		criteria.setQualificationLevelCodes(new HashSet<>());
		
		Collection<SearchDataDto> list = courseSearchService.findCourseSearchData(criteria);
		log.debug("########");
		list.stream().forEach(e -> {
			String pad = "--";
//			if (e.isGroup()) {
//				log.debug(e.getGroupName());
//				pad = "--";
//			}
//			for (SearchDataCourseDto dto : e.getCourses()) {
//				log.debug(pad + "" + dto.getName() + " - " + dto.getInstName());
//			}
			
			for (SearchDataCourseDto dto : e.getCourses()) {
				log.debug( pad + "-" + dto.getCourseId() + "-" + dto.getCourseNbrs().size());
			}
			
		});
		
	}
	
}
