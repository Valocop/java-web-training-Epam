package by.training.machine.monitoring.user;

import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.dao.ConnectionManager;
import by.training.machine.monitoring.dao.DaoException;
import by.training.machine.monitoring.dao.DaoSqlException;
import by.training.machine.monitoring.entity.UserEntity;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Bean
@AllArgsConstructor
public class UserDaoImpl implements UserDao {
    private static final String SELECT_ALL_QUERY = "select id, login, password, email, name, address, tel, picture from machine_monitoring_schema.user_account";
    private static final String SELECT_BY_ID_QUERY = "select id, login, password, email, name, address, tel, picture from machine_monitoring_schema.user_account where id = ?";
    private static final String SELECT_BY_LOGIN_QUERY = "select id, login, password, email, name, address, tel, picture from machine_monitoring_schema.user_account where login = ?";
    private static final String INSERT_QUERY = "insert into machine_monitoring_schema.user_account (login, password, email, name, address, tel, picture) values (?,?,?,?,?,?,?)";
    private static final String UPDATE_QUERY = "update machine_monitoring_schema.user_account set email=?, name=?, address=?, tel=?, picture=? where id = ?";
    private static final String DELETE_QUERY = "delete from machine_monitoring_schema.user_account where id = ?";
    private static final String SELECT_USERS_BY_MACHINE_ID = "SELECT id, login, password, email, name, address, tel, picture FROM machine_monitoring_schema.user_account " +
            "INNER JOIN machine_monitoring_schema.user_machine ON user_account.id = user_machine.user_id WHERE machine_id = ?";
    //language=PostgreSQL
    private static final String ASSIGN_USER_MACHINE = "INSERT INTO machine_monitoring_schema.user_machine (user_id, machine_id) VALUES (?,?)";
    //language=PostgreSQL
    private static final String DELETE_ASSIGN_USER_MACHINE = "delete from machine_monitoring_schema.user_machine where user_id = ?";
    //language=PostgreSQL
    private static final String DELETE_ASSIGN_USER_MACHINE_BY_ID = "delete from machine_monitoring_schema.user_machine where user_id = ? AND machine_id = ?";
    //language=PostgreSQL
    private static final String SELECT_ASSIGN_USER_MACHINE = "SELECT user_id, machine_id FROM machine_monitoring_schema.user_machine where user_id = ? AND machine_id = ?";
    private ConnectionManager connectionManager;

