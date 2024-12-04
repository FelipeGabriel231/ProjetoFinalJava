package com.example.crud_jogador;

import com.example.crud_jogador.model.Jogador;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;

public class JavaFXApp extends Application {

    private final String apiUrl = "http://localhost:8080/api/jogadores";

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #3b3b3b;");

        Label label = new Label("Gerenciar Jogadores:");
        label.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");

        // Campos para entrada de dados
        TextField idField = new TextField();
        idField.setPromptText("ID do Jogador (para busca/edição/exclusão)");

        TextField nameField = new TextField();
        nameField.setPromptText("Nome do Jogador");

        TextField positionField = new TextField();
        positionField.setPromptText("Posição do Jogador");

        TextField teamField = new TextField();
        teamField.setPromptText("Time do Jogador");

        TextField goalsField = new TextField();
        goalsField.setPromptText("Gols Marcados");

        TextField matchesField = new TextField();
        matchesField.setPromptText("Partidas Jogadas");

        TextField assistsField = new TextField();
        assistsField.setPromptText("Assistências");

        // Botões
        Button btnAddPlayer = new Button("Adicionar Jogador");
        btnAddPlayer.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px;");
        btnAddPlayer.setOnAction(e -> {
            String name = nameField.getText();
            String position = positionField.getText();
            String team = teamField.getText();
            String goals = goalsField.getText();
            String matches = matchesField.getText();
            String assists = assistsField.getText();

            if (!name.isEmpty() && !position.isEmpty() && !team.isEmpty()
                    && !goals.isEmpty() && !matches.isEmpty() && !assists.isEmpty()) {
                try {
                    addPlayerToAPI(name, position, team, Integer.parseInt(goals),
                            Integer.parseInt(matches), Integer.parseInt(assists));
                    clearFields(nameField, positionField, teamField, goalsField, matchesField, assistsField);
                    label.setText("Jogador Adicionado!");
                } catch (NumberFormatException ex) {
                    label.setText("Erro: Gols, Partidas e Assistências devem ser números!");
                }
            } else {
                label.setText("Preencha todos os campos!");
            }
        });

        Button btnViewAllPlayers = new Button("Consultar Todos os Jogadores");
        btnViewAllPlayers.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        btnViewAllPlayers.setOnAction(e -> {
            viewAllPlayers(nameField, positionField, teamField, goalsField, matchesField, assistsField);
        });

        Button btnViewPlayerById = new Button("Consultar Jogador por ID");
        btnViewPlayerById.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        btnViewPlayerById.setOnAction(e -> {
            String id = idField.getText();
            try {
                if (!id.isEmpty()) {
                    viewPlayerById(Long.parseLong(id), nameField, positionField, teamField, goalsField, matchesField, assistsField);
                } else {
                    label.setText("Digite um ID válido para consulta!");
                }
            } catch (NumberFormatException ex) {
                label.setText("O ID deve ser um número válido.");
            }
        });

        Button btnEditPlayerById = new Button("Editar Jogador por ID");
        btnEditPlayerById.setStyle("-fx-background-color: #FFC107; -fx-text-fill: black; -fx-font-size: 14px;");
        btnEditPlayerById.setOnAction(e -> {
            String id = idField.getText();
            String name = nameField.getText();
            String position = positionField.getText();
            String team = teamField.getText();
            String goals = goalsField.getText();
            String matches = matchesField.getText();
            String assists = assistsField.getText();

            if (!id.isEmpty() && !name.isEmpty() && !position.isEmpty() && !team.isEmpty()
                    && !goals.isEmpty() && !matches.isEmpty() && !assists.isEmpty()) {
                try {
                    editPlayerById(Long.parseLong(id), name, position, team, Integer.parseInt(goals),
                            Integer.parseInt(matches), Integer.parseInt(assists));
                    clearFields(nameField, positionField, teamField, goalsField, matchesField, assistsField);
                    label.setText("Jogador Editado!");
                } catch (NumberFormatException ex) {
                    label.setText("Erro: Gols, Partidas e Assistências devem ser números!");
                }
            } else {
                label.setText("Preencha todos os campos!");
            }
        });

        Button btnDeletePlayerById = new Button("Excluir Jogador por ID");
        btnDeletePlayerById.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px;");
        btnDeletePlayerById.setOnAction(e -> {
            String id = idField.getText();
            try {
                if (!id.isEmpty()) {
                    deletePlayerById(Long.parseLong(id));
                } else {
                    label.setText("Digite um ID para exclusão!");
                }
            } catch (NumberFormatException ex) {
                label.setText("O ID deve ser um número válido.");
            }
        });

