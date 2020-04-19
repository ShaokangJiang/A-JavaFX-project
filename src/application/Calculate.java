package application;

import javafx.scene.layout.BorderPane;

/**
 * To organize calculate class
 * 
 * @author shaokang
 *
 */
interface Calculate {

  /**
   * In this interface, you need to represent the result of different 
   * analyze in the borderpane. Currently we plan to have a TableView, 
   * A Graph view, two button to filter or export.
   * 
   * When filter is called, Graph and table content should be changed 
   * dynamically. It means, once user click save in the filter window
   * the program will change the table and graph in the borderPane window
   * 
   * @return the panel contains those information
   */
  BorderPane Analize();
  
}
