package com.template.main;

import com.template.controller.MainController;
import com.template.model.dao.IInstrumentoDAO;
import com.template.model.dao.InstrumentoDAO;
import com.template.service.IInstrumentoService;
import com.template.service.InstrumentoService;
import com.template.validator.IInstrumentoValidator;
import com.template.validator.InstrumentoValidator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../main.fxml"));

        // Instanciação dos módulos e injeção de dependências
        IInstrumentoDAO dao = new InstrumentoDAO();
        IInstrumentoValidator validator = new InstrumentoValidator();
        IInstrumentoService service = new InstrumentoService(dao, validator);

        // Configuração da fábrica para criar o controller injetando a dependência
        loader.setControllerFactory(controllerClass -> {
            if (controllerClass == MainController.class) {
                return new MainController(service);
            }
            try {
                return controllerClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Parent root = loader.load();
        primaryStage.setTitle("Gerenciador de Instrumentos Musicais");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}