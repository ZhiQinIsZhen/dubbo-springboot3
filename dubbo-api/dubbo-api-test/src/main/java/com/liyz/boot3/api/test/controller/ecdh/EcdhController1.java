package com.liyz.boot3.api.test.controller.ecdh;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.HexUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/4/7 9:58
 */
public class EcdhController1 {

        /**
         * 非对称加密算法ECDH
         */
        public static final String KEY_ALGORITHM = "ECDH";

        /**
         * 本地密钥算法，即对称加密密钥算法，可选Blowfish、RC2、RC4算法
         */
        public static final String SECRET_ALGORITHM = "Blowfish";

        /**
         * 密钥长度
         */
        private static final int KEY_SIZE = 256;

        /**
         * 公钥
         */
        private static final String PUBLIC_KEY = "ECDHPublicKey";

        /**
         * 私钥
         */
        private static final String PRIVATE_KEY = "ECDHPrivateKey";

        /**
         * 初始化甲方密钥
         */
        public static Map<String, Object> initKey() throws Exception {
            Security.addProvider(new BouncyCastleProvider());
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            ECGenParameterSpec spec = new ECGenParameterSpec("secp256r1");
            keyPairGenerator.initialize(spec);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();
            ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();
            Map<String, Object> keyMap = new HashMap<>(2);
            keyMap.put(PUBLIC_KEY, publicKey);
            keyMap.put(PRIVATE_KEY, privateKey);
            return keyMap;
        }

        /**
         * 初始化乙方密钥
         */
        public static Map<String, Object> initKey(byte[] key) throws Exception {
            //解析甲方公钥
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(key);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey pubKey = keyFactory.generatePublic(x509EncodedKeySpec);
            //由甲方公钥构建乙方密钥
            ECParameterSpec ecParameterSpec = ((ECPublicKey)pubKey).getParams();
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(keyFactory.getAlgorithm());
            keyPairGenerator.initialize(ecParameterSpec);
            KeyPair keyPair = keyPairGenerator.genKeyPair();
            ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();
            ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();
            Map<String, Object> keyMap = new HashMap<>(2);
            keyMap.put(PUBLIC_KEY, publicKey);
            keyMap.put(PRIVATE_KEY, privateKey);
            return keyMap;
        }


        /**
         * 构建本地密钥
         */
        public static byte[] getSecretKey(byte[] publicKey, byte[] privateKey) throws Exception {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            //还原公钥
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(x509EncodedKeySpec);
            //还原私钥
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey);
            PrivateKey priKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            //生成本地密钥
            KeyAgreement keyAgreement = KeyAgreement.getInstance(keyFactory.getAlgorithm());
            keyAgreement.init(priKey);
            keyAgreement.doPhase(pubKey, true);
            SecretKey secretKey = keyAgreement.generateSecret(SECRET_ALGORITHM);
            return secretKey.getEncoded();
        }

        /**
         * 获取私钥
         */
        public static byte[] getPrivateKey(Map<String, Object> keyMap) throws Exception {
            Key key = (Key) keyMap.get(PRIVATE_KEY);
            return key.getEncoded();
        }

        /**
         * 获取公钥
         */
        public static byte[] getPublicKey(Map<String, Object> keyMap) throws Exception {
            Key key = (Key) keyMap.get(PUBLIC_KEY);
            return key.getEncoded();
        }

        /**
         * 加密
         */
        public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
            SecretKey secretKey = new SecretKeySpec(key, SECRET_ALGORITHM);
            Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(data);
        }

        /**
         * 解密
         */
        public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
            SecretKey secretKey = new SecretKeySpec(key, SECRET_ALGORITHM);
            Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(data);
        }


        public static void main(String[] args) throws Exception {
            String str = "hello world";
            //甲方密钥
            Map<String, Object> aMap = initKey();
            System.out.println("甲方公钥：" + HexUtil.encodeHexStr(getPublicKey(aMap)));
            System.out.println("甲方公钥：" + Base64.encode(getPublicKey(aMap)));
            //乙方密钥，ECDH算法根据甲方公钥生成
            Map<String, Object> bMap = initKey(getPublicKey(aMap));
            System.out.println("乙方公钥：" + HexUtil.encodeHexStr(getPublicKey(bMap)));
            System.out.println("乙方公钥：" + Base64.encode(getPublicKey(bMap)));
            //甲方本地密钥,使用甲方私钥和乙方公钥构建
            byte[] aKey = getSecretKey(getPublicKey(bMap), getPrivateKey(aMap));

            System.out.println("甲方本地密钥：" + HexUtil.encodeHexStr(aKey));
            //甲方加密
            byte[] encrypt = encrypt(str.getBytes(), aKey);
            System.out.println("加密后的数据：" + HexUtil.encodeHexStr(encrypt));
            //乙方本地密钥，使用乙方私钥和甲方公钥构建
            byte[] bKey = getSecretKey(getPublicKey(aMap), getPrivateKey(bMap));
            System.out.println("乙方本地密钥：" + HexUtil.encodeHexStr(bKey));
            //乙方解密
            byte[] decrypt = decrypt(encrypt, aKey);
            System.out.println("解密后的数据：" + new String(decrypt));
        }


}
