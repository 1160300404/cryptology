package pack;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class enc {
    public static String salt="0fdfa5e5a88bebae640a5d88e7c84708";
    public static String jiami(String s){
        try {
            MessageDigest mDigest=MessageDigest.getInstance("MD5");
            mDigest.update(s.getBytes());
            byte[] bytes=mDigest.digest();
            StringBuffer strBuffer = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                strBuffer.append(Integer.toHexString(0xff & bytes[i]));
            }
            return  strBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }
}
