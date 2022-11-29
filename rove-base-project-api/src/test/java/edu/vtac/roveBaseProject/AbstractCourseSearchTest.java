package edu.vtac.roveBaseProject;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

@SpringBootTest(classes = { CourseSearchApplication.class, TestHibernateJpaConfiguration.class })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles(profiles = "unittest")
public abstract class AbstractCourseSearchTest {

	private static final Logger log = LoggerFactory.getLogger(AbstractCourseSearchTest.class);

	@Autowired
	protected WebApplicationContext context;

	protected MockMvc mvc;

	protected MockHttpServletResponse response;

	private HttpSession session;

	private Map<Object, Map<String, Object>> actualBeans = new HashMap<>();

	@Before
	public void before() {
		response = null;
		if (session != null) {
			session.invalidate();
			session = null;
		}
	}

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	protected MockHttpServletResponse get(String url) {
		MvcResult result = null;
		try {
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(url);

			if (session != null) {
				builder.session((MockHttpSession) session);
			}
			builder.with(SecurityMockMvcRequestPostProcessors.csrf());
			result = mvc.perform(builder).andDo(MockMvcResultHandlers.print()).andReturn();
		} catch (Exception e) {
			fail("Error while making http request " + url);
		}
		response = result.getResponse();
		session = result.getRequest().getSession();
		return response;
	}

	protected MockHttpServletResponse post(String url, Object postBody) {
		MvcResult result = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
			String requestJson = ow.writeValueAsString(postBody);
			log.debug("POSTING " + requestJson);
			MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post(url);
			if (session != null) {
				post.session((MockHttpSession) session);
			}
			post.with(SecurityMockMvcRequestPostProcessors.csrf());
			result = mvc.perform(post.contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
					.andDo(MockMvcResultHandlers.print()).andReturn();
		} catch (Exception e) {
			fail("Error while making http request " + url);
		}
		response = result.getResponse();
		session = result.getRequest().getSession();
		return response;
	}

	protected void assertOk() {
		assertThat(response.getStatus(), is(200));
	}

	protected void assertRedirectedToLogin() {
		assertThat(response.getStatus(), is(303));
		assertThat(response.getHeader("location"), endsWith("/"));
	}

	protected <T> T transferTo(Class<T> class1) {
		try {
			String jsonString = response.getContentAsString();
			ObjectMapper mapper = new ObjectMapper();
			ObjectReader reader = mapper.readerFor(class1);
			return reader.readValue(jsonString);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	protected void invalidateSession() {
		if (session != null) {
			session.invalidate();
		}

	}

	@After
	public void tearDown() {
		int i = 0;
		for (Entry<Object, Map<String, Object>> entry : actualBeans.entrySet()) {
			Object targetClass = entry.getKey();
			Map<String, Object> actuals = entry.getValue();
			for (Entry<String, Object> entry2 : actuals.entrySet()) {
				ReflectionTestUtils.setField(targetClass, entry2.getKey(), entry2.getValue());
				i++;
			}
		}
		log.debug("beans are set with actual during @After " + i);
	}

	protected <T> T actual(String beanName) {
		int i = 0;
		T returnObj = null;
		for (Entry<Object, Map<String, Object>> entry : actualBeans.entrySet()) {
			Object targetClass = entry.getKey();
			Map<String, Object> actuals = entry.getValue();
			for (Entry<String, Object> entry2 : actuals.entrySet()) {
				if (entry2.getKey().equals(beanName)) {
					i++;
					ReflectionTestUtils.setField(targetClass, entry2.getKey(), entry2.getValue());
					returnObj = (T) entry2.getValue();

				}
			}
		}
		log.debug("beans are set with actual  " + i);
		return returnObj;
	}

	protected void inject(Object targetClass, String name, Object value) {
		Map<String, Object> map = actualBeans.get(targetClass);
		if (map == null) {
			map = new HashMap<>();
			actualBeans.put(targetClass, map);
		}
		Object field = ReflectionTestUtils.getField(targetClass, name);
		map.put(name, field);
		ReflectionTestUtils.setField(targetClass, name, value);
		log.debug("bean " + name + " is injected with mock in " + targetClass.getClass().getName());
	}

	protected void reflectSet(Object targetClass, String name, Object value) {
		Object field = ReflectionTestUtils.getField(targetClass, name);
		ReflectionTestUtils.setField(targetClass, name, value);
		log.debug("field " + name + " is set with " + value);
	}

	protected <T extends Object> T fillObject(T obj) {
		try {
			Class<? extends Object> clazz = obj.getClass();
			Field[] fields = clazz.getDeclaredFields();
			RandomString session = new RandomString();
			for (Field field : fields) {
				if (field.getType().getCanonicalName().startsWith("java.lang")
						|| field.getType().getCanonicalName().startsWith("java.sql")
						|| field.getType().getCanonicalName().startsWith("java.util")) {
					field.setAccessible(true);
					if (field.getType().isAssignableFrom(Long.class)) {
						field.set(obj, ThreadLocalRandom.current().nextLong(100));
					} else if (field.getType().isAssignableFrom(Integer.class)) {
						field.set(obj, ThreadLocalRandom.current().nextInt(100));
					} else if (field.getType().isAssignableFrom(Double.class)) {
						field.set(obj, ThreadLocalRandom.current().nextDouble(100));
					} else if (field.getType().isAssignableFrom(Boolean.class)) {
						field.set(obj, false);
					} else if (field.getType().isAssignableFrom(Date.class)
							|| field.getType().isAssignableFrom(java.sql.Date.class)
							|| field.getType().isAssignableFrom(Time.class)
							|| field.getType().isAssignableFrom(Timestamp.class)) {
						field.set(obj, new Date());
					} else if (field.getType().isAssignableFrom(List.class)) {
						field.set(obj, new ArrayList<>());
					} else {

						field.set(obj, session.nextString());
					}
				}

			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return (T) obj;
	}

	public <T> CSSArgumentCaptor<T> capture(Class<T> clazz, Consumer<T> consumer) {
		return CSSArgumentCaptor.forClass(clazz, consumer);
	}

	public CSSArgumentCaptor<String> captureString(String value) {
		return capture(String.class, (e) -> {
			assertThat(e, is(value));
		});

	}

	public <T> CSSArgumentCaptor<T> captureValue(T value) {
		Class<? extends Object> clazz = value.getClass();
		return (CSSArgumentCaptor<T>) capture(clazz, (e) -> {
			assertThat(e, is(value));
		});

	}

}
