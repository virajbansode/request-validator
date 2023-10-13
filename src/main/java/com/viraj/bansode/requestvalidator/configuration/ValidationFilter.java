package com.viraj.bansode.requestvalidator.configuration;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.atlassian.oai.validator.report.JsonValidationReportFormat;
import com.atlassian.oai.validator.report.ValidationReport;
import com.atlassian.oai.validator.report.ValidationReport.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viraj.bansode.requestvalidator.Exception.ErrorDetails;
import com.viraj.bansode.requestvalidator.Exception.Response;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Component
public class ValidationFilter extends OncePerRequestFilter {

	@Autowired
	ValidationProcessor processor;


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("inside filter");
		/**
		 * Convert HttpServletRequest to CustomHttpRequest
		 */
		CustomHttpRequest customHttpRequest = new CustomHttpRequest(request);
		/**
		 * call validation method from Swagger lib 
		 */
		final ValidationReport report = processor.validate(customHttpRequest);
		System.out.println("done with valitor");
		
		if (report.hasErrors()) {
		/**
		 * post validation if there are any errors,
		 * loop through errors and return http response with 400
		 */
			
			com.viraj.bansode.requestvalidator.Exception.Error errorResponse = new com.viraj.bansode.requestvalidator.Exception.Error();
			errorResponse.setDescription("Request validation failed");
			List<ErrorDetails> errorList = report.getMessages().stream().map(message -> {
				ErrorDetails errorDetails = new ErrorDetails();
				errorDetails.setDescription(message.getKey());
				errorDetails.setMessage(message.getMessage());
				return errorDetails;
				
			}).collect(Collectors.toList());
			errorResponse.setErrors(errorList);
				
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(convertObjectToJson(errorResponse));
		} else {
			/**
			 * if validation is successful the pass request to web api
			 */
			System.out.println("done with valitor, chaining request");
			filterChain.doFilter(customHttpRequest, response); 
		}		
	}
	
	  public String convertObjectToJson(Object object) throws JsonProcessingException {
	        if (object == null) {
	            return null;
	        }
	        ObjectMapper mapper = new ObjectMapper();
	        return mapper.writeValueAsString(object);
	    }
}
