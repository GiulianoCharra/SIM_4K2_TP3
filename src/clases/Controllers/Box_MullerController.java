package clases.Controllers;

import clases.TP1.Numero;
import clases.funcionDistribucion.Normal;
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

public class Box_MullerController implements Initializable
{
    public AnchorPane ap_base;
    public Button bt_Calcular;
    public TextField tf_muestra;
    public TextField tf_Media;
    public TextField tf_Varianza;
    public TextField tf_Desviacion;

    public ToggleGroup tg_intervalo;

    public TableView<Numero> tv_Numeros;
    public TableColumn<Object, Object> tc_Numeros;

    public TableView<Intervalo> tv_Distribuccion;
    public TableColumn<Object, Object> tc_Desde;
    public TableColumn<Object, Object> tc_Hasta;
    public TableColumn<Object, Object> tc_F_Obserbada;
    public TableColumn<Object, Object> tc_F_Esperada;
    public TableColumn<Object, Object> tc_Chi;

    public LineChart lc_Distribucion;
    public Label lbl_TVDistribucion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Box_MullerController cn  = this;
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
            public void changed(ObservableValue observableValue, String o, String num)
            {

                if (!num.matches("^(-|\\d)(\\d)*([.]\\d{0,4})?"))
                {
                    if (!tf.getText().isEmpty())
                    {
                        char[] str = num.toCharArray();
                        str[str.length - 1] = '\0';
                        tf.setText(String.valueOf(str));
                    }
                }

//                if (!num.matches("\\d*([.]\\d{0,16})?")) {
//                    tf.setText(num.replaceAll("[^\\d]", ""));
//                }

            }

        });
    }


    public void calcularVarianza() throws InterruptedException {
        if (tf_Varianza.getText().isEmpty())
            return;
        //Thread.sleep(2000);

        double varianza = Double.parseDouble(tf_Varianza.getText());
        double desviacion = (double) Math.round( Math.sqrt(varianza)*10000)/10000;

        tf_Desviacion.setText(String.valueOf(desviacion));
    }

    public void calcularDesviacion() throws InterruptedException {
        if (tf_Desviacion.getText().isEmpty())
            return;
        //Thread.sleep(2000);
        double desviacion = Double.parseDouble(tf_Desviacion.getText());
        double varianza =(double)Math.round( Math.pow(desviacion,2)*10000)/10000;

        tf_Varianza.setText(String.valueOf(varianza));
    }

    public void calcular()
    {
        if (tf_muestra.getText().isEmpty() || tf_Media.getText().isEmpty() || tf_Desviacion.getText().isEmpty())
        {

            JOptionPane.showMessageDialog(null,"Porfavor ingree todos los datos necesarios","Olvidaste ingresar algo",JOptionPane.PLAIN_MESSAGE);
            return;
        }

        int muestra = Integer.parseInt(tf_muestra.getText());
        float media = Float.parseFloat(tf_Media.getText());
        float desviacion = Float.parseFloat(tf_Desviacion.getText());

        ObservableList<Numero> numeros = FXCollections.observableArrayList();

        Normal box = new Normal();

        double[] vec = box.box_Muller(media, desviacion, muestra);

        int i = 0;
        for (double num: vec)
        {
            numeros.add(new Numero(i,(float)Math.round(num*10000)/10000 ));
            i++;
        }

        RadioButton rb =(RadioButton) tg_intervalo.getSelectedToggle();
        int cant = Integer.parseInt(rb.getText());


        ObservableList<Intervalo> chi = box.calcularChi(cant);


        tv_Numeros.setItems(numeros);
        tv_Distribuccion.setItems(chi);


        XYChart.Series<Integer,Float> frecO = new XYChart.Series<>();
        XYChart.Series<Integer,Float> frecE = new XYChart.Series<>();

        frecE.setName("Esperada");
        frecO.setName("Obserbada");
        System.out.println("\n------Lo que se carga en la Tabla-----\n");
        int j = 0;
        for (Intervalo n: box.getIntervalosNormal())
        {
            System.out.println("Intevalo " + j +": " + n);
            frecO.getData().add(new XYChart.Data("" + j, n.getF_Obs()));
            frecE.getData().add(new XYChart.Data("" + j, n.getF_Esp()));
            j++;
        }

        lc_Distribucion.getData().clear();
        lc_Distribucion.getData().addAll(frecO, frecE);
    }
}
