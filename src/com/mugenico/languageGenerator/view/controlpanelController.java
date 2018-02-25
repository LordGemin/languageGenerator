package com.mugenico.languageGenerator.view;

import com.mugenico.languageGenerator.MainApp;
import com.mugenico.languageGenerator.generators.languages.LanguageSet;
import com.mugenico.languageGenerator.generators.languages.Sentences;
import com.mugenico.languageGenerator.generators.languages.Words;
import com.mugenico.languageGenerator.util.RNG;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Arrays;

/**
 *
 * Created by Gemin on 25.02.2018.
 */
public class controlpanelController {

    private RNG rng;
    private LanguageSet ls;
    private Words words;
    private Sentences sentences;
    private long seed;
    private Stage dialogStage;

    @FXML
    private TextField seed_in;
    @FXML
    private Text tf_construct;
    @FXML
    private Text tf_grammar;
    @FXML
    private Text tf_avg_length;
    @FXML
    private Text tf_language_name;
    @FXML
    private Text tf_consonants;
    @FXML
    private Text tf_vowels;
    @FXML
    private Text tf_sibilants;
    @FXML
    private Text tf_liquids;
    @FXML
    private Text tf_finals;
    @FXML
    private TextArea output;
    @FXML
    private Button bt_random_seed;
    @FXML
    private Button bt_generate;
    @FXML
    private Button bt_more_text;
    private MainApp mainApp;

    @FXML
    public void initialize() {
        seed_in.setText("");
        output.setWrapText(true);
        output.setEditable(false);
        bt_more_text.setVisible(false);
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public long getSeed() {
        return seed;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    public void handleRandomSeed() {
        seed = new RNG().nextLong();
        seed_in.setText(""+seed);
        bt_more_text.setVisible(false);
        output.setText("");
        wipeTexts();
    }

    private void wipeTexts() {
        tf_language_name.setText("Language name");
        tf_construct.setText("Morpheme construction rule");
        tf_grammar.setText("Sentence grammar");
        tf_avg_length.setText("Average morpheme length");
        tf_consonants.setText("List of consonants used");
        tf_vowels.setText("List of vowels used");
        tf_sibilants.setText("List of sibilants used");
        tf_liquids.setText("List of liquids used");
        tf_finals.setText("List of finals used");
    }

    @FXML
    public void handleGenerate() {
        if(inputOk()) {
            rng = new RNG(seed);
            ls = new LanguageSet(rng);
            words = new Words(ls);
            sentences = new Sentences(ls);

            tf_language_name.setText(ls.getNAME(words));
            tf_construct.setText(ls.getCONSTRUCT());
            tf_grammar.setText(ls.getGRAMMAR());
            tf_avg_length.setText(words.getAvgMorphLength()+"");
            tf_consonants.setText(Arrays.toString(ls.getCONSONANTS()));
            tf_vowels.setText(Arrays.toString(ls.getVOWELS()));
            tf_sibilants.setText(Arrays.toString(ls.getSIBILANTS()));
            tf_liquids.setText(Arrays.toString(ls.getLIQUIDS()));
            tf_finals.setText(Arrays.toString(ls.getFINALS()));
            output.setText(sentences.createLoremIpsum(500));

            bt_more_text.setVisible(true);
        }
    }

    @FXML
    public void handleMoreText() {
        output.setText(output.getText().concat(sentences.createLoremIpsum(500)));
    }

    private boolean inputOk() {
        String errorMessage = "";

        // try to parse the cost into an int.
        try {
            if(seed_in.getText().equals("")) {
                seed_in.setText(""+new RNG().nextLong());
            }
            seed = Long.parseLong(seed_in.getText());
        } catch (NumberFormatException e) {
            errorMessage += "Seed must be a whole number!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
