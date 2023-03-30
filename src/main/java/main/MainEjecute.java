package main;






import Requests.SlackIntegration;
import java.io.IOException;
import javax.swing.JOptionPane;


//Esta clase tiene el main que llama al que desencadena todo el programa
public class MainEjecute {

   
    public static void main(String[] args) {
        
        try {
            Main main=new Main();
            main.fileReader();
        } catch (Exception ex) {
            
            System.out.println(ex.getMessage());
             System.out.println("The program did not complete successfully.");
        }
    }

}
