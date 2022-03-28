package scs.lab;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewFXMLController implements Initializable {

    @FXML private Label cpuUsageLabel;
    @FXML private TextField threadNoPrimeFinder;
    @FXML private TextField threadNoPiCalc;
    @FXML private TextField threadNoMergeSort;
    @FXML private Label timeTakenPrimeFinderLabel;
    @FXML private Label timeTakenPiCalcLabel;
    @FXML private Label timeTakenMergeSortLabel;
    @FXML private Label primeTestStatus;
    @FXML private Label piCalcTestStatus;
    @FXML private Label mergeSortTestStatus;

    public void updateCpuStatsLabel(String cpuUsage) {
        this.cpuUsageLabel.setText(cpuUsage);
    }

    public void startBenchmarks(ActionEvent event) {
        initStates();
        primeTestStatus.setText("Running...");
        primeTestStatus.setTextFill(Color.rgb(50, 200, 50));
        String noOfThreadsPrimeFinderStr = threadNoPrimeFinder.getText();
        String noOfThreadsPiCalcStr = threadNoPiCalc.getText();
        String noOfThreadsMergeSortStr = threadNoMergeSort.getText();

        try {
            int noOfThreadsPrimeFinder = Integer.parseInt(noOfThreadsPrimeFinderStr);
            int noOfThreadsPiCalc = Integer.parseInt(noOfThreadsPiCalcStr);
            int noOfThreadsMergeSort = Integer.parseInt(noOfThreadsMergeSortStr);
            StartTests startTests = new StartTests(noOfThreadsPrimeFinder, noOfThreadsPiCalc, noOfThreadsMergeSort,
                    this);
            Thread startTestsThread = new Thread(startTests);
            startTestsThread.start();

        } catch (Exception ex) {
            System.out.println("Execution failed!");
            ex.printStackTrace();
        }

    }

    public void updateTimeTakenPrimeFinderLabel(String timeTakenPrimeFinder) {
        timeTakenPrimeFinderLabel.setText(timeTakenPrimeFinder);
        primeTestStatus.setText("Done.");
        primeTestStatus.setTextFill(Color.rgb(200, 120, 0));
        piCalcTestStatus.setText("Running...");
        piCalcTestStatus.setTextFill(Color.rgb(50, 200, 50));
    }

    public void updateTimeTakenPiCalcLabel(String timeTakenPiCalc) {
        timeTakenPiCalcLabel.setText(timeTakenPiCalc);
        piCalcTestStatus.setText("Done.");
        piCalcTestStatus.setTextFill(Color.rgb(200, 120, 0));
        mergeSortTestStatus.setText("Running...");
        mergeSortTestStatus.setTextFill(Color.rgb(50, 200, 50));
    }

    public void updateTimeTakenMergeSort(String timeTakenMergeSort) {
        timeTakenMergeSortLabel.setText(timeTakenMergeSort);
        mergeSortTestStatus.setText("Done.");
        mergeSortTestStatus.setTextFill(Color.rgb(200, 120, 0));
    }

    private void initStates() {
        timeTakenPiCalcLabel.setText("");
        timeTakenPrimeFinderLabel.setText("");
        timeTakenMergeSortLabel.setText("");
        primeTestStatus.setText("Not running.");
        primeTestStatus.setTextFill(Color.rgb(50, 50, 50));
        piCalcTestStatus.setText("Not running.");
        piCalcTestStatus.setTextFill(Color.rgb(50, 50, 50));
        mergeSortTestStatus.setText("Not running.");
        mergeSortTestStatus.setTextFill(Color.rgb(50, 50, 50));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       initStates();
    }


}
