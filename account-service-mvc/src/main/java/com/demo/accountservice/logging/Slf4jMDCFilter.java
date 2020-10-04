package com.demo.accountservice.logging;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

public class Slf4jMDCFilter extends OncePerRequestFilter {

	private final static String GLOBAL_TRANSACTION_ID_FIELD_KEY = "global_transaction_id";
	private final static String MAIL_FIELD_KEY = "client_email";
	private final static String CLIENT_FIELD_KEY = "client_id";
	private final static String DEVICE_FIELD_KEY = "device_id";
	private final static String TRANSACTION_FIELD_KEY = "transaction_id";

	
    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
        throws java.io.IOException, ServletException {
		try {
            String globalTransactionId = request.getHeader("x-global-transaction-id");
            String mail = request.getHeader("x-naranja-email");
            String client = request.getHeader("x-naranja-client-id");
            String device = request.getHeader("x-naranja-device-id");
            String transaction = request.getHeader("x-naranja-transactionid");
            		
            MDC.put(GLOBAL_TRANSACTION_ID_FIELD_KEY, globalTransactionId);
            MDC.put(MAIL_FIELD_KEY, mail);
            MDC.put(CLIENT_FIELD_KEY, client);
            MDC.put(DEVICE_FIELD_KEY, device);
            MDC.put(TRANSACTION_FIELD_KEY, transaction);
            chain.doFilter(request, response);
        } finally {
            MDC.remove(GLOBAL_TRANSACTION_ID_FIELD_KEY);
        }
    }
}