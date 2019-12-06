package by.training.machine.monitoring.machine;

import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.dao.ConnectionManager;
import by.training.machine.monitoring.dao.DaoException;
import by.training.machine.monitoring.dao.DaoSqlException;
import by.training.machine.monitoring.entity.MachineLogEntity;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Bean
@AllArgsConstructor
public class MachineLogDaoImpl implements MachineLogDao {
    //language=PostgreSQL
    private static final String INSERT_MACHINE_LOG = "INSERT INTO machine_monitoring.machine_monitoring_schema.machine_log (date, fuel_level, oil_pressure, oil_level, coolant_temp, machine_id) VALUES (?, ?, ?, ?, ?, ?)";
    //language=PostgreSQL
    private static final String DELETE_MACHINE_LOG = "DELETE FROM machine_monitoring.machine_monitoring_schema.machine_log WHERE id = ?";
    //language=PostgreSQL
    private static final String UPDATE_MACHINE_LOG = "UPDATE machine_monitoring.machine_monitoring_schema.machine_log SET date = ?, fuel_level = ?, oil_pressure = ?, oil_level = ?, coolant_temp = ?, machine_id = ? WHERE id = ?";
    //language=PostgreSQL
    private static final String SELECT_MACHINE_LOG_BY_ID = "SELECT id, date, fuel_level, oil_pressure, oil_level, coolant_temp, machine_id FROM machine_monitoring.machine_monitoring_schema.machine_log WHERE id = ?";
    //language=PostgreSQL
    private static final String SELECT_MACHINE_LOG_BY_MACHINE_ID = "SELECT id, date, fuel_level, oil_pressure, oil_level, coolant_temp, machine_id FROM machine_monitoring.machine_monitoring_schema.machine_log WHERE machine_id = ?";
    //language=PostgreSQL
    private static final String SELECT_ALL_MACHINE_LOGS = "SELECT id, date, fuel_level, oil_pressure, oil_level, coolant_temp, machine_id FROM machine_monitoring.machine_monitoring_schema.machine_log";
    private ConnectionManager connectionManager;

    @Override
    public List<MachineLogDto> getMachineLogByMachineId(Long machineId) throws DaoException {
        List<MachineLogEntity> machineLogEntities = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_MACHINE_LOG_BY_MACHINE_ID)) {
            stmt.setLong(1, machineId);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                machineLogEntities.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to get machine logs by id", e);
        }
        return machineLogEntities.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Long save(MachineLogDto machineLogDto) throws DaoException {
        MachineLogEntity machineLogEntity = fromDto(machineLogDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(INSERT_MACHINE_LOG, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            stmt.setDate(++i, new java.sql.Date(machineLogEntity.getDate().getTime()));
            stmt.setDouble(++i, machineLogEntity.getFuelLevel());
            stmt.setDouble(++i, machineLogEntity.getOilPressure());
            stmt.setDouble(++i, machineLogEntity.getOilLevel());
            stmt.setDouble(++i, machineLogEntity.getCoolantTemp());
            stmt.setLong(++i, machineLogEntity.getMachineId());
            stmt.executeUpdate();
            ResultSet resultSet = stmt.getGeneratedKeys();
            while (resultSet.next()) {
                machineLogEntity.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to save machine log", e);
        }
        return machineLogEntity.getId();
    }

    @Override
    public boolean update(MachineLogDto machineLogDto) throws DaoException {
        MachineLogEntity machineLogEntity = fromDto(machineLogDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(UPDATE_MACHINE_LOG)) {
            int i = 0;
            stmt.setDate(++i, new java.sql.Date(machineLogEntity.getDate().getTime()));
            stmt.setDouble(++i, machineLogEntity.getFuelLevel());
            stmt.setDouble(++i, machineLogEntity.getOilPressure());
            stmt.setDouble(++i, machineLogEntity.getOilLevel());
            stmt.setDouble(++i, machineLogEntity.getCoolantTemp());
            stmt.setLong(++i, machineLogEntity.getMachineId());
            stmt.setLong(++i, machineLogEntity.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to update machine log", e);
        }
    }

    @Override
    public boolean delete(MachineLogDto machineLogDto) throws DaoException {
        MachineLogEntity machineLogEntity = fromDto(machineLogDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(DELETE_MACHINE_LOG)) {
            stmt.setLong(1, machineLogEntity.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to delete machine log", e);
        }
    }

    @Override
    public MachineLogDto getById(Long id) throws DaoException {
        List<MachineLogEntity> machineLogEntities = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_MACHINE_LOG_BY_ID)) {
            stmt.setLong(1, id);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                machineLogEntities.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to get machine log by id", e);
        }
        return machineLogEntities.stream()
                .map(this::fromEntity)
                .findFirst()
                .orElseThrow(() -> new DaoException("Failed to get machine log by id"));
    }

    @Override
    public List<MachineLogDto> findAll() throws DaoException {
        List<MachineLogEntity> machineLogEntities = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_MACHINE_LOGS)) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                MachineLogEntity machineLogEntity = parseResultSet(resultSet);
                machineLogEntities.add(machineLogEntity);
            }
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to find all models", e);
        }
        return machineLogEntities.stream().map(this::fromEntity).collect(Collectors.toList());
    }

    private MachineLogEntity parseResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        Date date = resultSet.getDate("date");
        Double fuelLevel = resultSet.getDouble("fuel_level");
        Double oilPressure = resultSet.getDouble("oil_pressure");
        Double oilLevel = resultSet.getDouble("oil_level");
        Double coolantTemp = resultSet.getDouble("coolant_temp");
        Long machineId = resultSet.getLong("machine_id");
        return MachineLogEntity.builder()
                .id(id)
                .date(date)
                .fuelLevel(fuelLevel)
                .oilPressure(oilPressure)
                .oilLevel(oilLevel)
                .coolantTemp(coolantTemp)
                .machineId(machineId)
                .build();
    }

    private MachineLogEntity fromDto(MachineLogDto machineLogDto) {
        return MachineLogEntity.builder()
                .id(machineLogDto.getId())
                .date(machineLogDto.getDate())
                .fuelLevel(machineLogDto.getFuelLevel())
                .oilPressure(machineLogDto.getOilPressure())
                .oilLevel(machineLogDto.getOilLevel())
                .coolantTemp(machineLogDto.getCoolantTemp())
                .machineId(machineLogDto.getMachineId())
                .build();
    }

    private MachineLogDto fromEntity(MachineLogEntity entity) {
        return MachineLogDto.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .fuelLevel(entity.getFuelLevel())
                .oilPressure(entity.getOilPressure())
                .oilLevel(entity.getOilLevel())
                .coolantTemp(entity.getCoolantTemp())
                .machineId(entity.getMachineId())
                .build();
    }
}
