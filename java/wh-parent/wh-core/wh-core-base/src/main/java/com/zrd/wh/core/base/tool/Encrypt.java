package com.zrd.wh.core.base.tool;

import javax.crypto.*;
import javax.crypto.spec.*;

import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;

/**
 * <p>
 * Title： Encrypt.java
 * </p>
 * <p>
 * Description：加密解密类
 * </p>
 * 
 * 
 * @author
 * @version 1.031
 * @see
 */

public class Encrypt {
	/** 实例 */
	private static Encrypt _instance = null;

	/** cipher */
	Cipher cipher = null;

	/** Secretkey */
	SecretKey key = null;

	/** key */
	private String m_keyString = "password encrypt key";

	/**
	 * @param
	 * @return 实例
	 * @throws
	 *         <DT>
	 * 
	 *         <pre>
	 * Description
	 *       功能：返回类实例
	 *       实现方法：
	 *         </pre>
	 * 
	 * @see
	 */
	public static Encrypt getInstance() {
		if (_instance == null) {
			try {
				_instance = new Encrypt();
			} catch (Exception ex) {
				return null;
			}
		}
		return _instance;
	}

	/**
	 * @param key 密码
	 * @return
	 * @throws
	 *         <DT>
	 * 
	 *         <pre>
	 * Description
	 *       功能：设置加密解密密码
	 *       实现方法：
	 *         </pre>
	 * 
	 * @see
	 */
	public void setKey(String key) {
		m_keyString = key;
	}

	/**
	 * @param
	 * @return 密码
	 * @throws
	 *         <DT>
	 * 
	 *         <pre>
	 * Description
	 *       功能：取得加密解密密码
	 *       实现方法：
	 *         </pre>
	 * 
	 * @see
	 */
	public String getKey() {
		return m_keyString;
	}

	/**
	 * 构造函数
	 */
	private Encrypt() throws Exception {
		try {
			DESKeySpec dks = new DESKeySpec(m_keyString.getBytes()); // Constants.DES_KEY);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			key = keyFactory.generateSecret(dks);
			cipher = Cipher.getInstance("DES");
		} catch (Exception ex) {
			ex.printStackTrace();
//            LogWriter.getInstance().writeSystemLog("初始化加密算法出错",ex,1);
			throw new Exception("初始化加密算法出错");
		}
	}

	/**
	 * @param source 源信息
	 * @return 加密信息
	 * @throws 加密异常信息
	 *                <DT>
	 * 
	 *                <pre>
	 * Description
	 *       功能：加密
	 *       实现方法：
	 *                </pre>
	 * 
	 * @see
	 */
	public String decrypt(byte[] source) throws Exception {
		String result = null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] rbs = cipher.doFinal(source);
			result = new String(rbs, "ISO-8859-1");
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("密码解密错误");
			throw new Exception("密码解密错误");
//            LogWriter.getInstance().writeSystemLog("密码解密错误",ex,2);
		}
		return result;
	}

	/**
	 * @param source 源信息
	 * @return 解密信息
	 * @throws 解密异常信息
	 *                <DT>
	 * 
	 *                <pre>
	 * Description
	 *       功能：解密
	 *       实现方法：
	 *                </pre>
	 * 
	 * @see
	 */
	@SuppressWarnings("restriction")
	public String decryptToString(String source) throws Exception {
		String result = null;
		try {
			if (source != null && !"".equals(source)) {
				BASE64Decoder decoder = new BASE64Decoder();
				byte[] rp = decoder.decodeBuffer(source);
				cipher.init(Cipher.DECRYPT_MODE, key);
				byte[] rbs = cipher.doFinal(rp);
				result = new String(rbs, "ISO-8859-1");
			} else {
				throw new Exception("密码解密错误! 原因：加密参数为NULL或空值。");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("密码解密错误");
			throw new Exception("密码解密错误");
		}
		return result;
	}

	/**
	 * @param source 源信息
	 * @return 解密后信息
	 * @throws 解密错误信息
	 *                <DT>
	 * 
	 *                <pre>
	 * Description
	 *       功能：解密
	 *       实现方法：
	 *                </pre>
	 * 
	 * @see
	 */
	public byte[] encrypt(String source) throws Exception {
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] pwdbs = source.getBytes("ISO-8859-1");
			byte[] finalresult = cipher.doFinal(pwdbs);
			return finalresult;
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("密码加密错误");
			throw new Exception("密码加密错误");
//            LogWriter.getInstance().writeSystemLog("密码加密错误",ex,2);
		}
	}

	@SuppressWarnings("restriction")
	public String encryptToString(String source) throws Exception {
		String rStr = null;
		try {
			if (null != source && !"".equals(source)) {
				cipher.init(Cipher.ENCRYPT_MODE, key);
				byte[] pwdbs = source.getBytes("ISO-8859-1");
				byte[] finalresult = cipher.doFinal(pwdbs);
				BASE64Encoder base64Encoder = new BASE64Encoder();
				rStr = base64Encoder.encode(finalresult);
			} else {
				throw new Exception("密码加密错误！原因：加密参数为NULL或空值。");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("密码加密错误");
		}
		return rStr;
	}

	public static void main(String args[]) {
		String pwd = "1234567890abcdefghij";
		System.out.println(pwd + "\t" + pwd.length());
		try {
			/*byte[] rs = Encrypt.getInstance().encrypt(pwd);
			System.out.println(new String(rs) + "\t" + rs.length + "\t" + new String(rs).length());

			BASE64Encoder encoder = new BASE64Encoder();
			String newpwd = encoder.encode(rs);
			System.out.println("BASE64后" + newpwd + "\t" + newpwd.length());

			BASE64Decoder decoder = new BASE64Decoder();
			byte[] rp = decoder.decodeBuffer(newpwd);

			String str = Encrypt.getInstance().decrypt(rp);
			System.out.println(str + "\t" + str.length());*/
			System.out.println(Encrypt.getInstance().encryptToString(pwd));
			System.out.println(Encrypt.getInstance().decryptToString(Encrypt.getInstance().encryptToString(pwd)));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}