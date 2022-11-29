package edu.vtac.roveBaseProject.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.concurrent.Future;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Service
public class ErrorReporterServiceImpl implements ErrorReporterService {

	
	private static final Logger log = LoggerFactory.getLogger(ErrorReporterServiceImpl.class);

	
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private org.springframework.mail.javamail.JavaMailSender javaMailSender;

	private String hostname;
	
	@Override
	public ErrorReporterData createData(HttpServletRequest req, Exception e) {
		try {
			StringBuilder buff = _createBody(req, e);
			
			//creating subject
			String[] split = req.getServerName().split(".");
			String server = split.length == 0 ? req.getServerName() : split[0];
			String env = server.equalsIgnoreCase("delta") ? "Prod" : server; 
			String subject = "Error in CourseSearch API [" + env + "]";
			
			return new ErrorReporterData(buff, subject);
		} catch (Exception e2) {
			log.error(e.getMessage(), e);
		}
		return null;
		
	}

	private StringBuilder _createBody(HttpServletRequest req, Exception e, Object... data) {
		Map<String, String> map = new HashMap<>();
		if (req != null) {
			map.put("URI", req.getRequestURI());
			map.put("Server Name", req.getServerName());
			map.put("Local Add", req.getLocalAddr());
			map.put("Local Name", req.getLocalName());
			map.put("Remote Addr", req.getRemoteAddr());
			map.put("Remote Host", req.getRemoteHost());
			
			Enumeration<String> at = req.getHeaderNames();
			while(at.hasMoreElements()) {
				String key = at.nextElement();
				map.put(key, req.getHeader(key) + "");
			}
			Map<String, String[]> map2 = req.getParameterMap();
			for (Entry<String, String[]> ee : map2.entrySet()) {
				map.put(ee.getKey(), Arrays.toString(ee.getValue()));
			}
			map.put("Path Info", req.getPathInfo());
			map.put("Query String", req.getQueryString());
		}
		
		
		
		StringBuilder buff = new StringBuilder();
		
		try {
			if (hostname == null) {
				InetAddress addr;
				addr = InetAddress.getLocalHost();
				hostname = addr.getHostName();
			}
		} catch (UnknownHostException ex) {
			hostname = "Unknown";
		}
		buff.append(_wrap("Host Name " + hostname));
		
		if (data != null && data.length > 0) {
			for (Object obj : data) {
				if (obj == null) {
					continue;
				}
				try {
					if (obj instanceof String) {
						buff.append(_wrap(obj.toString()));
					} else {
						objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
						String string = objectMapper.writeValueAsString(obj);
						buff.append(_wrap(string));
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		
		
		if (map.size() > 0) {
			buff.append("<table>");
			for (Entry<String, String> entry : map.entrySet()) {
				buff.append("<tr><td>" + entry.getKey() + "</tc><td>" + entry.getValue() + "</tc></tr>");
			}
			buff.append("</table>");
		}
		
		if (e != null) {
			String fullStackTrace = ExceptionUtils.getFullStackTrace(e);
			buff.append("<table>");
			
			StringTokenizer frames = new StringTokenizer(fullStackTrace, SystemUtils.LINE_SEPARATOR);
			buff.append("<tr><td></td><td>" + frames.nextToken() +  "</td></tr>");
			while (frames.hasMoreTokens()) {
				buff.append("<tr><td style='width: 100px;'></td><td>" + frames.nextToken() +  "</td></tr>");
			}
			
			buff.append("</table>");
		}
		
		return buff;
	}
	
	
	public class ErrorReporterData {
		
		private StringBuilder buff;
		private String subject;

		ErrorReporterData(StringBuilder buff, String subject) {
			this.buff = buff;
			this.subject = subject;
		}
		
		public String getBody() {
			return buff.toString();
		}
		
		public String getSubject() {
			return subject;
		}
	}
	
	@Override
	@Async
	public Future<Boolean> send(ErrorReporterData data) {
		try {
			javaMailSender.send(new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws MessagingException {
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
					message.setFrom("noreply@vtac.edu.au");
					message.setTo("error@vtac.edu.au");
					message.setSubject(data.getSubject());
					message.setText(data.getBody(), true);
				}
			});
		} catch (Exception e) {
			log.error("Error while sending error email " + e.getMessage());
			return new AsyncResult<Boolean>(false);
		}
		return new AsyncResult<Boolean>(true);
	}
	
	@Override
	@Async
	public Future<Boolean> sendShortList(String emailBody, String toEmail) {
		try {
			javaMailSender.send(new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws MessagingException {
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
					message.setFrom("noreply@vtac.edu.au");
					message.setTo(toEmail);
					message.setSubject("VTAC Course Search shortlist");
					message.setText(emailBody, true);
				}
			});
		} catch (Exception e) {
			log.error("Error while sending error email " + e.getMessage());
			return new AsyncResult<Boolean>(false);
		}
		return new AsyncResult<Boolean>(true);
	}
	
	
	@Override
	@Async
	public Future<Boolean> send(String subject, Exception e, Object... data) {
		StringBuilder body = _createBody(null, e, data);
		ErrorReporterData reporterData = new ErrorReporterData(body, subject);
		return send(reporterData);
	}	
	
	private String _wrap(String s) {
		return "<p>" + s + "</p>";
	}
}
