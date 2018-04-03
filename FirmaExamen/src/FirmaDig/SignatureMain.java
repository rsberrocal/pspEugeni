package FirmaDig;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.*;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Scanner;

public class SignatureMain {
    private static final Scanner sc = new Scanner(System.in);
    //Funcion para generar llaves
    private static KeyPair genKeys(){
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

    //Funcion para guardar la firma
    private static void saveSignature(byte[] signature)throws Exception{
        //Hace falta crear las carpetas si no estan
        FileOutputStream filePublicKey = new FileOutputStream("files/signature.txt");
        //Se escribe en el archivo
        filePublicKey.write(signature);
        //Se cierra el archivo
        filePublicKey.close();
        //Se le avisa al usuario que se ha creado el archivo
        System.out.println("Firma creada");
    }

    private static byte[] genHashFromFile(String file,String mode){
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
            MessageDigest md = MessageDigest.getInstance(mode);
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
    private static byte[] creaFirma(String fileName,String mode,PrivateKey pk_user){
        //Array de bytes
        byte[]hash;
        byte[]signature=null;
        //Llave privada
        try {
            //Se genera el hash del archivo para encriptar
            hash=genHashFromFile(fileName,mode);
            //Se crea la instancia
            Cipher cyberman = Cipher.getInstance("RSA");
            //Se asigna el modo encrypt junto con la llave
            cyberman.init(Cipher.ENCRYPT_MODE,pk_user);
            //Se encripta todo el hash
            signature=cyberman.doFinal(hash);
        }catch (Exception e){
            //Si sale algun error se muestra y au
            e.printStackTrace();
        }
        return signature;
        //Se devuelve el texto cifrado en un idioma entendible
    }
    //Funcion para desencriptar con una llave
    private static byte[] decryptWithKey(PublicKey superKey, String signName){
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
            FileInputStream fis = new FileInputStream(signName);
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
    private static boolean verificaFirma(String filename, String fileSign,String mode,PublicKey pk_user){
        //Array de bytes
        boolean check=false;
        byte[]hash;
        byte[]hashToCompare;
        try{
            //Se desencripta la firma para comparar el hash original
            hashToCompare=decryptWithKey(pk_user,fileSign);
            //Se genera el hash del archivo que se ha enviado
            hash=genHashFromFile(filename,mode);
            //Finalmente se comparan los hashes
            if(Arrays.equals(hash,hashToCompare)){
                //Si es igual significa que no se ha modifica el archivo y es el original
                check=true;
            }
        }catch (Exception e){
            System.out.println("Ha ocurrido algo malo: "+e.getMessage());
        }
        return check;
    }


    public static void main(String[] args) {
        int menuOption;
        String fileName;
        KeyPair userKeys=genKeys();
        byte[]firma;
        do {
            System.out.println("\tMenu");
            System.out.println("\t----");
            System.out.println("1)Crea Firma");
            System.out.println("2)Verificar Firma");
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
                    //Se firma
                    firma=creaFirma(fileName,"MD5",userKeys.getPrivate());
                    try{
                        saveSignature(firma);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    //Se recoge el archivo a verificar
                    System.out.println("Introduce el archivo para verificar");
                    fileName=sc.nextLine();
                    //Se recoge la firma
                    //Se verifica el archivo
                    String signFile="files/signature.txt";
                    if(verificaFirma(fileName,signFile,"MD5",userKeys.getPublic())){
                        System.out.println("Todo correcto");
                    }else{
                        System.out.println("Todo incorrecto");
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
