package clases.Controllers;

import clases.TP1.Numero;
import clases.funcionDistribucion.Poisson;
import clases.soporte.Intervalo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class PoissonController implements Initializable
{
    public AnchorPane ap_base;
    public Button bt_Calcular;
    public TextField tf_muestra;
    public TextField tf_Lambda;

    public TableView<Numero> tv_Numeros;
    public TableColumn<Numero, Integer> tc_Numeros;

    public TableView<Intervalo> tv_Distribuccion;
    public TableColumn<Object,Object>  tc_Desde;
    public TableColumn<Object,Object>  tc_Hasta;
    public TableColumn<Object,Object>  tc_F_Obserbada;
    public TableColumn<Object,Object>  tc_F_Esperada;
    public TableColumn<Object,Object>  tc_Chi;

    public LineChart lc_Distribucion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        PoissonController cn  = this;
        formatNodes(cn.ap_base);


        tc_Numeros.setCellValueFactory(new PropertyValueFactory<>("numRand"));

        tc_Desde.setCellValueFactory(new PropertyValueFactory<>("inferior"));
        tc_Hasta.setCellValueFactory(new PropertyValueFactory<>("superior"));
        tc_F_Obserbada.setCellValueFactory(new PropertyValueFactory<>("f_Obs"));
        tc_F_Esperada.setCellValueFactory(new PropertyValueFactory<>("f_Esp"));
        tc_Chi.setCellValueFactory(new PropertyValueFactory<>("chi"));
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



    public void calcular(ActionEvent actionEvent)
    {
        if (tf_muestra.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null,"No imgreso la muestra","Olvidaste ingresar algo",JOptionPane.PLAIN_MESSAGE);
            return;
        }

        int muestra = Integer.parseInt(tf_muestra.getText());
        float lambda = Float.parseFloat(tf_Lambda.getText());

        ObservableList<Numero> numeros = FXCollections.observableArrayList();

        Poisson poisson = new Poisson();
        int[] vec = poisson.generar(lambda,muestra);

        int i = 0;
        for (int num: vec)
        {
            numeros.add(new Numero(i,num));
        }


        tv_Numeros.setItems(numeros);
        ObservableList<Intervalo> chi = poisson.calcularChi();
        tv_Distribuccion.setItems(chi);


        XYChart.Series<Integer,Float> frecO = new XYChart.Series<>();
        XYChart.Series<Integer,Float> frecE = new XYChart.Series<>();

        frecE.setName("Esperada");
        frecO.setName("Obserbada");

        int j = 0;
        for (Intervalo n: poisson.getIntervalosPoisson())
        {
            frecO.getData().add(new XYChart.Data("" + j, n.getF_Obs()));
            frecE.getData().add(new XYChart.Data("" + j, n.getF_Esp()));
            j++;
        }

        lc_Distribucion.getData().clear();
        lc_Distribucion.getData().addAll(frecO, frecE);
    }
}
