package gui;

import domain.Cursa;
import domain.Rezervare;
import domain.RezervareDTO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import services.FTException;
import services.IObserver;
import services.IService;

import java.io.Serializable;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainPaneController extends UnicastRemoteObject implements IObserver, Serializable {

    IService service;
    private ObservableList<Cursa> modelCursa= FXCollections.observableArrayList();
    private ObservableList<RezervareDTO> modelRezervareDTO= FXCollections.observableArrayList();
    String nume;

    public MainPaneController() throws RemoteException {

    }

    public void setServiceMain(IService service) {
        this.service = service;
        //initModelCurse();
    }
    public void setNume(String email) {
        this.nume = email;
    }
    public void setAllCurse() {
        System.out.println("all fligths main");

//
        initModelCurse();
    }



    Stage primaryStage;

    @FXML
    TableView<Cursa> curseTable;
    @FXML
    TableColumn<Cursa, String> destinatieTableColumn;
    @FXML
    TableColumn<Cursa, Date> dataTableColumn;
    @FXML
    TableColumn<Cursa, Time> oraTableColumn;
    @FXML
    TableColumn<Cursa, Integer> locuriTableColumn;


    @FXML
    TableView<RezervareDTO> rezervareDtoTable;
    @FXML
    TableColumn<RezervareDTO, String> clientTableColumn;
    @FXML
    TableColumn<RezervareDTO, Integer> locuriDtoTableColumn;

    @FXML
    TextField destCautareTableField;

    @FXML
    TextField numeRezervareTextField;
    @FXML
    TextField locuriRezervareTextField;


    @FXML
    public void initialize(){
        destinatieTableColumn.setCellValueFactory(new PropertyValueFactory<Cursa, String>("destinatie"));
        dataTableColumn.setCellValueFactory(new PropertyValueFactory<Cursa, Date>("plecare"));
        locuriTableColumn.setCellValueFactory(new PropertyValueFactory<Cursa, Integer>("locuriDisponibile"));
        curseTable.setItems(modelCursa);

        clientTableColumn.setCellValueFactory(new PropertyValueFactory<RezervareDTO, String>("numeClient"));
        locuriDtoTableColumn.setCellValueFactory(new PropertyValueFactory<RezervareDTO, Integer>("nrLoc"));
        rezervareDtoTable.setItems(modelRezervareDTO);
    }

    private void initModelCurse(){
        try {
            List<Cursa> curse = service.findAllCurse();
            modelCursa.setAll(curse);
        }catch (FTException  e){
            Alert message = new Alert(Alert.AlertType.ERROR);
            message.setHeaderText("Init invalid");
            message.setContentText(e.getMessage());
            message.showAndWait();

        }
    }

    public void handleFindBYDestinatie(KeyEvent keyEvent) {
        String destinatie=destCautareTableField.getText();
        try {
            List<Cursa> curse = service.findAllCurse();
            modelCursa.setAll(curse.stream()
                    .filter(c -> c.getDestinatie().startsWith(destinatie))
                    .collect(Collectors.toList()));
        }catch (FTException e){

        }
    }

    public void handleDetaliiCursa(ActionEvent actionEvent) {
        Cursa cursa=curseTable.getSelectionModel().getSelectedItem();
        try {
            if (cursa != null) {
                List<RezervareDTO> rezervareDTOS = service.findRezervareDTO(cursa.getId().toString());
                modelRezervareDTO.setAll(rezervareDTOS);
            }
        }catch (FTException e){
            Alert message = new Alert(Alert.AlertType.ERROR);
            message.setHeaderText("Rezervare invalida");
            message.setContentText(e.getMessage());
            message.showAndWait();
        }
    }


    public void handleRezervare(ActionEvent actionEvent) {
        Cursa cursa=curseTable.getSelectionModel().getSelectedItem();
        String nume= numeRezervareTextField.getText();
        Integer locuri=null;
        if(cursa!=null && cursa.getLocuriDisponibile()>0) {
            try {
                locuri = Integer.parseInt(locuriRezervareTextField.getText());
                Rezervare rezervare= new Rezervare(nume,locuri,cursa.getId());
                service.saveRezervare(rezervare);
                numeRezervareTextField.setText("");
                locuriRezervareTextField.setText("");
                //modelCursa.setAll(service.findAllCurse());

            } catch (NumberFormatException ex) {
                Alert message = new Alert(Alert.AlertType.ERROR);
                message.setHeaderText("Date invalide");
                message.setContentText("Numarul de locuri trebuie sa fie un nr natural");
                message.showAndWait();
            }catch (FTException ex){
                Alert message = new Alert(Alert.AlertType.ERROR);
                message.setHeaderText("Rezervare invalida");
                message.setContentText(ex.getMessage());
                message.showAndWait();

            }
        }
        else{
            Alert message = new Alert(Alert.AlertType.ERROR);
            message.setHeaderText("Rezervare invalida");
            message.setContentText("Selectati o cursa");
            message.showAndWait();
        }


    }

    public void handleLogout(ActionEvent actionEvent) {
        logout();
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }

    public void logout() {
        try {
            service.logout(nume, this);
        } catch (FTException e) {
            System.out.println("Logout error " + e);
        }
    }




    @Override
    public void saveDRezervare(Rezervare rezervare) throws FTException {

        Platform.runLater(()->{
            List<Cursa> list= List.copyOf(modelCursa);
            List<RezervareDTO> listf= List.copyOf(modelRezervareDTO);

            curseTable.getItems().clear();
            rezervareDtoTable.getItems().clear();

            System.out.println("list.size: "+list.size());
            System.out.println("cursa_id: "+rezervare.getIdCursa());
            list.forEach(x -> {
                if (x.getId() != 0) {
                    System.out.println(x.getId()+":"+ rezervare.getIdCursa());
                    if (x.getId().equals(rezervare.getIdCursa()))
                    {
                        x.setLocuriDisponibile(x.getLocuriDisponibile() - rezervare.getNrLocuri());
                        System.out.println("Update nr seats: "+x.getLocuriDisponibile());
                    }
                    curseTable.getItems().add(x);

                }
            });

        });

    }
}
