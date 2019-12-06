package by.training.machine.monitoring.manufacture;

import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.dao.ConnectionManager;
import by.training.machine.monitoring.dao.DaoException;
import by.training.machine.monitoring.dao.DaoSqlException;
import by.training.machine.monitoring.entity.ManufactureEntity;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Bean
@AllArgsConstructor
public class ManufactureDaoImpl implements ManufactureDao {
    //language=PostgreSQL
    private static final String INSERT_MANUFACTURE = "INSERT INTO machine_monitoring.machine_monitoring_schema.manufacture (name, user_id) VALUES (?, ?)";
    //language=PostgreSQL
    private static final String UPDATE_MANUFACTURE = "UPDATE machine_monitoring.machine_monitoring_schema.manufacture SET name = ?, user_id = ? WHERE id = ?";
    //language=PostgreSQL
    private static final String SELECT_MANUFACTURE_BY_USER_ID = "SELECT id, name, user_id FROM machine_monitoring.machine_monitoring_schema.manufacture WHERE user_id = ?";
    //language=PostgreSQL
    private static final String SELECT_MANUFACTURE_BY_ID = "SELECT id, name, user_id FROM machine_monitoring.machine_monitoring_schema.manufacture WHERE id = ?";
    //language=PostgreSQL
    private static final String DELETE_MANUFACTURE_BY_ID = "DELETE FROM machine_monitoring.machine_monitoring_schema.manufacture WHERE id = ?";
    private ConnectionManager connectionManager;

    @Override
    public Long save(ManufactureDto manufactureDto) throws DaoException {
        ManufactureEntity manufactureEntity = fromDto(manufactureDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(INSERT_MANUFACTURE, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            stmt.setString(++i, manufactureEntity.getName());
            stmt.setLong(++i, manufactureEntity.getUserId());
            stmt.executeUpdate();
            ResultSet resultSet = stmt.getGeneratedKeys();
            while (resultSet.next()) {
                manufactureEntity.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to save manufacture", e);
        }
        return manufactureEntity.getId();
    }

    @Override
    public boolean update(ManufactureDto manufactureDto) throws DaoException {
        ManufactureEntity manufactureEntity = fromDto(manufactureDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(UPDATE_MANUFACTURE)) {
            int i = 0;
            stmt.setString(++i, manufactureEntity.getName());
            stmt.setLong(++i, manufactureEntity.getUserId());
            stmt.setLong(++i, manufactureEntity.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to update manufacture", e);
        }
    }

    @Override
    public boolean delete(ManufactureDto manufactureDto) throws DaoException {
        ManufactureEntity manufactureEntity = fromDto(manufactureDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(DELETE_MANUFACTURE_BY_ID)) {
            stmt.setLong(1, manufactureEntity.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to delete manufacture", e);
        }
    }

    @Override
    public ManufactureDto getById(Long id) throws DaoException {
        List<ManufactureEntity> manufactureEntities = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_MANUFACTURE_BY_ID)) {
            stmt.setLong(1, id);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                manufactureEntities.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to getById manufacture", e);
        }
        return manufactureEntities.stream()
                .map(this::fromEntity)
                .findFirst()
                .orElseThrow(() -> new DaoException("Failed to get manufacture by id"));
    }

    @Override
    public Optional<ManufactureDto> getByUserId(Long userId) throws DaoException {
        List<ManufactureEntity> manufactureEntities = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_MANUFACTURE_BY_USER_ID)) {
            stmt.setLong(1, userId);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                manufactureEntities.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to get manufacture by user id", e);
        }
        return manufactureEntities.stream()
                .map(this::fromEntity)
                .findFirst();
    }

    @Override
    public List<ManufactureDto> findAll() throws DaoException {
        throw new UnsupportedOperationException("Unsupported findAll operation");
    }

    private ManufactureEntity parseResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        Long userId = resultSet.getLong("user_id");
        return ManufactureEntity.builder()
                .id(id)
                .name(name)
                .userId(userId)
                .build();
    }

    private ManufactureEntity fromDto(ManufactureDto manufactureDto) {
        return ManufactureEntity.builder()
                .id(manufactureDto.getId())
                .name(manufactureDto.getName())
                .userId(manufactureDto.getUserId())
                .build();
    }

    private ManufactureDto fromEntity(ManufactureEntity manufactureEntity) {
        return ManufactureDto.builder()
                .id(manufactureEntity.getId())
                .name(manufactureEntity.getName())
                .userId(manufactureEntity.getUserId())
                .build();
    }
}
