package com.liyz.boot3.api.test.controller.ecdh;

import cn.hutool.core.codec.Base64;
import com.liyz.boot3.common.util.CryptoUtil;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.generators.HKDFBytesGenerator;
import org.bouncycastle.crypto.params.HKDFParameters;
import org.springframework.security.crypto.codec.Hex;

import javax.crypto.KeyAgreement;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/4/7 9:58
 */
public class EcdhController {

    public static void main(String[] args) throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        ECGenParameterSpec spec = new ECGenParameterSpec("secp256r1");

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDH", "BC");

        keyPairGenerator.initialize(spec);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        PrivateKey privateKey = keyPair.getPrivate();
        System.out.println(Base64.encode(privateKey.getEncoded()));
        PublicKey publicKey = keyPair.getPublic();
        System.out.println(Base64.encode(publicKey.getEncoded()));

        // 模拟另一方，获得其公钥
        KeyPairGenerator keyGen2 = KeyPairGenerator.getInstance("ECDH", "BC");
        keyGen2.initialize(spec);
        KeyPair keyPair2 = keyGen2.generateKeyPair();
        System.out.println(Base64.encode(keyPair2.getPrivate().getEncoded()));
        PublicKey publicKey2 = keyPair2.getPublic();
        String encode = Base64.encode(publicKey2.getEncoded());
        System.out.println(encode);
        X509EncodedKeySpec priPKCS8 = new X509EncodedKeySpec(java.util.Base64.getDecoder().decode(encode.getBytes(StandardCharsets.UTF_8)));

        KeyFactory keyFactory = KeyFactory.getInstance("ECDH");
        PublicKey publicKey3 = keyFactory.generatePublic(priPKCS8);


        // 实现ECDH协议
        KeyAgreement keyAgreement = KeyAgreement.getInstance("ECDH", "BC");
        keyAgreement.init(privateKey);
        keyAgreement.doPhase(publicKey2, true);

        byte[] salt = Hex.decode("000102030405060708090a0b0c0d0e0f");

        byte[] info = Hex.decode("f0f1f2f3f4f5f6f7f8f9");

        int keySize = 256; // 生成的密钥长度（以位为单位）

        byte[] sharedSecret = keyAgreement.generateSecret();
        byte[] bytes = deriveAESKey(sharedSecret, salt, info, keySize);
        String aesKet = bytesToHex(bytes);
        System.out.println(aesKet);
        String encryptAES = CryptoUtil.Symmetric.encryptAES("就是你", aesKet);



    }

    // 使用HKDF从共享秘密中派生AES密钥
    private static byte[] deriveAESKey(byte[] sharedSecret, byte[] salt, byte[] info, int keySize) {
        HKDFBytesGenerator hkdf = new HKDFBytesGenerator(new SHA256Digest());
        HKDFParameters params = new HKDFParameters(sharedSecret, salt, info);
        hkdf.init(params);

        byte[] keyBytes = new byte[keySize / 8];
        hkdf.generateBytes(keyBytes, 0, keyBytes.length);

        return keyBytes;
    }



    // 辅助方法，将字节数组转换为十六进制字符串
    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 0xFF);
            if(hex.length() < 2){
                result.append(0);
            }
            result.append(hex);
        }
        return result.toString();
    }
}
