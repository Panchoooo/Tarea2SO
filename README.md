# Tarea2SO

Esteban Barrios 201773530-9
Francisco Riveros 201773581-1

Problema 1:

Para resolverlo se procede a analizar línea por línea el archivo de texto para luego generar un objeto de clase hebra en el cual se guarda la función en forma de String y se implementan los métodos para su ejecución. Por otro lado, para saber a qué función se está llamando se genera un diccionario en donde su llave es el nombre de la función y su valor la hebra asociada a esta. De esta forma al pedir una función basta con acceder al diccionario y ejecutarla. 

Las mayores complicaciones que existieron a lo largo de este problema, en primer lugar, fue la forma de evaluar las funciones anidadas dado que es necesario hacer un llamado recursivo de las funciones que lo componen hasta encontrar una "función base" que nos entrega un valor con la cual se podrá ir aplicando las funciones anteriores de forma recursiva y retornar su valor correspondiente. En segundo lugar, fue el cómo encontrar las herramientas necesarias para resolver las operaciones aritméticas presentes en cada función.
