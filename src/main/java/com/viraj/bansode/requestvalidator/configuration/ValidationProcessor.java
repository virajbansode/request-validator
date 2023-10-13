package com.viraj.bansode.requestvalidator.configuration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.oai.validator.OpenApiInteractionValidator;
import com.atlassian.oai.validator.model.SimpleRequest;
import com.atlassian.oai.validator.report.ValidationReport;

@Component 
public class ValidationProcessor {

	@Autowired 
	private OpenApiInteractionValidator validator; 

	public ValidationReport validate(CustomHttpRequest req) throws IOException{
		/**
		 * Prepare  builder object to pass to validation 
		 */
		SimpleRequest.Builder builder = 
				new SimpleRequest.Builder(req.getMethod(), req.getRequestURI(), false);

		String requestBody = IOUtils.toString(req.getInputStream(),StandardCharsets.UTF_8);
		builder.withBody(requestBody,StandardCharsets.UTF_8);


		req.getHeaderNames().asIterator().forEachRemaining(( hname ->
		builder.withHeader(hname.toString(), req.getHeader(hname.toString()))));
		

		if (req.getContentType() != null) {
			builder.withContentType(req.getContentType());
		}

		new HashMap<String, Object>(req.getParameterMap())
		.forEach((key, value) -> {
			if (value instanceof List) {
				builder.withQueryParam(key, (List) value);
			} else if (value instanceof String) {
				builder.withQueryParam(key, (String) value);
			}
		});	    
		/**
		 * call validation method from Swagger lib 
		 */
		return validator.validateRequest(builder.build());

	}
}
