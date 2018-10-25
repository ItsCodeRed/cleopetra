package dk.aau.cs.ds306e18.tournament.UI;

import dk.aau.cs.ds306e18.tournament.UI.Tabs.BracketOverview;
import dk.aau.cs.ds306e18.tournament.UI.Tabs.ParticipantSettings;
import dk.aau.cs.ds306e18.tournament.UI.Tabs.TournamentSettings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class NavigationFrame  {

    private TabPane navigationTabs;
    private VBox mainContect;

    NavigationFrame() throws Exception {

        navigationTabs = new TabPane();
        navigationTabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        addTabsToPane(); //Calls the addContent and fills in tabs.

        mainContect = new VBox();
        HBox bottomNav = bottomNav();

        mainContect.getChildren().addAll(navigationTabs, bottomNav);


    }

    public void  setStage(Stage primaryStage){

        primaryStage.setScene(new Scene(mainContect));

    }

    /** Calls Creates new tabs and adds them to the pane. */
    public void addTabsToPane(){
        this.navigationTabs.getTabs().addAll(new TournamentSettings(), new ParticipantSettings(), new BracketOverview());
    }

    private HBox bottomNav(){
        HBox bottomNavigation = new HBox();

        Button prevBtn = new Button("< Prev");
        Button nextBtn = new Button("Next >");

        bottomNavigation.setAlignment(Pos.BASELINE_RIGHT);
        bottomNavigation.setSpacing(15);
        bottomNavigation.setPadding(new Insets(5));
        bottomNavigation.setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
                BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE,
                null, null, null)));

        bottomNavigation.getChildren().addAll(prevBtn, nextBtn);

        return bottomNavigation;
    }

    /** This is where the content of the window goes.
     * Create tabs with the desired content and return them in an arrayList
     * @return an arrayList of tabs with content for the window. */
    // ArrayList<Tab> addContent();

}
