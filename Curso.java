
public class Curso {
    private String id_curso;
    private int id_sede;
    private String nombre_curso;
    private int horario;
    private int duracion;
    private String[] dias;
    private int cantidad_estudiantes;
    private int[] indices = {0,0,0,0,0,0};
    private int indiceHora;
    private String conversion;

    public String getNombre(){
        return this.nombre_curso;
    }

    public int getHorario(){
        return this.horario;
    }

    public String getDias(){
        conversion = String.join(",", dias);
        return conversion;
    }

    public int[] getIndices(){
        return this.indices;
    }

    public int getDuracion(){
        return this.duracion;
    }

    public Curso(int id_curso, int id_sede, String nombre_curso, int horario, int duracion, String[] dias, int cantidad_estudiantes){
        this.id_curso = String.valueOf(id_curso);
        this.id_sede = id_sede;
        this.nombre_curso = nombre_curso;
        this.horario = horario;
        this.duracion = duracion;
        this.dias = dias;
        this.cantidad_estudiantes = cantidad_estudiantes;

        indiceHora = horario-6;

        for (int i=0; i < dias.length; i++){
            if(dias[i].equals("lunes")){
                indices[0] = indiceHora;
            }
            if(dias[i].equals("martes")){
                indices[1] = indiceHora;
            }
            if(dias[i].equals("miercoles")){
                indices[2] = indiceHora;
            }
            if(dias[i].equals("jueves")){
                indices[3] = indiceHora;
            }
            if(dias[i].equals("viernes")){
                indices[4] = indiceHora;
            }
            if(dias[i].equals("sabado")){
                indices[5] = indiceHora;
            }
        }

    }

    public int getCantEstudiantes(){
        return this.cantidad_estudiantes;
    }

    public String getIDCURSO(){
        return this.id_curso;
    }

    public int getIDSEDE(){
        return this.id_sede;
    }
}
