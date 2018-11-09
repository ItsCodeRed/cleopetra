package dk.aau.cs.ds306e18.tournament.ui.controllers;

import dk.aau.cs.ds306e18.tournament.model.*;
import dk.aau.cs.ds306e18.tournament.model.format.SwissFormat;
import dk.aau.cs.ds306e18.tournament.model.tiebreaker.TieBreaker;
import dk.aau.cs.ds306e18.tournament.model.tiebreaker.TieBreakerBySeed;
import dk.aau.cs.ds306e18.tournament.ui.StageFormatOption;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Collections;

public class TournamentSettingsTabController {

    @FXML
    private GridPane tournamentSettingsTab;
    @FXML
    private TextField nameTextField;
    @FXML
    private ChoiceBox<TieBreaker> tieBreakerChoiceBox;
    @FXML
    private ListView<Stage> stagesListView;
    @FXML
    private Button addStageBtn;
    @FXML
    private Button removeStageBtn;
    @FXML
    private VBox stageSettingsVBox;
    @FXML
    private Text selectStageText;
    @FXML
    private Text stageSettingsHeadLabel;
    @FXML
    private HBox stageSettingsContent;
    @FXML
    private TextField stageNameTextfield;
    @FXML
    private ChoiceBox<StageFormatOption> formatChoicebox;
    @FXML
    private TextField roundsTextfield;
    @FXML
    private GridPane stageOptions;
    @FXML
    private Button swapUp;
    @FXML
    private Button swapDown;


    @FXML
    private void initialize() {
        /* Retrieve and set tournament name into textfield. */
        nameTextField.setText(Tournament.get().getName());

        /* Retrieve and add choices to choicebox for the Tiebreaker box.
         * Also upon change sets the new tiebreaker rule to the tournament model. */
        tieBreakerChoiceBox.setItems(FXCollections.observableArrayList(new TieBreakerBySeed()));
        tieBreakerChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> Tournament.get().setTieBreaker(newValue));
        tieBreakerChoiceBox.getSelectionModel().select(0);

        /* By default the stage settings are hidden.
         * This listener is used to show the stage settings when there is at least one Stage added.
         * Also handles disabling and enabling of buttons for stages.
         * */
        stagesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            stageSettingsVBox.setVisible(stagesListView.getItems().size() != 0);

            /* Handle stage order button disabling / enabling */
            int selectedIndex = getSelectedIndex();
            swapUp.setDisable(selectedIndex == 0);
            swapDown.setDisable(selectedIndex == stagesListView.getItems().size() - 1 && selectedIndex != -1);

            /* Set content inside stage settings to show chosen stage */
            showStageValues();
        });

        /* Retrieve possible formats and add to a choicebox */
        formatChoicebox.setItems(FXCollections.observableArrayList(StageFormatOption.values()));

        /* Listener for the format choicebox. Used to change a Stage format when a different format is chosen */
        formatChoicebox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Stage selectedStage = getSelectedStage();
            if (selectedStage != null && StageFormatOption.getOption(selectedStage.getFormat()) != newValue) {
                selectedStage.setFormat(newValue.getNewInstance());
            }

            /* Rounds are only visible if a specific format is chosen. TODO: Load options from format instead of type checking. */
            boolean showRounds = selectedStage != null && StageFormatOption.getOption(selectedStage.getFormat()) != StageFormatOption.SWISS_SYSTEM;
            stageOptions.setVisible(showRounds);
        });
    }

    @FXML
    void nameTextFieldOnKeyReleased(KeyEvent event) {
        Tournament.get().setName(nameTextField.getText());
    }

    @FXML
    void stageNameTextFieldOnKeyReleased(KeyEvent event) {
        getSelectedStage().setName(stageNameTextfield.getText());
        stagesListView.refresh();
    }

    /**
     * Adds a stage to the stages list and also to the tournament model.
     */
    @FXML
    void addStageBtnOnAction(ActionEvent actionEvent) {
        // increments unique id
        Tournament.get().addStage(new Stage("New Stage", new SwissFormat(), Tournament.get().getStages().size()+1));

        stagesListView.setItems(FXCollections.observableArrayList(Tournament.get().getStages()));
        stagesListView.refresh();
        stagesListView.getSelectionModel().selectLast();
    }

    /**
     * Removes a stage from the Stages list and also from the tournament model.
     */
    @FXML
    void removeStageBtnOnAction() {
        if (getSelectedIndex() != -1) {
            Tournament.get().removeStage(getSelectedIndex());
            stagesListView.getItems().remove(getSelectedIndex());
        }
    }

    /**
     * Show the correct values in the stage settings panel.
     */
    private void showStageValues() {
        if (getSelectedStage() != null) {
            stageNameTextfield.setText(getSelectedStage().getName());
            formatChoicebox.getSelectionModel().select(StageFormatOption.getOption(getSelectedStage().getFormat()));
        }
    }

    /**
     * Swaps a stage upwards in the list of stages. Used to allow ordering of stages.
     * This also swaps the stages in the tournament model.
     */
    @FXML
    private void swapStageUpwards() {
        if (getSelectedIndex() != 0 && getSelectedIndex() != -1) {
            Collections.swap(stagesListView.getItems(), getSelectedIndex(), getSelectedIndex() - 1);
            Tournament.get().swapStages(stagesListView.getItems().get(getSelectedIndex()), stagesListView.getItems().get(getSelectedIndex() - 1));

            stagesListView.getSelectionModel().select(getSelectedIndex() - 1);
        }
    }

    /**
     * Swaps a stage downwards in the list of stages. Used to allow ordering of stages.
     * This also swaps the stages in the tournament model.
     */
    @FXML
    private void swapStageDownwards() {
        int listSize = stagesListView.getItems().size();

        if (getSelectedIndex() != listSize - 1 && getSelectedIndex() != -1) {
            Collections.swap(stagesListView.getItems(), getSelectedIndex(), getSelectedIndex() + 1);
            Tournament.get().swapStages(stagesListView.getItems().get(getSelectedIndex()), stagesListView.getItems().get(getSelectedIndex() + 1));
            stagesListView.getSelectionModel().select(getSelectedIndex() + 1);
        }
    }

    private Stage getSelectedStage() {
        return stagesListView.getSelectionModel().getSelectedItem();
    }

    private int getSelectedIndex() {
        return stagesListView.getSelectionModel().getSelectedIndex();
    }

}
