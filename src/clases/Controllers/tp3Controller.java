package clases.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        formatTextFiield(tf_A);
        formatTextFiield(tf_B);
        formatTextFiield(tf_Lambda);
        formatTextFiield(tf_Media);
        formatTextFiield(tf_Desviacion);
        formatTextFiield(tf_Varianza);
        formatTextFiield(tf_muestra);

    }

    private void formatTextFiield(TextField tf)
    {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observableValue, String o, String num) {
                if (!num.matches("\\d*")) {
                    tf.setText(num.replaceAll("[^\\d]", ""));
                }
            }

        });
    }

    public void calcular(ActionEvent actionEvent)
    {
    }
}
