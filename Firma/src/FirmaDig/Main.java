package FirmaDig;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.*;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static KeyPair genKeys(){
        KeyPair finalKeys=null;
        try{
            KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
            finalKeys=keygen.generateKeyPair();
        }catch (Exception e){
            e.printStackTrace();
        }
        return finalKeys;
    }

    static void saveKeys(KeyPair key, String fileName) throws Exception {
        byte[] publicKey = key.getPublic().getEncoded();
        byte[] privateKey = key.getPrivate().getEncoded();
        FileOutputStream filePublicKey = new FileOutputStream("keys/"+fileName+"_publicKey.txt");
        filePublicKey.write(publicKey);
        filePublicKey.close();
        System.out.println("Fichero keys/"+fileName+"_publicKey.txt creado");
        FileOutputStream filePrivateKey = new FileOutputStream("keys/"+fileName+"_privateKey.txt");
        filePrivateKey.write(privateKey);
        filePrivateKey.close();
        System.out.println("Fichero keys/"+fileName+"_privateKey.txt creado");
    }

    public static void saveSignature(byte[]signature)throws Exception{
        String fileName;
        System.out.println("Introduce un nombre para guardar la firma");
        fileName=sc.nextLine();
        FileOutputStream filePublicKey = new FileOutputStream("signatures/"+fileName+"_signature.txt");
        filePublicKey.write(signature);
        filePublicKey.close();
        System.out.println("Fichero signatures/"+fileName+"_signature.txt creado");
    }
    public static PublicKey loadPublicKey(String fileName) throws Exception {
        FileInputStream fis = new FileInputStream("keys/"+fileName);
        int numBtyes = fis.available();
        byte[] bytes = new byte[numBtyes];
        fis.read(bytes);
        fis.close();

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        KeySpec keySpec = new X509EncodedKeySpec(bytes);
        PublicKey keyFromBytes = keyFactory.generatePublic(keySpec);
        return keyFromBytes;

    }
    public static PrivateKey loadPrivateKey(String fileName) throws Exception {
        FileInputStream fis = new FileInputStream("keys/"+fileName);
        int numBtyes = fis.available();
        byte[] bytes = new byte[numBtyes];
        fis.read(bytes);
        fis.close();

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        KeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
        PrivateKey keyFromBytes = keyFactory.generatePrivate(keySpec);
        return keyFromBytes;
    }

    public static byte[] genHashFromFile(String file){
        byte[]hashResult=null;
        byte[]textByte;
        try{
            //Se genera la instancia del hash
            FileInputStream fis = new FileInputStream("files/"+file);
            textByte= new byte[fis.available()];
            fis.read(textByte);
            fis.close();
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            //Se crea el hash con el numero como contraseña
            hashResult=md.digest(textByte);
        }catch (Exception e){
            //Por si sale algun error
            e.printStackTrace();
        }
        //Se devuelve la llave
        return hashResult;
    }

    public static void signFile(String fileKey,String file){
        byte[]hash;
        byte[]signature;
        PrivateKey superKey;
        try {
            //Se crea la instancia
            superKey=loadPrivateKey(fileKey);
            hash=genHashFromFile(file);
            Cipher cyberman = Cipher.getInstance("RSA");
            //Se asigna el modo encrypt junto con la llave
            cyberman.init(Cipher.ENCRYPT_MODE,superKey);
            //Se recogen en un array los bytes del texto que ha puesto el usuario
            //Y se encripta finalmente
            signature=cyberman.doFinal(hash);
            saveSignature(signature);
        }catch (Exception e){
            //Si sale algun error se muestra y au
            e.printStackTrace();
        }
        //Se devuelve el texto cifrado en un idioma entendible
    }

    public static byte[] decryptWithKey(PublicKey superKey,String singName){
        byte[]signByte=null;
        byte[]textByte=null;
        try {
            //Se crea la instancia
            Cipher Cyberman = Cipher.getInstance("RSA");
            //Se le asigna el modo y la llave
            Cyberman.init(Cipher.DECRYPT_MODE,superKey);
            //Se desencripta el texto y se pasa en un array de bytes con Base64
            FileInputStream fis = new FileInputStream("signatures/"+singName);
            signByte= new byte[fis.available()];
            fis.read(signByte);
            fis.close();
            textByte=Cyberman.doFinal(signByte);
        }catch (Exception e){
            //El error que suele salir es que la llave no es la correcta y textByte queda en null
        }
        //Se devuelve el array de bytes
        return textByte;
    }

    public static void checkFile(String filename,String fileKey,String fileSign){
        byte[]hash;
        byte[]hashToCompare;
        byte[]signature=null;
        PublicKey userPublicKey;
        try{
            userPublicKey=loadPublicKey(fileKey);
            hashToCompare=decryptWithKey(userPublicKey,fileSign);
            hash=genHashFromFile(filename);
            if(hash==hashToCompare){
                System.out.println("El archivo es correcto e integro");
            }else {
                System.out.println("El archivo ha sido tocado Errorror");
            }
        }catch (Exception e){

        }
    }

    public static void main(String[] args) {
        int menuOption;
        String fileName;
        String fileKey;
        String fileSign;
        KeyPair userKeys;

        do {
            System.out.println("\tMenu");
            System.out.println("\t----");
            System.out.println("1)Enviar con firma");
            System.out.println("2)Verificar archivo");
            System.out.println("3)Generar llaves");
            System.out.println("0)Salir");
            menuOption=sc.nextInt();
            sc.nextLine();
            switch (menuOption){
                case 0:
                    System.out.println("Vinga dew");
                    break;
                case 1:
                    System.out.println("Introduce el archivo para firmar");
                    fileName=sc.nextLine();
                    System.out.println("Introduce el archivo donde esta tu llave privada para firmar");
                    fileKey=sc.nextLine();
                    signFile(fileKey,fileName);
                    break;
                case 2:
                    System.out.println("Introduce el archivo para verificar");
                    fileName=sc.nextLine();
                    System.out.println("Introduce la firma");
                    fileSign=sc.nextLine();
                    System.out.println("Introduce la clave publica del remitente");
                    fileKey=sc.nextLine();
                    checkFile(fileName,fileKey,fileSign);
                    break;
                case 3:
                    userKeys=genKeys();
                    System.out.println(userKeys.getPublic().getEncoded());
                    System.out.println("Introduce el nombre para guardar las llaves");
                    fileName=sc.nextLine();
                    try {
                        saveKeys(userKeys,fileName);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("Numero incorrecto");
                    break;
            }
        }while (menuOption!=0);

    }
}
