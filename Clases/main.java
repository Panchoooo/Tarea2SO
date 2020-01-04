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

public class main {

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
		public Double evalFun(int x){
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("js"); // Utilizada para evaluar funciones.
			engine.put("x", x);
			try {
					Object operation = engine.eval(this.funcion);
					System.out.println("Evaluado operacion: " + operation);
					return (double)operation;
			} catch (ScriptException e) {
					e.printStackTrace();
			}
			return (double)0;
		}
	}

	public static void main(String[] args) throws FileNotFoundException{
		//variables
		File mapFile = new File("Clases/funciones.txt");
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
			System.out.println(resultado);
			// Primer caso no nos interesa
			if(flg!=0){
				Hebra hilo = new Hebra(resultado.get(1)); // Creamos la hebra con la funcion respectiva
				H.put(resultado.get(0).substring(0,resultado.get(0).length()-4), hilo);
			}
			flg=1;
		}

		System.out.println(H.get("h").getFuncion()); // Llave: Nombre Fun , Valor: Hebra respect.
		H.get("h").evalFun(3); // Al evaluar h(3)

	}

}
