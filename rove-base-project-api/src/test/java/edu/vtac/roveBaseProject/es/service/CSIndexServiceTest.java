package edu.vtac.roveBaseProject.es.service;

import static org.junit.Assert.fail;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.vtac.roveBaseProject.AbstractCourseSearchTest;

public class CSIndexServiceTest extends AbstractCourseSearchTest {

	@Autowired
	private CSIndexService csIndexService;
	
	@Autowired
	private CSIndexCourseSearchService indexCourseSearchService;	
	
	
	@Test
	public void test_indexCourseData() throws InterruptedException, ExecutionException {
		Future<Boolean> future = csIndexService.indexCourseData(19);
		Boolean boolean1 = future.get();
		System.out.println(boolean1 + "");
		int i = 0;
	}
//
//	
//	@Test
//	public void test_refreshDocuments() throws InterruptedException, ExecutionException {
//		Future<Boolean> future = csIndexService.refreshDocuments(23);
//		Boolean boolean1 = future.get();
//		System.out.println(boolean1 + "");
//		int i = 0;
//	}
	
	
//	@Test
//	public void test_refreshIndexByCourseNbrd() throws Exception {
//		List<CourseSearchFlatData> list = csIndexService.refreshDocumentByCourseNbr(23, 8140110132L);
//		list = list;
//	}
//
//	@Test
//	public void test_deleteDocumentByCourseNbr() throws Exception {
//		csIndexService.deleteDocumentByCourseNbr(23, 8140110132L);
//	}
//
//	
//	@Test
//	public void test_refreshIndexByCourseId() throws Exception {
//		List<CourseSearchFlatData> list = csIndexService.refreshDocumentByCourseId(23, 8141013);
//		list = list;
//	}
//
//	@Test
//	public void test_deleteDocumentByCourseId() throws Exception {
//		long l = csIndexService.deleteDocumentByCourseId(23, 8141013);
//	}
//
//	
//	@Test
//	public void test_getIndexStatus() {
//		CSIndexStatus status = csIndexService.getIndexStatus(23);
//		
//	}
	
//	@Test
//	public void test_refreshCourseDataSchedular() {
//		ArrayList<CourseSearchFlatData> list = new ArrayList<>();
//		CourseSearchCriteriaDto criteria = new CourseSearchCriteriaDto();
//		criteria.setApplicationPeriod(23);
//		criteria.setKeyword("blah");
//		criteria.setInstitutionCodes(new HashSet<>());
//		criteria.setAscedCodes(new HashSet<>());
//		criteria.setApplyMethodCodes(new HashSet<>());
//		criteria.setStudentTypeCodes(new HashSet<>());
//		criteria.setStudyModeCodes(new HashSet<>());
//		criteria.setGroupId(null);
//		criteria.setQualificationLevelCodes(new HashSet<>());
//		boolean result = indexCourseSearchService.findCourse(criteria, list);
//		list.forEach(e -> {
//			if (e.getActive() == false) {
//				fail("not active");
//			}
//			//System.out.println(e.getAscedCode1() + " " + e.getAscedCode2());
//		});		
//		csIndexService.refreshCourseDataSchedular();
//		int i = 0;
//	}

}
