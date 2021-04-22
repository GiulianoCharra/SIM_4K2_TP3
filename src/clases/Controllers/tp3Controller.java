package clases.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class tp3Controller implements Initializable
{

    public ToggleGroup tg_intervalo;
    public TextField tf_A;
    public TextField tf_B;
    public TextField tf_Lambda;
    public TextField tf_Media;
    public TextField tf_Varianza;
    public TextField tf_Desviacion;
    public LineChart lc_Distribucion;
    public TableView tv_Distribuccion;
    public TextField tf_muestra;
    public AnchorPane ap_Base_tp3;
    public Button bt_Calcular;
    public Label lbl_Distribucion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        tp3Controller cn  = this;
        formatNodes(cn.ap_Base_tp3);

//        formatTextFiield(tf_A);
//        formatTextFiield(tf_B);
//        formatTextFiield(tf_Lambda);
//        formatTextFiield(tf_Media);
//        formatTextFiield(tf_Desviacion);
//        formatTextFiield(tf_Varianza);
//        formatTextFiield(tf_muestra);

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
                if (!num.matches("\\d*([.,]\\d{0,4})?")) {
                    tf.setText(num.replaceAll("[^\\d]", ""));
                }
            }

        });
    }

    public void calcular(ActionEvent actionEvent)
    {

    }
}
