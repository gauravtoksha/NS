import javafx.geometry.Pos;
import javafx.concurrent.*;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
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
public class Nsgui extends Application {


	VBox vbox1,vbox2;
	static String cipher;
	static Label statuslabel;
	BorderPane root;
	Scene scene;
	Thread thread;
	BorderPane rootofnext;
	TextField sptf;
	TextField aptf;
	EncryptHandler encryptHandler;
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage){
		primaryStage.setTitle("Network Security");
		root=new BorderPane();

		ToggleGroup radioToggle=new ToggleGroup();

		RadioButton rb1= new RadioButton("Symmetric");
		rb1.setUserData("Symmetric");
		RadioButton rb2= new RadioButton("Asymmetric");
		rb2.setUserData("ASymmetric");

		rb1.setToggleGroup(radioToggle);
		rb2.setToggleGroup(radioToggle);

		final Separator separator = new Separator();
		separator.setOrientation(Orientation.VERTICAL);

		root.setCenter(separator);

		HBox toppanel = new HBox();
		toppanel.setPadding(new Insets(10));
		toppanel.setSpacing(120);

		toppanel.getChildren().addAll(rb1,rb2);

		root.setTop(toppanel);

		vbox1 = new VBox();
	    vbox1.setPadding(new Insets(10));
    	vbox1.setSpacing(8);

	    vbox2 = new VBox();
	    vbox2.setPadding(new Insets(10));
	    vbox2.setSpacing(8);

		root.setLeft(vbox1);
		root.setRight(vbox2);

		vbox1.getChildren().add(new Text("Symmetric"));
		vbox2.getChildren().add(new Text("Asymmetric"));
		vbox1.setVisible(false);
        vbox2.setVisible(false);

		ObservableList<String> symcombobox = 
    	FXCollections.observableArrayList(
        "Ceasar",
        "Playfair",
        "Hill",
        "Columnar",
        "Onetime",
        "SDES"
    	);
		final ComboBox comboBox1 = new ComboBox(symcombobox);



		ObservableList<String> asymcombobox = 
    	FXCollections.observableArrayList(
        "Ceaser",
        "Playfair",
        "Hill"
    	);
		final ComboBox comboBox2 = new ComboBox(asymcombobox);

		
		
		vbox1.getChildren().add(comboBox1);
		vbox2.getChildren().add(comboBox2);

		sptf = new TextField();
		aptf = new TextField();
		sptf.setMaxWidth(120);
		aptf.setMaxWidth(120);
		Text spt = new Text("Plaintext:");
		Text apt = new Text("Plaintext:");
		

		TextField skeytf = new TextField();
		TextField akeytf = new TextField();
		Text skey = new Text("Key:");
		Text akey = new Text("Key:");

		skeytf.setMaxWidth(120);
		akeytf.setMaxWidth(120);


		vbox1.getChildren().add(spt);
		vbox1.getChildren().add(sptf);
		vbox1.getChildren().add(skey);
		vbox1.getChildren().add(skeytf);
		vbox2.getChildren().add(apt);
		vbox2.getChildren().add(aptf);
		vbox2.getChildren().add(akey);
		vbox2.getChildren().add(akeytf);



		vbox2.setAlignment(Pos.CENTER_LEFT);
		Button next= new Button("Next");
		root.setBottom(next);
		next.setPadding(new Insets(8));
		BorderPane.setAlignment(next,Pos.TOP_RIGHT);
		BorderPane.setMargin(next,new Insets(15,15,15,15));
		BorderPane.setMargin(vbox2,new Insets(0,40,0,0));
		BorderPane.setMargin(vbox1,new Insets(0,0,0,30));
		statuslabel=new Label();

		scene=new Scene(root,400,280);

		EncryptHandler encryptHandler=new EncryptHandler();

		Task <Void> task = new Task<Void>() {
			      @Override public Void call() throws InterruptedException {
						        // "message2" time consuming method (this message will be seen).
			      				updateMessage("Encrypting..");
			      				
			      				
						   		updateMessage("Connecting...");
						        Client client= new Client("127.0.0.1",9000,encryptHandler);
						        updateMessage(Client.status);
						        updateMessage("Msg sent..Connection Closed");
						        return null;
						      }
						    };

		statuslabel.textProperty().bind(task.messageProperty());

		thread = new Thread(task);
    	thread.setDaemon(true);

    	rootofnext=new  BorderPane();
    	rootofnext.setCenter(statuslabel);

		next.setOnAction(new EventHandler<ActionEvent>() {
    	@Override 
    		public void handle(ActionEvent e) {
				if(radioToggle.getSelectedToggle().getUserData().toString()=="Symmetric"){
					String userChoice = comboBox1.getSelectionModel().getSelectedItem().toString();
					System.out.println(userChoice);
					cipher=(String)comboBox1.getValue();
					
					encryptHandler.setPlaintext(sptf.getText());
					if(cipher.equals("Onetime")){
						encryptHandler.setKey("");
					}
					else{
						encryptHandler.setKey(skeytf.getText());
					}
					encryptHandler.setCipher(cipher);
					encryptHandler.startEncryption();
					scene.setRoot(rootofnext);
				}else{
					String userChoice = comboBox2.getSelectionModel().getSelectedItem().toString();
					System.out.println(userChoice);
					cipher=(String)comboBox2.getValue();
					
					encryptHandler.setPlaintext(aptf.getText());
					encryptHandler.setKey(akeytf.getText());
					encryptHandler.setCipher(cipher);
					encryptHandler.startEncryption();
					scene.setRoot(rootofnext);
				}
				thread.start();
    		}
		});







		radioToggle.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
    	public void changed(ObservableValue<? extends Toggle> ov,
        Toggle old_toggle, Toggle new_toggle) {
            if (radioToggle.getSelectedToggle() != null) {
                if(radioToggle.getSelectedToggle().getUserData().toString()=="Symmetric"){
                	vbox1.setVisible(true);
                	vbox2.setVisible(false);
                }
                else{
                	vbox1.setVisible(false);
                	vbox2.setVisible(true);
                }
            }                
        }
});											


		
		primaryStage.setScene(scene);
		primaryStage.show();



 		}
}