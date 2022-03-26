package gui;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import services.FTException;
import services.IService;

public class LoginController {
    private IService service;
    private MainPaneController mainPaneController;

    Parent mainParent;

    public void setParent(Parent p){
        mainParent=p;
    }


    @FXML
    TextField numeTextField;
    @FXML
    PasswordField parolaField;

    public void setService(IService service) {
        this.service = service;
    }

    public void setMainPaneController(MainPaneController mainPaneController) {
        this.mainPaneController = mainPaneController;
    }


    @FXML
    public void handleLoginButton(ActionEvent actionEvent) {
        String nume=numeTextField.getText();
        String parola=parolaField.getText();
        parolaField.setText("");
        try{
            service.login(nume,parola,mainPaneController);

            System.out.println("login ....");
            Stage stage=new Stage();
            stage.setScene(new Scene(mainParent));
            stage.setTitle(nume);

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    mainPaneController.logout();
                    System.exit(0);
                }
            });

            stage.show();
            mainPaneController.setNume(nume);
            mainPaneController.setAllCurse();


            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();


        }
        catch (FTException ex){
            Alert message= new Alert(Alert.AlertType.ERROR);
            message.setHeaderText("Login failed");
            message.setContentText("Wrong name or password.");
            message.showAndWait();

        }
    }
}
