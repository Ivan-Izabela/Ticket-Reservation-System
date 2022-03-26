import gui.LoginController;
import gui.MainPaneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rpcProtocol.ServicesRpcProxy;
import services.IService;

import java.io.IOException;
import java.util.Properties;

public class StartRPCClientFX extends Application {

    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("In start");
        Properties clientProps = new Properties();
        try {
            clientProps.load(StartRPCClientFX.class.getResourceAsStream("views/client.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find client.properties " + e);
            return;
        }
        String serverIP = clientProps.getProperty("server.host", defaultServer);
        int serverPort = defaultChatPort;

        try {
            serverPort = Integer.parseInt(clientProps.getProperty("server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultChatPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        IService service=new ServicesRpcProxy(serverIP,serverPort);
        System.out.println("Am setat service");

/*
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("views/mainPaneView.fxml"));
        BorderPane mainPaneView = loader.load();
        MainPaneController mainPaneController = loader.getController();
        Scene mainScene = new Scene(mainPaneView);
        primaryStage.setScene(mainScene);
        mainPaneController.setPrimaryStage(primaryStage);

*/
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/loginView.fxml"));
        Parent root = loader.load();


        LoginController ctrl =loader.<LoginController>getController();
        ctrl.setService(service);

        System.out.println("..........");

        FXMLLoader cloader = new FXMLLoader(getClass().getClassLoader().getResource("views/mainPaneView.fxml"));
        Parent croot = cloader.load();


        System.out.println("-----------");
        MainPaneController chatCtrl =cloader.<MainPaneController>getController();
        chatCtrl.setServiceMain(service);

        System.out.println("clientFXRPC");

        ctrl.setMainPaneController(chatCtrl);
        ctrl.setParent(croot);
        System.out.println("___________");

        primaryStage.setTitle("Firma Transport");
        primaryStage.setScene(new Scene(root, 374, 439));
        primaryStage.show();

/*
        FXMLLoader loginLoader = new FXMLLoader();
        System.out.println("1");
        loginLoader.setLocation(getClass().getResource("views/loginView.fxml"));
        System.out.println("2");
        AnchorPane loginLayout = loginLoader.load();
        System.out.println("3");
        LoginController loginController = loginLoader.getController();
        System.out.println("4");
        loginController.setService(service);
        System.out.println("5");
        loginController.setMainPaneController(mainPaneController);
        System.out.println("6");
        mainPaneController.setLoginLayout(loginLayout);
        System.out.println("7");
        mainPaneController.displayLogin();

        System.out.println("8");


        FXMLLoader curseLoader= new FXMLLoader();
        System.out.println("9");
        curseLoader.setLocation(getClass().getResource("views/curseView.fxml"));
        System.out.println("10");
        AnchorPane cureseLayout= curseLoader.load();
        System.out.println("11");
        CurseController curseController=curseLoader.getController();
        System.out.println("12");
        curseController.setService(service);
        System.out.println("13");
        curseController.setMainPaneController(mainPaneController);
        System.out.println("14");
        mainPaneController.setCurseLayout(cureseLayout);
        System.out.println("15");

        primaryStage.show();
        System.out.println("16");
*/


    }
}
