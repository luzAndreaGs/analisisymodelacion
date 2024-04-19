package optimización;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Archivos {
    public String leertxt(String direccion){
        String txt="";
        try {
            Scanner s = new Scanner(new File(direccion));
            s.nextLine();
            while(s.hasNext()){
                txt+=s.nextLine();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return txt;
    }
   
}
