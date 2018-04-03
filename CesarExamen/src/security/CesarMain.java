package security;

import java.util.ArrayList;
import java.util.Scanner;

public class CesarMain {
    //Lista donde estan todos los caracteres del alfabeto
    static ArrayList<Character> alphabet = new ArrayList<Character>();;
    //Funcion para encriptar
    public static String cesar(String s,int n){
        boolean done=false;
        char[] r=s.toCharArray();
        String finalText="";
        int i=0;
        int j=0;
        int letraNum=0;
        //Bucle para cifrar
        while(!done){
            if (r[i]==alphabet.get(j)){
                letraNum=j+n;
                while(letraNum>alphabet.size()-1){
                    letraNum=letraNum-alphabet.size();
                }
                finalText=finalText+alphabet.get(letraNum);
                i++;
            }
            j++;
            if(j>alphabet.size()-1){
                j=0;
            }
            if(finalText.length()==s.length()){
                done=true;
            }
        }
        return finalText;
    }
    //Funcion para desencriptar
    public static String rasec(String s,int n){
        boolean done=false;
        char[] r=s.toCharArray();
        String finalText="";
        int i=0;
        int j=alphabet.size()-1;
        int letraNum=0;
        //Bucle para descifrar
        while(!done){
            if (r[i]==alphabet.get(j)){
                letraNum=j-n;
                while (letraNum<0){
                    letraNum=letraNum+alphabet.size();
                }
                finalText=finalText+alphabet.get(letraNum);
                i++;
            }
            j--;
            if(j<0){
                j=alphabet.size()-1;
            }
            if(finalText.length()==s.length()){
                done=true;
            }
        }
        return finalText;
    }
    //La funcion para el examen
    public static String xifratCesar(int numAMover,char mode,String text){
        String textResult=null;
        switch (mode){
            case 'E':
                textResult=cesar(text,numAMover);
                break;
            case 'D':
                textResult=rasec(text,numAMover);
                break;
            default:
                System.out.println("Error");
                break;
        }
        return textResult;
    }

    public static ArrayList fillAlph(ArrayList c){
        String k="ABCDEFGHIJKLMNÑOPQRSTUVWYZabcdefghijklmnñopqrstuvwyz1234567890 ";
        for (int i=0;i<k.length();i++){
            c.add(k.charAt(i));
        }
        return c;
    }

    public static void main(String[] args) {
        int changeNum;
        String textEncrypt;
        String opt;
        int optMenu=0;
        Scanner sc = new Scanner(System.in);


        alphabet=fillAlph(alphabet);
        System.out.println("\tMetodo Cesar");
        System.out.println("\t------ -----");

        do {
            System.out.println("1)Cifrar");
            System.out.println("2)Descifrar");
            System.out.println("0)Salir");
            optMenu=sc.nextInt();
            switch (optMenu){
                case 1:
                    System.out.println("Dame el numero para intercambiar");
                    changeNum = sc.nextInt();
                    System.out.println("Dame la frase a cifrar");
                    sc.nextLine();
                    textEncrypt=sc.nextLine();
                    textEncrypt=xifratCesar(changeNum,'E',textEncrypt);
                    System.out.println("Frase cifrada");
                    System.out.println(textEncrypt);
                    System.out.println("\t");
                    break;
                case 2:
                    System.out.println("Dame el numero para intercambiar");
                    changeNum = sc.nextInt();
                    System.out.println("Dame la frase a descifrar");
                    sc.nextLine();
                    textEncrypt=sc.nextLine();
                    textEncrypt=xifratCesar(changeNum,'D',textEncrypt);
                    System.out.println("Frase descifrada");
                    System.out.println(textEncrypt);
                    System.out.println("\t");
                    break;
            }
        }while (optMenu!=0);
        System.out.println("Vinga dew");
    }
}
