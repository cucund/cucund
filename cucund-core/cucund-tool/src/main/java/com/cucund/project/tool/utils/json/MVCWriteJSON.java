package com.cucund.project.tool.utils.json;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class MVCWriteJSON {

	public static void write( Object result) {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
    	HttpServletResponse response = requestAttributes.getResponse();
		if(response == null)
			throw new RuntimeException("响应为空");
		if(result == null)
			result = "";
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JSONUtils.obj2json(result));
			writer.flush();
		} catch (IOException ex) {
			log.error(ex.getMessage());
		} catch (Exception e) {
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

}
