package Actividad;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 *
 * @author MARIANA
 */
public class Analizadorlexico {

    private String valor;
    private Tipos tipo;

    public Analizadorlexico(String valor, Tipos tipo) {
        this.valor = valor;
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Tipos getTipo() {
        return tipo;
    }

    public void setTipo(Tipos tipo) {
        this.tipo = tipo;
    }

    //Define las constantes que se van a validar 
    enum Tipos {
        NUMERO, SIMBOLO;
    }
    /* A CONTINUACIÓN LA EJECUCIÓN DEL EJERCICIO */
    //Ejecuta el ejercicio
    public static void main(String[] args) {
        try {
            System.out.println("Bienvenido");
            System.out.println("Este es un programa analizador lexico, identifica si el valor introducido es el esperado y evalua si es correcto");
            System.out.println("A continuacion te mostrare los valores correctos:");
            System.out.println("1. 7");
            System.out.println("2. 2.77");
            System.out.println("3. 0.77");
            System.out.println("4. .");
            System.out.println("A continuacion te mostrare los valores incorrectos:");
            System.out.println("1. 7..");
            System.out.println("2. 0.7.");
            System.out.println("Si ingresas alguno de estos valores o distintos se evaluara y te mostrara si el valor es correcto o incorrecto");
            System.out.println("Por favor Ingrese un valor decimal o entero");
            Scanner sc = new Scanner(System.in);
            String entrada = sc.nextLine();//Lee lo que el usuario ingresó
            System.out.println("Recibi el siguiente valor: " + entrada);

            //Almaceno lo que retorna el metodo lexico para despues recorrerlo y mostrar uno a uno lo que ingreso el usuario
            ArrayList<Analizadorlexico> tokens = analizadorLexico(entrada);

            //Recorre la lista de Token y muestra cada uno de los caracteres ingresados
            for (Analizadorlexico token : tokens) {
                if (token.getTipo() == Analizadorlexico.Tipos.NUMERO) {
                    System.out.println("Numero: " + token.getValor());
                }
                if (token.getTipo() == Analizadorlexico.Tipos.SIMBOLO) {
                    System.out.println("Simbolo: " + token.getValor());
                }
            }//Finaliza ciclo For
            //Compila la expresión regular en un patrón, el automaticamente crea el automata
            Pattern pattern = Pattern.compile("^(\\d+(\\.\\d*)?|\\.)$");
            //Busca las coincidencias en la cadena de texto en base a la exp. regular de arriba
            Matcher matcher = pattern.matcher(entrada);
            //Valida que la entrada cumpla con la expresion regular
            if (matcher.matches()) {
                System.out.println(entrada + " es un valor valido.");
            } else {
                System.out.println(entrada + " es un valor invalido.");
            }
        } catch (Exception e) {
            System.err.println("Exception main main: " + e.toString());
        }
    }//Finaliza main

    //Recibe la entrada del usuario, analiza la entrada, recorre los caracteres y 
    //los adiciona a la lista de Token
    private static ArrayList<Analizadorlexico> analizadorLexico(String entrada) {
        ArrayList<Analizadorlexico> tokens = new ArrayList<>();
        try {
            //Se convierte la cadena en un array de caracteres, para acceder a cada numero o simbolo
            //de manera individual
            char[] caracteres = entrada.toCharArray();
            //Manipula la cadena sin crear un nuevo objeto
            StringBuilder numero = new StringBuilder();

            //Se itera sobre cada caracter para identificar numeros y el punto
            for (char c : caracteres) {
                //Valida con la expresion regular de que sea un numero
                if (Pattern.matches("\\d", Character.toString(c))) {
                    //Se adiciona el valor si es un número
                    numero.append(c);
                } else {
                    if (numero.length() > 0) {
                        //Agrega el valor y el tipo al que pertenece
                        tokens.add(new Analizadorlexico(numero.toString(), Analizadorlexico.Tipos.NUMERO));
                        numero.setLength(0);
                    }
                    //Valida con la expresion regular de que sea un punto
                    if (Pattern.matches("\\.", Character.toString(c))) {
                        //Se adiciona el valor si es un punto y el tipo al que pertenece
                        tokens.add(new Analizadorlexico(Character.toString(c), Analizadorlexico.Tipos.SIMBOLO));
                    }
                }
            }//Finaliza ciclo For
            //Se adiciona el ultimo numero 2.4->este
            if (numero.length() > 0) {
                tokens.add(new Analizadorlexico(numero.toString(), Analizadorlexico.Tipos.NUMERO));
            }//Finaliza If
        } catch (Exception e) {
            System.err.println("Exception main lexico: " + e.toString());
        }
        return tokens;
    }//Finaliza metodo

}
