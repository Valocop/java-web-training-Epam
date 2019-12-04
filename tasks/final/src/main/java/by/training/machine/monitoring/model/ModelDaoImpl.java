package by.training.machine.monitoring.model;

import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.dao.ConnectionManager;
import by.training.machine.monitoring.dao.DaoException;
import by.training.machine.monitoring.dao.DaoSqlException;
import by.training.machine.monitoring.entity.ModelEntity;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Bean
@AllArgsConstructor
public class ModelDaoImpl implements ModelDao {
    //language=PostgreSQL
    private static final String INSERT_MODEL = "INSERT INTO machine_monitoring.machine_monitoring_schema.machine_model (name, release_date, picture, description, manufacture_id) VALUES (?, ?, ?, ?, ?)";
    //language=PostgreSQL
    private static final String DELETE_MODEL = "DELETE FROM machine_monitoring.machine_monitoring_schema.machine_model WHERE id = ?";
    //language=PostgreSQL
    private static final String UPDATE_MODEL = "UPDATE machine_monitoring.machine_monitoring_schema.machine_model SET name = ?, release_date = ?, picture = ?, description = ?, manufacture_id = ? WHERE id = ?";
    //language=PostgreSQL
    private static final String SELECT_MODEL_BY_ID = "SELECT id, name, release_date, picture, description, manufacture_id FROM machine_monitoring.machine_monitoring_schema.machine_model WHERE id = ?";
    //language=PostgreSQL
    private static final String SELECT_ALL_MODELS = "SELECT id, name, release_date, picture, description, manufacture_id FROM machine_monitoring.machine_monitoring_schema.machine_model";
    private ConnectionManager connectionManager;

    @Override
    public Long save(ModelDto modelDto) throws DaoException {
        ModelEntity modelEntity = ModelUtil.fromDto(modelDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(INSERT_MODEL, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            stmt.setString(++i, modelEntity.getName());
            stmt.setDate(++i, new Date(modelEntity.getReleaseDate().getTime()));
            stmt.setBytes(++i, modelEntity.getPicture());
            stmt.setString(++i, modelEntity.getDescription());
            stmt.setLong(++i, modelEntity.getManufactureId());
            stmt.executeUpdate();
            ResultSet resultSet = stmt.getGeneratedKeys();
            while (resultSet.next()) {
                modelEntity.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to save model", e);
        }
        return modelEntity.getId();
    }

    @Override
    public boolean update(ModelDto modelDto) throws DaoException {
        ModelEntity modelEntity = ModelUtil.fromDto(modelDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(UPDATE_MODEL)) {
            int i = 0;
            stmt.setString(++i, modelEntity.getName());
            stmt.setDate(++i, new Date(modelEntity.getReleaseDate().getTime()));
            stmt.setBytes(++i, modelEntity.getPicture());
            stmt.setString(++i, modelEntity.getDescription());
            stmt.setLong(++i, modelEntity.getManufactureId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to update model", e);
        }
    }

    @Override
    public boolean delete(ModelDto modelDto) throws DaoException {
        ModelEntity modelEntity = ModelUtil.fromDto(modelDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(DELETE_MODEL)) {
            stmt.setLong(1, modelEntity.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to delete model", e);
        }
    }

    @Override
    public ModelDto getById(Long id) throws DaoException {
        List<ModelEntity> modelEntities = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_MODEL_BY_ID)) {
            stmt.setLong(1, id);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                ModelEntity modelEntity = parseResultSet(resultSet);
                modelEntities.add(modelEntity);
            }
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to get model by id", e);
        }
        return modelEntities.stream()
                .map(ModelUtil::fromModelEntity)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Failed to get model by id"));
    }

    @Override
    public List<ModelDto> findAll() throws DaoException {
        List<ModelEntity> modelEntities = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_MODELS)) {
                ResultSet resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    ModelEntity modelEntity = parseResultSet(resultSet);
                    modelEntities.add(modelEntity);
                }
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to find all models", e);
        }
        return modelEntities.stream().map(ModelUtil::fromModelEntity).collect(Collectors.toList());
    }

    private ModelEntity parseResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        java.util.Date date = new java.util.Date(resultSet.getDate("release_date").getTime());
        byte[] picture = resultSet.getBytes("picture");
        Long userId = resultSet.getLong("user_id");
        String description = resultSet.getString("description");
        return ModelEntity.builder()
                .id(id)
                .name(name)
                .releaseDate(date)
                .picture(picture)
                .manufactureId(userId)
                .description(description)
                .build();
    }
}
