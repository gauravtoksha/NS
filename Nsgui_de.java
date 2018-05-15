
import javafx.geometry.Pos;
import javafx.concurrent.*;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.beans.value.*;
import javafx.event.ActionEvent;
import javafx.event.*;
import javafx.scene.control.Separator;
import javafx.geometry.Orientation;



public class Nsgui_de extends Application {
	EncryptHandler encryptHandler;
	DecryptHandler decryptHandler;
	Thread thread;
	public static void main(String[] args) {
			launch(args);
		}
	@Override
	public void start(Stage primaryStage){

		GridPane grid=new GridPane();


		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text entxt=new Text("Encrypted:");
		Text encrypted= new Text();		
		Text keytxt=new Text("Key:");
		Text key = new Text();
		Text ciphertxt=new Text("Cipher:");
		Text cipher=new Text();

		Text detxt=new Text("Decrypted:");
		Text decrypted=new Text();

		Label status=new Label("Status..");

		grid.add(status,0,1);
		grid.add(entxt,0,2);
		grid.add(encrypted,1,2);
		grid.add(keytxt,0,3);
		grid.add(key,1,3);
		grid.add(ciphertxt,0,4);
		grid.add(cipher,1,4);
		grid.add(detxt,0,5);
		grid.add(decrypted,1,5);


		Task <Void> task = new Task<Void>() {
			      @Override public Void call() throws InterruptedException {
						        // "message2" time consuming method (this message will be seen).
			      				
			      				updateMessage(Server.status);
			      				Server server=new Server(9000);
			      				Thread.sleep(1000);
						  		
						        updateMessage("Decrypting");
						         encryptHandler=server.getOutput();
						         
						        decryptHandler=new DecryptHandler(encryptHandler.getEncrypted(),encryptHandler.getCipher(),encryptHandler.getKey());
						        updateMessage("Decrypted");
						        encrypted.setText(decryptHandler.getEncrypted());
    							key.setText(decryptHandler.getKey());
    							cipher.setText(decryptHandler.getCipher());
    							decrypted.setText(decryptHandler.getDecrypted());

						        return null;
						      }
						    };

		status.textProperty().bind(task.messageProperty());
		thread = new Thread(task);
    	thread.setDaemon(true);
    	thread.start();
    	
		Scene scene=new Scene(grid,400,200);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}