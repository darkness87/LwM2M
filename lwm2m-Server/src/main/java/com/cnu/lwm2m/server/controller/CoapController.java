package com.cnu.lwm2m.server.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cnu.lwm2m.server.service.CoapService;
import com.cnu.lwm2m.server.service.RedisService;
import com.cnu.lwm2m.server.vo.ObserveDataVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CoapController {
	@Autowired
	CoapService coapService;

	@Autowired
	RedisService redisService;

	@RequestMapping("/coapObserve.do")
	public @ResponseBody String sendCoapObserve(@RequestParam String endpoint, @RequestParam String uri,
			@RequestParam String contentType, @RequestParam int timeout) {
		log.info("=== sendCoapObserve ===");
		List<ObserveDataVO> result = coapService.sendCoapTLVObserve(endpoint, uri, contentType, timeout);
		ObjectMapper mapper = new ObjectMapper();
		String setString = null;

		try {
			setString = mapper.writeValueAsString(result);
			// redisService.setHistory(endpoint, "OBS", setString); // Redis 저장정보
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return setString;
	}

	@RequestMapping("/coapObserveCancel.do")
	public @ResponseBody String sendCoapObserveCancel(@RequestParam String endpoint, @RequestParam String uri) {
		log.info("=== sendCoapObserve Cancel ===");
		String result = coapService.sendCoapObserveCancel(endpoint, uri);

		if (result == null) {
			result = "null";
		}

		return result;
	}

	@RequestMapping("/coapRead.do")
	public @ResponseBody String sendCoapRead(@RequestParam String endpoint, @RequestParam String uri,
			@RequestParam String type, @RequestParam String contentType, @RequestParam int timeout) {
		log.info("=== sendCoapRead ===");
		String result = coapService.sendCoapTLVRead(endpoint, uri, type, contentType, timeout);

		if (result == null) {
			result = "null";
		}

		return result;
	}

	@RequestMapping("/coapWrite.do")
	public @ResponseBody String sendCoapWrite(@RequestParam String endpoint, @RequestParam String uri,
			@RequestParam String type, @RequestParam String data, @RequestParam String contentType,
			@RequestParam int timeout) {
		log.info("=== sendCoapWrite ===");
		log.info(data);
		boolean code = coapService.sendCoapTLVWrite(endpoint, uri, type, data, contentType, timeout);
		String result = null;

		if (code == false) {
			result = "Write False : Error Check";
		} else if (code == true) {
			result = coapService.sendCoapTLVRead(endpoint, uri, type, contentType, timeout);
		}

		return result;
	}

	@RequestMapping("/coapWriteFile.do")
	public @ResponseBody boolean sendCoapWriteFile(@RequestParam String endpoint, @RequestParam String uri,
			@RequestParam String type, @RequestParam String data, @RequestParam String contentType,
			@RequestParam int timeout) {
		log.info("=== sendCoapWrite File ===");
		log.info("File Url : {}", data);

		String fileString = new String();
		FileInputStream inputStream = null;
		ByteArrayOutputStream byteOutStream = null;

		try {
			inputStream = new FileInputStream("C:\\Users\\sookwon\\Desktop\\LwM2M 개발.txt"); // TODO 주소 가져오는거 체크 필요
			// inputStream = new FileInputStream(data);
			byteOutStream = new ByteArrayOutputStream();
			int len = 0;
			byte[] buf = new byte[1024];
			while ((len = inputStream.read(buf)) != -1) {
				byteOutStream.write(buf, 0, len);
			}

			byte[] fileArray = byteOutStream.toByteArray();
			fileString = new String(Base64.encodeBase64(fileArray));

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
				byteOutStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		log.info("Opaque FileString : {}", fileString);
		boolean code = coapService.sendCoapTLVWrite(endpoint, uri, type, fileString, contentType, timeout);

		return code;
	}

	@RequestMapping("/coapExec.do")
	public @ResponseBody String sendCoapExec(@RequestParam String endpoint, @RequestParam String uri,
			@RequestParam String type, @RequestParam String contentType, @RequestParam int timeout) {
		log.info("=== sendCoapExec ===");
		String execValue = null;
		log.info("exec uri : {}", uri);
		if (uri.equals("/1/0/4")) {
			execValue = coapService.sendCoapTLVRead(endpoint, "/1/0/5", type, contentType, timeout);
			log.info("execValue : {}", execValue);
		}

		String result = coapService.sendCoapExec(endpoint, uri);

		if (result == null) {
			result = "null";
		}

		if (uri.equals("/1/0/4")) {
			result = execValue;
		}

		return result;
	}

}