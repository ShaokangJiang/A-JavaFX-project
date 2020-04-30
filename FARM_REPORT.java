package application;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Assignment requirement:
 * 
 * Prompt user for a farm id and year : <id, year> = <Integer, Integer>
 * 
 * Choice 2 (or use all available data) <id, year> = <null, null>
 * 
 * Then, display the total milk weight and percent of the total of all farm for each month.
 * 
 * Sort, the list by month number 1-12, show total weight, then that farm's percent of the total
 * milk received for each month.
 * 
 * @author shaokang
 *
 */
public class FARM_REPORT extends Report implements Calculate, Export {
  private class Download {
    SimpleObjectProperty<Integer> part1 = new SimpleObjectProperty<>(0);
    SimpleObjectProperty<Integer> part2 = new SimpleObjectProperty<>(0);

    // SimpleObjectProperty<Integer> part3= new SimpleObjectProperty<>(0);
    public Download(Object p1, Object p2) {
      part1.set((Integer) p1);
      part2.set((Integer) p2);
      // part3.set((Integer)p3);

    }

    public int getPart1() {
      return part1.get();
    }

    public SimpleObjectProperty<Integer> part1Property() {
      return part1;
    }

    public void setPart1(int part1) {
      this.part1.set(part1);
    }

    public int getPart2() {
      return part2.get();
    }

    public SimpleObjectProperty<Integer> part2Property() {
      return part2;
    }

    public void setPart2(int part2) {
      this.part2.set(part2);
    }
    /*
     * public int getPart3() { return part3.get(); }
     * 
     * public SimpleObjectProperty<Integer> part3Property() { return part3; }
     * 
     * public void setPart3(int part3) { this.part3.set(part3); }
     */
  }
  private Integer galL=0;
  private Integer galU=Integer.MAX_VALUE ;
  private Integer[] dateL=new Integer[]{1,0};
  private Integer[] dateU=new Integer[]{12,31};
  
  private TableView<Download> tv;
  private DataFrameIndex displaystuff;
  private DataFrameIndex expt;
  protected Integer id;
  protected Integer year;
  protected int farmersTotalWeight;
  private TableColumn<Download, Integer> part1Col;
  private TableColumn<Download, Integer> part2Col ;
  private TableColumn<Download, Double> part3Col ;
  private ObservableList<Download> data = FXCollections.observableArrayList();

  private static DecimalFormat df = new DecimalFormat("#.00");

  public FARM_REPORT(HashMap<Integer, Farmer> farmers, Integer id, Integer year,
      Integer farmersTotalWeight) {
    super(farmers);
    // TODO Auto-generated constructor stub
    this.id = id;
    this.year = year;
    this.farmersTotalWeight = farmersTotalWeight;
    part1Col = new TableColumn<>("ID");
    part2Col = new TableColumn<>("Tol,wg");
    part3Col = new TableColumn<>("precent");
    part1Col.setCellValueFactory(param -> param.getValue().part1Property());


    part2Col.setCellValueFactory(param -> param.getValue().part2Property());


    part3Col.setCellValueFactory(param -> {
      return Bindings.createObjectBinding(() -> {
        return (double) ((double) param.getValue().getPart2() / (double) this.farmersTotalWeight);
      }, param.getValue().part2Property());
    });

    tv = new TableView<Download>();
  tv.getColumns().addAll(part1Col, part2Col, part3Col);

  }

  
  @Override
  public DataFrame export(TableView<Object[]> a) {
    // TODO Auto-generated method stub
    return null;
  }


  private void showIEWD() {
    ImportExportWindow wd = new ImportExportWindow();
    Stage IEWindow = new Stage();
    IEWindow.setTitle("JavaFX GUI");

    Label labelmain = new Label();

    Button buttonmain = new Button("Displayimport");
    buttonmain.setOnAction(e -> {
      try {
        displaystuff = ImportExportWindow.Displayimport(IEWindow);
        //tv=new TableView<Download>();
        //tv.clear();
        data.clear();
        fill();
        tv.setItems(data);
        
      } catch (Exception as) {
        as.printStackTrace();

      }
    });

    Button buttonmain1 = new Button("Displayexport");
    buttonmain1.setOnAction(e -> {
      try {
        if (ImportExportWindow.DisplayExport(IEWindow, expt)) {

        } ;
      } catch (Exception as) {

      }
    });

    VBox layout = new VBox(20);
    layout.getChildren().addAll(labelmain, buttonmain, buttonmain1);

    Scene scene1 = new Scene(layout, 300, 250);
    IEWindow.setScene(scene1);

    IEWindow.show();
  }

  private void fill() throws IllegalArgumentException, ParseException {
   
    expt = new DataFrameIndex(Arrays.copyOf(displaystuff.column, displaystuff.column.length),
        Arrays.copyOf(displaystuff.dataType, displaystuff.dataType.length));
      Integer[] tar=new Integer[2] ;
      Integer gal=0;
    for (int i = 0; i < displaystuff.rows.size(); i++) {
      // System.out.println(displaystuff.getRow(i)[0].toString().substring(0,4));
      // System.out.println(this.id.equals(displaystuff.getRow(i)[1]));
      // System.out.println(this.year.equals(displaystuff.getRow(i)[0].toString().substring(0,4)));
      tar[0]=Integer.valueOf(displaystuff.getRow(i)[0].toString().split("-")[1]);
      tar[1]=Integer.valueOf(displaystuff.getRow(i)[0].toString().split("-")[2]);
      gal=Integer.valueOf(displaystuff.getRow(i)[1].toString());
      if (this.id.equals(displaystuff.getRow(i)[1]) && this.year
          .equals(Integer.valueOf(displaystuff.getRow(i)[0].toString().substring(0, 4)))&&dateL[0]<=tar[0]&&tar[0]<=dateU[0]&&galL<=gal&&gal<=galU) {
        if(dateL[0]==tar[0]) {
          if(tar[1]<dateL[1]) {
            continue;
          }
        }else if(dateU[0]==tar[0]) {
          if(tar[1]>dateU[1]) {
            continue;
          }
          
        }
        data.add(new Download(displaystuff.getRow(i)[1], displaystuff.getRow(i)[2]));
        expt.appendRow(displaystuff.getRow(i));
      }



    }


    // TableView<Download> tableView = new TableView<>();



  }



