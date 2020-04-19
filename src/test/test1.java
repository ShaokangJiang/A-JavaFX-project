package test;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Pair;

public class test1 extends Application{

	public static void main(String[] args) {
		Application.launch(alert.class,args);
		alert.message = "aaaaa";
		Popup.display();
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
