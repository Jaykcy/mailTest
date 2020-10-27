package com.ddang.demo.trans;

import freemarker.template.Template;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
@Service
public class EmailUtil {
    /**
     * 模板
     * @param ftlName
     * @param ftlPath
     * @param model
     * @return
     */
    public static String geFreeMarkerTemplateContent(String ftlName, String ftlPath,
                                                     Object model) {
        String html = "";
        try {
            Template tpl = getTemplateByInputStream(ftlName, ftlPath);
            html = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, model);

        }
        catch (Exception e) {
            e.printStackTrace();
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



}
