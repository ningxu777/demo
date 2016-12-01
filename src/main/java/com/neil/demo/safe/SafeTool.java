package com.neil.demo.safe;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;

/**
 * 各种编码、加密、摘要算法集合
 * 编码：Base64，Base64内容传送编码被设计用来把任意序列的8位字节描述为一种不易被人直接识别的形式。
 * （The Base64 Content-Transfer-Encoding is designed to represent arbitrary sequences of octets in a form that need not be humanly readable.）
 * 通常被下面的加密算法加密后会转成Base64编码返回（不知为什么）。
 * 不可逆加密（摘要）：MD5、SHA、HMAC。加密后不可解密所以一般用于验票等。
 * 可逆算法（对称）：DES、3DES（TripleDES或DESede）、AES、Blowfish、RC2、RC4、RC5
 * 可逆算法（非对称）：RSA、Elgamal、背包算法、Rabin、D-H、ECC（椭圆曲线加密算法）
 * SSH（Secure SHell）：基于RSA进行加密除传输，安全的壳，为已经能正常运行的程序添加的壳。
 * SSL（Secure Sockets Layer）：为程序构建时期为了安全性而加入的一个协议层。
 * SSH和SSL的关系是： ssh 是用SSL协议 构建的一个类似telnet的应用SSH = TELNET + SSL
 *
 * @author gooooooo
 */
public class SafeTool {

    /***************************第一种Base64加密解密算法************************/
    /**
     * base 64 encode
     *
     * @param bytes 待编码的byte[]
     * @return 编码后的base 64 code
     */
    public static String base64Encode(byte[] bytes) {
        return new BASE64Encoder().encode(bytes);
    }

