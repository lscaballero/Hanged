package application.controller;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import application.Main;
import application.model.Pregunta;
import application.model.PreguntaListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class JuegoLayoutController {

	@FXML
    private ButtonBar btnBarLetras1;
	@FXML
    private ButtonBar btnBarLetras2;
	@FXML
    private ButtonBar btnBarLetras3;
	@FXML
    private TextArea txtPregunta;
    @FXML
    private Label lblPalabra;
    @FXML
    private Label lblNivel;
    @FXML
    private Label lblIntentos;
    @FXML
    private ImageView imgAhorcado;

	// instacia de objeto de la clase principal.
    private Main mainApp;

    private char[] respuestaArray;
    private String respuesta="";
    private String traduccion="";
    private int intentos=0;
    private int nivel=0;
    private boolean cambioNivel=true;
    private ObservableList<Pregunta> lstPreguntas = FXCollections.observableArrayList();
    private ObservableList<Pregunta> lstPreguntasNivelActual = FXCollections.observableArrayList();

    /**
     *
     * inicializa la clase, este metodo se llama automaticamente
     * despues de que carga el archivo fmxl relacionado
     */
    @FXML
    private void initialize() {
    	cargarPreguntas();
    	iniciarJuego();
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    private void iniciarJuego()
    {
    	try {
	    	intentos=6;
	    	lblIntentos.setText(Integer.toString(intentos));

	    	File file = new File("recursos/img/" + Integer.toString(intentos) + ".jpg");
	        Image image = new Image(file.toURI().toString());
	    	imgAhorcado.setImage(image);

	    	if(lstPreguntasNivelActual.size()==0 || cambioNivel){
	    		lstPreguntasNivelActual.clear();
	    		cambioNivel=false;
	    		nivel++;
	    		lstPreguntasNivelActual.addAll(lstPreguntas.filtered(x->x.getNivel()==nivel));
	    	}

	    	if(lstPreguntasNivelActual.size()>0){

	    		lblNivel.setText(Integer.toString(nivel));

		    	Pregunta pregunta= lstPreguntasNivelActual.get(0);
		    	lstPreguntasNivelActual.remove(0);


		        txtPregunta.setText(pregunta.getDescripcion());
		    	respuesta=pregunta.getRespuesta();
		    	traduccion=pregunta.getTraduccion();

		    	// Creating array of string length
		    	respuestaArray = new char[respuesta.length()];

		        // Copy character by character into array
		        for (int i = 0; i < respuesta.length(); i++) {
		        	respuestaArray[i] = '_';
		        }

		        String palabraOculta="";
		        // Printing content of array
		        for (char c : respuestaArray) {
		        	palabraOculta += c;
		        }
		        lblPalabra.setText(palabraOculta);

		        ObservableList<Node> elementos =  btnBarLetras1.getButtons();
		        elementos.forEach((elemento) -> {
		        	Button btn=(Button)elemento;
		        	btn.setDisable(false);
		        	btn.setStyle("-fx-background-color: CADETBLUE;");;
		        });

		        ObservableList<Node> elementos2 =  btnBarLetras2.getButtons();
		        elementos2.forEach((elemento) -> {
		        	Button btn=(Button)elemento;
		        	btn.setDisable(false);
		        	btn.setStyle("-fx-background-color: CADETBLUE;");;
		        });

		        ObservableList<Node> elementos3 =  btnBarLetras3.getButtons();
		        elementos3.forEach((elemento) -> {
		        	Button btn=(Button)elemento;
		        	btn.setDisable(false);
		        	btn.setStyle("-fx-background-color: CADETBLUE;");;
		        });
	    	}else{
	    		Alert alert = new Alert(AlertType.CONFIRMATION);
	            alert.initOwner(mainApp.getPrimaryStage());
	            alert.setTitle("Game over");
	            alert.setHeaderText("");
	            alert.setContentText("Winner!!!");
	            alert.showAndWait();

	            nivel=0;
	            lstPreguntasNivelActual.removeAll();
	            iniciarJuego();
	    	}
    	} catch (Exception e) { // catches ANY exception
        	System.out.println(e);
        }

    }

    /**
     * Loads person data from the specified file. The current person data will
     * be replaced.
     */
    public void cargarPreguntas() {
    	File file = new File("recursos/data/preguntas.xml");

    	try {
            JAXBContext context = JAXBContext
                    .newInstance(PreguntaListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            PreguntaListWrapper wrapper = (PreguntaListWrapper) um.unmarshal(file);

            lstPreguntas.clear();
            lstPreguntas.addAll(wrapper.getPreguntas());

        } catch (Exception e) { // catches ANY exception
        	Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Error");
        	alert.setHeaderText("Could not load data");
        	alert.setContentText("Could not load data from file:\n" + file.toURI().toString());
        	alert.showAndWait();
        }
    }

	/**
     * se ejecuta cuando se da click en los botones letras.
     */
    @FXML
    private void handleClickLetras(ActionEvent event) {

    	Boolean letraCorrecta= false;

    	//capturar el boton que hizo click
    	Button btn=(Button)event.getSource();

    	//buscar si la letra esta en la palabra
        for (int i = 0; i < respuesta.length(); i++) {
        	if(btn.getText().equals(Character.toString(respuesta.charAt(i))))
        	{
        		respuestaArray[i] = respuesta.charAt(i);
        		letraCorrecta=true;
        	}
        }

        btn.setDisable(true);
        //si la letra es correcta mustra la letra correta si no reduce los intentos
        if(letraCorrecta){
        	btn.setStyle("-fx-background-color: GREEN;");

	        String palabraOculta="";
	        // Printing content of array
	        for (char c : respuestaArray) {
	        	palabraOculta += c;
	        }
	        lblPalabra.setText(palabraOculta);

	        if(palabraOculta.equals(respuesta)){
	        	Alert alert = new Alert(AlertType.INFORMATION);
	            alert.initOwner(mainApp.getPrimaryStage());
	            alert.setTitle("Muy bien!!");
	            alert.setHeaderText("");
	            alert.setContentText("Palabra Correcta. Traduccion: " + traduccion);
	            alert.showAndWait();
	            iniciarJuego();
	        }

        }else{
        	btn.setStyle("-fx-background-color: RED;");

        	intentos--;

        	File file = new File("recursos/img/" + Integer.toString(intentos) + ".jpg");
            Image image = new Image(file.toURI().toString());
        	imgAhorcado.setImage(image);

        	lblIntentos.setText(Integer.toString(intentos));

        	if(intentos==0)
        	{
	        	Alert alert = new Alert(AlertType.ERROR);
	            alert.initOwner(mainApp.getPrimaryStage());
	            alert.setTitle("Game over");
	            alert.setHeaderText("");
	            alert.setContentText("You lost, Palabra Incorrecta. La respuesta es: " + respuesta + ". Traduccion: " + traduccion);
	            alert.showAndWait();

	            nivel=0;
	            cambioNivel=true;
	            iniciarJuego();
        	}
        }
    }
}