  @Override
  public BorderPane Analize() {
    // TODO Auto-generated method stub
    BorderPane pane = new BorderPane();

    Stage FilterWindow = new Stage();
    GridPane pane2 = new GridPane();
    pane2.setHgap(10);
    pane2.setVgap(10);
    pane2.setPadding(new Insets(20, 150, 10, 10));

   

    // filters
   Label label1 = new Label("See dates from");
    TextField textfield1 = new TextField();
    Label label11 = new Label("to");
    TextField textfield11 = new TextField();
    HBox hBox1 = new HBox(label1, textfield1, label11, textfield11);

    Label label2 = new Label("See gallon from");
    TextField textfield2 = new TextField();
    Label label21 = new Label("to");
    TextField textfield21 = new TextField();
    HBox hBox2 = new HBox(label2, textfield2, label21, textfield21);



    // save button
    Button save = new Button("Save");
    save.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        //store the selected data
     /*  if( textfield1.getText().split("-").length==3&&textfield11.getText().split("-").length==3) {
        dateL[0]=Integer.valueOf(textfield1.getText().split("-")[1]);
        dateL[1]=Integer.valueOf(textfield1.getText().split("-")[2]);
        dateU[0]=Integer.valueOf(textfield11.getText().split("-")[1]);
        dateU[1]=Integer.valueOf(textfield11.getText().split("-")[2]);}*/
        galL=Integer.valueOf(textfield2.getText());
        galU=Integer.valueOf(textfield21.getText());
        data.clear();
        try{fill(); tv.setItems(data); pane.setLeft(tv);}
        catch(Exception e) {
          e.printStackTrace();
        }
      }
    });
    HBox hBox = new HBox(save);
    

    // filter gridpane
  
    pane2.add(hBox1, 0, 1);
    pane2.add(hBox2, 0, 2);
    pane2.add(hBox, 0, 3);
    hBox.setAlignment(Pos.CENTER_RIGHT);

    // Scene
    Scene fts = new Scene(pane2, 700, 800);
    // Add the stuff and set the primary stage
    FilterWindow.setTitle("filter");
    FilterWindow.setScene(fts);


    /*
     * TableColumn<Object[], Integer> id = new TableColumn<Object[], Integer>( "ID");
     * id.setCellValueFactory( new Callback<CellDataFeatures<Object[], Integer>,
     * ObservableValue<Integer>>() { public ObservableValue<Integer> call(
     * CellDataFeatures<Object[], Integer> p) { // p.getValue() returns the Person instance for a //
     * particular TableView row return new ReadOnlyObjectWrapper<>( (Integer) p.getValue()[0]); }
     * });
     * 
     * TableColumn<Object[], Integer> total = new TableColumn<Object[], Integer>( "Tot_Wei");
     * total.setCellValueFactory( new Callback<CellDataFeatures<Object[], Integer>,
     * ObservableValue<Integer>>() { public ObservableValue<Integer> call(
     * CellDataFeatures<Object[], Integer> p) { // p.getValue() returns the Person instance for a //
     * particular TableView row return new ReadOnlyObjectWrapper<>( (Integer) p.getValue()[1]); }
     * });
     * 
     * TableColumn<Object[], String> percent = new TableColumn<Object[], String>( "percent(%)");
     * percent.setCellValueFactory( new Callback<CellDataFeatures<Object[], String>,
     * ObservableValue<String>>() { public ObservableValue<String> call( CellDataFeatures<Object[],
     * String> p) { // p.getValue() returns the Person instance for a // particular TableView row
     * return new ReadOnlyObjectWrapper<>( df.format(((Double) p.getValue()[2]))); } });
     */
    //TableView<Download> tableview = new TableView<Download>();

    // tableview.setItems(data);

     //id.setSortable(true);
     //id.setSortType(SortType.DESCENDING);
     //tableview.getColumns().addAll(id, total, percent);



    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));
    tv = new TableView<Download>();
    Button Filter = new Button("Filter");
    Filter.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        FilterWindow.show();
      }
    });

    Button Export = new Button("Export");
    Export.setOnAction(new EventHandler<ActionEvent>() {

      public void handle(ActionEvent event) {
        showIEWD();



      }
    });



    grid.add(Filter, 0, 0);
    grid.add(Export, 0, 1);

    pane.setRight(grid);
    pane.setLeft(tv);

    alert1.display("This effort will display the annual report for each farmer in year" + year
        + "\nRepresentation of each field:" + "\n  id -- Farmer_id in decending order"
        + "\n  tot_weight -- the tital weight for "
        + "\n  percent -- weight of this farm/weight of all farmers in this year");

    return pane;
  }
}
