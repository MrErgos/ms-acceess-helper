package io.github.mrergos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/members");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new LocalDateFormatter());
    }

    private static class LocalDateFormatter implements Formatter<LocalDate> {
        @Override
        public LocalDate parse(String text, Locale locale) throws ParseException {
            return LocalDate.parse(text, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        }

        @Override
        public String print(LocalDate object, Locale locale) {
            return object.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        }
    }
}
