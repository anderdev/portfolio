package br.com.organizer.util;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import br.com.organizer.exception.OrganizerException;

public final class Criptografa {
	private static SecretKey skey;
	private static KeySpec ks;
	private static PBEParameterSpec ps;
	private static final String algorithm = "PBEWithMD5AndDES";
	private static BASE64Encoder enc = new BASE64Encoder();
	private static BASE64Decoder dec = new BASE64Decoder();

	private static Logger logger = Logger.getLogger(Criptografa.class);

	static {
		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance(algorithm);
			ps = new PBEParameterSpec(new byte[] { 3, 1, 4, 1, 5, 9, 2, 6 }, 20);

			ks = new PBEKeySpec("0gRfuY583VfRkZZPOA2009".toCharArray());
			// esta é a chave que você quer manter secreta.
			// Nao use caracteres especiais (como ?) para nao dar problemas.

			skey = skf.generateSecret(ks);
		} catch (NoSuchAlgorithmException e) {
			logger.warn("NoSuchAlgorithmException: " + e.getMessage());
		} catch (InvalidKeySpecException e) {
			logger.warn("InvalidKeySpecException: " + e.getMessage());
		}
	}

	public static final String encrypt(final String text) throws OrganizerException {
		Cipher cipher;
		String ret = new String();
		try {
			cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.ENCRYPT_MODE, skey, ps);
			ret = enc.encode(cipher.doFinal(text.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			logger.warn("NoSuchAlgorithmException: " + e.getMessage());
			throw new OrganizerException(e.getMessage());
		} catch (NoSuchPaddingException e) {
			logger.warn("NoSuchPaddingException: " + e.getMessage());
			throw new OrganizerException(e.getMessage());
		} catch (InvalidKeyException e) {
			logger.warn("InvalidKeyException: " + e.getMessage());
			throw new OrganizerException(e.getMessage());
		} catch (InvalidAlgorithmParameterException e) {
			logger.warn("InvalidAlgorithmParameterException: " + e.getMessage());
			throw new OrganizerException(e.getMessage());
		} catch (IllegalBlockSizeException e) {
		} catch (BadPaddingException e) {
			logger.warn("BadPaddingException: " + e.getMessage());
			throw new OrganizerException(e.getMessage());
		}
		return ret;
	}

	public static final String decrypt(final String text) throws OrganizerException {
		Cipher cipher;
		String ret = new String();
		try {
			cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.DECRYPT_MODE, skey, ps);
			if (text != null) {
				ret = new String(cipher.doFinal(dec.decodeBuffer(text)));
			} else {
				ret = "";
			}
		} catch (NoSuchAlgorithmException e) {
			logger.warn("NoSuchAlgorithmException: " + e.getMessage());
			throw new OrganizerException(e.getMessage());
		} catch (NoSuchPaddingException e) {
			logger.warn("NoSuchPaddingException: " + e.getMessage());
			throw new OrganizerException(e.getMessage());
		} catch (InvalidKeyException e) {
			logger.warn("InvalidKeyException: " + e.getMessage());
			throw new OrganizerException(e.getMessage());
		} catch (InvalidAlgorithmParameterException e) {
			logger.warn("InvalidAlgorithmParameterException: " + e.getMessage());
			throw new OrganizerException(e.getMessage());
		} catch (IllegalBlockSizeException e) {
		} catch (BadPaddingException e) {
			logger.warn("BadPaddingException: " + e.getMessage());
			throw new OrganizerException(e.getMessage());
		} catch (IOException e) {
			logger.warn("IOException: " + e.getMessage());
			throw new OrganizerException(e.getMessage());
		}
		return ret;
	}

	public static final Double encryptValor(final Double valor) {
		Double ret = valor * 3 / 10;

		return ret;
	}

	public static final Double decryptValor(final Double valor) {
		Double ret = valor / 3 * 10;

		return ret;
	}

}
