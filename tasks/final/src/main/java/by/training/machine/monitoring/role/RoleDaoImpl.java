package by.training.machine.monitoring.role;

import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.dao.ConnectionManager;
import by.training.machine.monitoring.dao.DaoException;
import by.training.machine.monitoring.entity.UserRole;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Bean
@AllArgsConstructor
public class RoleDaoImpl implements RoleDao {
    //language=PostgreSQL
    private static final String SELECT_DEFAULT_ROLES = "SELECT id FROM machine_monitoring.machine_monitoring_schema.role" +
            " WHERE default_role = TRUE";
    //language=PostgreSQL
    private static final String ASSIGN_ROLE = "INSERT INTO machine_monitoring.machine_monitoring_schema.user_account_role_relation" +
            " (user_account_id, role_id) VALUES (?, ?)";
    //language=PostgreSQL
    private static final String SELECT_USER_ROLES_BY_ID = "SELECT id, name, default_role FROM " +
            "machine_monitoring.machine_monitoring_schema.role JOIN machine_monitoring.machine_monitoring_schema.user_account_role_relation" +
            " ON role.id = user_account_role_relation.role_id WHERE user_account_id = ?";
    //language=PostgreSQL
    private static final String INSERT_USER_ROLE = "INSERT INTO machine_monitoring.machine_monitoring_schema.role (name, default_role) VALUES (?, ?)";
    //language=PostgreSQL
    private static final String UPDATE_USER_ROLE = "UPDATE machine_monitoring.machine_monitoring_schema.role SET name = ?, default_role = ? WHERE id = ?";
    //language=PostgreSQL
    private static final String DELETE_USER_ROLE = "DELETE FROM machine_monitoring.machine_monitoring_schema.role WHERE id = ?";
    //language=PostgreSQL
    private static final String SELECT_ROLE_BY_ID = "SELECT id, name, default_role FROM machine_monitoring.machine_monitoring_schema.role WHERE id = ?";
    //language=PostgreSQL
    private static final String SELECT_ALL_ROLES = "SELECT id, name, default_role FROM machine_monitoring.machine_monitoring_schema.role";
    //language=PostgreSQL
    private static final String DELETE_ASSIGN_ROLES = "DELETE FROM machine_monitoring.machine_monitoring_schema.user_account_role_relation WHERE user_account_id = ?";
    private ConnectionManager connectionManager;

    @Override
    public void assignDefaultRoles(Long userId) throws DaoException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_DEFAULT_ROLES)) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                long roleId = resultSet.getLong(1);
                assignRole(roleId, userId, connection);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to assign default roles", e);
        }
    }

    @Override
    public void assignRole(Long roleId, Long userId) throws DaoException {
        try (Connection connection = connectionManager.getConnection()) {
            this.assignRole(roleId, userId, connection);
        } catch (SQLException e) {
            throw new DaoException("Failed to assign role id" + roleId + " to userId " + userId);
        }
    }

    private void assignRole(Long roleId, Long userId, Connection connection) throws DaoException {
        try (PreparedStatement stmt = connection.prepareStatement(ASSIGN_ROLE)) {
            int i = 0;
            stmt.setLong(++i, userId);
            stmt.setLong(++i, roleId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to assign role", e);
        }
    }

    @Override
    public List<RoleDto> getUserRoles(Long userId) throws DaoException {
        List<RoleDto> roleDtoList = new ArrayList<>();
        try {
            try (Connection connection = connectionManager.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(SELECT_USER_ROLES_BY_ID)) {
                int i = 0;
                stmt.setLong(++i, userId);
                ResultSet resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    RoleDto roleDto = RoleDto.builder()
                            .id(resultSet.getLong(1))
                            .roleName(resultSet.getString(2))
                            .system(resultSet.getBoolean(3))
                            .build();
                    roleDtoList.add(roleDto);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to get user role", e);
        }
        return roleDtoList;
    }

    @Override
    public boolean deleteAssignRoles(Long userId) throws DaoException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(DELETE_ASSIGN_ROLES)) {
            stmt.setLong(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException | DaoException e) {
            throw new DaoException("Failed to delete assign roles", e);
        }
    }

    @Override
    public Long save(RoleDto roleDto) throws DaoException {
        UserRole userRole = fromDto(roleDto);
        try {
            try (Connection connection = connectionManager.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(INSERT_USER_ROLE, Statement.RETURN_GENERATED_KEYS)) {
                int i = 0;
                stmt.setString(++i, userRole.getRoleName());
                stmt.setBoolean(++i, userRole.isDefaultRole());
                stmt.executeUpdate();
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                while (generatedKeys.next()) {
                    userRole.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to save role", e);
        }
        return userRole.getId();
    }

    @Override
    public boolean update(RoleDto roleDto) throws DaoException {
        UserRole userRole = fromDto(roleDto);
        try {
            try (Connection connection = connectionManager.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(UPDATE_USER_ROLE)) {
                int i = 0;
                stmt.setString(++i, userRole.getRoleName());
                stmt.setBoolean(++i, userRole.isDefaultRole());
                stmt.setLong(++i, userRole.getId());
                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to update role", e);
        }
    }

    @Override
    public boolean delete(RoleDto roleDto) throws DaoException {
        UserRole userRole = fromDto(roleDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(DELETE_USER_ROLE)) {
            stmt.setLong(1, userRole.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to delete role", e);
        }
    }

    @Override
    public RoleDto getById(Long id) throws DaoException {
        List<UserRole> userRoles = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_ROLE_BY_ID)) {
            stmt.setLong(1, id);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                UserRole userRole = parseResultSer(resultSet);
                userRoles.add(userRole);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to get by id role", e);
        }
        return userRoles.stream()
                .map(this::fromEntity)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Failed to get role by id"));
    }

    @Override
    public List<RoleDto> findAll() throws DaoException {
        List<UserRole> userRoles = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_ROLES)) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                UserRole userRole = parseResultSer(resultSet);
                userRoles.add(userRole);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find all roles", e);
        }
        return userRoles.stream().map(this::fromEntity).collect(Collectors.toList());
    }

    private UserRole parseResultSer(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        Boolean isDefaultRole = resultSet.getBoolean("default_role");
        return UserRole.builder()
                .id(id)
                .roleName(name)
                .defaultRole(isDefaultRole)
                .build();
    }

    private UserRole fromDto(RoleDto roleDto) {
        return UserRole.builder()
                .id(roleDto.getId())
                .roleName(roleDto.getRoleName())
                .defaultRole(roleDto.isSystem())
                .build();
    }

    private RoleDto fromEntity(UserRole userRole) {
        return RoleDto.builder()
                .id(userRole.getId())
                .roleName(userRole.getRoleName())
                .system(userRole.isDefaultRole())
                .build();
    }
}
