package com.mconnti.moneymanager.utils;

import java.math.BigDecimal;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public final class Crypt {

	private static final String ALGORITHM = "AES";
	private static final byte[] keyValue = "AKL112013MM00987".getBytes();

	private static Key key = null;
	private static Cipher c = null;

	public static void main(String args[]) throws Exception {
		String TEST = "123456";
		String dec = encrypt(TEST);
		decrypt(dec);
	}

	static {
		key = new SecretKeySpec(keyValue, ALGORITHM);
		try {
			c = Cipher.getInstance(ALGORITHM);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param args
	 * @throws Exception
	 */

	public static String encrypt(String valueToEnc) throws Exception {
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encValue = valueToEnc.getBytes();
		String encryptedValue = Base64.encodeBase64String(encValue);
		return encryptedValue;
	}

	public static String decrypt(String encryptedValue) throws Exception {
		c.init(Cipher.DECRYPT_MODE, key);
		byte[] enctVal = encryptedValue.getBytes();
		byte[] decordedValue = new Base64().decode(enctVal);
		String ret = new String(decordedValue);
		return ret;
	}

	public static final BigDecimal encryptValor(final BigDecimal valor) {
		return valor.multiply(new BigDecimal(3)).divide(new BigDecimal(10));
	}

	public static final BigDecimal decryptValor(final BigDecimal valor) {
		BigDecimal result =  valor.divide(new BigDecimal(3)).multiply(new BigDecimal(10));
		return result;
	}

}
