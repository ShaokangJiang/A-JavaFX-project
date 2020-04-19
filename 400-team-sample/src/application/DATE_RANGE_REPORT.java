package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class DATE_RANGE_REPORT extends Report implements Calculate, Export<DATE_RANGE_REPORT.DATE_RANGE>{

  protected Date start;
  protected Date end; 
  
  /**
   * Instead of passing in the daate range, let user click a button to choose at first
   * @param st
   */
  public DATE_RANGE_REPORT(List<Farmer> st, Date start, Date end) {
    super(st);
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
    return null;
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
