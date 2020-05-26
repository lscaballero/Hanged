package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Pregunta {
	 	private final StringProperty descripcion;
	    private final StringProperty respuesta;
	    private final StringProperty traduccion;
	    private final IntegerProperty nivel;

	    /**
	     * default constructor.
	     */
	    public Pregunta() {
	    	this(null,null,null,0);
	    }

	    /**
	     * constructor con data.
	     */
	    public Pregunta(String descripcion,String respuesta,String traduccion, int nivel) {
	    	this.descripcion = new SimpleStringProperty(descripcion);
	    	this.respuesta = new SimpleStringProperty(respuesta);
	    	this.traduccion = new SimpleStringProperty(traduccion);
	    	this.nivel = new SimpleIntegerProperty(nivel);
	    }

	    public String getDescripcion() {
	        return descripcion.get();
	    }

	    public void setDescripcion(String descripcion) {
	        this.descripcion.set(descripcion);
	    }

	    public String getRespuesta() {
	        return respuesta.get();
	    }

	    public void setRespuesta(String respuesta) {
	        this.respuesta.set(respuesta);
	    }

	    public String getTraduccion() {
	        return traduccion.get();
	    }

	    public void setTraduccion(String traduccion) {
	        this.traduccion.set(traduccion);
	    }

	    public int getNivel() {
	        return nivel.get();
	    }

	    public void setNivel(int nivel) {
	        this.nivel.set(nivel);
	    }
}