        root.getChildren().addAll(label, idField, nameField, positionField, teamField, goalsField,
              matchesField, assistsField, btnAddPlayer, btnViewAllPlayers, btnViewPlayerById, btnEditPlayerById, btnDeletePlayerById);

        Scene scene = new Scene(root, 600, 700);
        primaryStage.setTitle("App de Jogadores");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addPlayerToAPI(String name, String position, String team, int goals, int matches, int assists) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String playerJson = String.format(
                "{\"nome\":\"%s\",\"posicao\":\"%s\",\"time\":\"%s\",\"golsMarcados\":%d,\"partidasJogadas\":%d,\"assistencias\":%d}",
                name, position, team, goals, matches, assists);
        HttpEntity<String> entity = new HttpEntity<>(playerJson, headers);

        try {
            restTemplate.postForObject(apiUrl, entity, String.class);
            System.out.println("Jogador enviado com sucesso: " + playerJson);
        } catch (Exception e) {
            System.err.println("Erro ao enviar jogador: " + e.getMessage());
        }
    }

    private void viewAllPlayers(TextField nameField, TextField positionField, TextField teamField,
                                TextField goalsField, TextField matchesField, TextField assistsField) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            List<Jogador> jogadores = restTemplate.getForObject(apiUrl, List.class);

            if (jogadores != null && !jogadores.isEmpty()) {
                // Criando uma ListView para exibir os jogadores
                ListView<String> listView = new ListView<>();
                StringBuilder allPlayers = new StringBuilder();

                for (Jogador jogador : jogadores) {
                    allPlayers.append(jogador.getNome()).append(" - ")
                            .append(jogador.getPosicao()).append(" - ")
                            .append(jogador.getTime()).append("\n");
                }

                // Adicionando jogadores à ListView
                listView.getItems().add(allPlayers.toString());

                // Criando uma nova cena para mostrar os jogadores
                VBox vbox = new VBox();
                vbox.setAlignment(Pos.CENTER);
                vbox.setSpacing(10);
                vbox.getChildren().add(listView);

                Scene scene = new Scene(vbox, 400, 300);
                Stage stage = new Stage();
                stage.setTitle("Lista de Jogadores");
                stage.setScene(scene);
                stage.show();
            } else {
                showInfo("Nenhum jogador encontrado.");
            }
        } catch (Exception e) {
            showError("Erro ao buscar jogadores: " + e.getMessage());
        }
    }

    private void viewPlayerById(Long id, TextField nameField, TextField positionField, TextField teamField,
                                TextField goalsField, TextField matchesField, TextField assistsField) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            Jogador jogador = restTemplate.getForObject(apiUrl + "/" + id, Jogador.class);
            if (jogador != null) {
                nameField.setText(jogador.getNome());
                positionField.setText(jogador.getPosicao());
                teamField.setText(jogador.getTime());
                goalsField.setText(String.valueOf(jogador.getGolsMarcados()));
                matchesField.setText(String.valueOf(jogador.getPartidasJogadas()));
                assistsField.setText(String.valueOf(jogador.getAssistencias()));
            } else {
                showInfo("Jogador não encontrado!");
            }
        } catch (Exception e) {
            showError("Erro ao buscar jogador: " + e.getMessage());
        }
    }

    private void editPlayerById(Long id, String name, String position, String team, int goals, int matches, int assists) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String playerJson = String.format(
                "{\"nome\":\"%s\",\"posicao\":\"%s\",\"time\":\"%s\",\"golsMarcados\":%d,\"partidasJogadas\":%d,\"assistencias\":%d}",
                name, position, team, goals, matches, assists);
        HttpEntity<String> entity = new HttpEntity<>(playerJson, headers);

        try {
            restTemplate.put(apiUrl + "/" + id, entity);
            System.out.println("Jogador editado com sucesso: " + playerJson);
        } catch (Exception e) {
            System.err.println("Erro ao editar jogador: " + e.getMessage());
        }
    }

    private void deletePlayerById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.delete(apiUrl + "/" + id);
            System.out.println("Jogador excluído com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao excluir jogador: " + e.getMessage());
        }
    }

    private void clearFields(TextField nameField, TextField positionField, TextField teamField,
                             TextField goalsField, TextField matchesField, TextField assistsField) {
        nameField.clear();
        positionField.clear();
        teamField.clear();
        goalsField.clear();
        matchesField.clear();
        assistsField.clear();
    }

    private void showError(String message) {
        showAlert(Alert.AlertType.ERROR, "Erro", message);
    }

    private void showInfo(String message) {
        showAlert(Alert.AlertType.INFORMATION, "Informação", message);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
