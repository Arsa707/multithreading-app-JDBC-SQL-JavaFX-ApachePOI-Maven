module com.example.LearnEnglishApp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.poi.poi;


    opens com.example.LearnEnglishApp to javafx.fxml;
    exports com.example.LearnEnglishApp;
}