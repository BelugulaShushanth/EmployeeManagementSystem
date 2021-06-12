package com.ems.MailService;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ems.MailBean.MailBean;

@Service
public class MailService {
	
	public boolean sendMail(MailBean mbean) throws Exception {
		final String Mail_Service_URI = "http://localhost:8081/mailService/sendMail";
		HttpEntity<MailBean> mailEntity = new HttpEntity<MailBean>(mbean);
		RestTemplate rt = new RestTemplate();
		boolean isSent = false;
		try {
			ResponseEntity<String> rE = rt.exchange(Mail_Service_URI, HttpMethod.POST, mailEntity, String.class);
			System.out.println(rE.getBody());
			isSent = HttpStatus.OK.equals(rE.getStatusCode());
			if(!isSent) {
				throw new Exception("Unable to send mail");
			}
		}
		catch(Exception e){
			throw e;
		}
		
		return isSent;
		
	}
	
	
}
