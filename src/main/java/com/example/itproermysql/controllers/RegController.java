package com.example.itproermysql.controllers;

import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import com.example.itproermysql.DB;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button auth_button;

    @FXML
    private TextField auth_log;

    @FXML
    private PasswordField auth_pass;

    @FXML
    private Button reg_button;

    @FXML
    private TextField reg_email;

    @FXML
    private TextField reg_log;

    @FXML
    private PasswordField reg_pass;

    @FXML
    private CheckBox reg_right;

    private DB db = new DB();

    @FXML
    void initialize() {
        reg_button.setOnAction(actionEvent -> {
            registrationUser();
        });
        auth_button.setOnAction(actionEvent -> {
            authUser();
        });
    }

    private void authUser() {
        String login = auth_log.getCharacters().toString();
        String pass = auth_pass.getCharacters().toString();
        auth_log.setStyle("-fx-border-color: #fafafa");
        auth_pass.setStyle("-fx-border-color: #fafafa");


        if (login.length() <= 1)
            auth_log.setStyle("-fx-border-color: #e06249");
        else if (pass.length() <= 3)
            auth_pass.setStyle("-fx-border-color: #e06249");
        else if (db.authUser(login, md5String(pass))) {
            auth_button.setText("Такого пользователя нет");
        }
        else {
            auth_log.setText("");
            auth_pass.setText("");
            auth_button.setText("Все готово :) ");
        }
    }

    private void registrationUser() {
        String login = reg_log.getCharacters().toString();
        String email = reg_email.getCharacters().toString();
        String pass = reg_pass.getCharacters().toString();
        reg_log.setStyle("-fx-border-color: #fafafa");
        reg_email.setStyle("-fx-border-color: #fafafa");
        reg_pass.setStyle("-fx-border-color: #fafafa");


        if (login.length() <= 1)
            reg_log.setStyle("-fx-border-color: #e06249");
        else if (email.length() <= 5 || !email.contains("@") || !email.contains("."))
            reg_email.setStyle("-fx-border-color: #e06249");
        else if (pass.length() <= 3)
            reg_pass.setStyle("-fx-border-color: #e06249");
        else if (!reg_right.isSelected())
          reg_button.setText("Поставьте галочку");
        else if (db.isExistUser(login))
            reg_button.setText("Введите другой логин");
        else {
            db.regUser(login, email, md5String(pass));
            reg_log.setText("");
            reg_email.setText("");
            reg_pass.setText("");
            reg_button.setText("Все готово :) ");
        }
    }

    public static String md5String(String pass) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(pass.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        BigInteger bigInteger = new BigInteger(1, digest);
        StringBuilder m5dHex = new StringBuilder(bigInteger.toString(16));

        while (m5dHex.length() < 32) {
            m5dHex.insert(0, "0");
        }
        return m5dHex.toString();
    }
}
