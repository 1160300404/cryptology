package pack;

import mysql.sqlop;
import net.sf.json.JSONObject;
import org.omg.CORBA.PUBLIC_MEMBER;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RequsetServlet  extends HttpServlet {
    public void init() throws ServletException {
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out=response.getWriter();
        out.write("nono");
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out=response.getWriter();
        //out.write("ok");
        String SHOP_SERVER_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApqf3qq/W9f0rvyfrtTdxEPOPkEO4p2n7\n" +
                "sDWMmUH+Sk5fmk6nBkH3KsYI+xNV7z373OAvd8nF3O+xnT+oD9VJD4SxEIgaEBn0Gv5xLE8Q/hjL\n" +
                "tFaDMtc6vYBh/zSEv/QdaOzIXun7UzorqBHwqd2XFAlgdDX5EJj8rq5x+72mZ91PVn9h+7tmgdhk\n" +
                "vuX6bsPSg6Wfvr4Yk57z2QW28kE/hR/9ZfV+y4Se9+/7WMCCb7DlWQDd31HFVdyUFZtHZzkquCpJ\n" +
                "SPVPfWA1QKYl4IXucWIoMKhLjf0XAWpuUYrOEXCIS9rSv+LIdiiE32Klp8JZnfqcLihhmV/f1awx\n" +
                "yvyojwIDAQAB";
        String BANK_PRIVATE_KEY="MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCJjTX/EejTxT2etKWASpoPxz0r\n" +
                "5j0vjpUPdR6SkU0NgUisxry2KGKrO4sJkbbFfd7METwGZHhIl+gDnp1D8MCk8mxsWHhEHMq9v8Ad\n" +
                "3bp9XmJnctvZA1T5aV2Bcf4B6gbG8YATmb0EwNaExBp4FyrO8vHAcXNycQlp1xUakltjDnZTBfSp\n" +
                "z0Jbpmc5KKnPcZIPO6qnjxRZFO63Kp/9j0N/MapMvFLwv+gxLu2mc/31ht7t8/X5/wd20KviyfVz\n" +
                "5qetYIxn9Zx2CWkKBHM6DaE5Jb6Z4udsqAkP9kq0j+DNjCXmNH8Wtbc/p9v3bn9cqhb6M7pBi2wI\n" +
                "8ve1pMkcXsjtAgMBAAECggEAei8UgtH+1nZDiUGALdiWx2M9BEzNlvv5jrSDhsBJCLEJxGf443o2\n" +
                "Q1Jt6/8isoVfrje08Ry3+AvvzZ9MpTgrd5ncE9X2ZtKIiUrWNfFqociActIFVdzu02nz5HNEpaCj\n" +
                "in6lPUP4Mku1glPMC1bQ8Om/MynT2hlsujaTCXpucSrpdECaPRGnHTT+8/MRe5fDvAuaAJL7CtI8\n" +
                "fahKaIWsImDnz15PCBRRHulFkK4/kstn1uWVwxGDNiR0xT6NJ2VxrdMmSV/6j1lu/BoQwrVil3Rh\n" +
                "fbIBe25VjCZbXFQyoWIKe7i3nm2d1NbNMd9zhh6spnBrXUxZAJFe6vyDxoWfgQKBgQDh2Zgj7Qa6\n" +
                "6BSvRghcvu2PLbYOBS9e9gJZVLp7itaXvAIX068F+JoTFgQ9Cf61uLucnmx3mrSo+IhX1kSoTGkI\n" +
                "GD6s339MA+89s4Puun2K2auNR8Y8FpTYNpbGq5D8MKfhrbWeB0j6rAemm0dbUSyP7Tb4LRu0Kt83\n" +
                "cT7c73SEYQKBgQCb6gbIzSj5Xny2BhsMbEFLo2bGxBAo23LEvLgzC1KiD7TXe0NsWR6npNL5tNxS\n" +
                "tk0Z2jcrqUyRDqLVXyXuZJdbbvVcfEjKrCzvsl6rsbKwn96ylI5QGXuUHbBGAWhU9Ro3K4NqdZGT\n" +
                "4nl2RLT/bKktlVBNc2bJHWUevuftOe4QDQKBgELL71JYzQ/EijGYneqIUYxyMZEN+Ye1bNZs18ao\n" +
                "NnWkFl5jrjUu5KrWbSR8a0flvh9BP8VUDTCYaQLJjX7VMo9BVHAenfCmjUCLcm7N20mQvUtIuhmV\n" +
                "eCpGYuuZXIUqAs0dakS11ODGtnCRJlacuHhM6WAXP0VXFLO6G6SOccChAoGAC0MmZk7bjNfUblmW\n" +
                "jpkrRklxXR5AhJMFgUyYiadhuq8jwwT2Y9c0LavsSXlVCOx+OUXYAmFiVIAYJw2Ocd/2RvaG5r1b\n" +
                "jLhPLqlVyVU2sSd7MK8mcIbGtHZIi2YCoVBrKl0MSx5e+626VF62LFUvC0nj2RRi8lgbO9NScYJj\n" +
                "vekCgYBVv8WS1b/UGgXhUsmiDLWVDnelLPkDrWU2sr7FOLdo4byBdDEMi9ioXZLIzYtEYX0lzO8U\n" +
                "wMTVtU8Qla8RucoLDGPEd3hsjRJtTVptFhMx20w612gQ5GJ9nYKMq/4+EaIE3XfMRIX9J75LDAs3\n" +
                "fTf/+UP/j0qNMdTLDN5VS8Hddw==";

        String ID=request.getParameter("name");
        ID=ID.replaceAll(" ","+");
        System.out.println("ID:"+ID);
       /* JSONObject result=JSONObject.fromObject(ID);
        String info=(String)result.get("content");*/
        try {
            String bytes=RSA_AES.serverDecrypt(ID);
            System.out.println("bytes :"+bytes);
            ID=bytes;
            String[] strings=ID.split("//");
            //String[] strings1=strings[0].split("=");
            String contrast=MD5Util.encodebyMD5(MD5Util.encodebyMD5(strings[1])+strings[2]);
            byte[] bytes1=RSACoder.publicDecrypt(RSACoder.base642Byte(strings[0]),RSACoder.string2PublicKey(SHOP_SERVER_PUBLIC_KEY));
            String contrast1=new String(bytes1);
            System.out.println(contrast);
            System.out.println("contrast1: "+contrast1);
            if(contrast.equals(contrast1)){
                String[] info=strings[1].split(";");
                System.out.println(info[0]+info[1]+info[2]);
                String result= null;
                try {
                    result = sqlop.tsfaccount(info[0],info[2],Double.valueOf(info[1]));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                if(result.equals("yes")) {
                    System.out.println(result);
                    out.write("ok");
                }
                else {
                    out.write("no");
                }
            }
            else {
                out.write("no");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
