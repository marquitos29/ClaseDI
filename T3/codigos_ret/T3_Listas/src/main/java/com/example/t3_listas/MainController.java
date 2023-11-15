package com.example.t3_listas;

import com.example.t3_listas.model.Pelicula;
import com.example.t3_listas.model.Usuario;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable, EventHandler<ActionEvent> {

    @FXML
    private MenuItem menuWarning;
    @FXML
    private MenuItem manuAlert;
    @FXML
    private MenuItem menuConfirmacion;
    @FXML
    private MenuItem menuInput;
    @FXML
    private MenuItem menuChoice;
    @FXML
    private MenuItem menuPerso;
    @FXML
    private CheckMenuItem menuActivar;

    @FXML
    private RadioMenuItem menuDeshabilitar;

    @FXML
    private RadioMenuItem menuHabilitar;

    @FXML
    private MenuItem menuSalir;

    @FXML
    private GridPane parteCentral;

    private ToggleGroup grupoHabilitar;

    @FXML
    private Button botonComprobar;

    @FXML
    private ChoiceBox<Pelicula> choice;
    private ObservableList<Pelicula> listaPeliculas;

    @FXML
    private ComboBox<String> combo;

    private ObservableList<String> listaGeneros;

    @FXML
    private ListView<?> lisview;

    @FXML
    //private Spinner<Integer> spinner;
    private Spinner<String> spinner;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        instancias();
        personalizarMenu();
        acciones();
    }

    private void acciones() {

        botonComprobar.setOnAction(this);
        menuSalir.setOnAction(this);
        menuWarning.setOnAction(this);
        manuAlert.setOnAction(this);
        menuInput.setOnAction(this);
        menuChoice.setOnAction(this);
        menuConfirmacion.setOnAction(this);
        menuPerso.setOnAction(this);
        combo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                System.out.println(t1);
            }
        });
        menuActivar.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                combo.getSelectionModel().select((int) (Math.random()*listaGeneros.size()));
            }
        });
        grupoHabilitar.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                if (t1 != null) {
                    if (((RadioMenuItem) t1).getText().equalsIgnoreCase("habilitar")) {
                        parteCentral.setDisable(false);
                    } else if ((((RadioMenuItem) t1).getText().equalsIgnoreCase("deshabilitar"))) {
                        parteCentral.setDisable(true);
                    }
                } else {
                    grupoHabilitar.selectToggle(toggle);
                }

            }
        });
    }

    private void instancias() {

        // pelicula - titulo - genero - calificacion - año
        // mostrar el titulo
        grupoHabilitar = new ToggleGroup();
        grupoHabilitar.getToggles().addAll(menuHabilitar, menuDeshabilitar);
        listaGeneros = FXCollections.observableArrayList();
        listaGeneros.addAll("Terror","Comedia","Musical","Infantil");
        combo.setItems(listaGeneros);

        listaPeliculas = FXCollections.observableArrayList();
        listaPeliculas.add(new Pelicula("Titulo 1","Terror",4.5,1990));
        listaPeliculas.add(new Pelicula("Titulo 2","Comedia",4.1,2015));
        listaPeliculas.add(new Pelicula("Titulo 3","Infantil",4.2,2017));
        listaPeliculas.add(new Pelicula("Titulo 4","Musical",4,2019));
        choice.setItems(listaPeliculas);

        //spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,50,5));
        ObservableList<String> opciones = FXCollections.observableArrayList();
        opciones.add("Opcion 1");
        opciones.add("Opcion 2");
        opciones.add("Opcion 3");
        opciones.add("Opcion 4");
        spinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<String>(opciones));
    }

    private void personalizarMenu() {
        menuSalir.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("salir.png"))));
        menuActivar.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("activar.png"))));
        menuDeshabilitar.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("hab.png"))));
        menuHabilitar.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("des.png"))));
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if (menuActivar.isSelected()) {


            // Construidos - Alert
            // ALERTA - WARN - INFO - CONF - INPUT - CHOICE
            Alert dialogoConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            dialogoConfirmacion.setTitle("¿Salir?");
            dialogoConfirmacion.setHeaderText("¿Estas seguro que quieres salir?");

            Optional<ButtonType> respuesta = dialogoConfirmacion.showAndWait();
            if (respuesta.get() == ButtonType.OK) {
                System.exit(0);
            } else if (respuesta.get() == ButtonType.CANCEL) {
                Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
                dialogoInfo.setTitle("¿Salir?");
                dialogoInfo.setHeaderText("Continuaras en la aplicacion");
                dialogoInfo.show();
            }

        } else if (actionEvent.getSource() == menuWarning) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Header");
            alert.setContentText("Content");
            alert.show();
        } else if (actionEvent.getSource() == manuAlert) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
        } else if (actionEvent.getSource() == menuConfirmacion) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            ButtonType boton1 = new ButtonType("No me interesa");
            ButtonType boton2 = new ButtonType("Me interesa");
            alert.getButtonTypes().setAll(boton2, boton1, ButtonType.CLOSE);
            Optional<ButtonType> respuesta = alert.showAndWait();
            if (respuesta.get() == ButtonType.APPLY) {
                System.out.println("Seleccionado apply");
            } else if (respuesta.get() == boton1) {
                System.out.println("Seleccionado tal vez");
            }
        } else if (actionEvent.getSource() == menuInput) {
            TextInputDialog dialogoTexto = new TextInputDialog("introduce datos");
            dialogoTexto.setTitle("Titulo");
            dialogoTexto.setHeaderText("Header");
            dialogoTexto.setContentText("Content");
            Optional<String> respuesta = dialogoTexto.showAndWait();
            if (respuesta.isPresent()) {
                System.out.println(respuesta.get());
            }
        } else if (actionEvent.getSource() == menuChoice) {

            ObservableList<Usuario> listaUsuario = FXCollections.observableArrayList();
            listaUsuario.addAll(new Usuario("Usuario1", "Pass1", "Correo1@correo1"),
                    new Usuario("Usuario2", "Pass2", "Correo1@correo2"),
                    new Usuario("Usuario3", "Pass3", "Correo1@correo3"));

            ChoiceDialog<Usuario> dialogoLista = new ChoiceDialog(listaUsuario.get(2), listaUsuario);
            Optional<Usuario> respuesta = dialogoLista.showAndWait();

            if (respuesta.isPresent()) {
                System.out.println(respuesta.get().getPass());
            }

            // al seleccionar un usuario, aparece por consola su contraseña
        } else if (actionEvent.getSource() == menuPerso) {
            Dialog<Usuario> dialog = new Dialog<>();
            dialog.setTitle("Dialogo login");

            // poner lo grafico
            // cargar un elemento FXML
            DialogoController dialogoController;
            FXMLLoader loader = new FXMLLoader
                    (getClass().getResource("dialog-view.fxml"));
            Parent root = null;
            try {
                root = loader.load();
                dialogoController = loader.getController();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            dialog.setResizable(true);
            dialog.getDialogPane().setContent(root);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY);
            dialog.setResultConverter(new Callback<ButtonType, Usuario>() {
                @Override
                public Usuario call(ButtonType buttonType) {

                    // DialogController -> getUsuario()
                    if (dialogoController.isUsuario()) {
                        return dialogoController.getUsuario();
                    }
                    return null;
                }
            });
            Optional<Usuario> respuesta = dialog.showAndWait();
            if (respuesta.isEmpty()) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setContentText("No estan todos los datos seleccionados");
                alerta.show();
            } else {
                System.out.println(respuesta.get().getCorreo());
            }

        } else if (actionEvent.getSource() == botonComprobar) {
            // combo -> elementoSeleccionado
            // combo -> adapter -> elementoEnPosicion

            if (combo.getSelectionModel().getSelectedIndex()>-1
            && choice.getSelectionModel().getSelectedIndex()>-1){
                System.out.println("El elemento seleccionado del combo es: "+
                        combo.getSelectionModel().getSelectedItem());
                System.out.println("El elemento seleccionado del choice es: "+
                        choice.getSelectionModel().getSelectedItem().getAnio());
                System.out.println("El elemento seleccionado del spinner es: "+
                        spinner.valueProperty().get());
            } else {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Elementos");
                alerta.setHeaderText("No hay elementos seleccionados");
                alerta.show();
            }

            //System.out.println("El elemento seleccionado es: "+
            // combo.getItems().get(combo.getSelectionModel().getSelectedIndex()));
        } else {
            System.exit(0);
        }
    }
}