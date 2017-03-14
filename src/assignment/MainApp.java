package assignment;

import assignment.database.DatabaseSetup;
import assignment.view.LoginScreenController;
import java.io.IOException;
import java.util.prefs.Preferences;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainApp extends Application {

    private Stage primaryStage;
    private AnchorPane loginScreen;

    public MainApp() {
    }

    public void buildData() {
        try {
            System.out.println("buildData() run");
            DatabaseSetup.setupDatabase();
        } catch (Exception e) {
            System.out.println("buildData() ERROR!");
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Management Application");

        buildData();
        showLoginScreen();
    }

    private static final String WINDOW_POSITION_X = "Window_Position_X";
    private static final String WINDOW_POSITION_Y = "Window_Position_Y";
    private static final String WINDOW_WIDTH = "Window_Width";
    private static final String WINDOW_HEIGHT = "Window_Height";
    private static final double DEFAULT_X = 10;
    private static final double DEFAULT_Y = 10;
    private static final double DEFAULT_WIDTH = 800;
    private static final double DEFAULT_HEIGHT = 600;
    private static final String NODE_NAME = "ViewSwitcher";

    public void showLoginScreen() {
        try {   
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/LoginScreen.fxml"));
            loginScreen = (AnchorPane) loader.load();

            LoginScreenController controller = loader.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(loginScreen);
            
            /*
            // Maximize the window             
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            primaryStage.setX(bounds.getMinX());
            primaryStage.setY(bounds.getMinY());
            primaryStage.setWidth(bounds.getWidth());
            primaryStage.setHeight(bounds.getHeight());*/

            //primaryStage.getIcons().add(new Image("file:resources/images/hotel_icon.png"));
            primaryStage.setScene(scene);
            primaryStage.show();

            // Pull the saved preferences and set the stage size and start location
            try {
                Preferences pref = Preferences.userRoot().node(NODE_NAME);
                double x = pref.getDouble(WINDOW_POSITION_X, DEFAULT_X);
                double y = pref.getDouble(WINDOW_POSITION_Y, DEFAULT_Y);
                double width = pref.getDouble(WINDOW_WIDTH, DEFAULT_WIDTH);
                double height = pref.getDouble(WINDOW_HEIGHT, DEFAULT_HEIGHT);
                primaryStage.setX(x);
                primaryStage.setY(y);
                primaryStage.setWidth(width);
                primaryStage.setHeight(height);
            } catch (Exception e) { //When there hasn't been a preference saved yet
                //Do nothing
            }

            // When the stage closes store the current size and window location.
            primaryStage.setOnCloseRequest((final WindowEvent event) -> {
                Preferences preferences = Preferences.userRoot().node(NODE_NAME);
                preferences.putDouble(WINDOW_POSITION_X, primaryStage.getX());
                preferences.putDouble(WINDOW_POSITION_Y, primaryStage.getY());
                preferences.putDouble(WINDOW_WIDTH, primaryStage.getWidth());
                preferences.putDouble(WINDOW_HEIGHT, primaryStage.getHeight());
            });
        } catch (IOException e) {
            System.out.println("showLoginScreen() Error!");
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
