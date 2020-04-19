package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class ImportExportWindow extends Application {

  protected String path;
  protected Stage s; //need to visit in the future. So start will
  //also need to setup this field. 
  
  @Override
  public void start(Stage s) throws Exception {
    // TODO Auto-generated method stub

  }
  
  /**
   * put the passed in ds to the path, if path doesn;t contain 
   * filename, use export.csv if file already exist, use path+Date.currentTime. 
   * @param ds the dataframe being exported
   */
  public void export(DataFrame ds, String path) {
    
  }
  
  /**
   * Hide this window
   */
  public void hide() {
    
  }
  
  /**
   * Show this window
   * 0 -- import model
   * 1 -- export model
   * 
   * 
   * @param i int to indicate this is in import model or export model
   */
  public void show(int i) {
    
  }
  
  /**
   * Should detect of the path is multiple paths("C:/a.a;C:/b.a") 
   * or not If it is, read each csv in each position
   * and merge them into one dataframe then set protected path to the first path in the list
   * , if any of file doesn;t meet the requirement(error file) prompt the user then 
   * return null
   * 
   * 
   * @param path path or path list to files
   * @return the DataFrame contains all information
   */
  public DataFrame Import(String path) {
    
  }

}
