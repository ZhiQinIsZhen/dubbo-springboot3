package com.liyz.boot3.common.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.Pair;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.AsymmetricAlgorithm;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.DES;
import com.liyz.boot3.common.remote.exception.CommonExceptionCodeEnum;
import com.liyz.boot3.common.remote.exception.RemoteServiceException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.security.KeyPair;
import java.util.Objects;

/**
 * Desc:加解密工具类
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/9/15 10:07
 */
@Slf4j
@UtilityClass
public class CryptoUtil {

    /**
     * 对称加密
     *
     * 加密算法
     *     DES:
     *     AES:
     *
     * 加密模式
     *     ECB: 电子密码本，需要加密的消息按照块密码的块大小被分为数个块，并对每个块进行独立加密
     *          优点：可以进行处理数据
     *          缺点：同样的原文生成同样的密文，不能很好的保护数据
     *     CBC: 密码块链接，每个明文块先于前一个密文块进行异或后，再进行加密，每个密文块都依赖于它前面的所有明文块
     *          优点：同样的原文生成的密文不一样
     *          缺点：串行处理数据
     *
     * 填充模式
     *     NoPadding不填充
     *          1.在DES加密算法下, 要求原文长度必须是8byte的整数倍
     *          2.在AES加密算法下, 要求原文长度必须是16byte的整数倍
     *     PKCS5Padding
     *          数据块的大小为8位, 不够就补足
     *
     */
    @UtilityClass
    public static class Symmetric {
        private static final int[] AES_KEY_SIZES = new int[]{16, 24, 32};

        /**
         * 加密
         * DES加密算法，ECB模式
         *
         * @param content 明文
         * @param key 加密key 长度不能小于8，超过8，也会只取8位长度
         * @return 密文
         */
        public static String encryptDES(String content, String key) {
            if (StringUtils.isBlank(content)) {
                return content;
            }
            if (Objects.isNull(key) || key.getBytes().length < 8) {
                throw new RemoteServiceException(CommonExceptionCodeEnum.DEC_KEY_LENGTH_ERROR);
            }
            DES des = new DES(Mode.ECB, Padding.PKCS5Padding, key.getBytes());
            return des.encryptBase64(content);
        }

        /**
         * 解密
         * DES加密算法，ECB模式
         *
         * @param content 明文
         * @param key 加密key 长度不能小于8，超过8，也会只取8位长度
         * @return 密文
         */
        public static String decryptDES(String content, String key) {
            if (StringUtils.isBlank(content)) {
                return content;
            }
            if (Objects.isNull(key) || key.getBytes().length < 8) {
                throw new RemoteServiceException(CommonExceptionCodeEnum.DEC_KEY_LENGTH_ERROR);
            }
            DES des = new DES(Mode.ECB, Padding.PKCS5Padding, key.getBytes());
            return des.decryptStr(content);
        }

        /**
         * 加密
         * DES加密算法，CBC模式
         *
         * @param content 明文
         * @param key 加密key 长度不能小于8，超过8，也会只取8位长度
         * @param iv 偏移量 长度不能小于8，超过8，也会只取8位长度
         * @return 密文
         */
        public static String encryptDES(String content, String key, String iv) {
            if (StringUtils.isBlank(content)) {
                return content;
            }
            if (Objects.isNull(key) || key.getBytes().length < 8) {
                throw new RemoteServiceException(CommonExceptionCodeEnum.DEC_KEY_LENGTH_ERROR);
            }
            if (Objects.isNull(iv) || iv.getBytes().length != 8) {
                throw new RemoteServiceException(CommonExceptionCodeEnum.DEC_IV_LENGTH_ERROR);
            }
            DES des = new DES(Mode.CBC, Padding.PKCS5Padding, key.getBytes(), iv.getBytes());
            return des.encryptBase64(content);
        }

        /**
         * 解密
         * DES加密算法，CBC模式
         *
         * @param content 明文
         * @param key 加密key 长度不能小于8，超过8，也会只取8位长度
         * @param iv 偏移量 长度不能小于8，超过8，也会只取8位长度
         * @return 密文
         */
        public static String decryptDES(String content, String key, String iv) {
            if (StringUtils.isBlank(content)) {
                return content;
            }
            if (Objects.isNull(key) || key.getBytes().length < 8) {
                throw new RemoteServiceException(CommonExceptionCodeEnum.DEC_KEY_LENGTH_ERROR);
            }
            if (Objects.isNull(iv) || iv.getBytes().length != 8) {
                throw new RemoteServiceException(CommonExceptionCodeEnum.DEC_IV_LENGTH_ERROR);
            }
            DES des = new DES(Mode.CBC, Padding.PKCS5Padding, key.getBytes(), iv.getBytes());
            return des.decryptStr(content);
        }

        /**
         * 加密
         * AES加密算法，ECB模式
         *
         * @param content 明文
         * @param key 加密key 长度只能为16,24,32
         * @return 密文
         */
        public static String encryptAES(String content, String key) {
            if (StringUtils.isBlank(content)) {
                return content;
            }
            if (Objects.isNull(key) || keySizeValid(key.getBytes().length)) {
                throw new RemoteServiceException(CommonExceptionCodeEnum.AEC_KEY_LENGTH_ERROR);
            }
            AES aes = new AES(Mode.ECB, Padding.PKCS5Padding, key.getBytes());
            return aes.encryptBase64(content);
        }

