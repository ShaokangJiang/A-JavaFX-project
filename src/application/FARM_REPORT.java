package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class FARM_REPORT extends Report implements Calculate, Export<FARM_REPORT.FARM>{
  

  public FARM_REPORT(HashMap<Integer, Farmer> farmers) {
	  super(farmers);
	// TODO Auto-generated constructor stub
}

@Override
  public DataFrame export(TableView<FARM> a) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public BorderPane Analize() {
    // TODO Auto-generated method stub
    return new BorderPane();
  }
  /**
   * This class is used to store information that will be represented in table view in the 
   * @author Shaokang Jiang
   *
   */
  protected class FARM{
    protected int id;
    protected Date day;
    protected int Num;
  }
  
}
