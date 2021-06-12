package com.ems.PasswordService;

import java.security.SecureRandom;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class PasswordService{
	
	public String generatePassoword() {
		
		final String SOURCE = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz" + "@#$&_-"; 
		SecureRandom secureRnd = new SecureRandom();
		int length = 10;
		StringBuilder sb = new StringBuilder(length); 
		for (int i = 0; i < length; i++) {
			sb.append(SOURCE.charAt(secureRnd.nextInt(SOURCE.length()))); 
		}
		return sb.toString(); 
	}
	

    private final int logRounds = 11;

    public String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(logRounds));
    }

    public boolean verifyHash(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }


}
