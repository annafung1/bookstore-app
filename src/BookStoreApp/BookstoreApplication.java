
package BookStoreApp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class BookstoreApplication extends Application {
    ScreenState currentState = new LoginScreen();
    File booksFile = new File("books.txt"), customersFile = new File("customers.txt");
    Customer currentCustomer;
    
    @Override
    public void start(Stage bookstoreStage) throws IOException {
        currentCustomer = new Customer("","",0);  //basically string for user , string for password, sets point to 0 
        ObservableList<Book> booksOL = getBooks(); //  ObservableList of type book; A list that allows listeners to track changes when they occur. Calls getbook method
        ObservableList<Customer> custOL = readCustomers();
        ArrayList<Book> custCart = new ArrayList<Book>();
        // Declare all scenes right away for buttons
        Scene loginScene, ownerStartScene, ownerBooksScene, ownerCustomersScene, customerStartScene, customerCostScene;
        
        // start at login screen
        // create username and password fields
        Label unLb = new Label("Username:");
        TextField unTF = new TextField();
        
        Label pwLb = new Label("Password:");
        PasswordField pwPF = new PasswordField();

        Label welLb = new Label("Welcome to the Bookstore App");
       
        Button loginBtn = new Button();
        loginBtn.setText("login");
        
        
        
        GridPane loginGP = new GridPane();
        loginGP.setAlignment(Pos.CENTER);
        loginGP.setHgap(10);
        loginGP.setVgap(10);
        loginGP.setPadding(new Insets(25,25,25,25)); // set top, right, bottom, left
        
        loginGP.add(welLb, 0, 0, 2, 1);
        loginGP.setHalignment(welLb, HPos.CENTER);
        loginGP.add(unLb, 0, 1);
        loginGP.add(unTF, 1, 1);
        loginGP.add(pwLb, 0, 2);
        loginGP.add(pwPF, 1, 2);
        loginGP.add(loginBtn, 0, 3, 2, 1);
        loginGP.setHalignment(loginBtn, HPos.CENTER);
        
        loginScene = new Scene(loginGP, 400, 300);
        
        //----------------------------- owner start screen -----------------------------//
        Button booksBtn = new Button("Books");
        Button customersBtn = new Button("Customers");
        Button logoutBtn = new Button("Logout");
        
        GridPane ownerStartGP = new GridPane();
        ownerStartGP.setAlignment(Pos.CENTER);
        ownerStartGP.setHgap(10);
        ownerStartGP.setVgap(40);
        ownerStartGP.setPadding(new Insets(25,25,25,25)); //column index, row index, column span, row span
        ownerStartGP.add(booksBtn, 0, 0); 
        ownerStartGP.setHalignment(booksBtn, HPos.CENTER);
        ownerStartGP.add(customersBtn, 0, 1);
        ownerStartGP.setHalignment(customersBtn, HPos.CENTER);
        ownerStartGP.add(logoutBtn, 0, 2);
        ownerStartGP.setHalignment(logoutBtn, HPos.CENTER);
            
        ownerStartScene = new Scene(ownerStartGP, 400, 300);
        
        //----------------------------- owner books screen -----------------------------//
        
        
        TableView booksTV = new TableView();//makes a table 
        TableColumn<Book, String> bookNameCol = new TableColumn<>("Book Name"); //set column to Book name  <type of table, type its table's contents)
        bookNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));  //creating a name property 
                                                                              //setCellValueFactory = determins how many cells
        bookNameCol.setMinWidth(200);
        TableColumn<Book, Double> bookPriceCol = new TableColumn<>("Book Price"); //<type of table, type its table's contents)
        bookPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        booksTV.getColumns().addAll(bookNameCol,bookPriceCol);
        booksTV.setItems(booksOL);
        
        
        Label bookNameLb = new Label("Name:"), bookPriceLb = new Label("Price:");
        
        TextField bookNameTF = new TextField(), bookPriceTF = new TextField();
        
        Button addBookBtn = new Button("Add"), delBookBtn = new Button("Delete"), obsBackBtn = new Button("Back");
        
        GridPane ownerBooksGP = new GridPane();
        ownerBooksGP.setAlignment(Pos.CENTER);
        ownerBooksGP.setHgap(10);
        ownerBooksGP.setVgap(40);
        ownerBooksGP.setPadding(new Insets(25,25,25,25));
        ownerBooksGP.add(booksTV, 0, 0, 5, 1);
        ownerBooksGP.add(bookNameLb, 0, 1);
        ownerBooksGP.add(bookNameTF, 1, 1);
        ownerBooksGP.add(bookPriceLb, 2, 1);
        ownerBooksGP.add(bookPriceTF, 3, 1);
        ownerBooksGP.add(addBookBtn, 4, 1);
        ownerBooksGP.add(delBookBtn, 0, 2, 2, 1);
        ownerBooksGP.add(obsBackBtn, 3, 2, 2, 1);
        ownerBooksScene = new Scene(ownerBooksGP, 700, 500);
        
        addBookBtn.setOnAction(e -> {
            Book addedBook = new Book(bookNameTF.getText(), Double.parseDouble(bookPriceTF.getText()));
            booksOL.add(addedBook);
            
            bookNameTF.clear();
            bookPriceTF.clear();
            
            //System.out.println("Added book with name: "+addedBook.getName()+", price: $"+addedBook.getPrice());
        });
        
        delBookBtn.setOnAction(e -> {
            Book selectedBook = (Book)booksTV.getSelectionModel().getSelectedItem();
            booksOL.remove(selectedBook);
        });
        
        obsBackBtn.setOnAction(e -> {
            bookstoreStage.setScene(ownerStartScene);
        });
        
        //----------------------------- owner customers screen -----------------------------//
        TableView custTV = new TableView();
        TableColumn<Customer, String> custUnCol = new TableColumn<>("Username");
        custUnCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        custUnCol.setMinWidth(150);
        TableColumn<Customer, String> custPwCol = new TableColumn<>("Password");
        custPwCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        custPwCol.setMinWidth(150);        
        TableColumn<Customer, Double> custPtsCol = new TableColumn<>("Points");
        custPtsCol.setCellValueFactory(new PropertyValueFactory<>("points"));
        custPtsCol.setMinWidth(100);        
        
        custTV.getColumns().addAll(custUnCol,custPwCol,custPtsCol);
        custTV.setItems(custOL);
        
        Label custUnLb = new Label("Username:"), custPwLb = new Label("Password:");
        
        TextField custUnTF = new TextField(), custPwTF = new TextField();
        
        Button addCustBtn = new Button("Add"), delCustBtn = new Button("Delete"), ownerCustBackBtn = new Button("Back");
        
        
        addCustBtn.setOnAction(e -> {
            Customer addedCust = new Customer(custUnTF.getText(), custPwTF.getText(), 0);
            custOL.add(addedCust);
            
            custUnTF.clear();
            custPwTF.clear();
        });
        
        delCustBtn.setOnAction(e -> {
            Customer selectedCust = (Customer)custTV.getSelectionModel().getSelectedItem(); // SelectionModel- select single or multiple items within a ListView,
            custOL.remove(selectedCust);
        });
        
        
        GridPane ownerCustGP = new GridPane();
        
        ownerCustGP.setAlignment(Pos.CENTER);
        ownerCustGP.setHgap(10);
        ownerCustGP.setVgap(40);
        ownerCustGP.add(custTV, 0, 0, 5, 1);
        ownerCustGP.add(custUnLb, 0, 1);
        ownerCustGP.add(custUnTF, 1, 1);
        ownerCustGP.add(custPwLb, 2, 1);
        ownerCustGP.add(custPwTF, 3, 1);
        ownerCustGP.add(addCustBtn, 4, 1);
        ownerCustGP.add(delCustBtn, 0, 2, 2, 1);
        ownerCustGP.add(ownerCustBackBtn, 3, 2, 2, 1);
        
        ownerCustomersScene = new Scene(ownerCustGP, 700, 500);
        
        //----------------------------- customer start screen -----------------------------//
        Label welcomeMsgLb = new Label();
        
        Button custBuyBtn = new Button("Buy"), custRedeemBtn = new Button("Redeem points and Buy"), custLogoutBtn = new Button("Logout");
        
        TableView<Book> custBooksTV = new TableView<Book>(booksOL);
        
        TableColumn<Book,String> name = new TableColumn<Book,String>("Book");
        name.setCellValueFactory(new PropertyValueFactory<Book,String>("name"));

        
        TableColumn<Book,Double> price = new TableColumn<Book,Double>("Price");
        price.setCellValueFactory(new PropertyValueFactory<Book,Double>("price"));

       
        TableColumn select = new TableColumn("Select");
        select.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book, CheckBox>, ObservableValue<CheckBox>>() { //The mechanism of calling a function from another function is called “callback

            @Override
            public ObservableValue<CheckBox> call(
                TableColumn.CellDataFeatures<Book, CheckBox> arg0) {//wrapper class to provide all necessary information for a particular Cell. Once instantiated, this class is immutable.
                Book cbook = arg0.getValue();                         //Instantiates a CellDataFeatures instance with the given properties set as read-only values of this instance.
                                                                      //makes properties read-only 
                                                                      //listener is a method which is called when the user does something (
                CheckBox checkBox = new CheckBox();

                checkBox.selectedProperty().setValue(cbook.IsSelected());
                checkBox.selectedProperty().addListener(
                (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                   System.out.println("Book with name: "+cbook.getName()+" has been toggled");
                   cbook.setIsSelected(checkBox.selectedProperty().getValue());
                   if(checkBox.selectedProperty().get()){
                       custCart.add(cbook);
                   } else {
                       custCart.remove(cbook);
                   }
                });
                
                return new SimpleObjectProperty<CheckBox>(checkBox);
            }

        });
        //select.setCellFactory(column -> new CheckBoxTableCell()); 

        
        custBooksTV.getColumns().addAll(name, price, select);//problem with line ; unchecked/unsafe i believe
        custBooksTV.prefHeightProperty().bind(bookstoreStage.heightProperty());
        custBooksTV.prefWidthProperty().bind(bookstoreStage.widthProperty());
        
        GridPane custSSGP = new GridPane();
        custSSGP.setAlignment(Pos.CENTER);
        custSSGP.setHgap(10);
        custSSGP.setVgap(40);
        custSSGP.setPadding(new Insets(25,25,25,25));
        custSSGP.add(welcomeMsgLb, 0, 0, 3, 1);
        custSSGP.add(custBooksTV, 0, 1, 3, 1);
        custSSGP.add(custBuyBtn, 0, 2);
        custSSGP.add(custRedeemBtn, 1, 2);
        custSSGP.add(custLogoutBtn, 2, 2);
     
        customerStartScene = new Scene(custSSGP, 700, 600);
        
        //----------------------------- customer cost screen -----------------------------//
        
        Label totalCostLb = new Label("Total Cost:"), pointsStatusLb = new Label("Points: P, Status: S");
        
        Button custCostLogoutBtn = new Button("Logout");
        
        GridPane custCostGP = new GridPane();
        custCostGP.setAlignment(Pos.CENTER);
        custCostGP.setHgap(10);
        custCostGP.setVgap(40);
        custCostGP.setPadding(new Insets(25,25,25,25));
        custCostGP.add(totalCostLb, 0, 0);
        custCostGP.add(pointsStatusLb, 0, 1);
        custCostGP.add(custCostLogoutBtn, 0, 2);
        
        customerCostScene = new Scene(custCostGP, 500, 400);
        
        // initialize window
        bookstoreStage.setTitle("Bookstore Application");
        bookstoreStage.setScene(loginScene);
        bookstoreStage.show();
        
        // on close, write to files
        bookstoreStage.setOnCloseRequest(e -> { //when closed overwrites the textfiles 
            writeBooks(booksOL);
            writeCustomers(custOL);
        });
        
        
        
        // when login button is pressed
        loginBtn.setOnAction(e -> {
            Boolean validCustCred = false;
            String welcomeMsg = "Welcome";
            for(Customer c : custOL){
                if(unTF.getText().equals(c.getUsername()) && pwPF.getText().equals(c.getPassword())){
                    validCustCred = true;
                    currentCustomer = c;
                    welcomeMsg = "Welcome "+currentCustomer.getUsername()+". You have "+currentCustomer.getPoints()+" points. Your status is "+currentCustomer.getStatus()+".";
                    
                }
            }
            
            if(unTF.getText().equals("admin") && pwPF.getText().equals("admin")){
                //System.out.println("admin login");
                bookstoreStage.setScene(ownerStartScene);
            } else if(validCustCred){
                
                welcomeMsgLb.setText(welcomeMsg);
                bookstoreStage.setScene(customerStartScene);
            }
            
            // clear fields
            unTF.clear();
            pwPF.clear();
        });
        
        // when logout button is clicked
        logoutBtn.setOnAction(e -> {
            bookstoreStage.setScene(loginScene);
        });
        custLogoutBtn.setOnAction(e -> {
            bookstoreStage.setScene(loginScene);
        });
        custCostLogoutBtn.setOnAction(e -> {
            bookstoreStage.setScene(loginScene);
        });
        
        // when ownerbooks button is pressed
        booksBtn.setOnAction(e -> {
            bookstoreStage.setScene(ownerBooksScene);
        });
        
        // when ownerCustomers button is clicked
        customersBtn.setOnAction(e -> {
            bookstoreStage.setScene(ownerCustomersScene);
        });
        
        // when ownerCustBack button is clicked
        ownerCustBackBtn.setOnAction(e -> {
            bookstoreStage.setScene(ownerStartScene);
        });
        
        // when customer buy button is pressed
        custBuyBtn.setOnAction(e -> {
            // handle cost
            double totalCost = 0;
            for(Book b : custCart){
                totalCost += b.getPrice();
            }
            
            // handle points
            double pointsGained = totalCost*10;
            currentCustomer.setPoints(currentCustomer.getPoints()+pointsGained);
            
            totalCostLb.setText("Total Cost: CAD "+totalCost);
            pointsStatusLb.setText("Points: "+currentCustomer.getPoints()+", Status: "+currentCustomer.getStatus());
            
            
            
            bookstoreStage.setScene(customerCostScene);
        });
        
        custRedeemBtn.setOnAction(e -> {
            // handle cost
            double totalCost = 0;
            for(Book b : custCart){
                totalCost += b.getPrice();
            }
            
            // handle points
            double pointsCADValue = currentCustomer.getPoints()/100;
            
            if(pointsCADValue > totalCost){
                currentCustomer.setPoints(currentCustomer.getPoints()-(totalCost*100));
                totalCost = 0;
            } else {
                totalCost -= pointsCADValue;
                currentCustomer.setPoints(totalCost*10);
            }
            
            
            
            totalCostLb.setText("Total Cost: CAD "+totalCost);
            pointsStatusLb.setText("Points: "+currentCustomer.getPoints()+", Status: "+currentCustomer.getStatus());
            
            
            
            bookstoreStage.setScene(customerCostScene);
        });
    }

    public ObservableList<Book> getBooks(){
        ObservableList<Book> books = FXCollections.observableArrayList();
        // Get data from books.txt 
        
        try {
            booksFile.createNewFile();
            Scanner s = new Scanner(booksFile);
        
            while(s.hasNextLine()){
                //String bookString = s.nextLine();
                String bookName = s.nextLine();
                Double bookPrice = Double.parseDouble(s.nextLine());
                System.out.println("Found book with name: "+bookName+", price: $"+bookPrice);
               
                books.add(new Book(bookName, bookPrice));
            }
                     
        } catch (IOException e){
            System.out.println("ERROR while trying to read data files");
            e.printStackTrace();
        }
        
        return books;
    }
    
    public void writeBooks(ObservableList<Book> books){       
        try {            
            PrintWriter pw = new PrintWriter(booksFile);//Prints formatted representations of objects to a text-output stream
            pw.close();
            
            FileWriter fw = new FileWriter(booksFile, true);
            BufferedWriter bw = new BufferedWriter(fw); //Writes text to a character-output stream;buffering character makes writing single chars/string more efficient
             
            for(Book b : books){
                //System.out.println(b.getName()+" "+b.getPrice());
                String bookString = b.getName()+" "+b.getPrice() + System.lineSeparator();
                fw.write(b.getName()+ System.lineSeparator());
                fw.write(Double.toString(b.getPrice())+ System.lineSeparator());
                
            }
            
            fw.close();
        } catch (IOException ex) {             
            System.out.println("ERROR while writing to books.txt");             
            ex.printStackTrace();         
        }     
        
    }
    
    public ObservableList<Customer> readCustomers(){
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        // Get data from books.txt 
        
        try {      
            customersFile.createNewFile();
            Scanner s = new Scanner(customersFile);
        
            while(s.hasNextLine()){

                String custUN = s.nextLine();
                String custPW = s.nextLine();
                Double custPoints = Double.parseDouble(s.nextLine());
               
                customers.add(new Customer(custUN, custPW, custPoints));
            }
                     
        } catch (IOException e){
            System.out.println("ERROR while trying to read customer.txt");
            e.printStackTrace();
        }
        
        return customers;
    }
    
    public void writeCustomers(ObservableList<Customer> customers){       
        try {            
            PrintWriter pw = new PrintWriter(customersFile);
            pw.close();
            
            FileWriter fw = new FileWriter(customersFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
             
            for(Customer c : customers){
                fw.write(c.getUsername() + System.lineSeparator()); //lineSeparator = creates empty new line
                fw.write(c.getPassword() + System.lineSeparator());
                fw.write(Double.toString(c.getPoints())+ System.lineSeparator());               
            }
            
            fw.close();
        } catch (IOException ex) {             
            System.out.println("ERROR while writing to customers.txt");             
            ex.printStackTrace();         
        }     
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
