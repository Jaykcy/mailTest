package com.ddang.demo.util;

import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

public class FreeMakerUtil {

	private final static Logger logger = LoggerFactory.getLogger(FreeMakerUtil.class);

	public static String geFreeMarkerTemplateContent(String ftlName, String ftlPath,
										    Object model) {
		String html = "";
		try {
			Template tpl = getTemplateByInputStream(ftlName, ftlPath);
			html = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, model);
			logger.debug(html);
		}
		catch (Exception e) {
			logger.error("Exception occured while processing fmtemplate:" + e.getMessage(), e);
		}
		return html;
	}

	public static String geFreeMarkerTemplateContent(String ftlName, String ftlPath,
										    Map<String, Object> model) {
		String html = "";
		try {
			Template tpl = getTemplateByInputStream(ftlName, ftlPath);
			html = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, model);
			logger.debug(html);
		}
		catch (Exception e) {
			logger.error("Exception occured while processing fmtemplate:" + e.getMessage(), e);
		}
		return html;
	}

	private static Template getTemplateByInputStream(String ftlName, String ftlPath)
			throws IOException {
		ClassPathResource resource = new ClassPathResource(ftlPath);
		InputStream inputStream = resource.getInputStream();
		Reader reader = new InputStreamReader(inputStream);
		Template t = new Template(ftlName, reader);
		return t;
	}
	public static String getStringByFile(String ftlPath)
			throws IOException {
		InputStream inputStream = null;
		String result = null;
		try {
			ClassPathResource resource = new ClassPathResource(ftlPath);
			inputStream = resource.getInputStream();
			byte[] bytes = new byte[0];
			bytes = new byte[inputStream.available()];
			inputStream.read(bytes);
			result = new String(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}

		return result;
	}

}
