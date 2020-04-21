package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class DATE_RANGE_REPORT extends Report implements Calculate, Export<DATE_RANGE_REPORT.DATE_RANGE>{

  protected Date start;
  protected Date end; 
  
  /**
   * Instead of passing in the daate range, let user click a button to choose at first
   * @param st
   */
  public DATE_RANGE_REPORT(HashMap<Integer, Farmer> farmers, Date start, Date end) {
    super(farmers);
    // TODO Auto-generated constructor stub
    this.start = start;
    this.end = end; 
  }

  @Override
  public DataFrame export(TableView<DATE_RANGE> a) {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  /**
   * A little bit different at here. You need to generate the table and the graph based on
   * data range. And when you are registering a filter, you need to make sure the date range
   * are not able to exceed the start and end date range. 
   */
  public BorderPane Analize() {
    // TODO Auto-generated method stub
	  BorderPane tmp = new BorderPane();
	  GridPane grid = new GridPane();
	  
	  grid.add(new Label(start.toString()), 0, 0);
	  grid.add(new Label(end.toString()), 0, 1);
	  
	  tmp.setLeft(grid);
    return tmp;
  }
  /**
   * This class is used to store information that will be represented in table view in the 
   * @author Shaokang Jiang
   *
   */
  protected class DATE_RANGE{
    protected int id;
    protected Date day;
    protected int Num;
  }


  
}
