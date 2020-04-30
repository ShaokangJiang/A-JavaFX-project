package application;

import javafx.scene.control.TableView;
/**
 * The export interface to organize data
 * @author shaokang
 *
 * @param <S>
 */
interface Export{

  /**
   * For this method, you need to put the analyze result to a 
   * DataFrame, The analyzed result is represented in Tableview a
   * But there are some other special circumstances. 
   * For e.g., In FarmerManager, it will be good to return dataFrame 
   * directly.  
   * @return the dataframe that contains required information in different 
   * cases
   */
  DataFrame export(TableView<Object[]> a);
  
}
