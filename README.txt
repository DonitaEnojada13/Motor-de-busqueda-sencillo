README
Motor de Búsqueda (Proyecto 02)
Integrantes

-Cantero Zavaleta Hector
-Sánchez Girón Yael Adrian

Descripción del Proyecto
Este proyecto es un Motor de Búsqueda diseñado bajo la combinacion de arboles de busqueda y listas ligadas. 
Su objetivo es indexar archivos de texto plano (.txt) y permitir consultas de términos para devolver los resultados 
más relevantes basados en el cálculo de TF-IDF y la Similitud.

Índice Invertido: Implementado mediante un Árbol Binario Rojinegro de diseño propio, que garantiza búsquedas en tiempo logarítmico.  
Sinergia de Datos: Cada nodo del árbol almacena un término y una Lista Ligada de objetos Conteo, que registran el archivo y la frecuencia de aparición.  
Procesamiento de Relevancia: Se utiliza una fase de precalculación de normas (longitud de los vectores de cada documento) para asegurar que el ranking final sea eficiente y preciso.

Inconvenientes Presentados
-Normalización de Vectores: Un reto importante fue el cálculo eficiente del denominador de la función de similitud 
(la raíz de la suma de cuadrados de los pesos de todos los términos del documento). Se resolvió implementando una 
pasada inicial por el árbol para precalcular estos valores.  
-Manejo de Memoria: Al procesar grandes cantidades de archivos, 
fue necesario asegurar que las listas ligadas dentro de cada nodo del árbol no generaran redundancia innecesaria.

Instrucciones para Ejecutarlo

- Compila todos los archivos fuente desde la terminal
- Ejecuta la clase principal
- El programa solicitará primero la ruta del directorio que contiene los archivos .txt. 
  Una vez procesados, podrás ingresar consultas de una o más palabras para obtener el Top 10 de relevancia
  
  

