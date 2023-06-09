package com.example.LearnEnglishApp;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

import java.lang.reflect.Type;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class BasicViewController {

    @FXML
    private Button ButtonWord1;

    @FXML
    private Button ButtonWord2;

    @FXML
    private Button ButtonWord3;

    @FXML
    private TextFlow LearnText;

    @FXML
    private TextFlow HighScoreText;

    @FXML
    ArrayList<Button> getButtonsArray(){
        ArrayList<Button> buttonsArray = new ArrayList<>();
        buttonsArray.add(ButtonWord1);
        buttonsArray.add(ButtonWord2);
        buttonsArray.add(ButtonWord3);
        return buttonsArray;
    }

    @FXML
    void setTextOfQuestion(String wordOfQuestion){
        //инициализируем текст вопроса
        Text textOfQuestion = new Text(wordOfQuestion);
        textOfQuestion.setFill(Color.BLACK);
        textOfQuestion.setFont(Font.font("Arial Unicode MS ", 20));

        //очищаем текст вопроса
        LearnText.getChildren().clear();
        //устанавливаем новый текст вопроса
        LearnText.getChildren().add(textOfQuestion);

        //выравниваем текст вопроса по центру
        LearnText.setTextAlignment(TextAlignment.CENTER);

        //без этого метода не появляется надпись, пока не будет изменен размер изображения
        LearnText.autosize();
    }

    @FXML
    void setTextOfButton(ArrayList<String> textOfButton){
        ArrayList<Button> buttonsArray = getButtonsArray();
        for (int i = 0; i<3; i++){
            buttonsArray.get(i).setText(textOfButton.get(i));
        }
    }

    @FXML
    void setTextOfHighScore(String selectTextOfButton, String actualTextOfQuestion){
        //переменная актуального текста рекорда
        String actualTextOfHighScore = "";
        //получаем актуальный текст рекорда
        ObservableList<Node> nodes = HighScoreText.getChildren();
        StringBuilder sb = new StringBuilder();
        if (nodes.size() == 0){
            actualTextOfHighScore = "0";
        }
        else {
            for (Node node : nodes) {
                StringBuilder append = sb.append((((Text) node).getText()));
            }
             actualTextOfHighScore = sb.toString();
        }
        int numberOfHighScore = Integer.parseInt(actualTextOfHighScore);

        //добавляем очки, в зависимости от успешного выбора кнопки
        DatabaseHandler databaseHandler = new DatabaseHandler();

        if (selectTextOfButton.length()>0){
            if (databaseHandler.CheckSelection(selectTextOfButton, actualTextOfQuestion)) {
                numberOfHighScore++;
            }
            else numberOfHighScore = 0;
        }
        String totalNumberOfHighScore = String.valueOf(numberOfHighScore);

        //инициализируем текст рекорда
        Text textOfHighScore = new Text(totalNumberOfHighScore);
        textOfHighScore.setFill(Color.BLACK);
        textOfHighScore.setFont(Font.font("Berlin Sans FL Demi",18));

        //очищаем текст рекорда
        HighScoreText.getChildren().clear();
        //устанавливаем новый текст вопроса
        HighScoreText.getChildren().add(textOfHighScore);

        //выравниваем текст рекорда по центру
        HighScoreText.setTextAlignment(TextAlignment.CENTER);

        //без этого метода не появляется надпись, пока не будет изменен размер изображения
        HighScoreText.autosize();
    }

    @FXML
    void setTextOnTheScene(String selectTextOfButton){
        DatabaseHandler databaseHandler = new DatabaseHandler();
        //получаем массивы данных таблицы из БД
        ArrayList<String> englishWords = databaseHandler.getArrayListWords(Const.WORD);
        ArrayList<String> RUWords = databaseHandler.getArrayListWords(Const.TRANSLATE_RU);

        //создаем массивы для рандомных чисел
        ArrayList<Integer> randomNumbers = new ArrayList<Integer>();
        ArrayList<Integer> lastRandomNumbers = new ArrayList<Integer>();
        //массив для рандомных слов, полученных из БД
        ArrayList<String> textOfButton = new ArrayList<String>();

        //переменная с количеством кнопок
        int numberOfButton = getButtonsArray().size();
        //переменная с количеством слов в БД
        int numberOfWords = englishWords.size();
        //переменные с рандомными числами;
        int randomNumber = 0;
        int lastNumber = -1;

        //переменная для контроля цикла
        boolean toContinue = true;

        Random random = new Random();

        //заполняем массив рандомных чисел в пределах количества кнопок
        for(int i = 0; i<numberOfButton; i++) {
            toContinue = true;
            randomNumber = random.nextInt(numberOfWords);

            for (int actNumber:lastRandomNumbers){
                if (randomNumber==actNumber){
                    toContinue = false;
                    i--;
                    break;
                }
            }

            if (toContinue){
                lastNumber = randomNumber;
                randomNumbers.add(randomNumber);
                lastRandomNumbers.add(lastNumber);
            }
        }

        //сбрасываем значения переменных и массива с рандомными числами
        randomNumber = 0;
        lastNumber = -1;
        lastRandomNumbers.clear();

        //заполняем массив рандомных слов
        for(int i = 0; i<numberOfButton; i++) {
            toContinue = true;
            randomNumber = random.nextInt(numberOfButton);

            for (int actNumber : lastRandomNumbers) {
                if (randomNumber == actNumber) {
                    toContinue = false;
                    i--;
                    break;
                }
            }
            if (toContinue) {
                lastNumber = randomNumber;
                textOfButton.add(RUWords.get(randomNumbers.get(randomNumber)));
                lastRandomNumbers.add(lastNumber);
            }
        }

        //переменная со значение текущего текста вопроса
        ObservableList<Node> nodes = LearnText.getChildren();
        StringBuilder sb = new StringBuilder();
        for (Node node:nodes){
            StringBuilder append = sb.append((((Text)node).getText()));
        }
        String actualTextOfQuestion = sb.toString();

        //переменная для будущего текста вопроса
        String wordOfQuestion = "";

        //получаем слово для текста вопроса и проверяем на совпадение с текущим
        for(int i = 0; i<numberOfButton; i ++) {
            randomNumber = random.nextInt(numberOfButton);
            wordOfQuestion = englishWords.get(randomNumbers.get(randomNumber));
            if (actualTextOfQuestion.equals(wordOfQuestion.toString())){
                continue;
            }
            wordOfQuestion = englishWords.get(randomNumbers.get(randomNumber));
            break;
        }

        //устанавливаем текст вопроса, рекорда и кнопок
        setTextOfQuestion(wordOfQuestion);
        setTextOfButton(textOfButton);
        setTextOfHighScore(selectTextOfButton, actualTextOfQuestion);
    }

    @FXML
    void initialize (){
        setTextOnTheScene("");

        ButtonWord1.setOnAction(event -> {
            setTextOnTheScene(ButtonWord1.getText());
        });
        ButtonWord2.setOnAction(event -> {
            setTextOnTheScene(ButtonWord2.getText());
        });
        ButtonWord3.setOnAction(event -> {
            setTextOnTheScene(ButtonWord3.getText());
        });
    }
}
