package com.demo.accountservice.config;

import java.util.concurrent.Executor;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.demo.accountservice.logging.MDCTaskDecorator;
import com.demo.accountservice.logging.Slf4jMDCFilter;

import ch.qos.logback.access.tomcat.LogbackValve;

@Configuration
@EnableAsync
public class Config {
	@Bean
	public TomcatServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
		tomcat.addContextValves(new LogbackValve());
		return tomcat;
	}

	@Bean
	public FilterRegistrationBean servletRegistrationBean() {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		final Slf4jMDCFilter log4jMDCFilterFilter = new Slf4jMDCFilter();
		registrationBean.setFilter(log4jMDCFilterFilter);
		registrationBean.setOrder(2);
		return registrationBean;
	}
	
	@Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setTaskDecorator(new MDCTaskDecorator());
        executor.initialize();
        return executor;
    }

}