        /**
         * 解密
         * AES加密算法，ECB模式
         *
         * @param content 明文
         * @param key 加密key 长度只能为16,24,32
         * @return 密文
         */
        public static String decryptAES(String content, String key) {
            if (StringUtils.isBlank(content)) {
                return content;
            }
            if (Objects.isNull(key) || keySizeValid(key.getBytes().length)) {
                throw new RemoteServiceException(CommonExceptionCodeEnum.AEC_KEY_LENGTH_ERROR);
            }
            AES aes = new AES(Mode.ECB, Padding.PKCS5Padding, key.getBytes());
            return aes.decryptStr(content);
        }

        /** 对称加密
         * 加密
         * AES加密算法，CBC模式
         *
         * @param content 明文
         * @param key 加密key 长度只能为16,24,32
         * @param iv 偏移量 长度只能为16
         * @return 密文
         */
        public static String encryptAES(String content, String key, String iv) {
            if (StringUtils.isBlank(content)) {
                return content;
            }
            if (Objects.isNull(key) || keySizeValid(key.getBytes().length)) {
                throw new RemoteServiceException(CommonExceptionCodeEnum.AEC_KEY_LENGTH_ERROR);
            }
            if (Objects.isNull(iv) || iv.getBytes().length != 16) {
                throw new RemoteServiceException(CommonExceptionCodeEnum.AEC_IV_LENGTH_ERROR);
            }
            AES aes = new AES(Mode.CBC, Padding.PKCS5Padding, key.getBytes(), iv.getBytes());
            return aes.encryptBase64(content);
        }

        /**
         * 解密
         * AES加密算法，CBC模式
         *
         * @param content 明文
         * @param key 加密key 长度只能为16,24,32
         * @param iv 偏移量 长度只能为16
         * @return 密文
         */
        public static String decryptAES(String content, String key, String iv) {
            if (StringUtils.isBlank(content)) {
                return content;
            }
            if (Objects.isNull(key) || keySizeValid(key.getBytes().length)) {
                throw new RemoteServiceException(CommonExceptionCodeEnum.AEC_KEY_LENGTH_ERROR);
            }
            if (Objects.isNull(iv) || iv.getBytes().length != 16) {
                throw new RemoteServiceException(CommonExceptionCodeEnum.AEC_IV_LENGTH_ERROR);
            }
            AES aes = new AES(Mode.CBC, Padding.PKCS5Padding, key.getBytes(), iv.getBytes());
            return aes.decryptStr(content);
        }

        /**
         * 验证key的长度
         *
         * @param length key的长度
         * @return 是否通过验证
         */
        private static boolean keySizeValid(int length) {
            for (int aesKeySize : AES_KEY_SIZES) {
                if (length == aesKeySize) {
                    return false;
                }
            }
            return true;
        }
    }


    /**
     * 非对称加密
     *
     * 加密算法
     *     RSA:
     *     ECC:
     */
    @UtilityClass
    public static class Asymmetric {

        private static final String KEY_ALGORITHM = "RSA";

        /**
         * 生成密钥对
         *
         * @return 秘钥对：L-公钥；R-私钥
         */
        public static Pair<String, String> generateKeyPair() {
            return generateKeyPair(KEY_ALGORITHM);
        }

        /**
         * 生成密钥对
         *
         * @param algorithm 加密算法
         * @return 秘钥对：L-公钥；R-私钥
         */
        public static Pair<String, String> generateKeyPair(String algorithm) {
            KeyPair pair = SecureUtil.generateKeyPair(algorithm);
            return Pair.of(Base64.encode(pair.getPublic().getEncoded()), Base64.encode(pair.getPrivate().getEncoded()));
        }

        /**
         * 公钥加密
         *
         * @param content 明文
         * @param publicKey 公钥
         * @return 密文
         */
        public static String encryptPublicRSA(String content, String publicKey) {
            RSA rsa = new RSA(AsymmetricAlgorithm.RSA_ECB_PKCS1.getValue(), null, publicKey);
            return rsa.encryptBase64(content, KeyType.PublicKey);
        }

        /**
         * 私钥解密
         *
         * @param content 密文
         * @param privateKey 私钥
         * @return 明文
         */
        public static String decryptPrivateRSA(String content, String privateKey) {
            RSA rsa = new RSA(AsymmetricAlgorithm.RSA_ECB_PKCS1.getValue(), privateKey, null);
            return rsa.decryptStr(content, KeyType.PrivateKey);
        }

        /**
         * 私钥加密
         *
         * @param content 明文
         * @param privateKey 私钥
         * @return 密文
         */
        public static String encryptPrivateRSA(String content, String privateKey) {
            RSA rsa = new RSA(AsymmetricAlgorithm.RSA_ECB_PKCS1.getValue(), privateKey, null);
            return rsa.encryptBase64(content, KeyType.PrivateKey);
        }

        /**
         * 公钥解密
         *
         * @param content 密文
         * @param publicKey 公钥
         * @return 铭文
         */
        public static String decryptPublicRSA(String content, String publicKey) {
            RSA rsa = new RSA(AsymmetricAlgorithm.RSA_ECB_PKCS1.getValue(), null, publicKey);
            return rsa.decryptStr(content, KeyType.PublicKey);
        }
    }

}
