
package BookStoreApp;


import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public abstract class ScreenState extends Application{ //extends means inheritance so screenstate is the child class of the parent, application 
    Scene scene;  //creates Scene of type Scene 

    public Scene getScene() {  //getter method called getScene of type Scene. returns scene. 
        return scene;
    }
}

class LoginScreen extends ScreenState { //extends means inheritance so loginscreen is the child class of the Screenstate the parent
    @Override
    public void start(Stage bookstoreStage) {     
        
    }
    
    public LoginScreen(){
        // start at login screen
        // create username and password fields
        Label unLb = new Label("Username:");
        TextField unTF = new TextField();
        
        Label pwLb = new Label("Password:");
        TextField pwTF = new TextField();

        Label welLb = new Label("Welcome to the Bookstore App");
       
        Button loginBtn = new Button();
        loginBtn.setText("login");
        
        
        
        GridPane loginGP = new GridPane(); //creates a new gridpane object called login gp 
        loginGP.setAlignment(Pos.CENTER); //default is top-left; this one sets to center
        loginGP.setHgap(10);   //set Hgap meaning set Gridpane's Horizontal gap to 10; this 10 is pixel width!
        loginGP.setVgap(10);// set Gridpane's vertical gap to 10 pixel width! 
        loginGP.setPadding(new Insets(25,25,25,25)); //  padding property manages the space around the edges of the grid pane. Inset order is: top, right, bottom, left
        
        //so  gap sets space between the rows and columns.
        // padding property manages the space around the edges of the grid pane. Insets are in the order of top, right, bottom, and left.
        
        
        
        
        
        
        loginGP.add(welLb, 0, 0, 2, 1); //login gp is a gridpane; adds the button welb and the numbers sets the edges?????????????????
        loginGP.setHalignment(welLb, HPos.CENTER);
        loginGP.add(unLb, 0, 1); //column to 0 and row to 2 ; adds button unLn to loginGP gridpane
        loginGP.add(unTF, 1, 1); //column then row  ; adds textfield  to loginGP gridpane
        loginGP.add(pwLb, 0, 2); //column then row   ; adds label to loginGP gridpane
        loginGP.add(pwTF, 1, 2);//column then row 
        loginGP.add(loginBtn, 0, 3, 2, 1); //column index, row index, column span, row span
        
        //column index is the column where child's layout area starts.
        //row index = row where child's layout area starts.
        //ColumnSpan =the number of columns the child's layout area spans horizontally.
        //rowSpan=the number of rows the child's layout area spans vertically.
        loginGP.setHalignment(loginBtn, HPos.CENTER);
        
        
        
        scene = new Scene(loginGP, 400, 300); 
    }
}
class OwnerStartScreen extends ScreenState {
    @Override
    public void start(Stage bookstoreStage) {     
        
    }
    
    public OwnerStartScreen(){
        Button booksBtn = new Button("Books");
        Button customersBtn = new Button("Customers");
        Button logoutBtn = new Button("Logout");
        
        GridPane ownerStartGP = new GridPane();
        ownerStartGP.setAlignment(Pos.CENTER);
        ownerStartGP.setHgap(10);
        ownerStartGP.setVgap(40);
        ownerStartGP.setPadding(new Insets(25,25,25,25)); // column index, row index, column span, row span
        ownerStartGP.add(booksBtn, 0, 0);
        ownerStartGP.setHalignment(booksBtn, HPos.CENTER);
        ownerStartGP.add(customersBtn, 0, 1);
        ownerStartGP.setHalignment(customersBtn, HPos.CENTER);
        ownerStartGP.add(logoutBtn, 0, 2);
        ownerStartGP.setHalignment(logoutBtn, HPos.CENTER);
            
        scene = new Scene(ownerStartGP, 400, 300);
    }
    
}
