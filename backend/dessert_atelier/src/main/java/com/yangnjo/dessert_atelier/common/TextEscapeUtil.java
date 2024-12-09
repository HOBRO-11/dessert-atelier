package com.yangnjo.dessert_atelier.common;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

public class TextEscapeUtil {

    public static String escapeUserInput(String input) {
        if (input.length() > 200) {
            input = input.substring(0, 200);
        }

        PolicyFactory policy = new HtmlPolicyBuilder()
                .allowElements("p", "br", "b", "strong", "i", "em", "u", "s", "del",
                        "sub", "sup", "ul", "ol", "li", "dl", "dt", "dd",
                        "a", "h1", "h2", "h3", "h4", "h5", "h6")
                .toFactory();
        String sanitizedInput = policy.sanitize(input);

        return sanitizedInput;
    }

    public static String escapeAdminInput(String input) {
        PolicyFactory policy = new HtmlPolicyBuilder()
                .allowElements("p", "br", "b", "strong", "i", "em", "u", "s", "del",
                        "sub", "sup", "ul", "ol", "li", "dl", "dt", "dd",
                        "a", "h1", "h2", "h3", "h4", "h5", "h6")
                .toFactory();
        String sanitizedInput = policy.sanitize(input);

        return sanitizedInput;
    }
}
