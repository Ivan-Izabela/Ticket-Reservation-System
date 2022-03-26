package gui;

import domain.Cursa;
import domain.Rezervare;
import domain.RezervareDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import services.FTException;
import services.IService;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

public class CurseController {
    private IService service;
    private MainPaneController mainPaneController;
    private ObservableList<Cursa> modelCursa= FXCollections.observableArrayList();
    private ObservableList<RezervareDTO> modelRezervareDTO= FXCollections.observableArrayList();

    public void setService(IService service) {
        this.service = service;
        //initModelCurse();
    }

    public void setMainPaneController(MainPaneController mainPaneController) {
        this.mainPaneController = mainPaneController;
    }

    public void setAllCurse(){
        initModelCurse();
    }

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
        }catch (FTException e){
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
        try{
        if(cursa!=null){
            List<RezervareDTO> rezervareDTOS=service.findRezervareDTO(cursa.getId().toString());
            modelRezervareDTO.setAll(rezervareDTOS);
        }
        }catch (FTException e){

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
                modelCursa.setAll(service.findAllCurse());

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
        //mainPaneController.displayLogin();
    }


}
