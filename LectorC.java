//Bryan Alberto Martínez Orellana
//Carnét 23542
//Ingeniería en Ciencias de la Computación
//Programación Orientada a Objetos
//Creación: 16/09/2023
//Última modificación: 17/09/2023

//Importamos las librerías a utilizar
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;

public class LectorC {
    //Declaración de las variables
    private String nombreCSV = "";
    private Scanner teclado = new Scanner(System.in);
    private List<String[]> filas = new ArrayList<>();
    private String linea;
    private int numLinea = 0;
    private String[] columnas;
    private String id_curso;
    private String id_sede;
    private String nombreCurso;
    private String horario;
    private String Duracion;
    private String Dias;
    private String cantEst;
    private Set<Integer> UnicosID = new HashSet<>();
    private String[] diasDisponibles = {"lunes", "martes", "miercoles", "jueves", "viernes", "sabado"};
    private Set<String> diasValidos = new HashSet<>(Arrays.asList(diasDisponibles));    
    private boolean validarDias = false;
    private ArrayList<Curso> cursos = new ArrayList<>();
    private boolean validacion = false;

    //Leemos el CSV de los cursos
    public void Bienvenida(){
        System.out.println("Ahora probemos con el segundo CSV, el que tiene la información de los cursos...");
        System.out.println("Por favor, indique cuál es el nombre de su archivo CSV, coloque el nombre y csv al final para que funcione");
        nombreCSV = teclado.nextLine();
    }

    //Getter para la validacion
    public boolean getValidacion(){
        return validacion;
    }

    //Setter para numLinea
    public void setNumLinea(int numLinea){
        this.numLinea = numLinea;
    }

    //Getter para la lista de cursos
    public ArrayList<Curso> getCursos(){
        return this.cursos;
    }

    //Nos aseguramos que se estén empleados los tipos de datos requeridos
    public void VerificarCSV(){
        //Se comienza la lectura del archivo
        numLinea = 0;
        try(BufferedReader br = new BufferedReader(new FileReader(nombreCSV))){
            br.readLine();
            //Ciclo para estudiar cada línea del CSV
            while((linea = br.readLine()) != null){
                //Avanzamos una linea
                numLinea++;
                //Separamos los datos uno por uno
                columnas = linea.split(",");
                //Verificamos el tamaño
                if(columnas.length == 7){
                    id_curso = columnas[0];
                    id_sede = columnas[1];
                    nombreCurso = columnas[2];
                    horario = columnas[3];
                    Duracion = columnas[4];
                    Dias = columnas[5];
                    cantEst = columnas[6];

                    //Comenzamos a validar los tipos
                    try{
                        //Verificamos que el id curso se trate de un numero
                        int curso = Integer.parseInt(id_curso);
                        //Verificamos que el id curso sea único
                        if (UnicosID.contains(curso)){
                            System.err.println("Error en la línea " + numLinea + ": el valor no es único.");
                            continue;
                        }
                        else{
                            UnicosID.add(curso);
                            //Verificamos que el id sede se trate de un numero
                            try{
                                int sede = Integer.parseInt(id_sede);

                                //Verificamos que el horario esté entre 7 y 21
                                try{
                                    int hora = Integer.parseInt(horario);
                                    if(hora >= 7 && hora <= 21){
                                        //Verificamos que la duración esté entre 1 y 3
                                        try{
                                            int dura = Integer.parseInt(Duracion);
                                            if(dura >= 1 && dura <= 3){

                                                //Verificamos que se tengan a los días validos delimitados por comas
                                                String[] fecha = Dias.replaceAll("\"", "").split(";");
                                                for(int i=0; i<fecha.length; i++){
                                                    fecha[i] = fecha[i].trim();
                                                }
                                                for(String dia: fecha){
                                                    if(!diasValidos.contains(dia)){
                                                        System.out.println("Se tienen días inválidos, o datos incorrectos en la linea: " + numLinea + ".");
                                                        validarDias = true;
                                                    }
                                                }
                                                if(!validarDias){
                                                    //Validando que el valor en estudiantes sea un entero y esté en el rango
                                                    try{
                                                        int cant = Integer.parseInt(cantEst);
                                                        if (cant>= 1 && cant <= 60){
                                                            //Se cumplieron con todas las verificaciones, entonces se crean los cursos
                                                            
                                                            Curso clase = new Curso(curso, sede, nombreCurso, hora, dura, fecha, cant);
                                                            cursos.add(clase);
                                                            validacion = true;
                                                            filas.add(columnas);   
                                                    //Se comienzan a colocar los mensajes de error
                                                        } else{
                                                            System.err.println("Error en la línea " + numLinea + ": la cantidad de estudiantes solo puede estar entre 1 y 60.");
                                                        }
                                                    } catch (NumberFormatException e){
                                                        System.err.println("Error en la línea " + numLinea + ", no se ha colocado un entero para la cantidad de estudiantes.");
                                                    }
                                                }
                                            } else {
                                                System.err.println("Error en la línea " +  numLinea + ": la duración solo puede estar entre 1 y 3.");
                                            }
                                        } catch (NumberFormatException e){
                                            System.err.println("Error en la línea " + numLinea + " no se ha colocado un entero para la duracion.");
                                        }
                                    } else {
                                        System.err.println("Error en la línea " + numLinea + ": las horas solo pueden estar entre 7 y 21.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.err.println("Error en la líena " + numLinea + ", no se ha colocado un entero para la hora.");
                                }
                            } catch (NumberFormatException e) {
                                System.err.println("Error en la línea " + numLinea + ", no se ha colocado un entero para el ID_sede");
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Error en la línea " + numLinea + ", no se ha colocado un entero para el ID_curso");
                    }
                } else {
                    System.err.println("Error en la líena " + numLinea + " el tamaño del archivo no es el adecuado.");
                }
            }
        } catch (IOException e) {
            System.err.println("Error: No se ha encontrado el archivo CSV. Asegúrate de que el archivo existe y tiene el nombre correcto.\n");
        }
    }
}
