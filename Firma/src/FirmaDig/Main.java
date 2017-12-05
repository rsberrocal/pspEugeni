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
    private static final Scanner sc = new Scanner(System.in);
    //Funcion para generar llaves
    private static KeyPair genKeys(){
        //La llave principal
        KeyPair finalKeys=null;
        try{
            //Se crea una instancia de un generador de llaves
            KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
            //Se genera un par de llaves, publica y privada
            finalKeys=keygen.generateKeyPair();
        }catch (Exception e){
            //Por si sale algo malo
            e.printStackTrace();
        }
        //Se devuelven las llaves
        return finalKeys;
    }
    //Funcion para guardar llaves en archivos
    private static void saveKeys(KeyPair key, String fileName) throws Exception {
        //Array de bytes con las llaves
        byte[] publicKey = key.getPublic().getEncoded();
        byte[] privateKey = key.getPrivate().getEncoded();
        //Hace falta crear las carpetas si no estan
        FileOutputStream filePublicKey = new FileOutputStream("keys/"+fileName+"_publicKey.txt");
        //Se guarda la llave publica en su archivo
        filePublicKey.write(publicKey);
        //Se cierra el archivo
        filePublicKey.close();
        //Se le avisa al usuario que el archivo ha sido creado
        System.out.println("Fichero keys/"+fileName+"_publicKey.txt creado");
        FileOutputStream filePrivateKey = new FileOutputStream("keys/"+fileName+"_privateKey.txt");
        //Se guarda la llave privada en su archivo
        filePrivateKey.write(privateKey);
        //Se cierra el archivo
        filePrivateKey.close();
        //Se le avisa al usuario que el archivo ha sido creado
        System.out.println("Fichero keys/"+fileName+"_privateKey.txt creado");
    }//end

    //Funcion para guardar la firma
    private static void saveSignature(byte[] signature)throws Exception{
        String fileName;
        System.out.println("Introduce un nombre para guardar la firma");
        //Se guarda el nombre del archivo
        fileName=sc.nextLine();
        //Hace falta crear las carpetas si no estan
        FileOutputStream filePublicKey = new FileOutputStream("signatures/"+fileName+"_signature.txt");
        //Se escribe en el archivo
        filePublicKey.write(signature);
        //Se cierra el archivo
        filePublicKey.close();
        //Se le avisa al usuario que se ha creado el archivo
        System.out.println("Fichero signatures/"+fileName+"_signature.txt creado");
    }
    //Funcion para recoger la llave publica mediante un archivo
    private static PublicKey loadPublicKey(String fileName) throws Exception {
        //Hace falta crear las carpetas si no estan
        FileInputStream fis = new FileInputStream("keys/"+fileName);
        //Se pilla la cantidad de bytes que tiene el archivo
        int numBtyes = fis.available();
        //Array de bytes para guardar los bytes del archivo
        byte[] bytes = new byte[numBtyes];
        //Se lee el archivo y se guarda en el array de bytes
        fis.read(bytes);
        //Se cierra el archivo
        fis.close();
        //Se crea la estancia para generar la llave
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        //Se genera la llave a partir de los bytes
        KeySpec keySpec = new X509EncodedKeySpec(bytes);
        //Se recoge la llave publica
        PublicKey keyFromBytes = keyFactory.generatePublic(keySpec);
        //Se devuelve esta llave
        return keyFromBytes;

    }
    private static PrivateKey loadPrivateKey(String fileName) throws Exception {
        //Hace falta crear las carpetas si no estan
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

    private static byte[] genHashFromFile(String file){
        byte[]hashResult=null;
        byte[]textByte;
        try{
            //Se genera la instancia del hash
            //Hace falta crear las carpetas si no estan
            FileInputStream fis = new FileInputStream("files/"+file);
            //Se crea un array de bytes que contendra los bytes del archivo
            textByte= new byte[fis.available()];
            //Se lee el archivo y se guarda en el array
            fis.read(textByte);
            //Se cierra el archivo
            fis.close();
            //Se genera la instancia del hash
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            //Se crea el hash a partir del array de bytes
            hashResult=md.digest(textByte);
        }catch (Exception e){
            //Por si sale algun error
            e.printStackTrace();
        }
        //Se devuelve el hash
        return hashResult;
    }
    //Funcion para firmar el archivo
    private static void signFile(String fileKey,String file){
        //Array de bytes
        byte[]hash;
        byte[]signature;
        //Llave privada
        PrivateKey superKey;
        try {
            //Se recoge la llave mediante un archivo
            superKey=loadPrivateKey(fileKey);
            //Se genera el hash del archivo para encriptar
            hash=genHashFromFile(file);
            //Se crea la instancia
            Cipher cyberman = Cipher.getInstance("RSA");
            //Se asigna el modo encrypt junto con la llave
            cyberman.init(Cipher.ENCRYPT_MODE,superKey);
            //Se encripta todo el hash
            signature=cyberman.doFinal(hash);
            //Se guarda el hash
            saveSignature(signature);
        }catch (Exception e){
            //Si sale algun error se muestra y au
            e.printStackTrace();
        }
        //Se devuelve el texto cifrado en un idioma entendible
    }
    //Funcion para desencriptar con una llave
    private static byte[] decryptWithKey(PublicKey superKey, String singName){
        //Array de bytes;
        byte[]signByte=null;
        byte[]textByte=null;
        try {
            //Se crea la instancia
            Cipher Cyberman = Cipher.getInstance("RSA");
            //Se le asigna el modo y la llave
            Cyberman.init(Cipher.DECRYPT_MODE,superKey);
            //Se lee el archivo donde esta la firma
            //Hace falta crear las carpetas si no estan
            FileInputStream fis = new FileInputStream("signatures/"+singName);
            //Se crea un array de bytes que guardara la firma del archivo
            signByte= new byte[fis.available()];
            //Se lee el archivo y se llena el array
            fis.read(signByte);
            //Se cierra el archivo
            fis.close();
            //Se desencripta la firma para obtener el hash original
            textByte=Cyberman.doFinal(signByte);
        }catch (Exception e){
            //El error que suele salir es que la llave no es la correcta y textByte queda en null
        }
        //Se devuelve el array de bytes
        return textByte;
    }
    //Funcion para verificar archivos
    private static void checkFile(String filename, String fileKey, String fileSign){
        //Array de bytes
        byte[]hash;
        byte[]hashToCompare;
        //Llave publica del remitente
        PublicKey userPublicKey;
        try{
            //Se recoge la llave publica del archivo
            userPublicKey=loadPublicKey(fileKey);
            //Se desencripta la firma para comparar el hash original
            hashToCompare=decryptWithKey(userPublicKey,fileSign);
            //Se genera el hash del archivo que se ha enviado
            hash=genHashFromFile(filename);
            //Finalmente se comparan los hashes
            if(Arrays.equals(hash,hashToCompare)){
                //Si es igual significa que no se ha modifica el archivo y es el original
                System.out.println("El archivo es correcto e integro");
            }else {
                //Sino significa que la han modificado y no es el mismo que el original
                System.out.println("El archivo ha sido tocado Errorror");
            }
        }catch (Exception e){
            System.out.println("Ha ocurrido algo malo: "+e.getMessage());
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
                    //Mensaje de salida
                    System.out.println("Vinga dew");
                    break;
                case 1:
                    //Se envia un archivo con su firma
                    System.out.println("Introduce el archivo para firmar");
                    //Se recoge el archivo para firmar
                    fileName=sc.nextLine();
                    System.out.println("Introduce el archivo donde esta tu llave privada para firmar");
                    //Se recoge la llave privada del usuario para encriptar el archivo
                    fileKey=sc.nextLine();
                    //Se firma
                    signFile(fileKey,fileName);
                    break;
                case 2:
                    //Se recoge el archivo a verificar
                    System.out.println("Introduce el archivo para verificar");
                    fileName=sc.nextLine();
                    //Se recoge la firma
                    System.out.println("Introduce la firma");
                    fileSign=sc.nextLine();
                    //Se recoge la llave publica
                    System.out.println("Introduce la clave publica del remitente");
                    fileKey=sc.nextLine();
                    //Se verifica el archivo
                    checkFile(fileName,fileKey,fileSign);
                    break;
                case 3:
                    //Se generan las llaves
                    userKeys=genKeys();
                    //Se recoge el nombre de los archivos
                    System.out.println("Introduce el nombre para guardar las llaves");
                    fileName=sc.nextLine();
                    try {
                        //Se guardan las llaves en dos archivos
                        saveKeys(userKeys,fileName);
                    }catch (Exception e){
                        //Por si sale algun error
                        e.printStackTrace();
                    }
                    break;
                default:
                    //Por si el usario pone un numero incorrecto
                    System.out.println("Numero incorrecto");
                    break;
            }
        }while (menuOption!=0);

    }
}
