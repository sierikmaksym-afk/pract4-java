import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Клас для роботи з базою даних.
 */
public class Repository {
    private Connection connection;

    /**
     * Конструктор для створення репозиторію та підключення до бази даних.
     */
    public Repository(String propertiesPath) throws SQLException {
        Properties properties = new Properties();
        FileInputStream inputStream = null;

        try {
            inputStream = new FileInputStream(propertiesPath);
            properties.load(inputStream);
        } catch (IOException e) {
            throw new SQLException("Не вдалося зчитати файл налаштувань: " + e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new SQLException("Не вдалося закрити файл налаштувань: " + e.getMessage());
                }
            }
        }

        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");

        if (url == null || user == null || password == null) {
            throw new SQLException("У файлі налаштувань відсутні параметри підключення.");
        }

        this.connection = DriverManager.getConnection(url, user, password);
    }

    /**
     * Додає працівника до таблиці бази даних.
     */
    public void insertEmployee(Employee employee) throws SQLException {
        String sql = "INSERT INTO employees " +
                "(id, type, name, salary, contract_months, bonus, work_country, team_size) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, employee.getId());
            preparedStatement.setString(2, employee.getClass().getSimpleName());
            preparedStatement.setString(3, employee.getName());
            preparedStatement.setDouble(4, employee.getSalary());

            if (employee instanceof ContractEmployee) {
                preparedStatement.setInt(5, ((ContractEmployee) employee).getContractMonths());
            } else {
                preparedStatement.setNull(5, java.sql.Types.INTEGER);
            }

            if (employee instanceof FullTimeEmployee) {
                preparedStatement.setDouble(6, ((FullTimeEmployee) employee).getBonus());
            } else {
                preparedStatement.setNull(6, java.sql.Types.DOUBLE);
            }

            if (employee instanceof RemoteEmployee) {
                preparedStatement.setString(7, ((RemoteEmployee) employee).getWorkCountry());
            } else {
                preparedStatement.setNull(7, java.sql.Types.VARCHAR);
            }

            if (employee instanceof Manager) {
                preparedStatement.setInt(8, ((Manager) employee).getTeamSize());
            } else {
                preparedStatement.setNull(8, java.sql.Types.INTEGER);
            }

            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    /**
     * Закриває з'єднання з базою даних.
     */
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}