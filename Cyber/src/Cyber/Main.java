package Cyber;

import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;


public class Main {

    public static String cripta(String key,String text){
        byte[]textByte={'A'};
        try {
            SecretKeySpec batKey = new SecretKeySpec(key.getBytes(),"DES");
            SecretKeyFactory factorio = SecretKeyFactory.getInstance("DES");
            SecretKey superKey = factorio.generateSecret(batKey);

            Cipher cyberCafe = Cipher.getInstance("DES");
            cyberCafe.init(Cipher.ENCRYPT_MODE,superKey);
            textByte=text.getBytes();
            textByte=cyberCafe.doFinal(textByte);
        }catch (Exception e){
            e.printStackTrace();
        }
        return DatatypeConverter.printBase64Binary(textByte);
    }

    public static String uncripta(String key,String text){
        byte[]textByte={'A'};
        try {
            SecretKeySpec batKey = new SecretKeySpec(key.getBytes(),"DES");
            SecretKeyFactory factorio = SecretKeyFactory.getInstance("DES");
            SecretKey superKey = factorio.generateSecret(batKey);

            Cipher cyberCafe = Cipher.getInstance("DES");
            cyberCafe.init(Cipher.DECRYPT_MODE,superKey);
            textByte=DatatypeConverter.parseBase64Binary(text);
            text= new String(cyberCafe.doFinal(textByte));
        }catch (Exception e){
            e.printStackTrace();
        }
        return text;
    }

    public static void main(String [ ] args){
        String text;
        String key;

        int opt;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("\tCyberCafe");
            System.out.println("\t---------");
            System.out.println("1)Cifrar");
            System.out.println("2)Descifrar");
            System.out.println("0)Salir");
            opt=sc.nextInt();
            sc.nextLine();
            switch (opt){
                case 1:
                    System.out.println("Dame una frase para cifrar");
                    text=sc.nextLine();
                    System.out.println("A continuación dame la clave");
                    key=sc.nextLine();
                    System.out.println("Frase cifrada");
                    System.out.println("----- -------");
                    System.out.println(cripta(key,text));
                    break;
                case 2:
                    System.out.println("Dame una frase para descifrar");
                    text=sc.nextLine();
                    System.out.println("A continuación dame la clave");
                    key=sc.nextLine();
                    System.out.println("Frase descifrada");
                    System.out.println("----- ----------");
                    System.out.println(uncripta(key,text));
                    break;
            }
        }while(opt!=0);

    }
}
