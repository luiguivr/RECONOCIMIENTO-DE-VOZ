package pkg2parcialia;

import java.io.FileReader;
import javax.speech.Central;
import javax.speech.EngineModeDesc;
import javax.speech.recognition.Recognizer;
import javax.speech.recognition.Result;
import javax.speech.recognition.ResultAdapter;
import javax.speech.recognition.ResultEvent;
import javax.speech.recognition.ResultToken;
import javax.speech.recognition.RuleGrammar;
import java.util.Locale;
//libreria como utilidades diversas
import javax.speech.*;
//contiene los elementos necesarios para hacer uso de los recursos de audio de la pc
import javax.speech.recognition.*;
//permite hacer uso de las funciones de reconocimiento de voz
import java.io.*;
//permite el acceso a archivos externos a la carpeta del proyecto

public class Main extends ResultAdapter{

    static Recognizer recognizer;
    String palabra;
    
   
    //metodo que recibe la cadena de texto proveniente del arcgivo gramatica
    @Override
    public void resultAccepted(ResultEvent re){
    //por si el programa genere un error no se cierre si no que muestre el mensaje de que ocurrio un error
        try{
            Result res = (Result)(re.getSource());
            //se obtiene uno de los elementos de la cadena de texto 
            ResultToken tokens[] = res.getBestTokens();
            //cadena que guardara la palabra 
            String args[] = new String[1];
            args[0]="";
            //ciclo que contendra un elemento hasta formar una palabra
            for (int i=0; i<tokens.length; i++){
                palabra = tokens[i].getSpokenText();
                args[0] += palabra;
                System.out.print(palabra + " ");
            }//cierre del for para crear la palabra
            System.out.println();
            //si palabra es igual a salir terminara el programa
            if(palabra.equals("salir")){
                recognizer.deallocate();
                args[0]="Hata la proxima curso";
                System.out.println(args[0]);
                //muestra 
                System.exit(0);
            }//fin del if salir
            else{
                recognizer.suspend();
                recognizer.resume();
            }//fin del else de salir
        }//fin del try
        catch(Exception ex){
            System.out.println("ha ocurrido un error" + ex);
        }
    
    }
            
    public static void main(String[] args) {
        try{
            //crea el objeto de reconizer
            recognizer = Central.createRecognizer(new EngineModeDesc(Locale.ROOT));
            //inicilaizamos el objeto
            recognizer.allocate();
            //se especifica la ruta del archivo de texto donde van a estar las palabras
            FileReader fileReader = new FileReader("D:\\my_grammar.grammar");
            //establece la forma o estructura del archivo tx donde estaran las palabras
            RuleGrammar grammar = recognizer.loadJSGF(fileReader);
            // inicializamos el archivo txt
            grammar.setEnabled(true);
            
            //se hace referencia a la clase main del reconocimiento de voz
            recognizer.addResultListener(new Main());
            //empezar a dictar las palabras 
            System.out.println("empezar dictado");
            recognizer.commitChanges();
            recognizer.requestFocus();
            recognizer.resume();
        }
        catch(Exception e){
            System.out.println("Error en" + e.toString() );
            e.printStackTrace();
            System.exit(0);
        }
    }
    
}
