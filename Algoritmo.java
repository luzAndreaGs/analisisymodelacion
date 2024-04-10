package algoritmo;

public class Algoritmo {

    public static void main(String[] args) {
        //Archivos a= new Archivos();
       
        
        // String s1= a.leertxt("C:\\Users\\abere\\OneDrive\\Documentos\\Fasta\\monkeyPoxVirus.fasta");  
        // String s2= a.leertxt("C:\\Users\\abere\\OneDrive\\Documentos\\Fasta\\Rhabdoviridae.fasta");
        // String s3= a.leertxt("C:\\Users\\abere\\OneDrive\\Documentos\\Fasta\\coronaVirus.fasta");
        
        String[] secuencias={"e-jem-plo","per-ro-","g-ato-","-mur-ciela-go"};
        //String[] secuencias={s1, s2, s3};
        Generacion obj = new Generacion(secuencias);
        obj.Ordenar();
        for (Individuo x : obj.poblacion) {
            for (char[] y : x.secuencias) {
                for (int i = 0; i < y.length; i++) {
                    System.out.print(y[i]+" ");
                }
                System.out.println("");
            }
            System.out.println(x.Calificar()+"\n");
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
                        System.out.print(y[i]+" ");
                    }
                    System.out.println("");
                }
                System.out.println(x.Calificar()+"\n");
            }
        }
    }
}