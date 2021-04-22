package clases.soporte;


import clases.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.File;
import java.net.URL;


public class PageLoader {
    private Pane vista;

    public Pane getPage(String filename)
    {
        //final File f = new File(main.class.getProtectionDomain().getCodeSource().getLocation().getPath());

        try {
            URL fileURL = Main.class.getResource("/clases/interfaz/" + filename + ".fxml");

            System.out.println("Esta es el Path "+fileURL.toString());

            if (fileURL == null)
            {
                throw  new java.io.FileNotFoundException("No se puede encontrar el archivo");
            }

            vista = FXMLLoader.load(fileURL);

        }catch (Exception e)
        {
            System.out.println("No se encontro la pagina " + filename +  " volve a intentar");
        }

        return vista;
    }

}
