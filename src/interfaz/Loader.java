package interfaz;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.net.URL;


public class Loader {
    private Pane vista;

    public Pane getPage(String filename)
    {
        try {
            URL fileURL = Main.class.getResource("/interfaz/" + filename + ".fxml");

            System.out.println(fileURL.toString());

            if (fileURL == null)
            {
                throw  new java.io.FileNotFoundException("No se puede encontrar el archivo");
            }

            vista = FXMLLoader.load(fileURL);

        }catch (Exception e)
        {
            System.out.println("No se encontro la pagina " + filename + "volve a intentar");
        }

        return vista;
    }

}
