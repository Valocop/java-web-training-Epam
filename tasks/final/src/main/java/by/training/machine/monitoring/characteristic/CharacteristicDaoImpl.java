package by.training.machine.monitoring.characteristic;

import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.dao.ConnectionManager;
import by.training.machine.monitoring.dao.DaoException;
import by.training.machine.monitoring.dao.DaoSqlException;
import by.training.machine.monitoring.entity.CharacteristicEntity;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Bean
@AllArgsConstructor
public class CharacteristicDaoImpl implements CharacteristicDao {
    //language=PostgreSQL
    private static final String INSERT_CHARACTERISTIC = "INSERT INTO machine_monitoring.machine_monitoring_schema.characteristic" +
            " (price, power, fuel_type, engine_volume, transmission, manufacturer_id) VALUES (?, ?, ?, ?, ?, ?)";
    //language=PostgreSQL
    private static final String DELETE_CHARACTERISTIC = "DELETE FROM machine_monitoring.machine_monitoring_schema.characteristic WHERE  id = ?";
    //language=PostgreSQL
    private static final String UPDATE_CHARACTERISTIC = "UPDATE machine_monitoring.machine_monitoring_schema.characteristic " +
            "SET price = ?, power = ?, fuel_type = ?, engine_volume = ?, transmission = ?, manufacturer_id = ? WHERE id = ?";
    //language=PostgreSQL
    private static final String SELECT_CHARACTERISTIC_BY_ID = "SELECT id, price, power, fuel_type, engine_volume, transmission, manufacturer_id " +
            "FROM machine_monitoring.machine_monitoring_schema.characteristic WHERE  id = ?";
    //language=PostgreSQL
    private static final String SELECT_CHARACTERISTIC_BY_MANUFACTURE_ID = "SELECT id, price, power, fuel_type, engine_volume, transmission, manufacturer_id " +
            "FROM machine_monitoring.machine_monitoring_schema.characteristic WHERE  manufacturer_id = ?";
    //language=PostgreSQL
    private static final String SELECT_ALL_CHARACTERISTICS = "SELECT id, price, power, fuel_type, engine_volume, transmission, manufacturer_id" +
            " FROM machine_monitoring.machine_monitoring_schema.characteristic";
    private ConnectionManager connectionManager;

    @Override
    public Long save(CharacteristicDto characteristicDto) throws DaoException {
        CharacteristicEntity characteristicEntity = fromDto(characteristicDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(INSERT_CHARACTERISTIC, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            stmt.setDouble(++i, characteristicEntity.getPrice());
            stmt.setString(++i, characteristicEntity.getPower());
            stmt.setString(++i, characteristicEntity.getFuelType());
            stmt.setString(++i, characteristicEntity.getEngineVolume());
            stmt.setString(++i, characteristicEntity.getTransmission());
            stmt.setLong(++i, characteristicEntity.getManufactureId());
            stmt.executeUpdate();
            ResultSet resultSet = stmt.getGeneratedKeys();
            while (resultSet.next()) {
                characteristicEntity.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to save characteristic", e);
        }
        return characteristicEntity.getId();
    }

    @Override
    public boolean update(CharacteristicDto characteristicDto) throws DaoException {
        CharacteristicEntity characteristicEntity = fromDto(characteristicDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(UPDATE_CHARACTERISTIC)) {
            int i = 0;
            stmt.setDouble(++i, characteristicEntity.getPrice());
            stmt.setString(++i, characteristicEntity.getPower());
            stmt.setString(++i, characteristicEntity.getFuelType());
            stmt.setString(++i, characteristicEntity.getEngineVolume());
            stmt.setString(++i, characteristicEntity.getTransmission());
            stmt.setLong(++i, characteristicEntity.getManufactureId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to update characteristic", e);
        }
    }

    @Override
    public boolean delete(CharacteristicDto characteristicDto) throws DaoException {
        CharacteristicEntity characteristicEntity = fromDto(characteristicDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(DELETE_CHARACTERISTIC)) {
            stmt.setLong(1, characteristicEntity.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to delete characteristic", e);
        }
    }

    @Override
    public CharacteristicDto getById(Long id) throws DaoException {
        List<CharacteristicEntity> characteristicEntities = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_CHARACTERISTIC_BY_ID)) {
            stmt.setLong(1, id);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                CharacteristicEntity characteristicEntity = parseResultSet(resultSet);
                characteristicEntities.add(characteristicEntity);
            }
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to get characteristic by id", e);
        }
        return characteristicEntities.stream()
                .map(CharacteristicDaoImpl::fromEntity)
                .findFirst()
                .orElseThrow(() -> new DaoException("Failed to get characteristic by id. Not found"));
    }

    @Override
    public List<CharacteristicDto> findAll() throws DaoException {
        List<CharacteristicEntity> characteristicEntities = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_CHARACTERISTICS)) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                CharacteristicEntity characteristicEntity = parseResultSet(resultSet);
                characteristicEntities.add(characteristicEntity);
            }
        } catch (SQLException e) {
            throw new DaoSqlException("Failed to get all characteristics", e);
        }
        return characteristicEntities.stream().map(CharacteristicDaoImpl::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<CharacteristicDto> getByManufactureId(Long manufactureId) throws DaoException {
        List<CharacteristicEntity> characteristicEntities = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_CHARACTERISTIC_BY_MANUFACTURE_ID)) {
            stmt.setLong(1, manufactureId);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                CharacteristicEntity characteristicEntity = parseResultSet(resultSet);
                characteristicEntities.add(characteristicEntity);
            }
        } catch (SQLException | DaoException e) {
            throw new DaoException("Failed to get characteristics by manufacture id", e);
        }
        return characteristicEntities.stream().map(CharacteristicDaoImpl::fromEntity).collect(Collectors.toList());
    }

    private CharacteristicEntity fromDto(CharacteristicDto characteristicDto) {
        return CharacteristicEntity.builder()
                .id(characteristicDto.getId())
                .price(characteristicDto.getPrice())
                .power(characteristicDto.getPower())
                .fuelType(characteristicDto.getFuelType())
                .engineVolume(characteristicDto.getEngineVolume())
                .transmission(characteristicDto.getTransmission())
                .manufactureId(characteristicDto.getManufactureId())
                .build();
    }

    public static CharacteristicDto fromEntity(CharacteristicEntity entity) {
        return CharacteristicDto.builder()
                .id(entity.getId())
                .price(entity.getPrice())
                .power(entity.getPower())
                .fuelType(entity.getFuelType())
                .engineVolume(entity.getEngineVolume())
                .transmission(entity.getTransmission())
                .manufactureId(entity.getManufactureId())
                .build();
    }

    private CharacteristicEntity parseResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        Double price = resultSet.getDouble("price");
        String power = resultSet.getString("power");
        String fuelType = resultSet.getString("fuel_type");
        String engineVolume = resultSet.getString("engine_volume");
        String transmission = resultSet.getString("transmission");
        Long manufactureId = resultSet.getLong("manufacturer_id");
        return CharacteristicEntity.builder()
                .id(id)
                .price(price)
                .power(power)
                .fuelType(fuelType)
                .engineVolume(engineVolume)
                .transmission(transmission)
                .manufactureId(manufactureId)
                .build();
    }
}
