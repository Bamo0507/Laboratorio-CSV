//Bryan Alberto Martínez Orellana
//Carnét 23542
//Ingeniería en Ciencias de la Computación
//Programación Orientada a Objetos
//Creación: 16/09/2023
//Última modificación: 17/09/2023

//Importamos todas las librerías a utilizar
import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class Universidad {
    //Establecemos las variables que se emplearán
    private static boolean val1 = true;
    private static ArrayList<Salon> aulas = new ArrayList<>();
    private static ArrayList<Curso> clases = new ArrayList<>();
    private static int[] verIndices;
    private static int indiceDia;
    private static int indiceHora;
    private static String verificacionLibre;
    private static boolean validarAsignacion;
    private static ArrayList<String> cursosValidos = new ArrayList<>();
    private static Map<String, Integer> asignaciones = new HashMap<>();
    private static Scanner sc = new Scanner(System.in);
    private static String opcion;
    private static boolean val2 = true;
    private static int sedeDeseada;
    private static int nivelDeseado;
    private static int idDeseado;
    private static String input;
    private static char edificioDeseado;
    private static int IdCursoDeseado;
    private static List<String[]> data = new ArrayList<>();



    public static void main (String[] args){
        //Creamos los objetos lectores
        LectorS lectorS = new LectorS();
        LectorC lectorC = new LectorC();
        //Verificamos que la persona esté ingresando dos archivos CSV y que ambos sean válidos
        while(val1){
            System.out.println("Se analizarán los datos........");
            System.out.println("------------------------------------");
            //Se trata de leer el archivo de los Salones
            lectorS.setNumLinea(0);
            lectorS.Bienvenida();
            lectorS.VerificarCSV();
            //Si se cumple con la lectura del archivo de los Salones se tratará de leer el archivo de los Cursos
            if(lectorS.getValidacion() == true){
                System.out.println("");
                lectorC.setNumLinea(0);
                lectorC.Bienvenida();
                lectorC.VerificarCSV(); 
                System.out.println("");
                System.out.println("-------------------------------");
            }
            //Si se pasan ambas validaciones, se corre esta parte del código
            if(lectorC.getValidacion() == true && lectorS.getValidacion() == true) {
                System.out.println("Felicidades, sus archivos CSV cuentan con todos los requerimientos!!");
                //Se realiza la asignación de los cursos de manera automatica
                aulas = lectorS.getSalones();
                ArrayList<String> cursosDisponibles = new ArrayList<>();
                //Comenzamos a recorrer todos los cursos y salones que se hayan cargado
                for (Curso curso : lectorC.getCursos()) {
                    cursosDisponibles.add(curso.getIDCURSO());
                    for (Salon salon : lectorS.getSalones()) {
                        //Verificamos que las sedes coincidan
                        if (salon.getIDSEDE() == curso.getIDSEDE()) {
                            //Estudiamos los indices para las horas y días
                            verIndices = curso.getIndices();
                            for (int i = 0; i < verIndices.length; i++) {
                                if (verIndices[i] != 0) {
                                    indiceDia = i + 1;
                                    indiceHora = verIndices[i];
                                    String claveAsignacion = curso.getIDCURSO() + "-" + indiceDia + "-" + indiceHora;
                                    // Verificar si ya se ha realizado esta asignación
                                    if (!asignaciones.containsKey(claveAsignacion)) {
                                        if (curso.getCantEstudiantes() <= salon.getCapacidad()) {
                                            validarAsignacion = VerificarAsignacion(curso.getDuracion(), salon, indiceHora, indiceDia);
                                            if (validarAsignacion) {
                                                Asignacion(curso.getDuracion(), salon, indiceHora, indiceDia, curso);
                                                // Registrar la asignación para evitar duplicados
                                                asignaciones.put(claveAsignacion, salon.getId_Salon());
                                                cursosValidos.add(curso.getIDCURSO());
                                                //Nos aeguramos de tener un ArrayList que contenga los cursos que no fueron asignados
                                                cursosDisponibles.remove(curso.getIDCURSO());
                                            } 
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                //Aquí se terminan de recorrer todos los salones y cursos
                while(val2){
                    //Desplegamos el menú de opciones
                    System.out.println("A continuación se le mostrará el menú de opciones:");
                    System.out.println("----------Menu----------");
                    System.out.println("1. Mostrar disponibilidad en un salon");
                    System.out.println("2. Mostrar información de un curso.");
                    System.out.println("3. Mostrar cursos que no se lograron asignar.");
                    System.out.println("4. Mostrar cursos que se lograron asignar.");
                    System.out.println("5. Exportar Resultado");
                    System.out.println("6. Salir");


                    System.out.println("Ingrese la opción deseada: ");
                    opcion = sc.nextLine();
                    System.out.println("");

                    switch(opcion) {
                        case "1":
                        //Solicitamos la información necesaria para localizar una sede, y nos aseguramos que sean datos validos
                            System.out.println("------------------------------");
                            System.out.println("Escriba la sede de la que quiera información: ");
                            sedeDeseada = obtenerEnteroValido(sc);
                            System.out.println("------------------------------");
                            System.out.println("Escriba el nivel al que pertenece: ");
                            nivelDeseado = obtenerEnteroValido(sc);
                            System.out.println("------------------------------");
                            System.out.println("Escriba el id del salón que quiere: ");
                            idDeseado = obtenerEnteroValido(sc);
                            System.out.println("------------------------------");
                            while(true){
                                System.out.println("Escriba el carácter del edificio requerido: "); 
                                input = sc.nextLine();
                                if(input.length() == 1){
                                    edificioDeseado = input.charAt(0);
                                    break;
                                } else {
                                    System.err.println("No ha colocado un carácter, vuelva a intentarlo.");
                                }
                            }

                            System.out.println("------------------------------");
                            for(Salon salon: lectorS.getSalones()){
                                //Buscamos si algun salon dentro de nuestro Array de salones cumple con la informacion indicada, si sí, les mostramos lo que se contiene en su calendario
                                if(salon.getIDSEDE() == sedeDeseada && salon.getNivel() == nivelDeseado && salon.getId_Salon() == idDeseado && salon.getEdificio() == edificioDeseado) {
                                    System.out.println("----------------------------------");
                                    System.out.println("Sede: " + sedeDeseada);
                                    System.out.println("----------------------------------");
                                    System.out.println("Edificio: " + edificioDeseado);
                                    System.out.println("----------------------------------");
                                    System.out.println("Nivel: " + nivelDeseado);
                                    System.out.println("----------------------------------");
                                    System.out.println("id_salon: " + idDeseado);
                                    System.out.println("----------------------------------");
                                    System.out.println("");
                                    salon.mostrarDisponibilidad();
                                    System.out.println("");
                                    break;
                                }
                            }
                            System.out.println("No se ha encontrado un salon con las especificaciones que ha dado.");
                            System.out.println("-----------------------------");
                            break;
                            

                        case "2":
                            //Solicitamos el ID del curso buscado
                            System.out.println("Por favor, indique el ID del curso.");
                            IdCursoDeseado = obtenerEnteroValido(sc);
                            for(Curso curso: lectorC.getCursos()){
                                //Verificamos si hay algún curso con el ID proporcionado, si sí, mostramos la información necesaria
                                if(Integer.parseInt(curso.getIDCURSO()) == IdCursoDeseado){
                                    System.out.println("id: " + curso.getIDCURSO());
                                    System.out.println("Sede: " + curso.getIDSEDE());
                                    System.out.println("Nombre Curso: " + curso.getNombre());
                                    System.out.println("Horario: " + curso.getHorario() + ":00 a.m. " + curso.getDias());
                                    System.out.println("Cantidad Estudiantes: " + curso.getCantEstudiantes());
                                    System.out.println("");
                                    break;
                                }
                                
                            }
                            System.out.println("No se ha encontrado un curso con ese ID :(");
                            System.out.println("------------------");
                            System.out.println("");
                            break;
                            

                        case "3":
                            System.out.println("Estos cursos no tienen salon: ");
                            System.out.println("---------------------------");
                            Set<String> cursosInvalidosSet = new HashSet<>(cursosDisponibles);
                            //Recorremos los cursos no asignados y los mostramos
                            for(String curso: cursosInvalidosSet){
                                System.out.println("ID_Curso: " + curso);
                                System.out.println("---------------------------");
                            }
                            System.out.println("");
                            break;

                        case "4":
                            System.out.println("Estos cursos tienen salon: ");
                            System.out.println("---------------------------");
                            Set<String> cursosValidosSet = new HashSet<>(cursosValidos);
                            //Recorremos los cursos que sí fueron validados y los desplegamos
                            for(String curso: cursosValidosSet){
                                System.out.println("ID_Curso: " + curso);
                                System.out.println("---------------------------");
                            }
                            System.out.println("");
                            break;  

                        case "5":
                            data.add(new String[] {"ID_Sede", "Edificio", "Nivel", "Salon", "Curso"});
                            Set<String> cursosAgregados = new HashSet<>(); // Conjunto para almacenar cursos ya agregados
                            //Recorremos los salones y los cursos que fueron asignados
                            for (Salon salon : lectorS.getSalones()) {
                                for (String curso : cursosValidos) {
                                    for (int fila = 1; fila < salon.getCalendario().length; fila++) {
                                        for (int columna = 1; columna < salon.getCalendario()[0].length; columna++) {
                                            if (salon.getCalendario()[fila][columna].equals(curso)) {
                                                // Verifica si el curso ya ha sido agregado
                                                if (!cursosAgregados.contains(curso)) {
                                                    //De manera ordenada vamos agregando lo que contendrá cada columna
                                                    data.add(new String[]{
                                                            String.valueOf(salon.getIDSEDE()),
                                                            Character.toString(salon.getEdificio()),
                                                            String.valueOf(salon.getNivel()),
                                                            String.valueOf(salon.getId_Salon()),
                                                            curso
                                                    });

                                                    // Agrega el curso al conjunto de cursos agregados
                                                    cursosAgregados.add(curso);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            String csvFilePath = "datos.csv";
                            generateCSV(data, csvFilePath);

                            break;

                        case "6":
                        //Opción de salida
                            System.out.println("Que tenga un buen día :)");
                            val1 = false;
                            val2 = false;
                            break;

                        //Código a mostrar en caso de que no se seleccione algo valido en el menú
                        default:
                            System.out.println("Asegurese que esté ingresando una opción válida...");

                    }
                }           
            }
            else {
                //Código a mostrar si hay fallos al cargar los CSV
                System.out.println("Por favor, vuelva a intentarlo cuando haya corregido sus archivos CSV...");
                System.out.println("----------------------------------------------------------------");
                System.out.println("");

            }
        }
    }

    //Método para verificar si es posible asignar un curso en todos los espacios que necesita
    public static boolean VerificarAsignacion(int duracion, Salon salon, int indiceHora, int indiceDia){
        if(duracion == 1) {
            verificacionLibre = salon.getCalendario(indiceHora, indiceDia);
            if(verificacionLibre.equals("Libre")){
                return true;
            } else{
                return false;
            }
        }
        else if(duracion == 2){
            verificacionLibre = salon.getCalendario(indiceHora, indiceDia);
            if(verificacionLibre.equals("Libre")){
                verificacionLibre = salon.getCalendario(indiceHora + 1, indiceDia);
                if(verificacionLibre.equals("Libre")){
                    return true;
                } else{
                    return false;
                }
            }
        }
        else if(duracion == 3){
            verificacionLibre = salon.getCalendario(indiceHora, indiceDia);
            if(verificacionLibre.equals("Libre")){
                verificacionLibre = salon.getCalendario(indiceHora + 1, indiceDia);
                if(verificacionLibre.equals("Libre")){
                    verificacionLibre = salon.getCalendario(indiceHora + 2, indiceDia);
                    if(verificacionLibre.equals("Libre")){
                        return true;
                    } else{
                        return false;
                    }
                }
            }
        }
        return false;
    }

    //Código a correr para hacer las asignaciones, varía acorde al valor ingresado en la duracion del curso
    public static void Asignacion(int duracion, Salon salon, int indiceHora, int indiceDia, Curso curso){
        if(duracion == 1){
            salon.setCalendario(indiceHora, indiceDia, curso.getIDCURSO());
        }
        else if(duracion == 2){
            salon.setCalendario(indiceHora, indiceDia, curso.getIDCURSO());
            salon.setCalendario(indiceHora+1, indiceDia, curso.getIDCURSO());
        }
        else if(duracion == 3){
            salon.setCalendario(indiceHora, indiceDia, curso.getIDCURSO());
            salon.setCalendario(indiceHora+1, indiceDia, curso.getIDCURSO());
            salon.setCalendario(indiceHora+2, indiceDia, curso.getIDCURSO());
        }
    }

    //Método para asegurarse que se ingrese un entero en los campos necesarios
    public static int obtenerEnteroValido(Scanner scanner) {
        int numero = 0;
        boolean entradaValida = false;
        System.out.println("------------------------");
        do {
            try {
                System.out.print("Por favor, ingresa un número entero: ");
                String entrada = scanner.nextLine();
                numero = Integer.parseInt(entrada);
                entradaValida = true;
                System.out.println("");
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Debes ingresar un número entero.");
            }
        } while (!entradaValida);

        return numero;
    }

    //Método para crear un CSV
    public static void generateCSV(List<String[]> data, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (String[] row : data) {
                // Crear una línea en el archivo CSV
                String line = String.join(",", row);
                writer.write(line + "\n");
            }
            System.out.println("Archivo CSV generado correctamente en: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


