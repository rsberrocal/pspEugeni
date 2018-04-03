package Cyber;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;


public class XifratMain {
    public static byte[] loadBytes(String file){
        byte[] bytes = null;
        //Hace falta crear las carpetas si no estan
        try{
            FileInputStream fis = new FileInputStream("files/"+file);
            //Se pilla la cantidad de bytes que tiene el archivo
            int numBtyes = fis.available();
            //Array de bytes para guardar los bytes del archivo
            bytes = new byte[numBtyes];
            //Se lee el archivo y se guarda en el array de bytes
            fis.read(bytes);
            //Se cierra el archivo
            fis.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return bytes;
    }

    public static SecretKey genKey(int numPass){
        SecretKey FinalKey=null;
        String pass;
        //Si el numero es menor de 10 se le añade un 0 para tener dos bytes
        if(numPass<10){
            pass="0"+String.valueOf(numPass);
        }else{
            pass=String.valueOf(numPass);
        }
        try{
            //Se genera la instancia del hash
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            //Se crea el hash con el numero como contraseña
            byte[] hash=md.digest(pass.getBytes("UTF-8"));
            byte[] key= Arrays.copyOf(hash,192/8);
            //Se crea una llave simetrica
            //System.out.println(Cipher.getMaxAllowedKeyLength("AES"));
            FinalKey= new SecretKeySpec(key,"AES");
        }catch (Exception e){
            //Por si sale algun error
            e.printStackTrace();
        }
        //Se devuelve la llave
        return FinalKey;
    }
    private static void saveKey(byte[] key,String fileName) throws Exception {
        //Hace falta crear las carpetas si no estan
        FileOutputStream fileKey = new FileOutputStream("files/"+fileName);
        //Se guarda la llave publica en su archivo
        fileKey.write(key);
        //Se cierra el archivo
        fileKey.close();
        //Se le avisa al usuario que el archivo ha sido creado
    }//end

    //Funcion para recoger la llave publica mediante un archivo
    private static SecretKey loadKey(String fileName) throws Exception {
        SecretKey FinalKey = null;
        //Hace falta crear las carpetas si no estan
        FileInputStream fis = new FileInputStream("files/"+fileName);
        //Se pilla la cantidad de bytes que tiene el archivo
        int numBtyes = fis.available();
        //Array de bytes para guardar los bytes del archivo
        byte[] bytes = new byte[numBtyes];
        //Se lee el archivo y se guarda en el array de bytes
        fis.read(bytes);
        //Se cierra el archivo
        fis.close();
        //Se crea la estancia para generar la llave
        FinalKey= new SecretKeySpec(bytes,"AES");
        //Se devuelve esta llave
        return FinalKey;

    }

    //Funcion para encriptar
    public static void cripta(int key,String textFile,String keyFile){
        byte[]textByte=loadBytes(textFile);
        SecretKey keyToUse = genKey(key);
        try{
            saveKey(keyToUse.getEncoded(),keyFile);
            Cipher cyberCafe = Cipher.getInstance("AES");
            cyberCafe.init(Cipher.ENCRYPT_MODE,keyToUse);
            textByte=cyberCafe.doFinal(textByte);
            saveKey(textByte,"holaholacifrado.txt");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Funcion para desencriptar
    public static void uncripta(String fileName,String fileKey){
        byte[]textByte=loadBytes(fileName);
        try {
            SecretKey keyToDecrypt=loadKey(fileKey);

            Cipher cyberCafe = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cyberCafe.init(Cipher.DECRYPT_MODE,keyToDecrypt);
            System.out.println(new String(cyberCafe.doFinal(textByte)));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String [ ] args){
        String textFile="holahola.txt";
        String encryptedFile="holaholacifrado.txt";
        String keyFile="key.txt";
        int key;
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
                    System.out.println("Dame la llave con dos cifras");
                    key=sc.nextInt();
                    sc.nextLine();
                    System.out.println("\tFrase cifrada");;
                    cripta(key,textFile,keyFile);
                    break;
                case 2:
                    System.out.println("\tFrase descifrada");
                    System.out.println("----- ----------");
                    uncripta(encryptedFile,keyFile);
                    break;
            }
        }while(opt!=0);

    }
}
