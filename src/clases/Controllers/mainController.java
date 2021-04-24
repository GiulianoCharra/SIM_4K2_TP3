package clases.Controllers;

import clases.soporte.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class mainController implements Initializable
{

    public MenuItem mi_Uniforme;
    public MenuItem mi_Exponensial;
    public MenuItem mi_Box_Muller;
    public MenuItem mi_Convolucion;
    public MenuItem mi_Poisson;
    public MenuItem mi_Generador;
    public MenuItem mi_Salir;
    public BorderPane bp_Vista;
    public MenuBar mb_Opciones;
    public Button btn_Salir;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }
    public void loadPage(ActionEvent actionEvent)
    {
        MenuItem mi = (MenuItem) actionEvent.getSource();
        System.out.println(mi.getText());
        setPage(mi);
    }

    public void tp1(ActionEvent actionEvent)
    {
        MenuItem mi = (MenuItem) actionEvent.getSource();
        System.out.println(mi.getText());
        setPage(mi);
    }

    public void setPage(MenuItem str)
    {
        PageLoader page = new PageLoader();
        String  fmxl;

        if (mi_Generador.equals(str)) {
            fmxl = "tp1";
        } else if (mi_Uniforme.equals(str)) {
            fmxl = "Uniforme";
        } else if (mi_Exponensial.equals(str)) {
            fmxl = "Exponencial";
        } else if (mi_Box_Muller.equals(str)) {
            fmxl = "Box_Muller";
        } else if (mi_Convolucion.equals(str)) {
            fmxl = "Convolucion";
        } else if (mi_Poisson.equals(str)) {
            fmxl = "Poisson";
        } else {
            throw new IllegalStateException("Unexpected value: " + str);
        }
        System.out.println("sale seto: " +str);
        Pane view = page.getPage(fmxl);
        bp_Vista.setCenter(view);

    }

    public void salir()
    {
        Stage stage = (Stage)btn_Salir.getScene().getWindow();
        stage.close();
    }
}
