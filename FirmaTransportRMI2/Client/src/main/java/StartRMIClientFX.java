import gui.LoginController;
import gui.MainPaneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.IService;

public class StartRMIClientFX extends Application {



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        try{
            ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
            IService service=(IService) factory.getBean("ftService");
            System.out.println("Obtained a reference to remote chat server");

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/loginView.fxml"));
            Parent root = loader.load();


            LoginController ctrl =loader.<LoginController>getController();
            ctrl.setService(service);

            System.out.println("..........");

            FXMLLoader cloader = new FXMLLoader(getClass().getClassLoader().getResource("views/mainPaneView.fxml"));
            Parent croot = cloader.load();


            System.out.println("-----------");
            MainPaneController chatCtrl = cloader.getController();
            //chatCtrl=new MainPaneController(service);
            chatCtrl.setServiceMain(service);

            System.out.println("clientFXRPC");

            ctrl.setMainPaneController(chatCtrl);
            ctrl.setParent(croot);
            System.out.println("___________");

            primaryStage.setTitle("Firma Transport");
            primaryStage.setScene(new Scene(root, 374, 439));
            primaryStage.show();
        }catch (Exception e) {
            System.err.println("Chat Initialization  exception:"+e);
            e.printStackTrace();

        }



    }
}
