package algoritmo;

public class Individuo {//Automaticamente cuando se crea un individuo este muta
    char[][] secuencias;
    int maxAgregar = 3; //Es el numero maximo de GAPS que se le puede agregar en la mutación
    
    public Individuo(char[][] entrada){
        secuencias = new char[entrada.length][];
        int c = 0;
        for (char[] i : entrada) {
            secuencias[c] = new char[i.length];
            for (int j = 0; j < i.length; j++) {
                secuencias[c][j] = i[j];
            }
            c++;
        }
        Mutar();
    }
    
    public int Calificar(){//Califica segun las columnas que se tiene
        int cal = 0;
        String letras = "";
        for (int i = 0; i < secuencias[0].length; i++) {
            for (int j = 0; j < secuencias.length; j++) {
                if(secuencias[j][i]=='-'){//si se encuentra un GAP se restan puntos
                    cal--;
                }
                else if(letras.contains(secuencias[j][i]+"")){//Si encuentra una letra repetida en la columna se suma 5 puntos
                    cal += 5;
                }
                else {
                    letras+=secuencias[j][i];//Se agregan letras y se suma puntos
                    cal++;
                }
            }
            letras = "";
        }
        return cal;
    }
    
    public void Mutar(){
        int[] Gaps = null;//Aqui se guardan las posiciones de los GAPS que ya se tiene
        for (int i = 0; i < secuencias.length; i++) {
            String StringGaps = "";
            for (int j = 0; j < secuencias[i].length; j++) {
                if(secuencias[i][j]=='-')
                    StringGaps += j + "%";
            }
            
            String[] intGaps = StringGaps.split("%");
            Gaps = new int[intGaps.length-1];
            for (int j = 0; j < Gaps.length; j++) {
                Gaps[j] = Integer.parseInt(intGaps[j]);
            }
            
            //Guarda las posiciones que se van a eliminar
            int max = (int)(Math.random()*Gaps.length/2);
            String cacheEliminar = "";
            for (int j = 0; j < max; j++) {
                int x = (int)(Math.random()*Gaps.length);
                if (!cacheEliminar.contains("%" + Gaps[x] + " ")){
                    cacheEliminar += "%" + Gaps[x] + " ";
                }
            }
            
            //Guarda las posiciones que se van a agregar
            max = maxAgregar;
            String cacheAgregar = "";
            for (int j = 0; j < max; j++) {
                int x = (int)(Math.random()*(secuencias[i].length));
                if(!cacheAgregar.contains("%" + x + " ")){
                    cacheAgregar += "%" + x + " ";
                }
            }
            ModificarGaps(i, cacheEliminar, cacheAgregar);
        }
        Alinear();
        Eliminar();
    }
    
    private void ModificarGaps(int i, String eliminar, String agregar){
        //calcular la longitud que va a tener cada variable 
        char[] elim;
        char[] res;
        if (eliminar.compareTo("") == 0){
            elim = new char[secuencias[i].length];
        }
        else{
            elim = new char[secuencias[i].length-eliminar.split("%").length+1];
        }
        
        if(agregar.compareTo("") == 0){
            res = new char[elim.length];
        }
        else{
            res = new char[elim.length+agregar.split("%").length-1];
        }
        
        //Elimina los GAPS
        int cont = 0;
        for (int j = 0; j < secuencias[i].length; j++) {
            if(!eliminar.contains("%" + j + " ")){
                elim[cont] = secuencias[i][j];
                cont++;
            }
        }
        //Agrega los GAPS
        cont = 0;
        for (int j = 0; j < res.length; j++) {
            if(!agregar.contains("%" + j + " ")){
                try{
                res[j] = elim[cont];
                cont++;
                }
                catch(Exception ex){
                    res[j] = '-';
                }
            }
            else{
                res[j] = '-';
            }
        }
        secuencias[i] = res;
    }
    
    private void Alinear(){ //Agrega GAPS para que todas las secuencias tengan la misma longitud
        //Perrito---
        //Murcielago
        int max = secuencias[0].length;
        for (int i = 1; i < secuencias.length; i++) {
            if(secuencias[i].length > max){
                max = secuencias[i].length;
            }
        }
        for (int i = 0; i < secuencias.length; i++) {
            char res[] = new char[max];
            for (int j = 0; j < secuencias[i].length; j++) {
                res[j] = secuencias[i][j];
            }
            for (int j = secuencias[i].length; j < max; j++) {
                res[j] = '-';
            }
            secuencias[i] = res;
        }
    }
    
    private void Eliminar(){//Elimina las columnas que solo tiene GAPS
        //P-erri-to      => Perrito
        //M-urci-elago   => Murcielago
        for (int i = 0; i < secuencias[0].length; i++) {
            boolean x=true;
            for (int j = 0; j < secuencias.length; j++) {
                if(secuencias[j][i]!='-'){
                    x=false;
                }
            }
            if(x){
                for (int j = 0; j < secuencias.length; j++) {
                    ModificarGaps(j, "%"+i+" ", "");
                }
                i--;
            }
        }
    }
}