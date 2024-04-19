package optimización;
import java.util.*;

public class Generacion {
    int num = 200; // Usamos un número múltiplo de 4 para la reproducción, cada pareja tiene 4 hijos
    Individuo[] poblacion = new Individuo[num];
    
    public Generacion(String[] entrada) {
        char[][] x = new char[entrada.length][];
        for (int j = 0; j < entrada.length; j++) {
            x[j]=entrada[j].toCharArray();
        }
        
        for (int i = 0; i < num; i++) {
            poblacion[i] = new Individuo(x);
        }
    }

    public void Ordenar() {// ordena la poblacion dependiendo su calificacion
        for (int i = 0; i < num; i++) {
            int x = i;
            for (int j = i - 1; j >= 0; j--) {
                if (poblacion[x].Calificar() > poblacion[j].Calificar()) {
                    Individuo temp = poblacion[x];
                    poblacion[x] = poblacion[j];
                    poblacion[j] = temp;
                    x = j;
                }
            }
        }
    }
    
    public int Letras(int i){//cuenta las letras, se dividen las secuencias de los individuos en la misma letra, ignorar gaps
        int cont = 0;
        for (char j : poblacion[0].secuencias[i]) {
            if(Character.isLetter(j))
                cont++;
        }
        return cont;
    }

    private Individuo seleccionPorRuleta() {
        double[] probabilidades = new double[poblacion.length];
        double sumaTotal = 0;
        // Calcular la suma total de las calificaciones y asignar probabilidades proporcionales
        for (int i = 0; i < poblacion.length; i++) {
            sumaTotal += poblacion[i].Calificar();
        }
        // Calcular la probabilidad de selección para cada individuo
        for (int i = 0; i < poblacion.length; i++) {
            probabilidades[i] = poblacion[i].Calificar() / sumaTotal;
        }
        // Generar un número aleatorio para la selección en el rango (0,1]
        Random random = new Random();
        double valorSeleccionado = random.nextDouble();
        double sumaProbabilidades = 0;
        // Iterar sobre las probabilidades acumuladas y seleccionar el individuo correspondiente
        for (int i = 0; i < poblacion.length; i++) {
            sumaProbabilidades += probabilidades[i];
            if (valorSeleccionado <= sumaProbabilidades) {
                return poblacion[i];
            }
        }
        // En caso de que no se haya seleccionado ningún individuo, se devuelve uno aleatorio
        return poblacion[new Random().nextInt(poblacion.length)];
    }
 
        private Individuo Hijo(Individuo padre1, Individuo padre2) {
        int letras = 0, n = 0;
        String[] secHijo = new String[poblacion[0].secuencias.length];
        boolean x;
        // Con sus letras dividimos en cuatro partes las secuencias del individuo
        int[][] particiones = new int[4][poblacion[0].secuencias.length];
        for (int i = 0; i < poblacion[0].secuencias.length; i++) {
            particiones[0][i] = (int) (Letras(i) / 4);
            particiones[1][i] = (int) (2 * Letras(i) / 4);
            particiones[2][i] = (int) (3 * Letras(i) / 4);
            particiones[3][i] = Letras(i); // El cuarto límite es el total de letras en la secuencia
        }

        for (int i = 0; i < poblacion[0].secuencias.length; i++) {
            //Primera parte
            letras = 0; // Inicializa el contador de letras
            x = true; // Indica que estamos en la primera parte del cruce
            String secuencia = ""; // Inicializa la secuencia del hijo
            for (int j = 0; j < padre1.secuencias[i].length; j++) {
                if (x) {
                    char letra = padre1.secuencias[i][j];
                    secuencia += letra; // Agrega la letra a la secuencia del hijo
                    if (Character.isLetter(letra)) {
                        letras++; // Incrementa el contador de letras si el carácter es una letra
                    }
                    if (letras == particiones[0][i]) {
                        x = false; // Cambia a la segunda parte del cruce al alcanzar el límite de letras para la primera parte
                    }
                }
            }

            // Segunda parte
            boolean y = false; // Indica si estamos en la siguiente parte del cruce (segunda, en este caso)
            x = true; // Reinicia la variable
            letras = 0; // Reinicia el contador de letras
            for (int j = 0; j < padre2.secuencias[i].length; j++) {
                char letra = padre2.secuencias[i][j];
                if (Character.isLetter(letra)) {
                    letras++;
                }
                if (x && y) {
                    secuencia += letra;
                    if (letras == particiones[1][i]) {
                        x = false; 
                    }
                }
                if (letras == particiones[0][i]) {
                    y = true; 
                }
            }

            // Tercera parte
            y = false; 
            x = true; 
            letras = 0; 
            for (int j = 0; j < padre1.secuencias[i].length; j++) {
                char letra = padre1.secuencias[i][j];
                if (Character.isLetter(letra)) {
                    letras++;
                }
                if (x && y) {
                    secuencia += letra;
                    if (letras == particiones[2][i]) {
                        x = false; 
                    }
                }
                if (letras == particiones[1][i]) {
                    y = true; 
                }
            }

            // Cuarta parte
            y = false; 
            x = true; 
            letras = 0; 
            for (int j = 0; j < padre2.secuencias[i].length; j++) {
                char letra = padre2.secuencias[i][j];
                if (Character.isLetter(letra)) {
                    letras++;
                }
                if (x && y) {
                    secuencia += letra;
                    if (letras == particiones[3][i]) {
                        x = false; 
                    }
                }
                if (letras == particiones[2][i]) {
                    y = true; 
                }
            }

            secHijo[n]=secuencia;
            n++;
        }
        char[][] y = new char[secHijo.length][];
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < secHijo.length; j++) {
                y[j] = secHijo[j].toCharArray();
            }
        }
        return new Individuo(y);
    }

    public void Reproducir() {
        Individuo[] hijos = new Individuo[num];
        for (int i = 0; i < num; i++) {
            Individuo padre1 = seleccionPorRuleta();
            Individuo padre2 = seleccionPorRuleta();
            hijos[i] = Hijo(padre1, padre2);
        }
        poblacion = hijos;
    }
    
    public String verificarSecuencia(Individuo hijo) {
        StringBuilder sb = new StringBuilder();
        for (char[] secuencia : hijo.secuencias) {
            for (char c : secuencia) {
                if (c != '-') {
                    sb.append(c);
                }
            }
        }
        String secuenciaHijo = sb.toString();

        boolean integridad = true;
        for (Individuo individuo : poblacion) {
            StringBuilder sbOriginal = new StringBuilder();
            for (char[] secuencia : individuo.secuencias) {
                for (char c : secuencia) {
                    if (c != '-') {
                        sbOriginal.append(c);
                    }
                }
            }
            String secuenciaOriginal = sbOriginal.toString();
            if (!secuenciaHijo.equals(secuenciaOriginal)) {
                integridad = false;
                break;
            }
        }

        if (integridad) {
            return "Individuo verificado correctamente";
        } else {
            return "Error en la secuencia";
        }
    }
}
