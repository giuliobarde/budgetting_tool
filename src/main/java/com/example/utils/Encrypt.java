package com.example.utils;

import at.favre.lib.crypto.bcrypt.*;

public class Encrypt {

	public static String encryptPass(String password) {
		String hashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());
		return hashString;
	}
	
	public static boolean checkPass(String input, String hash) {
		BCrypt.Result result = BCrypt.verifyer().verify(input.toCharArray(), hash);
		return result.verified;
	}
	
}
