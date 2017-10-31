package security;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static ArrayList<Character> alphabet = new ArrayList<Character>();;
    public static String cesar(String s,int n){
        boolean done=false;
        char[] r=s.toCharArray();
        String finalText="";
        int i=0;
        int j=0;
        while(!done){
            if (r[i]==alphabet.get(j)){
                if((j+n)>alphabet.size()-1){
                    finalText=finalText+alphabet.get((j+n)-alphabet.size());
                }else{
                    finalText=finalText+alphabet.get(j+n);
                }
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
    public static String rasec(String s,int n){
        boolean done=false;
        char[] r=s.toCharArray();
        String finalText="";
        int i=0;
        int j=alphabet.size()-1;
        while(!done){
            if (r[i]==alphabet.get(j)){
                if((j-n)<0){
                    finalText=finalText+alphabet.get((j-n)+alphabet.size());
                }else{
                    finalText=finalText+alphabet.get(j-n);
                }
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

    public static ArrayList fillAlf(ArrayList c){
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


        alphabet=fillAlf(alphabet);
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
                    textEncrypt=sc.next();
                    textEncrypt=cesar(textEncrypt,changeNum);
                    System.out.println("Frase cifrada");
                    System.out.println(textEncrypt);
                    System.out.println("\t");
                    break;
                case 2:
                    System.out.println("Dame el numero para intercambiar");
                    changeNum = sc.nextInt();
                    System.out.println("Dame la frase a descifrar");
                    textEncrypt=sc.next();
                    textEncrypt=rasec(textEncrypt,changeNum);
                    System.out.println("Frase descifrada");
                    System.out.println(textEncrypt);
                    System.out.println("\t");
                    break;
            }
        }while (optMenu!=0);
        System.out.println("Vinga dew");
    }
}
