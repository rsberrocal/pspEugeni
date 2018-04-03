package Main;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;

public class Main {
    //Funcion para encriptar con el hash
    public static String encryptWithHash(SecretKey superKey,String userText){
        byte[]textByte=null;
        try {
            //Se crea la instancia
            Cipher cyberman = Cipher.getInstance("AES");
            //Se asigna el modo encrypt junto con la llave
            cyberman.init(Cipher.ENCRYPT_MODE,superKey);
            //Se recogen en un array los bytes del texto que ha puesto el usuario
            textByte=userText.getBytes();
            //Y se encripta finalmente
            textByte=cyberman.doFinal(textByte);
        }catch (Exception e){
            //Si sale algun error se muestra y au
            e.printStackTrace();
        }
        //Se devuelve el texto cifrado en un idioma entendible
        return DatatypeConverter.printBase64Binary(textByte);
    }
    //Funcion para generar un hash mediante un numero como contraseña
    public static SecretKey genHash(int numPass){
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
            byte[] key= Arrays.copyOf(hash,16);
            //Se crea una llave simetrica
            FinalKey= new SecretKeySpec(key,"AES");
        }catch (Exception e){
            //Por si sale algun error
            e.printStackTrace();
        }
        //Se devuelve la llave
        return FinalKey;
    }

    //Funcion para desencriptar
    public static byte[] decryptWithHash(SecretKey superKey,String encryptedText){
        byte[]textByte=null;
        try {
            //Se crea la instancia
            Cipher Cyberman = Cipher.getInstance("AES/ECB/PKCS5Padding");
            //Se le asigna el modo y la llave
            Cyberman.init(Cipher.DECRYPT_MODE,superKey);
            //Se desencripta el texto y se pasa en un array de bytes con Base64
            textByte=Cyberman.doFinal(DatatypeConverter.parseBase64Binary(encryptedText));
        }catch (Exception e){
            //El error que suele salir es que la llave no es la correcta y textByte queda en null
            return textByte;
        }
        //Se devuelve el array de bytes
        return textByte;
    }
    //Funcion para encontrar la llave que desencripta el texto
    public static void discoverKey(String encryptedText){
        //Boolean para saber cuando se ha encontrado
        int i=0;
        SecretKey posibleKey=null;
        byte[] posibleText;
        System.out.println("Buscando el texto descifrado");
        //Bucle para encontrar cada posiblidad de llave
        while(i<100){
            //Se genera un hash con el numero que toca
            posibleKey=genHash(i);
            //Se intenta desincriptar con esa llave
            posibleText=decryptWithHash(posibleKey,encryptedText);
            //Si no es un null es que la ha encontrado
            if(posibleText!=null){
                System.out.println("Una posible solucion");
                System.out.println("--- ------- --------");
                //Se pasa el array de bits a Base64 para desencriptarlo y que salga el texto original
                System.out.println(new String(Base64.getDecoder().decode(DatatypeConverter.printBase64Binary(posibleText))));
                //Al encontrarse la frase se pone el boolean a true45
            }
            //Si no la encuentra suma el contador para la siguiente iteracion
            i++;
        }
    }

    public static void main(String [] args){
        //Variables principales
        SecretKey mainKey;
        int numPass;
        String textEncrypted;
        Scanner sc = new Scanner(System.in);
        //Bucle para controlar que el numero este en el rango aceptable
        do{
            //Se pide el numero al usuario
            System.out.println("Dame un numero del 1 al 99");
            numPass=sc.nextInt();
            //Para eleminar el buffer
            sc.nextLine();
            //Si no entra en el rango se le avisa al usuario
            if(numPass>=100||numPass<=0){
                System.out.println("Numero incorrecto");
            }else{
                //Si entra en el rango se crea primero la llave
                mainKey=genHash(numPass);
                //Se pide que el usuario una frase para cifrar
                System.out.println("Dame una frase a cifrar");
                textEncrypted=sc.nextLine();
                //Se encripta la frase
                textEncrypted=encryptWithHash(mainKey,textEncrypted);
                //Y se muestra al usuario
                System.out.println("Texto encriptado");
                System.out.println(textEncrypted);
                //Por ultimo se busca que frase era sin pillar la variable
                discoverKey(textEncrypted);
            }
        }while(numPass>=100||numPass<=0);

    }
}