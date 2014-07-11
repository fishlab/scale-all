package org.fishlab.web.spring.servlet.viewresolver;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fishlab.web.spring.servlet.view.GenericFileView;
import org.springframework.core.Ordered;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractCachingViewResolver;

public class GenericFileViewResolver extends AbstractCachingViewResolver
		implements Ordered {
	private Log logger = LogFactory.getLog(GenericFileViewResolver.class);
	private int order = Integer.MIN_VALUE;
	// requested file location under web app
	private String location;
	// View
	private String viewName;

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getOrder() {
		return this.order;
	}

	@Override
	protected View loadView(String viewName, Locale locale) throws Exception {
		if (location == null) {
			throw new Exception(
					"No location specified for GenericFileViewResolver.");
		}

		String requestedFilePath = location + viewName;
		Resource resource = null;
		// String url = "";
		try {
			logger.info(requestedFilePath);
			resource = getApplicationContext().getResource(requestedFilePath);
			// url = resource.getURI().toString();
		} catch (Exception e) {
			// this exception should be catched and return null in order to call
			// next view resolver
			logger.info("No file found for file: " + requestedFilePath);
			return null;
		}

		logger.info("Requested file found: " + requestedFilePath
				+ ", viewName:" + viewName);

		// get view from application content, scope=prototype
		GenericFileView view = this.getApplicationContext().getBean(
				this.viewName, GenericFileView.class);
		view.setUrl(requestedFilePath);
		view.setResponseContent(inputStreamTOString(resource.getInputStream(),
				"UTF-8"));
		return view;
	}

	final static int BUFFER_SIZE = 4096;

	public static String inputStreamTOString(InputStream in, String encoding)
			throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count = -1;
		while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
			outStream.write(data, 0, count);
		data = null;
		return new String(outStream.toByteArray(), encoding);
	}

}
