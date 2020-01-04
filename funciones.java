import java.util.List;
import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.lang.Object;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class funciones {

	public static class  Hebra extends Thread{
		String funcion;
		public Hebra(String funcion){
			this.funcion = funcion;
		}
		public String getFuncion(){
			return this.funcion;
		}
		public void setFuncion(String funcion){
			this.funcion = funcion;
		}
		public Double evalFun(int x){ // Funcion que tiene por objetivo reemplazar el valor X dentro del string por el valor entregado para luego evaluar dicha funcion.
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("js"); // Utilizada para evaluar funciones.
			engine.put("x", x);
			try {
					Object operation = engine.eval(this.funcion);
					return (double)operation;
			} catch (ScriptException e) {
					e.printStackTrace();
			}
			return (double)0;
		}
		public Double run(int x,Hashtable<String, Hebra> H ){
				Double valor = 0.0;
				Double finaal = 0.0;
				final String regex = "[a-z]\\(x\\)"; // Puede ser una Funcion, un numero o un simbolo.
				final Pattern pattern = Pattern.compile(regex);
				final Matcher matcher = pattern.matcher(this.funcion);
				// Buscamos si la funcion contiene otras funciones.
				final List<String> resultado = new ArrayList<String>(); // Guardamos lo obtenido en una lista temporal
				while (matcher.find()) {
						resultado.add(matcher.group());
				}
				// Si no posee niuna funcion en su interior, evalua directamente.
				if(resultado.size()==0){
					finaal = evalFun(x);
				}
				// Si no debemos encontrar los valores respectivos a dichas funciones.
				else{
					String temporal = this.funcion; // Generamos un String auxiliar de nuestra funcion original para su posterior edicion.
					// Reemplazamos en la funcion auxiliar los valores de las funciones que contenga con sus valores respectivos.
					for (int i = 0; i<resultado.size(); i++){ // Revisamos todas las funciones que se encontrasen anteriormente.
						String Funcion = resultado.get(i);
						valor = H.get(resultado.get(i).substring(0,resultado.get(i).length()-3)).run(x,H); // Evaluamos la funcion con x
						temporal = temporal.replace(Funcion,String.valueOf(valor)); // Reemplazamos todas las apariciones de la funcion con el valor obtenido
					}

					//Una vez finalizado lo anterior, procedemos a evaluar la X dentro de la ecuacion en caso de existir.
					ScriptEngineManager manager = new ScriptEngineManager();
					ScriptEngine engine = manager.getEngineByName("js"); // Utilizada para evaluar funciones.
					engine.put("x", x);
					try {
							Object operation = engine.eval(temporal); // Evaluamos
							finaal = (double)operation; // Valor final
					} catch (ScriptException e) {
							e.printStackTrace();
					}
				}
				return finaal;

		}
	}

	public static void main(String[] args) throws FileNotFoundException{
		//variables
		File mapFile = new File("funciones.txt");
		Scanner scan = new Scanner(mapFile);
		String linea = ""; // Variable auxiliar para leer las lineas del texto.
		Hashtable<String, Hebra> H = new Hashtable<String, Hebra>();
		int flg = 0; // Auxiliar para evitar la primera linea

    while(scan.hasNextLine()){ // Leemos linea a linea del texto
			linea = scan.nextLine();
			//variables para el regex
			final String regex = "[a-z]\\(x\\)=|.*"; // Puede ser una Funcion, un numero o un simbolo.
			final Pattern pattern = Pattern.compile(regex);
			final Matcher matcher = pattern.matcher(linea);
			final List<String> resultado = new ArrayList<String>(); // Guardamos lo obtenido en una lista temporal
			//buscamos todas las coincidencias
			while (matcher.find()) {
					//agregando una por una a la lista
					//matcher.group() devuelve la coincidencia del último matcher.find()
					resultado.add(matcher.group());
			}
			// Primer caso no nos interesa
			if(flg!=0){
				Hebra hilo = new Hebra(resultado.get(1)); // Creamos la hebra con la funcion respectiva
				H.put(resultado.get(0).substring(0,resultado.get(0).length()-4), hilo); // Le quitamos la seccion (x)= del string
			}
			flg=1;
		}

		System.out.println("Funciones Ingresadas !");
		System.out.println("[ Ingresa '0' para salir ]");
		String entradaTeclado = "";
		Scanner entradaEscaner = new Scanner (System.in); //Creación de un objeto Scanner
		entradaTeclado = entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanner

		// Seccion de codigo que se encarga de iterar el programa junto con mostrar por consola la informacion respectiva.
		String salir = "0";
		while(salir.equals(entradaTeclado) == false){
			String entrada = entradaTeclado;
			linea = entrada;
			//variables para el regex
			final String regex = "\\(\\d+(,\\d+)?\\)|[a-z]*"; // Obtenemos la funcion respectiva con el valor a evaluar.
			final Pattern pattern = Pattern.compile(regex);
			final Matcher matcher = pattern.matcher(linea);
			final List<String> resultado = new ArrayList<String>(); // Guardamos lo obtenido en una lista temporal
			//buscamos todas las coincidencias
			while (matcher.find()) {
					//agregando una por una a la lista
					//matcher.group() devuelve la coincidencia del último matcher.find()
					resultado.add(matcher.group());}
			if(resultado.size()<=2){
				System.out.println("Entrada no valida");
			}
			else{
				String val = resultado.get(1).substring(1,resultado.get(1).length()-1);
				double Resultado = H.get(resultado.get(0)).run(Integer.parseInt(val),H);
				System.out.println("El resultado es: "+Resultado);
			}
			entradaTeclado = entradaEscaner.nextLine ();
		}

	}

}