    @Override
    public Optional<UserDto> findByLogin(String login) throws DaoException {
        List<UserEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_BY_LOGIN_QUERY)) {
            selectStmt.setString(1, login);
            ResultSet resultSet = selectStmt.executeQuery();
            while (resultSet.next()) {
                UserEntity entity = parseResultSet(resultSet);
                result.add(entity);
            }
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
        return result.stream().map(this::fromEntity).findFirst();
    }

    @Override
    public List<UserDto> getUsersByMachineId(Long machineId) throws DaoException {
        List<UserEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_USERS_BY_MACHINE_ID)) {
            selectStmt.setLong(1, machineId);
            ResultSet resultSet = selectStmt.executeQuery();
            while (resultSet.next()) {
                UserEntity entity = parseResultSet(resultSet);
                result.add(entity);
            }
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
        return result.stream().map(this::fromEntity).collect(Collectors.toList());
    }

    @Override
    public boolean assignUserMachine(Long userId, Long machineId) throws DaoException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement insertStmt = connection.prepareStatement(ASSIGN_USER_MACHINE)) {
            int i = 0;
            insertStmt.setLong(++i, userId);
            insertStmt.setLong(++i, machineId);
            return insertStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
    }

    @Override
    public boolean deleteAssignUserMachine(Long userId) throws DaoException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(DELETE_ASSIGN_USER_MACHINE)) {
            updateStmt.setLong(1, userId);
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
    }

    @Override
    public boolean deleteAssignUserMachine(Long userId, Long machineId) throws DaoException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(DELETE_ASSIGN_USER_MACHINE_BY_ID)) {
            updateStmt.setLong(1, userId);
            updateStmt.setLong(2, machineId);
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
    }

    @Override
    public boolean assignUserMachineIsPresent(Long userId, Long machineId) throws DaoException {
        List<Long> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_ASSIGN_USER_MACHINE)) {
            int i = 0;
            stmt.setLong(++i, userId);
            stmt.setLong(++i, machineId);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                long user_id = resultSet.getLong("user_id");
                result.add(user_id);
            }
            return !result.isEmpty();
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
    }

    @Override
    public Long save(UserDto userDto) throws DaoException {
        UserEntity entity = fromDto(userDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement insertStmt = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            insertStmt.setString(++i, entity.getLogin());
            insertStmt.setString(++i, entity.getPassword());
            insertStmt.setString(++i, entity.getEmail());
            insertStmt.setString(++i, entity.getName());
            insertStmt.setString(++i, entity.getAddress());
            insertStmt.setString(++i, entity.getTel());
            insertStmt.setBytes(++i, entity.getPicture());
            insertStmt.executeUpdate();
            ResultSet generatedKeys = insertStmt.getGeneratedKeys();
            while (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
        return entity.getId();
    }

    @Override
    public boolean update(UserDto userDto) throws DaoException {
        UserEntity entity = fromDto(userDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(UPDATE_QUERY)) {
            int i = 0;
            updateStmt.setString(++i, entity.getEmail());
            updateStmt.setString(++i, entity.getName());
            updateStmt.setString(++i, entity.getAddress());
            updateStmt.setString(++i, entity.getTel());
            updateStmt.setBytes(++i, entity.getPicture());
            updateStmt.setLong(++i, entity.getId());
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
    }

    @Override
    public boolean delete(UserDto userDto) throws DaoException {
        UserEntity entity = fromDto(userDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(DELETE_QUERY)) {
            updateStmt.setLong(1, entity.getId());
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
    }

    @Override
    public UserDto getById(Long id) throws DaoException {
        List<UserEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            selectStmt.setLong(1, id);
            ResultSet resultSet = selectStmt.executeQuery();
            while (resultSet.next()) {
                UserEntity entity = parseResultSet(resultSet);
                result.add(entity);
            }
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
        return result.stream().map(this::fromEntity).findFirst().orElseThrow(() -> new IllegalArgumentException("Entity not found with given id: " + id));
    }

    @Override
    public List<UserDto> findAll() throws DaoException {
        List<UserEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = selectStmt.executeQuery();
            while (resultSet.next()) {
                UserEntity entity = parseResultSet(resultSet);
                result.add(entity);
            }
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
        return result.stream().map(this::fromEntity).collect(Collectors.toList());
    }

    private UserEntity parseResultSet(ResultSet resultSet) throws SQLException {
        long entityId = resultSet.getLong("id");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        String email = resultSet.getString("email");
        String name = resultSet.getString("name");
        String address = resultSet.getString("address");
        String tel = resultSet.getString("tel");
        byte[] picture = resultSet.getBytes("picture");
        return UserEntity.builder()
                .id(entityId)
                .login(login)
                .password(password)
                .email(email)
                .name(name)
                .address(address)
                .tel(tel)
                .picture(picture)
                .build();
    }

    private UserEntity fromDto(UserDto dto) {
        UserEntity entity = new UserEntity();
        entity.setId(dto.getId());
        entity.setLogin(dto.getLogin());
        entity.setPassword(dto.getPassword());
        entity.setEmail(dto.getEmail());
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setTel(dto.getTel());
        entity.setPicture(dto.getPicture());
        return entity;
    }

    private UserDto fromEntity(UserEntity entity) {
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setLogin(entity.getLogin());
        dto.setPassword(entity.getPassword());
        dto.setEmail(entity.getEmail());
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());
        dto.setTel(entity.getTel());
        dto.setPicture(entity.getPicture());
        return dto;
    }
}
