package dk.aau.cs.ds306e18.tournament.ui.controllers;

import dk.aau.cs.ds306e18.tournament.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ParticipantSettingsTabController {

    @FXML private BorderPane participantSettingsTab;
    @FXML private TextField teamNameTextField;
    @FXML private TextField botNameTextField;
    @FXML private TextField developerTextField;
    @FXML private TextArea botDescription;
    @FXML private Button configPathBtn;
    @FXML private Button addTeamBtn;
    @FXML private Button addBotBtn;
    @FXML private Button removeTeamBtn;
    @FXML private Button removeBotBtn;
    @FXML private ListView<Bot> botsListView;
    @FXML private ListView<Team> teamsListView;
    @FXML private VBox teamSettingsVbox;
    @FXML private VBox botSettingsVbox;

    @FXML private void initialize() {
    }

    @FXML void nameTextFieldOnKeyReleased(KeyEvent event) {
    }

    @FXML void addBotBtnOnAction(ActionEvent actionEvent){
        System.out.println("Test Add Bot");

    }

    @FXML void removeBotBtnOnAction(ActionEvent actionEvent){
        int selectedIndex = botsListView.getSelectionModel().getSelectedIndex();
        System.out.println("Test Remove Bot");

        if (selectedIndex != -1){
            //Remove bot from list of bots
            //refresh listview
        }
    }
    @FXML void addTeamBtnOnAction(ActionEvent actionEvent){
        System.out.println("Test Add Team");

    }

    @FXML void removeTeamBtnOnAction(ActionEvent actionEvent){
        int selectedIndex = teamsListView.getSelectionModel().getSelectedIndex();
        System.out.println("Test Remove team");
        if (selectedIndex != -1){
            //Remove bot from list of bots
            //refresh listview
        }
    }

    @FXML void configPathBtnOnAction(ActionEvent actionEvent){
                }
}
