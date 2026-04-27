import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.UUID;

/**
 * GUI-стартер для роботи з колекцією працівників.
 */
public class MainApp extends Application {
    private Company company;

    private ComboBox<String> typeComboBox;
    private TextField nameField;
    private TextField salaryField;
    private TextField extraField;

    private ListView<String> employeesListView;

    private TextField uuidSearchField;
    private TextArea detailsArea;

    /**
     * Точка входу для запуску JavaFX-застосунку.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Ініціалізує та показує головне вікно програми.
     */
    @Override
    public void start(Stage primaryStage) {
        company = new Company("Default Company", "Default Address");

        Label titleLabel = new Label("Керування працівниками");

        typeComboBox = new ComboBox<String>();
        typeComboBox.getItems().addAll(
                "Employee",
                "ContractEmployee",
                "FullTimeEmployee",
                "RemoteEmployee",
                "Manager"
        );
        typeComboBox.setValue("Employee");
        typeComboBox.setOnAction(event -> updateExtraFieldLabel());

        Label typeLabel = new Label("Тип об'єкта:");
        Label nameLabel = new Label("Ім'я:");
        Label salaryLabel = new Label("Зарплата:");
        Label extraLabel = new Label("Додаткове поле:");

        nameField = new TextField();
        salaryField = new TextField();
        extraField = new TextField();

        Button addButton = new Button("Додати");
        addButton.setOnAction(event -> addEmployee());

        GridPane addForm = new GridPane();
        addForm.setHgap(10);
        addForm.setVgap(10);
        addForm.add(typeLabel, 0, 0);
        addForm.add(typeComboBox, 1, 0);
        addForm.add(nameLabel, 0, 1);
        addForm.add(nameField, 1, 1);
        addForm.add(salaryLabel, 0, 2);
        addForm.add(salaryField, 1, 2);
        addForm.add(extraLabel, 0, 3);
        addForm.add(extraField, 1, 3);
        addForm.add(addButton, 1, 4);

        employeesListView = new ListView<String>();
        employeesListView.setPrefHeight(200);

        Label collectionLabel = new Label("Колекція працівників:");

        uuidSearchField = new TextField();
        Button findButton = new Button("Знайти");
        findButton.setOnAction(event -> findEmployeeByUuid());

        HBox searchBox = new HBox(10, new Label("UUID:"), uuidSearchField, findButton);

        detailsArea = new TextArea();
        detailsArea.setEditable(false);
        detailsArea.setWrapText(true);
        detailsArea.setPrefHeight(200);

        Label detailsLabel = new Label("Повна інформація про знайдений об'єкт:");

        VBox root = new VBox(15,
                titleLabel,
                addForm,
                collectionLabel,
                employeesListView,
                searchBox,
                detailsLabel,
                detailsArea
        );
        root.setPadding(new Insets(15));

        updateExtraFieldLabel();

        Scene scene = new Scene(root, 700, 700);
        primaryStage.setTitle("Employees GUI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Оновлює підпис для додаткового поля залежно від обраного типу об'єкта.
     */
    private void updateExtraFieldLabel() {
        String selectedType = typeComboBox.getValue();

        if ("Employee".equals(selectedType)) {
            extraField.setDisable(true);
            extraField.clear();
            return;
        }

        extraField.setDisable(false);

        if ("ContractEmployee".equals(selectedType)) {
            extraField.setPromptText("Тривалість контракту в місяцях");
            return;
        }

        if ("FullTimeEmployee".equals(selectedType)) {
            extraField.setPromptText("Бонус");
            return;
        }

        if ("RemoteEmployee".equals(selectedType)) {
            extraField.setPromptText("Країна дистанційної роботи");
            return;
        }

        if ("Manager".equals(selectedType)) {
            extraField.setPromptText("Кількість підлеглих");
        }
    }

    /**
     * Створює та додає нового працівника до колекції.
     */
    private void addEmployee() {
        try {
            String type = typeComboBox.getValue();
            String name = nameField.getText().trim();
            double salary = Double.parseDouble(salaryField.getText().trim());
            Employee employee;

            if ("Employee".equals(type)) {
                employee = new Employee(name, salary);
            } else if ("ContractEmployee".equals(type)) {
                int contractMonths = Integer.parseInt(extraField.getText().trim());
                employee = new ContractEmployee(name, salary, contractMonths);
            } else if ("FullTimeEmployee".equals(type)) {
                double bonus = Double.parseDouble(extraField.getText().trim());
                employee = new FullTimeEmployee(name, salary, bonus);
            } else if ("RemoteEmployee".equals(type)) {
                String workCountry = extraField.getText().trim();
                employee = new RemoteEmployee(name, salary, workCountry);
            } else if ("Manager".equals(type)) {
                int teamSize = Integer.parseInt(extraField.getText().trim());
                employee = new Manager(name, salary, teamSize);
            } else {
                showError("Невідомий тип об'єкта.");
                return;
            }

            company.addNewEmployee(employee);
            refreshEmployeesList();
            clearInputFields();
        } catch (NumberFormatException e) {
            showError("Числові поля містять некоректні дані.");
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }

    /**
     * Оновлює короткий список усіх об'єктів колекції.
     */
    private void refreshEmployeesList() {
        employeesListView.getItems().clear();

        ArrayList<Employee> employees = company.getEmployees();
        for (Employee employee : employees) {
            employeesListView.getItems().add(buildShortInfo(employee));
        }
    }

    /**
     * Формує коротке представлення об'єкта для списку.
     */
    private String buildShortInfo(Employee employee) {
        return employee.getClass().getSimpleName() +
                ": " + employee.getName() +
                " | UUID: " + employee.getUuid();
    }

    /**
     * Очищає поля введення після додавання об'єкта.
     */
    private void clearInputFields() {
        nameField.clear();
        salaryField.clear();
        extraField.clear();
        typeComboBox.setValue("Employee");
        updateExtraFieldLabel();
    }

    /**
     * Виконує пошук працівника за UUID та виводить повну інформацію.
     */
    private void findEmployeeByUuid() {
        String uuidText = uuidSearchField.getText().trim();

        if (uuidText.isEmpty()) {
            detailsArea.setText("Помилка: UUID не може бути порожнім.");
            return;
        }

        try {
            UUID uuid = UUID.fromString(uuidText);
            ArrayList<Employee> results = company.searchByUuid(uuid);

            if (results.isEmpty()) {
                detailsArea.setText("Не знайдено.");
                return;
            }

            detailsArea.setText(results.get(0).toString());
        } catch (IllegalArgumentException e) {
            detailsArea.setText("Помилка: некоректний формат UUID.");
        }
    }

    /**
     * Показує повідомлення про помилку.
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Помилка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}