package dk.aau.cs.ds306e18.tournament.ui.bracketObjects;

import dk.aau.cs.ds306e18.tournament.model.format.*;
import dk.aau.cs.ds306e18.tournament.model.match.Series;
import dk.aau.cs.ds306e18.tournament.ui.BracketOverviewTabController;
import dk.aau.cs.ds306e18.tournament.ui.SeriesVisualController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

import java.util.ArrayList;

import static dk.aau.cs.ds306e18.tournament.utility.PowMath.pow2;

public class DoubleEliminationNode extends VBox implements ModelCoupledUI, StageStatusChangeListener {

    private final Insets MARGINS = new Insets(0, 0, 16, 0);
    private final int SPACE_BETWEEN_BRACKETS_AND_LINE = 32;
    private final int CELL_HEIGHT = 48;

    private final DoubleEliminationFormat doubleElimination;
    private final BracketOverviewTabController boc;
    private final GridPane upperGrid = new GridPane();
    private final GridPane lowerGrid = new GridPane();
    private SeriesVisualController extraMatchMVC;

    private ArrayList<SeriesVisualController> mvcs = new ArrayList<>();

    /** Used to display the a double elimination stage. */
    public DoubleEliminationNode(DoubleEliminationFormat doubleElimination, BracketOverviewTabController boc){
        this.doubleElimination = doubleElimination;
        this.boc = boc;

        doubleElimination.registerStatusChangedListener(this);

        getChildren().add(upperGrid);
        getChildren().add(getLine());
        getChildren().add(lowerGrid);
        setSpacing(SPACE_BETWEEN_BRACKETS_AND_LINE);

        update();
    }

    /** Updates all ui elements for the double elimination stage. */
    private void update() {
        removeElements();

        // Upper bracket
        Series[] upperBracket = doubleElimination.getUpperBracket();
        int rounds = doubleElimination.getUpperBracketRounds();
        int m = 0; // match index
        for (int r = 0; r < rounds; r++) {
            int matchesInRound = pow2(r);
            int column = rounds - 1 - r;
            int rowSpan = pow2(column);

            // Add matches for round r
            for (int i = 0; i < matchesInRound; i++) {
                Series series = upperBracket[m++];
                VBox box = createMatchBox(series, rowSpan, false);
                upperGrid.add(box, column, (matchesInRound - 1 - i) * rowSpan);
            }
        }

        // Final match is added to upper bracket
        VBox finalsBox = createMatchBox(doubleElimination.getFinalSeries(), pow2(rounds - 1), false);
        upperGrid.add(finalsBox, rounds, 0);

        // Extra match is only shown when needed
        VBox extraBox = createMatchBox(doubleElimination.getExtraSeries(), pow2(rounds - 1), true);
        upperGrid.add(extraBox, rounds + 1, 0);
        updateDisplayOfExtraMatch();

        // Lower bracket
        Series[] lowerBracket = doubleElimination.getLowerBracket();
        if (lowerBracket.length > 0) {
            int matchesInCurrentRound = pow2(rounds - 2);
            int column = 0;
            int rowSpan = 1;
            m = 0; // match index
            while (true) {

                for (int i = 0; i < matchesInCurrentRound; i++) {
                    Series series = lowerBracket[m++];
                    VBox box = createMatchBox(series, rowSpan, false);
                    lowerGrid.add(box, column, i * rowSpan);
                }

                // Half the number of matches in a round every other round
                if (column % 2 == 1) {
                    if (matchesInCurrentRound == 1) {
                        break; // We can't do more halving
                    }
                    matchesInCurrentRound /= 2;
                    rowSpan *= 2;
                }

                column++;
            }
        }
    }

    private VBox createMatchBox(Series series, int rowSpan, boolean isExtra) {

        VBox box = new VBox();

        // Some matches are null because of byes. In those cases the VBox will just be empty
        if (series != null) {
            SeriesVisualController mvc = boc.loadSeriesVisual(series);
            mvcs.add(mvc);
            box.getChildren().add(mvc.getRoot());
            mvc.setShowIdentifier(true);

            if (isExtra) {
                extraMatchMVC = mvc;
            }
        }

        box.setAlignment(Pos.CENTER);
        box.setMinHeight(CELL_HEIGHT * rowSpan);

        GridPane.setRowSpan(box, rowSpan);
        GridPane.setMargin(box, MARGINS);
        GridPane.setValignment(box, VPos.CENTER);

        return box;
    }

    /** Creates the line between upper and lower bracket */
    private Node getLine() {
        int width = 180 * (doubleElimination.getUpperBracketRounds() + 2);
        Line line = new Line(0, 0, width, 0);
        line.setStroke(Paint.valueOf("#c1c1c1"));

        VBox box = new VBox();
        box.getChildren().add(line);
        VBox.setMargin(line, MARGINS);

        return box;
    }

    @Override
    public void decoupleFromModel() {
        removeElements();
        doubleElimination.unregisterStatusChangedListener(this);
    }

    /** Completely remove all ui elements. */
    public void removeElements() {
        upperGrid.getChildren().clear();
        lowerGrid.getChildren().clear();
        for (SeriesVisualController mvc : mvcs) {
            mvc.decoupleFromModel();
        }
        mvcs.clear();
    }

    private void updateDisplayOfExtraMatch() {
        extraMatchMVC.setDisabled(!doubleElimination.isExtraMatchNeeded());
    }

    @Override
    public void onStageStatusChanged(Format format, StageStatus oldStatus, StageStatus newStatus) {
        updateDisplayOfExtraMatch();
    }
}
