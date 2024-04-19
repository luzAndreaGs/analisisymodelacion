package optimización;

public class Algoritmo {
    

    public static void main(String[] args) {
        // Obtener el tiempo inicial
        long startTime = System.nanoTime();
        Archivos a= new Archivos();  

        String s1= a.leertxt("C:\\Users\\Luz Andrea\\Documentos escuela\\Analisis y Modelacion\\Escherichia coli strain.txt");  
        String s2= a.leertxt("C:\\Users\\Luz Andrea\\Documentos escuela\\Analisis y Modelacion\\Enterococcus faecalis strain.txt");
        String s3= a.leertxt("C:\\Users\\Luz Andrea\\Documentos escuela\\Analisis y Modelacion\\Porphyromonas gingivalis strain.txt");
        String s4= a.leertxt("C:\\Users\\Luz Andrea\\Documentos escuela\\Analisis y Modelacion\\Helicobacter pylori isolate.txt");  
        String s5= a.leertxt("C:\\Users\\Luz Andrea\\Documentos escuela\\Analisis y Modelacion\\Staphylococcus aureus strain.txt");
        String s6= a.leertxt("C:\\Users\\Luz Andrea\\Documentos escuela\\Analisis y Modelacion\\Bacteroides fragilis strain.txt");
        
//        String[] secuencias = {"paleontologia","biotecnologia","constitucionalidad","descomposicion"};
        String[] secuencias={s1, s2, s3};
        Generacion obj = new Generacion(secuencias);
        obj.Ordenar();
        for (Individuo x : obj.poblacion) {
            for (char[] y : x.secuencias) {
                for (int i = 0; i < y.length; i++) {
                    System.out.print(y[i]+" ");
                }
                System.out.println("");
            }
            System.out.println(x.Calificar());
        }
        for (int z = 0; z < 10; z++){
            System.out.println("╔══════════════════╗");
            System.out.println("          REPRODUCCION");
            System.out.println("╚══════════════════╝");
            obj.Reproducir();
            obj.Ordenar();
            for (Individuo x : obj.poblacion) {
                for (char[] y : x.secuencias) {
                    for (int i = 0; i < y.length; i++) {
//                        System.out.print(y[i]+" ");
                    }
//                   System.out.println("");
                }
                System.out.println(x.Calificar());
                System.out.println(obj.verificarSecuencia(x)+"\n");
            }
        }
        // Obtener el tiempo final
        long endTime = System.nanoTime();
        
        // Calcular el tiempo transcurrido en segundos
        double elapsedTimeSeconds = (endTime - startTime) / 1e9;
        
        System.out.println("Tiempo " + elapsedTimeSeconds);    
    }
}
