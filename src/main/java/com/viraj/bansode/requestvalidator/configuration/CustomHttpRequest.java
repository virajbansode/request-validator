package com.viraj.bansode.requestvalidator.configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class CustomHttpRequest extends HttpServletRequestWrapper {

	private byte[] body;
	
	public CustomHttpRequest(HttpServletRequest request) throws IOException {
		super(request);
		ServletInputStream sis = request.getInputStream();
		body = IOUtils.toByteArray(sis);
	}
	
	  @Override
	    public ServletInputStream getInputStream() {
	        ByteArrayInputStream inputStream = new ByteArrayInputStream(body);

	        return new ServletInputStream() {
	            @Override
	            public boolean isFinished() {
	                return inputStream.available() == 0;
	            }

	            @Override
	            public boolean isReady() {
	                return true;
	            }

	            @Override
	            public int read() {
	                return inputStream.read();
	            }

	            @Override
	            public void setReadListener(ReadListener readListener) {
	                throw new UnsupportedOperationException();
	            }
	        };
	    }
}
