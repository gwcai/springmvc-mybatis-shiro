package com.spring.base.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;

/**
 * @author lili14520@gmail.com
 * @date 2018/7/6
 */
public class MD5Util {

    private static char hexdigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


    /**
     * 获取单个文件的MD5值！
     *
     * @param inStr 文件内容
     * @return
     */
    public static String getRowMD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuilder hexValue = new StringBuilder();
        for (byte md5Byte : md5Bytes) {
            int val = ((int) md5Byte) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }


    /**
     * @param file:要加密的文件
     * @return MD5摘要码
     * @funcion 对文件全文生成MD5摘要
     */

    public static String getMD5(File file) {

        FileInputStream fis = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(file);
            byte[] buffer = new byte[2048];
            int length = -1;
            while ((length = fis.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }
            byte[] b = md.digest();
            return byteToHexString(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(null != fis) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    /**
     * @param tmp 要转换的byte[]
     * @return 十六进制字符串表示形式
     * @function 把byte[]数组转换成十六进制字符串表示形式
     */

    private static String byteToHexString(byte[] tmp) {
        String s;
        char str[] = new char[16 * 2];
        int k = 0; // 表示转换结果中对应的字符位置
        for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节转换成 16 进制字符的转换
            byte byte0 = tmp[i]; // 取第 i 个字节
            str[k++] = hexdigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换, >>> 为逻辑右移，将符号位一起右移
            str[k++] = hexdigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
        }
        s = new String(str); // 换后的结果转换为字符串
        return s;
    }


    public static String Md5SaltEncode(String rawPass, String passType, String salt) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance(passType);
            //加密后的字符串
            result = byteToHexString(md.digest(mergePasswordAndSalt(rawPass, salt).getBytes("utf-8")));
        } catch (Exception ex) {
        }
        return result;
    }

    public static String mergePasswordAndSalt(String password, String salt) {
        if (password == null) {
            password = "";
        }

        if ((salt == null) || "".equals(salt)) {
            return password;
        } else {
            return password + "{" + salt.toString() + "}";
        }
    }

}
