package app;



import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Client_App extends Application {
	PrintWriter pw;
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Showing The Interface Of App 
		primaryStage.setTitle("Chatt App ");
		BorderPane borderPane= new BorderPane();
		Label labelhost = new Label("Server Host  ") ;
		TextField textFieldHost= new TextField("localhost");
		Label labelport = new Label("PORT :  ") ;
		TextField textFieldPort= new TextField("1234");
		Button buttonConnect= new Button("Connect");
		HBox hBox= new HBox(); hBox.setSpacing(10);hBox.setPadding(new Insets(10));
		hBox.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.LIGHTSKYBLUE, null, null)));
		hBox.getChildren().addAll(labelhost,textFieldHost,labelport, textFieldPort, buttonConnect);
		VBox vBox=new VBox();vBox.setSpacing(10);vBox.setPadding(new Insets(10));
		
		ObservableList<String> listModel=FXCollections.observableArrayList();
		ListView<String>listView=new ListView<String>(listModel);
		vBox.getChildren().add(listView);
		borderPane.setCenter(vBox);
		
		Label labelMessage =new Label("Message");
		TextField textFieldMessage=new TextField();textFieldMessage.setPrefSize(400, 30);
		Button buttonEnvoyer = new Button ("SEND") ;
		
		HBox hBox2= new HBox();hBox2.setSpacing(10);hBox2.setPadding(new Insets(10));
		hBox2.getChildren().addAll(labelMessage,textFieldMessage,buttonEnvoyer);
		borderPane.setBottom(hBox2);
		
		borderPane.setTop(hBox);
		Scene scene =new Scene (borderPane, 800, 500 );
		primaryStage.setScene(scene);
		primaryStage.show();
		// Config The Button
		buttonConnect.setOnAction((evt)->{
			String host=textFieldHost.getText();
			int port= Integer.parseInt(textFieldPort.getText());
			try {
				Socket socket = new Socket(host, port);
				InputStream inputStream =socket.getInputStream();
				InputStreamReader isr=new InputStreamReader(inputStream);
				BufferedReader bufferedReader =new BufferedReader(isr);
				 pw=new PrintWriter(socket.getOutputStream(),true);
				new Thread(()->{
		
						while(true) {
						
								try {
									String replay=bufferedReader.readLine();
									
									Platform.runLater(()->{
									listModel.add(replay);
									});
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}	
						}
					
				}).start();
			} 
			
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				
				});
		
	
		buttonEnvoyer.setOnAction((evt)->{
			String message=textFieldMessage.getText();
			pw.println(message);
			
		});
		
	}
	

}
