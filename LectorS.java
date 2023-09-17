import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LectorS {
    //Declaración de las variables
    private String nombreCSV = "";
    private Scanner teclado = new Scanner(System.in);
    private List<String[]> filas = new ArrayList<>();
    private String linea;
    private int numLinea = 0;
    private String[] columnas;
    private String id_sede;
    private String edificio;
    private String nivel;
    private String id_salon;
    private String capacidad;
    private ArrayList<Salon> salones = new ArrayList <>();
    private boolean validacion = false;
    
    //Metodo para establecer el nombre del CSV
    public void Bienvenida(){
        System.out.println("Hola, muy buen día querido usuario!!");
        System.out.println("Por favor, indique cuál es el nombre de su archivo CSV, coloque el nombre y csv al final para que funcione:");
        nombreCSV = teclado.nextLine();
    }

    //Getter para la validacion
    public boolean getValidacion(){
        return this.validacion;
    }

    //Getter para nuestra lista de salones
    public ArrayList<Salon> getSalones(){
        return this.salones;
    }

    //Metodo para leer el CSV y verificar los datos que contenga
    public void VerificarCSV(){
        //Se lee el archivo
        try (BufferedReader br = new BufferedReader(new FileReader(nombreCSV))){
            br.readLine();
            //Ciclo para leer línea por línea el archivo
            while ((linea = br.readLine()) != null){
                //Nos movemos una linea tras hacer las verificaciones en toda una fila
                numLinea++;
                columnas = linea.split(",");
                //Se verifica el tamaño del archivo
                if (columnas.length == 5){
                    id_sede = columnas[0];
                    edificio = columnas[1];
                    nivel = columnas[2];
                    id_salon = columnas[3];
                    capacidad = columnas[4];

                    //Validamos los tipos
                    try{
                        //Verificamos que el id_sede sea un entero
                        int sede = Integer.parseInt(id_sede);
                        //Verificamos que el edificio sea un carácter
                        if(edificio.length() == 1) {
                            char caracter = edificio.charAt(0);
                            //Si es un caracter pasamos a verificar los niveles
                            try{
                                int numNivel = Integer.parseInt(nivel);
                                if (numNivel >= 1 && numNivel <= 10){
                                    
                                    //Si se cumple el edificio estando entre 1 y 10, pasamos a verificar el id de los salones
                                    try{
                                        int salon = Integer.parseInt(id_salon);
                                        if(salon >= 1 && salon <= 99){
                                            //Si se cumple el id del salon, pasamos a verificar la capacidad
                                            try{
                                                int cant = Integer.parseInt(capacidad);
                                                if(cant >= 10 && cant <= 150){
                                                    //Si se cumple con lo necesario se agrega la información a las filas
                                                    System.out.println("No se han encontrado errores en la línea "+ numLinea + " ;)");
                                                    //Creamos el objeto salon
                                                    Salon aula = new Salon(sede, caracter, numNivel, salon, cant);
                                                    salones.add(aula);
                                                    validacion = true;
                                                    filas.add(columnas);
                                                } 
                                                //Se comienzan con los mensajes de error dependiendo de qué haya sido lo que no se haya cumplido
                                                else {
                                                    System.err.println("Error en la línea " + numLinea + ": en capacidad se debe tener un valor entre 10 y 150.");
                                                }
                                            } catch (NumberFormatException e) {
                                                System.err.println("Error en la línea " + numLinea + ": la capacidad debe ser un numero.");
                                            }
                                        } else {
                                            System.err.println("Error en la línea " + numLinea + ": el id_salon debe ser un numero entre 1 y 99.");
                                        }
                                    } catch (NumberFormatException e){
                                        System.err.println("Error en la linea " + numLinea + ", en id_salon se deben tener enteros.");
                                    }
                                } else {
                                    System.err.println("Error en la linea " + numLinea + ": el valor del nivel debe estar entre 1 y 10.");
                                }
                            } catch (NumberFormatException e) {
                                System.err.println("Error en la linea " + numLinea + ", el nivel debe ser un valor entero.");
                            }
                        } else {
                            System.err.println("Error en la linea " + numLinea + ": en edificio se debe colocar un cáracter.");
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Error en la linea " + numLinea + ": el id de la sede debe ser un valor númerico.");
                    }
                } else {
                    System.err.println("Error en la linea " + numLinea + ": el CSV no tiene el tamaño esperado.");
                }
            }
        } catch (IOException e) {
            System.err.println("Error: No se ha encontrado el archivo CSV. Asegúrate de que el archivo existe y tiene el nombre correcto.\n");
        }
    }
}