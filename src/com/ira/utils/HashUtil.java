package com.ira.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {

	public static String generateHash(String password, String algorithem, byte[] salt) {

		byte[] hash = null;
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithem);
			digest.reset();
			digest.update(salt);
			hash = digest.digest(password.getBytes());

		} catch (NoSuchAlgorithmException e) {
			System.out.println(e.getMessage());
		}

		return bytesToStringHex(hash);

	}

	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

	public static String bytesToStringHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = HEX_ARRAY[v >>> 4];
			hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
		}
		return new String(hexChars);
	}

	public static byte[] createSalt() {
		byte[] bytes = new byte[20];
		SecureRandom random = new SecureRandom();
		random.nextBytes(bytes);
		return bytes;
	}
}