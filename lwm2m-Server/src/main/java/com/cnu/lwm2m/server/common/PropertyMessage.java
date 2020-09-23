package com.cnu.lwm2m.server.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class PropertyMessage {
	final static Logger logger = LogManager.getLogger(PropertyMessage.class);

	static MessageSourceAccessor messageSourceAccessor;

	public PropertyMessage() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:com/cnu/lwm2m/server/viewmessages");
		PropertyMessage.messageSourceAccessor = new MessageSourceAccessor(messageSource);
	}

	public static void setMessageSourceAccsssor(MessageSourceAccessor messageSourceAccessor) {
		PropertyMessage.messageSourceAccessor = messageSourceAccessor;
	}

	public static String getMessage(String key) {
		try {
			return messageSourceAccessor.getMessage(key);
		} catch (NoSuchMessageException e) {
			logger.error(e.getMessage());
			return "";
		}
	}

	public static String getMessage(String key, String value) {
		try {
			return messageSourceAccessor.getMessage(key).replace("{0}", value);
		} catch (NoSuchMessageException e) {
			logger.error(e.getMessage());
			return "";
		}
	}

	public static String getMessage(String key, String value1, String value2) {
		try {
			return messageSourceAccessor.getMessage(key).replace("{0}", value1).replace("{1}", value2);
		} catch (NoSuchMessageException e) {
			logger.error(e.getMessage());
			return "";
		}
	}

	public static String getCodeMessage(int key) {
		try {
			return messageSourceAccessor.getMessage("lwm2m.code.message." + Integer.toString(key));
		} catch (NullPointerException e) {
			return null;
		}
	}

	public static String getCodeMessage(int key, String... values) {
		try {
			String message = messageSourceAccessor.getMessage("lwm2m.code.message." + Integer.toString(key));

			for (int i = 0; i < values.length; i++) {
				message = message.replace("{" + i + "}", values[i]);
			}

			return message;
		} catch (NoSuchMessageException e) {
			logger.error(e.getMessage());
			return messageSourceAccessor.getMessage("lwm2m.code.message." + Integer.toString(key));
		} catch (Exception e) {
			logger.error(e.getMessage());
			return messageSourceAccessor.getMessage("lwm2m.code.message." + Integer.toString(key));
		}
	}
}