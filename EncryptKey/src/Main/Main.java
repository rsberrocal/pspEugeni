package Main;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static String encryptWithHash(SecretKey superKey,String userText){
        byte[]textByte=null;
        try {

            Cipher cyberman = Cipher.getInstance("AES");
            cyberman.init(Cipher.ENCRYPT_MODE,superKey);
            textByte=userText.getBytes();
            textByte=cyberman.doFinal(textByte);
        }catch (Exception e){
            e.printStackTrace();
        }
        return DatatypeConverter.printHexBinary(textByte);
    }

    public static SecretKey genHash(int numPass){
        SecretKey FinalKey=null;
        String pass;

        if(numPass<10){
            pass="0"+String.valueOf(numPass);
        }else{
            pass=String.valueOf(numPass);
        }
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash=md.digest(pass.getBytes("UTF-8"));
            byte[] key= Arrays.copyOf(hash,16);
            FinalKey= new SecretKeySpec(key,"AES");
        }catch (Exception e){
            e.printStackTrace();
        }
        return FinalKey;
    }

    public static byte[] decryptWithHash(SecretKey superKey,String encryptedText){
        byte[]textByte=null;
        try {

            Cipher Cyberman = Cipher.getInstance("AES");
            Cyberman.init(Cipher.DECRYPT_MODE,superKey);
            System.out.println(DatatypeConverter.parseHexBinary(encryptedText).length);
            textByte=Cyberman.doFinal(DatatypeConverter.parseHexBinary(encryptedText));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return textByte;
    }

    public static void discoverKey(String encryptedText){
        boolean found=false;
        int i=0;
        SecretKey posibleKey=null;
        byte[] posibleText;
        while(!found){
            posibleKey=genHash(i);
            posibleText=decryptWithHash(posibleKey,encryptedText);
            System.out.println(posibleKey);
            if(posibleText!=null){

                found=true;
            }else{
                i++;
            }
        }
    }

    public static void main(String [] args){
        SecretKey mainKey;
        int numPass;
        String textEncrypted;
        Scanner sc = new Scanner(System.in);


        do{
            System.out.println("Dame un numero del 1 al 99");
            numPass=sc.nextInt();
            sc.nextLine();
            if(numPass>=100||numPass<=0){
                System.out.println("Numero incorrecto");
            }else{
                mainKey=genHash(numPass);
                System.out.println("Dame una frase a cifrar");
                textEncrypted=sc.nextLine();
                textEncrypted=encryptWithHash(mainKey,textEncrypted);
                System.out.println(textEncrypted);
                System.out.println(DatatypeConverter.parseHexBinary(textEncrypted).length);
                discoverKey(textEncrypted);
            }
        }while(numPass>=100||numPass<=0);

    }
}