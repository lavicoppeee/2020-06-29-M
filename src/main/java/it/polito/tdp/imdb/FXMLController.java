/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.imdb;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.imdb.model.Arco;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnAdiacenti"
    private Button btnAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaAffini"
    private Button btnCercaAffini; // Value injected by FXMLLoader

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxRegista"
    private ComboBox<Director> boxRegista; // Value injected by FXMLLoader

    @FXML // fx:id="txtAttoriCondivisi"
    private TextField txtAttoriCondivisi; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	this.boxRegista.getItems().clear();
    	
    	Integer anno= this.boxAnno.getValue();
    	
    	if(anno==null) {
    		txtResult.setText("Devi scegliere un anno");
    		return ;
    	}
    	
    	this.model.creaGrafo(anno);
    	this.boxRegista.getItems().addAll(this.model.getVertici());
    	
    	txtResult.appendText("Grafo Creato!\n");
     	txtResult.appendText("# Vertici: " + model.nVertici()+ "\n");
     	txtResult.appendText("# Archi: " + model.nArchi() + "\n");
    	
    }

    @FXML
    void doRegistiAdiacenti(ActionEvent event) {
    	this.txtResult.clear();
    	
    	Director d=this.boxRegista.getValue();
    	
    	if(d==null) {
    		txtResult.setText("Devi scegliere un regista, se non c'è crea il grafo!");
    		return ;
    	}
    	
    	List<Arco> archi=this.model.getConnessi(d);
    	txtResult.appendText("Registi adiacenti a: "+d+" \n");
    			for(Arco a: archi) {
    				txtResult.appendText(a.toString()+"\n");
    			}
    	
    }

    @FXML
    void doRicorsione(ActionEvent event) {
		this.txtResult.clear();

		Director d = this.boxRegista.getValue();

		if (d == null) {
			txtResult.setText("Devi scegliere un regista, se non c'è crea il grafo!");
			return;
		}

		String cs = this.txtAttoriCondivisi.getText();

		Integer c;

		try {
			c = Integer.parseInt(cs);
		} catch (NumberFormatException e) {

			txtResult.setText("Devi inserire solo numeri");
			return;
		}
    	
		this.model.trovaDirettori(c, d);
		
		txtResult.appendText("Il cammino dal regista: "+d+" ha peso"+ this.model.getBestPeso()+"ed è formato da: \n");
		for(Director b: this.model.getBestCammino()) {
			txtResult.appendText(b.toString()+"\n");
		}
      
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAdiacenti != null : "fx:id=\"btnAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaAffini != null : "fx:id=\"btnCercaAffini\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxRegista != null : "fx:id=\"boxRegista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtAttoriCondivisi != null : "fx:id=\"txtAttoriCondivisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
   public void setModel(Model model) {
    	
    	this.model = model;
    	for(int i=0; i<3;i++) {
    		this.boxAnno.getItems().addAll(this.model.getAnno().get(i));
    	}
    	
    }
    
}
