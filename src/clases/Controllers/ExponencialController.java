package clases.Controllers;

import clases.TP1.Numero;
import clases.funcionDistribucion.Exponencial;
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

public class ExponencialController<E> implements Initializable
{
    public AnchorPane ap_base;
    public Button bt_Calcular;
    public TextField tf_Media;
    public TextField tf_Lambda;
    public TextField tf_Varianza;
    public TextField tf_Desviacion;
    public TextField tf_muestra;

    public ToggleGroup tg_intervalo;


    public TableView<Numero> tv_Numeros;
    public TableColumn<Integer,Float> tc_Numeros;

    public TableView<Intervalo> tv_Distribuccion;
    public TableColumn<Object, Object> tc_Hasta;
    public TableColumn<Object, Object> tc_Desde;
    public TableColumn<Object, Object> tc_F_Obserbada;
    public TableColumn<Object, Object> tc_F_Esperada;
    public TableColumn<Object, Object> tc_Chi;
    public LineChart<Integer,Float> lc_Distribucion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        ExponencialController<E> cn  = this;
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
                if (!num.matches("\\d+([.]\\d{0,4})?"))
                {
                    if (!tf.getText().isEmpty())
                    {
                        char[] str = num.toCharArray();
                        str[str.length - 1] = '\0';
                        tf.setText(String.valueOf(str));
                    }
                    //tf.setText(num.replaceAll("[^\\d]", ""));
                }
            }

        });
    }

    public void calcularLambda()
    {
        double lambda = Double.parseDouble(tf_Lambda.getText());
        double media = 1/lambda;
        double varianza = 1/Math.pow(lambda,2);
        double desviacion = Math.sqrt(varianza);


        tf_Media.setText(String.valueOf(media));
        tf_Varianza.setText(String.valueOf(varianza));
        tf_Desviacion.setText(String.valueOf(desviacion));
    }

    public void calcularMedia()
    {
        double media = Double.parseDouble(tf_Media.getText());
        double lambda = 1/media;
        double varianza = 1/Math.pow(lambda,2);
        double desviacion = Math.sqrt(varianza);

        tf_Lambda.setText(String.valueOf(lambda));
        tf_Varianza.setText(String.valueOf(varianza));
        tf_Desviacion.setText(String.valueOf(desviacion));
    }

    public void calcularVarianza()
    {
        double varianza = Double.parseDouble(tf_Varianza.getText());
        double desviacion = Math.sqrt(varianza);
        double lambda = 1/desviacion;
        double media = desviacion;

        tf_Lambda.setText(String.valueOf(lambda));
        tf_Media.setText(String.valueOf(media));
        tf_Desviacion.setText(String.valueOf(desviacion));
    }

    public void calcularDesviacion()
    {
        double desviacion = Double.parseDouble(tf_Desviacion.getText());
        double varianza = Math.pow(desviacion,2);
        double lambda = 1/desviacion;
        double media = desviacion;

        tf_Lambda.setText(String.valueOf(lambda));
        tf_Media.setText(String.valueOf(media));
        tf_Varianza.setText(String.valueOf(varianza));
    }

    public void calcular()
    {
        if (tf_muestra.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null,"No imgreso la muestra","Olvidaste ingresar algo",JOptionPane.PLAIN_MESSAGE);
            return;
        }

        int muestra = Integer.parseInt(tf_muestra.getText());
        float lambda = Float.parseFloat(tf_Lambda.getText());



        ObservableList<Numero> numeros = FXCollections.observableArrayList();
        
        Exponencial exp = new Exponencial(lambda);

        double[] vec = exp.generar(muestra);

        int i = 0;
        for (double num: vec)
        {
            numeros.add(new Numero(i,(float)Math.round(num*10000)/10000 ));
            i++;
        }

        RadioButton rb =(RadioButton) tg_intervalo.getSelectedToggle();
        int cant = Integer.parseInt(rb.getText());

        ObservableList<Intervalo> chi = exp.calcularChi(cant,vec);

        tv_Numeros.setItems(numeros);
        tv_Distribuccion.setItems(chi);



        XYChart.Series<Integer,Float> frecO = new XYChart.Series<>();
        XYChart.Series<Integer,Float> frecE = new XYChart.Series<>();

        frecE.setName("Esperada");
        frecO.setName("Obserbada");

        int j = 0;
        for (Intervalo n: exp.getIntervalosEXP())
        {
            frecO.getData().add(new XYChart.Data("" + j, n.getF_Obs()));
            frecE.getData().add(new XYChart.Data("" + j, n.getF_Esp()));
            j++;
        }

        lc_Distribucion.getData().clear();
        lc_Distribucion.getData().addAll(frecO, frecE);
    }

}
