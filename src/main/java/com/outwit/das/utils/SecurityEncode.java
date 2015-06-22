package com.outwit.das.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;
import java.util.TreeMap;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * SHA-256
 * 
 */
public class SecurityEncode {

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	// 签名算法SHA-256
	public static final String SHA256_ALGORITHM = "SHA-256";

	// 美军军方加密算法PBKDF2-SHA512
	public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA512";

	// The following constants may be changed without breaking existing hashes.
	public static final int SALT_BYTE_SIZE = 22;
	public static final int HASH_BYTE_SIZE = 24;

	public static final int SALT_CHAR_SIZE = SALT_BYTE_SIZE * 2;
	public static final int HASH_CHAR_SIZE = HASH_BYTE_SIZE * 2;

	public static final int ITERATION_Length = 4;
	public static final int[] ITERATIONS_INDEX = new int[] { HASH_CHAR_SIZE,
			HASH_CHAR_SIZE + ITERATION_Length };
	public static final int[] SALT_INDEX = new int[] {
			HASH_CHAR_SIZE + ITERATION_Length,
			HASH_CHAR_SIZE + ITERATION_Length + SALT_CHAR_SIZE };
	public static final int[] PBKDF2_INDEX = new int[] { 0, HASH_CHAR_SIZE };

	/**
	 * Returns a salted PBKDF2 hash of the password.
	 *
	 * @param password
	 *            the password to hash
	 * @return a salted PBKDF2 hash of the password
	 */
	public static String createHash(String password)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		return createHash(password.toCharArray());
	}

	/**
	 * Returns a salted PBKDF2 hash of the password.
	 *
	 * @param password
	 *            the password to hash
	 * @return a salted PBKDF2 hash of the password
	 */
	public static String createHash(char[] password)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		// Generate a random salt
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[SALT_BYTE_SIZE];
		random.nextBytes(salt);
		StringBuilder preChar = new StringBuilder("9");
		StringBuilder sufChar = new StringBuilder("1");
		for (int i = 0; i < ITERATION_Length - 1; i++) {
			preChar.append("0");
			sufChar.append("0");
		}
		int pbkdf2_iterations = random.nextInt(Integer.parseInt(preChar
				.toString())) + Integer.parseInt(sufChar.toString());
		// Hash the password
		byte[] hash = pbkdf2(password, salt, pbkdf2_iterations, HASH_BYTE_SIZE);
		// format hash+iterations+salt
		return toHex(hash) + pbkdf2_iterations + toHex(salt);
	}

	/**
	 * Validates a password using a hash.
	 *
	 * @param password
	 *            the password to check
	 * @param correctHash
	 *            the hash of the valid password
	 * @return true if the password is correct, false if not
	 */
	public static boolean validatePassword(String password, String correctHash)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		return validatePassword(password.toCharArray(), correctHash);
	}

	/**
	 * Validates a password using a hash.
	 *
	 * @param password
	 *            the password to check
	 * @param correctHash
	 *            the hash of the valid password
	 * @return true if the password is correct, false if not
	 */
	public static boolean validatePassword(char[] password, String correctHash)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		// Decode the hash into its parameters
		System.out.println(correctHash.length());
		byte[] salt = fromHex(correctHash.substring(SALT_INDEX[0],
				SALT_INDEX[1]));
		int iterations = Integer.parseInt(correctHash.substring(
				ITERATIONS_INDEX[0], ITERATIONS_INDEX[1]));
		byte[] hash = fromHex(correctHash.substring(PBKDF2_INDEX[0],
				PBKDF2_INDEX[1]));
		// Compute the hash of the provided password, using the same salt,
		// iteration count, and hash length
		byte[] testHash = pbkdf2(password, salt, iterations, hash.length);
		// Compare the hashes in constant time. The password is correct if
		// both hashes match.
		return slowEquals(hash, testHash);
	}

	/**
	 * Compares two byte arrays in length-constant time. This comparison method
	 * is used so that password hashes cannot be extracted from an on-line
	 * system using a timing attack and then attacked off-line.
	 * 
	 * @param a
	 *            the first byte array
	 * @param b
	 *            the second byte array
	 * @return true if both byte arrays are the same, false if not
	 */
	private static boolean slowEquals(byte[] a, byte[] b) {
		int diff = a.length ^ b.length;
		for (int i = 0; i < a.length && i < b.length; i++)
			diff |= a[i] ^ b[i];
		return diff == 0;
	}

	/**
	 * Computes the PBKDF2 hash of a password.
	 *
	 * @param password
	 *            the password to hash.
	 * @param salt
	 *            the salt
	 * @param iterations
	 *            the iteration count (slowness factor)
	 * @param bytes
	 *            the length of the hash to compute in bytes
	 * @return the PBDKF2 hash of the password
	 */
	private static byte[] pbkdf2(char[] password, byte[] salt, int iterations,
			int bytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
		PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
		return skf.generateSecret(spec).getEncoded();
	}

	/**
	 * Converts a string of hexadecimal characters into a byte array.
	 *
	 * @param hex
	 *            the hex string
	 * @return the hex string decoded into a byte array
	 */
	private static byte[] fromHex(String hex) {
		byte[] binary = new byte[hex.length() / 2];
		for (int i = 0; i < binary.length; i++) {
			binary[i] = (byte) Integer.parseInt(
					hex.substring(2 * i, 2 * i + 2), 16);
		}
		return binary;
	}

	/**
	 * Converts a byte array into a hexadecimal string.
	 *
	 * @param array
	 *            the byte array to convert
	 * @return a length*2 character string encoding the byte array
	 */
	private static String toHex(byte[] array) {
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		if (paddingLength > 0)
			return String.format("%0" + paddingLength + "d", 0) + hex;
		else
			return hex;
	}

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String SHA256Encode(String text, String charset) {
		String resultString = null;

		try {
			resultString = new String(text.getBytes());
			MessageDigest md = MessageDigest.getInstance(SHA256_ALGORITHM);
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes(charset)));
		} catch (Exception ex) {

		}
		return resultString;
	}

	public static String SHA256Encode(String text) {
		String resultString = null;

		try {
			resultString = new String(text.getBytes());
			MessageDigest md = MessageDigest.getInstance(SHA256_ALGORITHM);
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes()));
		} catch (Exception ex) {

		}
		return resultString;
	}

	public static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}

	/**
	 * 签名字符串
	 * 
	 * @param text
	 *            需要签名的字符串
	 * @param key
	 *            密钥
	 * @param charset
	 *            编码格式
	 * @return 签名结果
	 */
	public static String sign(String text, String key, String charset) {
		text = text + key;
		return DigestUtils.sha256Hex(getContentBytes(text, charset));
	}

	public static String sign(String text, String charset) {
		return DigestUtils.sha256Hex(getContentBytes(text, charset));
	}

	/**
	 * 签名字符串
	 * 
	 * @param text
	 *            需要签名的字符串
	 * @param sign
	 *            签名结果
	 * @param key
	 *            密钥
	 * @param charset
	 *            编码格式
	 * @return 签名结果
	 */
	public static boolean verify(String text, String sign, String key,
			String charset) {
		text = text + key;
		String mysign = DigestUtils.sha256Hex(getContentBytes(text, charset));
		if (mysign.equals(sign)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param content
	 * @param charset
	 * @return
	 * @throws SignatureException
	 * @throws UnsupportedEncodingException
	 */
	private static byte[] getContentBytes(String content, String charset) {
		if (charset == null || "".equals(charset)) {
			return content.getBytes();
		}
		try {
			return content.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:"
					+ charset);
		}
	}
}
