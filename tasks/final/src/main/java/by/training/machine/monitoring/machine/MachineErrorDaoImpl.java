package by.training.machine.monitoring.machine;

import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.dao.ConnectionManager;
import by.training.machine.monitoring.dao.DaoException;
import by.training.machine.monitoring.dao.DaoSqlException;
import by.training.machine.monitoring.entity.MachineErrorEntity;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Bean
@AllArgsConstructor
public class MachineErrorDaoImpl implements MachineErrorDao {
    //language=PostgreSQL
    private static final String INSERT_MACHINE_ERROR = "INSERT INTO machine_monitoring.machine_monitoring_schema.machine_error (date, error_code, machine_id) VALUES (?, ?, ?)";
    //language=PostgreSQL
    private static final String DELETE_MACHINE_ERROR = "DELETE FROM machine_monitoring.machine_monitoring_schema.machine_error WHERE id = ?";
    //language=PostgreSQL
    private static final String UPDATE_MACHINE_ERROR = "UPDATE machine_monitoring.machine_monitoring_schema.machine_error SET date = ?, error_code = ?, machine_id = ? WHERE id = ?";
    //language=PostgreSQL
    private static final String SELECT_MACHINE_ERROR_BY_ID = "SELECT id, date, error_code, machine_id FROM machine_monitoring.machine_monitoring_schema.machine_error WHERE id = ?";
    //language=PostgreSQL
    private static final String SELECT_MACHINE_ERROR_BY_MACHINE_ID = "SELECT id, date, error_code, machine_id FROM machine_monitoring.machine_monitoring_schema.machine_error WHERE machine_id = ?";
    //language=PostgreSQL
    private static final String SELECT_ALL_MACHINE_ERRORS = "SELECT id, date, error_code, machine_id FROM machine_monitoring.machine_monitoring_schema.machine_error";
    private ConnectionManager connectionManager;

    @Override
    public List<MachineErrorDto> getErrorsByMachineId(Long machineId) throws DaoException {
        List<MachineErrorEntity> machineErrorEntities = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_MACHINE_ERROR_BY_MACHINE_ID)) {
            stmt.setLong(1, machineId);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                machineErrorEntities.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to get machine error by machine id", e);
        }
        return machineErrorEntities.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Long save(MachineErrorDto machineErrorDto) throws DaoException {
        MachineErrorEntity machineErrorEntity = fromDto(machineErrorDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(INSERT_MACHINE_ERROR, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            stmt.setDate(++i, new java.sql.Date(machineErrorEntity.getDate().getTime()));
            stmt.setString(++i, machineErrorEntity.getErrorCode());
            stmt.setLong(++i, machineErrorEntity.getMachineId());
            stmt.executeUpdate();
            ResultSet resultSet = stmt.getGeneratedKeys();
            while (resultSet.next()) {
                machineErrorEntity.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to save machine error", e);
        }
        return machineErrorEntity.getId();
    }

    @Override
    public boolean update(MachineErrorDto machineErrorDto) throws DaoException {
        MachineErrorEntity machineErrorEntity = fromDto(machineErrorDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(UPDATE_MACHINE_ERROR)) {
            int i = 0;
            stmt.setDate(++i, new java.sql.Date(machineErrorEntity.getDate().getTime()));
            stmt.setString(++i, machineErrorEntity.getErrorCode());
            stmt.setLong(++i, machineErrorEntity.getMachineId());
            stmt.setLong(++i, machineErrorEntity.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to update machine error", e);
        }
    }

    @Override
    public boolean delete(MachineErrorDto machineErrorDto) throws DaoException {
        MachineErrorEntity machineErrorEntity = fromDto(machineErrorDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(DELETE_MACHINE_ERROR)) {
            stmt.setLong(1, machineErrorEntity.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to delete machine error", e);
        }
    }

    @Override
    public MachineErrorDto getById(Long id) throws DaoException {
        List<MachineErrorEntity> machineErrorEntities = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_MACHINE_ERROR_BY_ID)) {
            stmt.setLong(1, id);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                machineErrorEntities.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to get machine error by id", e);
        }
        return machineErrorEntities.stream()
                .map(this::fromEntity)
                .findFirst()
                .orElseThrow(() -> new DaoException("Failed to get machine error by id"));
    }

    @Override
    public List<MachineErrorDto> findAll() throws DaoException {
        List<MachineErrorEntity> machineErrorEntities = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_MACHINE_ERRORS)) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                MachineErrorEntity machineErrorEntity = parseResultSet(resultSet);
                machineErrorEntities.add(machineErrorEntity);
            }
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to find all errors", e);
        }
        return machineErrorEntities.stream().map(this::fromEntity).collect(Collectors.toList());
    }

    private MachineErrorEntity parseResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        Date date = resultSet.getDate("date");
        String errorCode = resultSet.getString("error_code");
        Long machineId = resultSet.getLong("machine_id");
        return MachineErrorEntity.builder()
                .id(id)
                .date(date)
                .errorCode(errorCode)
                .machineId(machineId)
                .build();
    }

    private MachineErrorEntity fromDto(MachineErrorDto dto) {
        return MachineErrorEntity.builder()
                .id(dto.getId())
                .date(dto.getDate())
                .errorCode(dto.getErrorCode())
                .machineId(dto.getMachineId())
                .build();
    }

    private MachineErrorDto fromEntity(MachineErrorEntity entity) {
        return MachineErrorDto.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .errorCode(entity.getErrorCode())
                .machineId(entity.getMachineId())
                .build();
    }
}
