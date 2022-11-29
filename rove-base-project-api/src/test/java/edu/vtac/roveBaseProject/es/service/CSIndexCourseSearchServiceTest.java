package edu.vtac.roveBaseProject.es.service;



import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.vtac.roveBaseProject.AbstractCourseSearchTest;
import edu.vtac.roveBaseProject.data.CourseSearchFlatData;
import edu.vtac.roveBaseProject.dto.CourseSearchCriteriaDto;

public class CSIndexCourseSearchServiceTest  extends AbstractCourseSearchTest  {
	
	
	private static final Logger log = LoggerFactory.getLogger(CSIndexCourseSearchServiceTest.class);

	
	@Autowired
	private CSIndexCourseSearchService indexCourseSearchService;
	
	@Test
	public void testMe() {
		ArrayList<CourseSearchFlatData> list = new ArrayList<>();
		CourseSearchCriteriaDto criteria = new CourseSearchCriteriaDto();
		criteria.setApplicationPeriod(19);
		criteria.setKeyword("Diploma");
		criteria.setInstitutionCodes(new HashSet<>());
		criteria.setAscedCodes(new HashSet<>());
		criteria.setApplyMethodCodes(new HashSet<>());
		criteria.setStudentTypeCodes(new HashSet<>());
		criteria.setStudyModeCodes(new HashSet<>());
		criteria.setGroupId(null);
		criteria.setQualificationLevelCodes(new HashSet<>());
		boolean result = indexCourseSearchService.findCourse(criteria, list);
		log.debug("########## "  + result);
		list.forEach(e -> {
			if (e.getActive() == false) {
				fail("not active");
			}
			//System.out.println(e.getAscedCode1() + " " + e.getAscedCode2());
		});
	}
	
//
//	@Test
//	public void test_findCourse() throws InterruptedException {
//		ArrayList<CourseSearchFlatData> list = new ArrayList<>();
//		CourseSearchCriteriaDto criteria = new CourseSearchCriteriaDto();
//		criteria.setApplicationPeriod(22);
//		criteria.setKeyword("9150210012");
//		criteria.setInstitutionCodes(new HashSet<>());
//		criteria.setAscedCodes(new HashSet<>());
//		criteria.setApplyMethodCodes(new HashSet<>());
//		criteria.setStudentTypeCodes(new HashSet<>());
//		criteria.setStudyModeCodes(new HashSet<>());
//		
//		boolean result = false ;
//		while(!result) {
//			result = indexCourseSearchService.findCourse(criteria, list);
//			log.debug("########## "  + result);
//			if (result) {
//				list.forEach(e -> {
//					if (e.getActive() == false) {
//						fail("not active");
//					}
//					//System.out.println(e.getAscedCode1() + " " + e.getAscedCode2());
//				});
//			} else {
//				Thread.currentThread().sleep(5000);
//			}
//		}
//	}

}
