package edu.vtac.roveBaseProject.es.service;

import java.util.Collection;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.vtac.roveBaseProject.AbstractCourseSearchTest;

public class CSIndexSuggestServiceTest  extends AbstractCourseSearchTest {

	
	private static final Logger log = LoggerFactory.getLogger(CSIndexSuggestServiceTest.class);

	
	@Autowired
	private CSIndexSuggestService csIndexSuggestService;
	
	@Autowired
	private CSIndexService csIndexService;
	
	@Test
	public void testMe() {
		csIndexService.isReadyToSearch(19);
		Collection<String> suggest = csIndexSuggestService.suggest("Information", 19);
		suggest.stream().forEach(e ->  log.debug("---- " + e));
	}
	
}
