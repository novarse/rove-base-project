package edu.vtac.roveBaseProject.service;

import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;

import edu.vtac.roveBaseProject.service.ErrorReporterServiceImpl.ErrorReporterData;

public interface ErrorReporterService {

	ErrorReporterData createData(HttpServletRequest req, Exception e);

	Future<Boolean> send(ErrorReporterData data);

	Future<Boolean> send(String subject, Exception e, Object... data);

	Future<Boolean> sendShortList(String emailBody, String toEmail);

}
