package utilities;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.Locale;

public class ThymeleafEmailClient {
    public static final String EMAIL_TEMPLATE_ENCODING = "UTF-8";

    private TemplateEngine templateEngine;

    public ThymeleafEmailClient(){
        this.templateEngine = new TemplateEngine();
        this.templateEngine.addTemplateResolver(htmlTemplateResolver());
    }

    /**
     * THYMELEAF: Template Resolver for email templates.
     */
    private ITemplateResolver htmlTemplateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding(EMAIL_TEMPLATE_ENCODING);
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    public String createEmail(String recipientName, String foundEmailAddress, String foundPerson, String imageResourceName, String template, Locale locale) {
        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("lostName", recipientName);
        ctx.setVariable("emailAdd", foundEmailAddress);
        ctx.setVariable("foundName", foundPerson);
        ctx.setVariable("imageResourceName", imageResourceName); // so that we can reference it from HTML

        //TODO remove the cid for emails :/

        // Create the HTML body using Thymeleaf
        final String htmlContent = this.templateEngine.process(template, ctx);
        return htmlContent;
    }

}
