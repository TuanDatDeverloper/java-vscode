import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SchedulerApp extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        MainLayout mainLayout = new MainLayout();
        
        Scene scene = new Scene(mainLayout, 1200, 800);
        
        primaryStage.setTitle("Personal Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}