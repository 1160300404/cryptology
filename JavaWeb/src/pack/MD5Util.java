package pack;

import java.security.MessageDigest;

public class MD5Util {
    /**
     * 用MD5加密密码
     */
    public static String encodebyMD5(String info){
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));//获得密文
            byte[] encryption = md5.digest();//将密文转为16进制

            StringBuffer strBuf = new StringBuffer();
            //每次读取一个16进制位
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    strBuf.append("0").append(
                            Integer.toHexString(0xff & encryption[i]));
                } else {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }

            return strBuf.toString();
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }
    }
}

