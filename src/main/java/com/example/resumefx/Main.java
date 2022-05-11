package com.example.resumefx;

import java.io.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application {
    public TextField name;
    public TextField email;
    public TextField phone;
    public ChoiceBox<String> day;
    public ChoiceBox<String> month;
    public ChoiceBox<String> year;
    public ToggleGroup gender;
    public ChoiceBox<String> course;
    public ChoiceBox<String> java;
    public ChoiceBox<String> python;
    public ChoiceBox<String> machineLearning;
    public TextArea comment;

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        VBox root = new VBox(10);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(10, 0, 10, 0));

        // title
        Text title = new Text("Resume");
        title.setFill(Color.BLUE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        // Accordion accordion = new Accordion();

        // PERSONAL INFO SECTION
        HBox personalInfoContainer = new HBox(15);
        personalInfoContainer.setPadding(new Insets(15, 15, 15, 15));

        GridPane formContainer = new GridPane();
        // name
        Label nameLabel = new Label("Full name:");
        name = new TextField();
        formContainer.add(nameLabel, 0, 0);
        formContainer.add(name, 1, 0);
        // email address
        Label emailLabel = new Label("Email address:");
        email = new TextField();
        formContainer.add(emailLabel, 0, 1);
        formContainer.add(email, 1, 1);
        // phone number
        Label phoneLabel = new Label("Phone Number:");
        phone = new TextField();
        formContainer.add(phoneLabel, 0, 2);
        formContainer.add(phone, 1, 2);
        // birth date
        Label birthdateLabel = new Label("Birth date:");

        day = new ChoiceBox<String>();
        for(int i = 1; i < 32; i++) {
            day.getItems().add(String.valueOf(i));
        }
        day.setValue("1");

        month = new ChoiceBox<String>();
        month.getItems().addAll("January", "February", "Mars", "April", "May", "June", "July", "Auguest", "September", "October", "November", "December");
        month.setValue("January");

        year = new ChoiceBox<String>();
        for(int i = 1950; i < 2022; i++) {
            year.getItems().add(String.valueOf(i));
        }
        year.setValue("1999");

        HBox birthdateComboBoxesContainer = new HBox(5, day, month, year);

        formContainer.add(birthdateLabel, 0, 3);
        formContainer.add(birthdateComboBoxesContainer, 1, 3);
        // gender
        Label genderLabel = new Label("Gender:");

        RadioButton male = new RadioButton("Male");
        RadioButton female = new RadioButton("Female");

        gender = new ToggleGroup();

        male.setToggleGroup(gender);
        female.setToggleGroup(gender);

        HBox genderOptionsContainer = new HBox(5, male, female);

        formContainer.add(genderLabel, 0, 4);
        formContainer.add(genderOptionsContainer, 1, 4);
        // course
        Label courseLabel = new Label("Course:");
        course = new ChoiceBox<>();
        course.getItems().addAll("Ingénieur", "License", "Master", "Doctorat");
        course.setValue("Ingénieur");

        formContainer.add(courseLabel, 0, 5);
        formContainer.add(course, 1, 5);
        // image
        FileInputStream input = new FileInputStream("src/main/java/com/example/resumefx/avatar.png");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);

        formContainer.setVgap(5);

        personalInfoContainer.getChildren().addAll(formContainer, imageView);

        TitledPane personalInfoPane = new TitledPane("Personal Information", personalInfoContainer);
        personalInfoPane.setExpanded(true);

        // TECHNICAL SKILLS SECTION
        // skills
        Label javaLabel = new Label("Java:");
        Label pythonLabel = new Label("Python");
        Label machineLearningLabel = new Label("Machine Learning");

        String options[] = {"Beginner", "Intermediate", "Advanced"};

        java = new ChoiceBox<>();
        java.getItems().addAll(options);
        java.setValue(options[0]);
        python = new ChoiceBox<>();
        python.getItems().addAll(options);
        python.setValue(options[0]);
        machineLearning = new ChoiceBox<>();
        machineLearning.getItems().addAll(options);
        machineLearning.setValue(options[0]);

        GridPane skillsFormContainer = new GridPane();
        skillsFormContainer.add(javaLabel, 0, 0);
        skillsFormContainer.add(java, 1, 0);
        skillsFormContainer.add(pythonLabel, 0, 1);
        skillsFormContainer.add(python, 1, 1);
        skillsFormContainer.add(machineLearningLabel, 0, 2);
        skillsFormContainer.add(machineLearning, 1, 2);

        skillsFormContainer.setVgap(5);

        // comment
        Label commentLabel = new Label("Comment:");
        comment = new TextArea();
        VBox commentContainer = new VBox(1, commentLabel, comment);

        skillsFormContainer.setMinWidth(200);
        HBox skillsContainer = new HBox(5, skillsFormContainer, commentContainer);
        skillsContainer.setPadding(new Insets(15));

        TitledPane skillsPane = new TitledPane("Skills", skillsContainer);
        skillsPane.setExpanded(true);

        //accordion.getPanes().addAll(personalInfoPane, skillsPane);

        // save as PDF before sending
        CheckBox pdfCheckbox = new CheckBox("Save as PDF before sending");

        // buttons
        Button sendBtn = new Button("Send");
        sendBtn.setOnAction(e -> {
            this.exportHTML();
        });

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e -> { this.exit(); });

        HBox buttonsContainer = new HBox(5, sendBtn, cancelBtn);
        buttonsContainer.setAlignment(Pos.CENTER);

        // root.getChildren().add(accordion);
        root.getChildren().addAll(title, personalInfoPane, skillsPane, pdfCheckbox, buttonsContainer); // root is a VBox

        Scene scene = new Scene(root, 500, 500);
        stage.setTitle("Resume");
        stage.setScene(scene);
        stage.show();
    }

    public void exportHTML() {
        File f = new File("cv.html");
        FileWriter fw;
        try {
            fw = new FileWriter(f,false);

            PrintWriter pw=new PrintWriter(fw);

            String htmlCode = ""
                + "<html>"
                + "<head>"
                + "<title>Resume Java FX</title>"
                + "<style> .bold { font-weight: bold } </style>"
                + "</head>"
                + "<body>"
                + "<h1>Resume FX</h1>"
                + "<ul>"
                + "<li><span class='bold'>Full Name:</span> " + name.getText() + "</li>"
                + "<li><span class='bold'>Email Address:</span> " + email.getText() + "</li>"
                + "<li><span class='bold'>Phone Number:</span> " + phone.getText() + "</li>"
                + "<li><span class='bold'>Birth date:</span> " + day.getValue() + "/" + month.getValue() + "/" + year.getValue() + "</li>"
                + "<li><span class='bold'>Gender:</span> " + (RadioButton) gender.getSelectedToggle() + "</li>"
                + "<li><span class='bold'>Course:</span> " + course.getValue() + "</li>"
                + "<li><span class='bold'>Java:</span> " + java.getValue() + "</li>"
                + "<li><span class='bold'>Python:</span> " + python.getValue() + "</li>"
                + "<li><span class='bold'>Machine Learning:</span> " + machineLearning.getValue() + "</li>"
                + "<li><span class='bold'>Comment:</span> " + comment.getText() + "</li>"
                + "</ul>"
                + "</body>"
                + "</html>";

            pw.print(htmlCode);

            pw.close();
            fw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        getHostServices().showDocument("cv.html");
    }

    public void exit() {
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch();
    }
}