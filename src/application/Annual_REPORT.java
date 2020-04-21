package application;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class Annual_REPORT extends Report implements Calculate, Export<Annual_REPORT.Annual>{

  public Annual_REPORT(HashMap<Integer, Farmer> farmers) {
    super(farmers);
    // TODO Auto-generated constructor stub
  }

  @Override
  public DataFrame export(TableView<Annual> a) {
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
  protected class Annual{
    protected int id;
    protected Date year;
    protected int Num;
  }
}
