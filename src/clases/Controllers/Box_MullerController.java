package clases.Controllers;

import clases.TP1.Numero;
import clases.funcionDistribucion.Normal;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Box_MullerController implements Initializable {
    public AnchorPane ap_base;
    public Button bt_Calcular;
    public TextField tf_muestra;
    public TextField tf_Media;
    public TextField tf_Varianza;
    public TextField tf_Desviacion;

    public TableColumn<Numero,Float> tc_Numeros;
    public TableColumn tc_F_Esperada;
    public TableColumn tc_Chi;
    public TableColumn tc_F_Obserbada;
    public TableView tv_Distribuccion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Box_MullerController cn  = this;
        formatNodes(cn.ap_base);

//        formatTextFiield(tf_A);
//        formatTextFiield(tf_B);
//        formatTextFiield(tf_Lambda);
//        formatTextFiield(tf_Media);
//        formatTextFiield(tf_Desviacion);
//        formatTextFiield(tf_Varianza);
//        formatTextFiield(tf_muestra);


        tc_Numeros.setCellValueFactory(new PropertyValueFactory<Numero,Float>("numRand"));

    }
    public void formatNodes(Node node) {
        String t = node.getTypeSelector();

        if (t.equals("AnchorPane") || t.equals("Pane")) {
            for (Node n : ((Pane) node).getChildren()) {
                formatNodes(n);
            }
        }
        if (t.equals("TextField")) {
            formatTextFiield((TextField) node);
        }

    }

    private void formatTextFiield(TextField tf)
    {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observableValue, String o, String num) {
                if (!num.matches("\\d*([.]\\d{0,16})?")) {
                    tf.setText(num.replaceAll("[^\\d]", ""));
                }
            }

        });
    }


    public void calcularVarianza()
    {
        double varianza = Double.parseDouble(tf_Varianza.getText());
        double desviacion = (double) Math.round( Math.sqrt(varianza)*10000)/10000;



        tf_Desviacion.setText(String.valueOf(desviacion));
    }

    public void calcularDesviacion()
    {
        double desviacion = Double.parseDouble(tf_Desviacion.getText());
        double varianza =(double)Math.round( Math.pow(desviacion,2)*10000)/10000;

        tf_Varianza.setText(String.valueOf(varianza));
    }

    public void calcular(ActionEvent actionEvent)
    {
        if (tf_muestra.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null,"No imgreso la muestra","Olvidaste ingresar algo",JOptionPane.PLAIN_MESSAGE);
            return;
        }

        int muestra = Integer.parseInt(tf_muestra.getText());
        float media = Float.parseFloat(tf_Media.getText());
        float desviacion = Float.parseFloat(tf_Desviacion.getText());

        ObservableList<Numero> numeros = FXCollections.observableArrayList();

        double[] vec = Normal.box_Muller(media,desviacion,muestra);

        int i = 0;
        for (double num: vec)
        {
            numeros.add(new Numero(i,(float)Math.round(num*10000)/10000 ));
        }

        tv_Distribuccion.setItems(numeros);


    }
}
