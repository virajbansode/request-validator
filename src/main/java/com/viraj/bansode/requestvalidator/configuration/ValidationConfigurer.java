package com.viraj.bansode.requestvalidator.configuration;

import static com.atlassian.oai.validator.report.ValidationReport.Level.IGNORE;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.atlassian.oai.validator.OpenApiInteractionValidator;
import com.atlassian.oai.validator.report.LevelResolver;

@Configuration 
public class ValidationConfigurer {

	@Value("${application.oas.file.path}")
	private String oasPath;
	
	@Bean
	public OpenApiInteractionValidator loadOpenApiContract() {
		return OpenApiInteractionValidator        
				.createFor(oasPath)
                .withLevelResolver(
                        // The key here is to use the level resolver to ignore the response validation messages
                        // Without this they would be emitted at ERROR level and cause a validation failure.
                        LevelResolver.create()
                                .withLevel("validation.response", IGNORE)
                                .build()
                )
                .build();
	}
}
