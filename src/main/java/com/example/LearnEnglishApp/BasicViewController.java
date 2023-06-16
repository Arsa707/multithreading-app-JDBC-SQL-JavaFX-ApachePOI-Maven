package com.example.LearnEnglishApp;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class BasicViewController {

    private static boolean motivationThreadOn = false;

    @FXML
    private ImageView Cloud;

    @FXML
    private TextFlow MotivationText;

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
    ArrayList<Button> getButtonsArray() {
        ArrayList<Button> buttonsArray = new ArrayList<>();
        buttonsArray.add(ButtonWord1);
        buttonsArray.add(ButtonWord2);
        buttonsArray.add(ButtonWord3);
        return buttonsArray;
    }

    @FXML
    void setTextOfQuestion(String wordOfQuestion) {
        //инициализируем текст вопроса
        Text textOfQuestion = new Text(wordOfQuestion);
        textOfQuestion.setFill(Color.BLACK);
        textOfQuestion.setFont(Font.font("Arial Unicode MS ", 20));

        //очищаем текст вопроса
        LearnText.getChildren().clear();
        //устанавливаем новый текст вопроса
        LearnText.getChildren().add(textOfQuestion);


    }

    @FXML
    void setTextOfButton(ArrayList<String> textOfButton) {
        ArrayList<Button> buttonsArray = getButtonsArray();
        for (int i = 0; i < 3; i++) {
            buttonsArray.get(i).setText(textOfButton.get(i));
        }
    }

    @FXML
    void setTextOfHighScore(String selectTextOfButton, String actualTextOfQuestion) throws SQLException, IOException {
        //переменная актуального текста рекорда
        String actualTextOfHighScore;
        //получаем актуальный текст рекорда
        ObservableList<Node> nodes = HighScoreText.getChildren();
        StringBuilder sb = new StringBuilder();
        if (nodes.size() == 0) {
            actualTextOfHighScore = "0";
        } else {
            for (Node node : nodes) {
                sb.append((((Text) node).getText()));
            }
            actualTextOfHighScore = sb.toString();
        }
        int scoreCount = Integer.parseInt(actualTextOfHighScore);

        //добавляем очки, в зависимости от успеха выбора кнопки
        DataBaseAndTextFileHandler dataBaseAndTextFileHandler = new DataBaseAndTextFileHandler();

        if (selectTextOfButton.length() > 0) {
            if (dataBaseAndTextFileHandler.CheckSelection(selectTextOfButton, actualTextOfQuestion)) {
                scoreCount++;
            } else scoreCount = 0;
        }
        String totalNumberOfHighScore = String.valueOf(scoreCount);

        //инициализируем текст рекорда
        Text textOfHighScore = new Text(totalNumberOfHighScore);
        textOfHighScore.setFill(Color.BLACK);
        textOfHighScore.setFont(Font.font("Berlin Sans FL Demi", 18));

        //очищаем текст рекорда
        HighScoreText.getChildren().clear();
        //устанавливаем новый текст вопроса
        HighScoreText.getChildren().add(textOfHighScore);

        //выравниваем текст рекорда по центру
        HighScoreText.setTextAlignment(TextAlignment.CENTER);
    }


    @FXML
    void setTextOnTheScene(String selectTextOfButton) throws SQLException, IOException {
        DataBaseAndTextFileHandler dataBaseAndTextFileHandler = new DataBaseAndTextFileHandler();

        //получаем массивы данных таблицы из БД
        ArrayList<String> englishWords = dataBaseAndTextFileHandler.getArrayListWords(Const.WORD);
        ArrayList<String> RUWords = dataBaseAndTextFileHandler.getArrayListWords(Const.TRANSLATE_RU);

        //создаем массивы для рандомных чисел
        ArrayList<Integer> randomNumbers = new ArrayList<>();
        ArrayList<Integer> lastRandomNumbers = new ArrayList<>();
        //массив для рандомных слов, полученных из БД
        ArrayList<String> textOfButton = new ArrayList<>();

        //переменная с количеством кнопок
        int buttonCount = getButtonsArray().size();
        //переменная с количеством слов в БД
        int numberOfWords = englishWords.size();
        //переменна с рандомным числом;
        int randomNumber = 0;

        //переменная для контроля цикла
        boolean toContinue = true;

        Random random = new Random();

        //заполняем массив рандомных чисел в пределах количества кнопок
        for (int i = 0; i < buttonCount; i++) {
            toContinue = true;
            randomNumber = random.nextInt(numberOfWords);

            for (int actNumber : lastRandomNumbers) {
                if (randomNumber == actNumber) {
                    toContinue = false;
                    i--;
                    break;
                }
            }

            if (toContinue) {
                randomNumbers.add(randomNumber);
                lastRandomNumbers.add(randomNumber);
            }
        }

        //сбрасываем значения переменных и массива с рандомными числами
        randomNumber = 0;
        lastRandomNumbers.clear();

        //заполняем массив рандомных слов
        for (int i = 0; i < buttonCount; i++) {
            toContinue = true;
            randomNumber = random.nextInt(buttonCount);

            for (int actNumber : lastRandomNumbers) {
                if (randomNumber == actNumber) {
                    toContinue = false;
                    i--;
                    break;
                }
            }
            if (toContinue) {
                textOfButton.add(RUWords.get(randomNumbers.get(randomNumber)));
                lastRandomNumbers.add(randomNumber);
            }
        }

        //переменная со значение текущего текста вопроса
        ObservableList<Node> nodes = LearnText.getChildren();
        StringBuilder sb = new StringBuilder();
        for (Node node : nodes) {
            sb.append(((Text) node).getText());
        }
        String actualTextOfQuestion = sb.toString();

        //переменная для будущего текста вопроса
        String wordOfQuestion = "";

        //получаем слово для текста вопроса и проверяем на совпадение с текущим
        while (true) {
            randomNumber = random.nextInt(buttonCount);
            wordOfQuestion = englishWords.get(randomNumbers.get(randomNumber));
            if (actualTextOfQuestion.equals(wordOfQuestion.toString())) continue;
            wordOfQuestion = englishWords.get(randomNumbers.get(randomNumber));
            break;
        }

        //Фоновая отрисовка надписей на форме
        if (!motivationThreadOn) {
            Thread motivationThread = new Thread() {

                @Override
                public void run() {
                 //   ArrayList<String> motivationText;
                    if (!motivationThreadOn) {
                        motivationThreadOn = true;
                   //     try {
                   //         motivationText = dataBaseAndTextFileHandler.getArrayListWords(Const.MOTIVATION_TEXT);
                   //     } catch (SQLException e) {
                   //         throw new RuntimeException(e);
                   //     } catch (IOException e) {
                   //         throw new RuntimeException(e);
                   //     }

                   //     int motivationTextCount = motivationText.size();
                    //    int i = motivationTextCount;
                        while (motivationThreadOn) {
                            //таймаут
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }


                           // Text text = new Text(motivationText.get(i));
                            Text text = new Text("sdsd");
                            text.setFill(Color.BLACK);
                            text.setFont(Font.font("Berlin Sans FL Demi", 12));

                      ///     if (i > 0) {
                       //         i--;
                       //     } else i = motivationTextCount;


                            //рисуем надпись и показываем облако мыслей
                            //элементы GUI можно обновлять только в основном потоке или в такой конструкции!
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    Cloud.setVisible(true);
                                    MotivationText.getChildren().clear();
                                    MotivationText.getChildren().setAll(text);
                                }
                            });


                            //показываем некоторое время
                            try {
                                sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            //затираем надпись и прячем облако
                            //элементы GUI можно обновлять только в основном потоке или в такой конструкции!
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    MotivationText.getChildren().clear();
                                    Cloud.setVisible(false);
                                }
                            });

                        }
                    }
                }
            };
            motivationThread.start();
        }

        //устанавливаем текст вопроса, рекорда и кнопок
        setTextOfQuestion(wordOfQuestion);
        setTextOfButton(textOfButton);
        setTextOfHighScore(selectTextOfButton, actualTextOfQuestion);

        //без этих методов форма корректно не обновляется, пока не изменить размер. Стряхиваем пыль с костылей
        LearnText.autosize();
        HighScoreText.autosize();
    }

    @FXML
    void initialize() {
        DataBaseAndTextFileHandler dataBaseAndTextFileHandler = new DataBaseAndTextFileHandler();

        //Получаем список таблиц, сами таблицы из БД и обновляем данные файловых таблиц
        try {
            ArrayList<String> tables = Const.getAllTables();
            for (String table : tables) {
                try {
                    dataBaseAndTextFileHandler.exportDatabaseTableToExcel(table);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            boolean result = dataBaseAndTextFileHandler.reconnection();
            if (result) {
                initialize();
            }
        }

        try {
            setTextOnTheScene("");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ButtonWord1.setOnAction(event -> {
            try {
                setTextOnTheScene(ButtonWord1.getText());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        ButtonWord2.setOnAction(event -> {
            try {
                setTextOnTheScene(ButtonWord2.getText());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        ButtonWord3.setOnAction(event -> {
            try {
                setTextOnTheScene(ButtonWord3.getText());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
