public class Salon {
    private int id_Sede;
    private char edificio;
    private int nivel;
    private int id_Salon;
    private int capacidad;
    private String[][] calendario = new String[16][7];


    public char getEdificio(){
        return this.edificio;
    }

    public int getNivel(){
        return this.nivel;
    }

    public int getIDSEDE(){
        return this.id_Sede;
    }

    public int getId_Salon(){
        return this.id_Salon;
    }

    public int getCapacidad(){
        return this.capacidad;
    }

    public String getCalendario(int indiceHora, int indiceDia){
        return this.calendario[indiceHora][indiceDia];
    }

    public String[][] getCalendario(){
        return this.calendario;
    }

    public void setCalendario(int indiceHora, int indiceDia, String id_curso){
        calendario[indiceHora][indiceDia] = id_curso;
    }

    public void mostrarDisponibilidad() {
        System.out.println("Disponibilidad del salón:");
        
        // Imprime los encabezados de los días de la semana
        for (int columna = 0; columna < calendario[0].length; columna++) {
            System.out.printf("%-15s", calendario[0][columna]);
        }
        System.out.println(); // Salto de línea después de los encabezados
        
        // Imprime el horario y la disponibilidad
        for (int fila = 1; fila < calendario.length; fila++) {
            System.out.printf("%-15s", calendario[fila][0]); // Imprime el horario
            for (int columna = 1; columna < calendario[0].length; columna++) {
                System.out.printf("%-15s", calendario[fila][columna]);
            }
            System.out.println(); // Salto de línea después de cada fila
        }
    }
    

    public Salon(int id_Sede, char edificio, int nivel, int id_Salon, int capacidad) {
        this.id_Sede = id_Sede;
        this.edificio = edificio;
        this.nivel = nivel;
        this.id_Salon = id_Salon;
        this.capacidad = capacidad;

        //Definimos los horarios para cada salon
        calendario[0][0] = "día/horario";
        calendario[1][0] = "7:00 a 8:00";
        calendario[2][0] = "8:00 a 9:00";
        calendario[3][0] = "9:00 a 10:00";
        calendario[4][0] = "10:00 a 11:00";
        calendario[5][0] = "11:00 a 12:00";
        calendario[6][0] = "12:00 a 13:00";
        calendario[7][0] = "13:00 a 14:00";
        calendario[8][0] = "14:00 a 15:00";
        calendario[9][0] = "15:00 a 16:00";
        calendario[10][0] = "16:00 a 17:00";
        calendario[11][0] = "17:00 a 18:00";
        calendario[12][0] = "18:00 a 19:00";
        calendario[13][0] = "19:00 a 20:00";
        calendario[14][0] = "20:00 a 21:00";
        calendario[15][0] = "21:00 a 22:00";

        //Definimos los días que estará disponible el salon
        calendario[0][1] = "Lunes";
        calendario[0][2] = "Martes";
        calendario[0][3] = "Miercoles";
        calendario[0][4] = "Jueves";
        calendario[0][5] = "Viernes";
        calendario[0][6] = "Sabado";

        //Definimos los espacios como libres
        for (int fila = 1; fila < calendario.length; fila++) {
            for (int columna = 1; columna < calendario[0].length; columna++){
                calendario[fila][columna] = "Libre";
            }
        }
    }  
}
