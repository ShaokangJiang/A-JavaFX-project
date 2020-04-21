package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class MONTHLY_REPORT extends Report implements Calculate, Export<MONTHLY_REPORT.MONTH>{

  public MONTHLY_REPORT(HashMap<Integer, Farmer> farmers) {
    super(farmers);
    // TODO Auto-generated constructor stub
  }

  @Override
  public DataFrame export(TableView<MONTH> a) {
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  public BorderPane Analize() {
    // TODO Auto-generated method stub
    return null;
  }
  /**
   * This class is used to store information that will be represented in table view in the 
   * @author Shaokang Jiang
   *
   */
  protected class MONTH{
    protected int id;
    protected Date day;
    protected int Num;
  }


}
