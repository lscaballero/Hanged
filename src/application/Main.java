package application;

import java.io.IOException;

import application.view.JuegoLayoutController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {

	private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        //establecer titulo de aplicacion
        this.primaryStage.setTitle("Hanged");

        //establecer icono de aplicacion.
        this.primaryStage.getIcons().add(new Image("file:recursos/img/icon.png"));

        iniciarRootLayout();
        mostrarJuejoLayout();
    }

    /**
     * inicia la vista principal.
     */
    public void iniciarRootLayout() {
        try {
            // cargar la vista pricipal fxml.
            rootLayout = (BorderPane)FXMLLoader.load(getClass().getResource("view/RootLayout.fxml"));

            // Mostrar la escena que contiene la vista principal.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
		Muestra la vista del juego dentro de la vista pricipal.
     */
    public void mostrarJuejoLayout() {
        try {
            // carga la vista del juego.        	
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/JuegoLayout.fxml"));
            AnchorPane juegoLayout = (AnchorPane) loader.load();

            // establece la vista del juego en el centro de la vista principal.
            rootLayout.setCenter(juegoLayout);
            
            // conceder acceso de la aplicacion principal al controlador.
            JuegoLayoutController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * retorna el main stage.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

	public static void main(String[] args) {
		launch(args);
	}
}
