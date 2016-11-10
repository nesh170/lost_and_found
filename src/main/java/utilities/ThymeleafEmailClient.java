package utilities;

import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

public class ThymeleafEmailClient {

    private TemplateResolver emailTemplateResolver() {
        TemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/mail/");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setOrder(1);
        return templateResolver;
    }

}
