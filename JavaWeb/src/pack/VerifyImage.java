package pack;

import java.util.Random;

public class VerifyImage {
    private Random random=new Random();
    private static final char[] CHARS={
            '0','1','2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm',
            'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'    };
    public String createCode(){
        StringBuilder sb=new StringBuilder();        //利用random生成随机下标
        for(int i=0;i<4;i++){
            sb.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return sb.toString();
    }

}