    /**
     * base 64 decode
     *
     * @param base64Code 待解码的base 64 code
     * @return 解码后的byte[]
     * @throws Exception
     */
    public static byte[] base64Decode(String base64Code) throws Exception {
        return StringUtils.isEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);
    }

    /***************************
     * 第二种Base64加密解密算法
     ************************/
    public static byte[] base64Decode1(byte[] bytes) throws Exception {
        return Base64.encodeBase64(bytes);
    }

    public static byte[] base64Decode1(String str) throws Exception {
        return Base64.decodeBase64(str);
    }


    /***************************第一种AES加密解密算法********************************/
    /**
     * AES加密为base 64 code
     *
     * @param content    待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的base 64 code
     * @throws Exception
     */
    public static String aesEncrypt(String content, String encryptKey) throws Exception {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }

    /**
     * AES加密
     *
     * @param content    待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的byte[]
     * @throws Exception
     */
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(encryptKey.getBytes());
        kgen.init(128, random);
        //kgen.init(128, new SecureRandom(encryptKey.getBytes()));

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));

        return cipher.doFinal(content.getBytes("utf-8"));
    }

    /**
     * AES解密
     *
     * @param encryptBytes 待解密的byte[]
     * @param decryptKey   解密密钥
     * @return 解密后的String
     * @throws Exception
     */
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(decryptKey.getBytes());
        kgen.init(128, random);
        //kgen.init(128, new SecureRandom(decryptKey.getBytes()));

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);

        return new String(decryptBytes);
    }

    /**
     * 将base 64 code AES解密
     *
     * @param encryptStr 待解密的base 64 code
     * @param decryptKey 解密密钥
     * @return 解密后的string
     * @throws Exception
     */
    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
        return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }


    /*****************************第二种AES加密解密算法******************************/
    /**
     * AES加密
     */
    public static String aesEncode(String content, String key) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom random = null;
            try {
                random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            } catch (NoSuchProviderException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            random.setSeed(key.getBytes());
            kgen.init(128, random);
            //kgen.init(128, new SecureRandom(key.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] byteRresult = cipher.doFinal(byteContent);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteRresult.length; i++) {
                String hex = Integer.toHexString(byteRresult[i] & 0xFF);
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }
                sb.append(hex.toUpperCase());
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * AES解密
     */
    public static String aesDecode(String content, String key) {
        if (content.length() < 1)
            return null;
        byte[] byteRresult = new byte[content.length() / 2];
        for (int i = 0; i < content.length() / 2; i++) {
            int high = Integer.parseInt(content.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(content.substring(i * 2 + 1, i * 2 + 2), 16);
            byteRresult[i] = (byte) (high * 16 + low);
        }
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom random = null;
            try {
                random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            } catch (NoSuchProviderException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            random.setSeed(key.getBytes());
            kgen.init(128, random);
            //kgen.init(128, new SecureRandom(key.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] result = cipher.doFinal(byteRresult);
            return new String(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /***************************第三种AES加密解密算法****************************/

    /**
     * AES加密
     *
     * @param keyStr
     * @param plainText
     * @return
     */
    public static String AES_Encrypt(String keyStr, String plainText) {
        byte[] encrypt = null;
        try {
            Key key = generateKey(keyStr);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encrypt = cipher.doFinal(plainText.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(Base64.encodeBase64(encrypt));//加密后再进行base64加密
    }

    /**
     * AES解密
     *
     * @param keyStr
     * @param encryptData
     * @return
     */
    public static String AES_Decrypt(String keyStr, String encryptData) {
        byte[] decrypt = null;
        try {
            Key key = generateKey(keyStr);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            decrypt = cipher.doFinal(Base64.decodeBase64(encryptData));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(decrypt).trim();
    }

    /**
     * AES加密／解密用到
     *
     * @param key
     * @return
     * @throws Exception
     */
    private static Key generateKey(String key) throws Exception {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            return keySpec;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    /**********************第一种MD5生成摘要实现************************/
    /**
     * @param plainText 明文
     * @return 32位密文
     */
    public static String md5(String plainText) {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            re_md5 = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }

    /*************************
     * 第二种MD5生成摘要实现
     **************************/
    public static String simpleMd5(String codes) {
        String sign = DigestUtils.md5Hex(codes);
        return sign;
    }

    /**************************
     * 第三种MD5生成摘要实现
     **********************/
    public static String encodeByMD5(String str) {
        String newStr = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(str.getBytes());
            newStr = byteToString(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return newStr;
    }

    private static String byteToString(byte[] digest) {
        StringBuffer sb = new StringBuffer(digest.length);
        String sTemp;
        for (int i = 0; i < digest.length; i++) {
            sTemp = Integer.toHexString(0Xff & digest[i]);
            if (sTemp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTemp.toLowerCase());
        }
        return sb.toString();

    }

    /****************************第一种3DES对称加密算法***********************/
    /**
     * 3des加密
     *
     * @param src
     * @param password_crypt_key
     * @return
     */
    public static String encryptMode(String src, String key) {

        String Algorithm = "DESede"; //定义加密算法，有DES、DESede(即3DES)、Blowfish
        //String password_crypt_key = "2012PinganVitality075522628888ForShenZhenBelter075561869839";
        try {
            SecretKey desKey = new SecretKeySpec(build3DesKey(key), Algorithm);
            Cipher c1 = Cipher.getInstance(Algorithm);    //实例化负责加密/解密的Cipher工具类
            c1.init(Cipher.ENCRYPT_MODE, desKey);    //初始化为加密模式
            return new String(Base64.encodeBase64(c1.doFinal(src.getBytes()))).trim(); //加密后再进行base64编码
        } catch (Exception e) {
            e.printStackTrace();
            //logger.error("3DES加密失败", e);
        }
        return null;
    }

    /**
     * 3des解密
     */
    public static String decryptMode(String src, String key) {
        String Algorithm = "DESede"; //定义加密算法，有DES、DESede(即3DES)、Blowfish
        try {
            SecretKey desKey = new SecretKeySpec(build3DesKey(key), Algorithm);
            Cipher c1 = Cipher.getInstance(Algorithm);    //实例化负责加密/解密的Cipher工具类
            c1.init(Cipher.DECRYPT_MODE, desKey);    //初始化为解密模式
            return new String(c1.doFinal(Base64.decodeBase64(src))).trim(); //先进行base64解码再进行3DES解密
        } catch (Exception e) {
            e.printStackTrace();
            //logger.error("3DES解密失败", e);
        }
        return null;
    }

    /**
     * 根据字符串生成密钥字节数组
     *
     * @param keyStr 密钥字符串
     * @return
     * @throws UnsupportedEncodingException
     */
    public static byte[] build3DesKey(String keyStr) throws UnsupportedEncodingException {
        byte[] key = new byte[24];    //声明一个24位的字节数组，默认里面都是0
        byte[] temp = keyStr.getBytes("UTF-8");    //将字符串转成字节数组
       
       /*
        * 执行数组拷贝
        * System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
        */
        if (key.length > temp.length) {
            //如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
            System.arraycopy(temp, 0, key, 0, temp.length);
        } else {
            //如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
            System.arraycopy(temp, 0, key, 0, key.length);
        }
        return key;
    }

}
