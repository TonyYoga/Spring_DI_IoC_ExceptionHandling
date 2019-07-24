package com.telran.springdiiocexceptionhandling.repository;

import com.telran.springdiiocexceptionhandling.repository.entity.UserEntity;
import com.telran.springdiiocexceptionhandling.repository.exception.RepositoryException;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class UserRepositoryDBImpl implements UserRepository {

    private DataSource source;

    public UserRepositoryDBImpl(DataSource source) {
        this.source = source;
    }

    @Override
    public boolean addUser(UserEntity userEntity) {
        try (Connection connection = source.getConnection()){
            connection.setAutoCommit(false);
            PreparedStatement addUser = connection.prepareStatement("INSERT INTO users VALUES (?, ?)");
            addUser.setString(1, userEntity.getEmail());
            addUser.setString(2, userEntity.getPassword());
            addUser.execute();
            PreparedStatement addRole = connection.prepareStatement("INSERT INTO users_roles VALUES (?, (SELECT id FROM roles WHERE role_name =?))");
            addRole.setString(1, userEntity.getEmail());
            addRole.setString(2, "USER");
            addRole.execute();
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        try (Connection connection = source.getConnection()){
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return UserEntity.builder()
                        .email(rs.getString("username"))
                        .password(rs.getString("password"))
                        .build();
            }
            throw new RepositoryException(String.format("User %s not found!", email));

        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    @Override
    public String[] getRoles(String email) {
        ArrayList<String> res = new ArrayList<>();
        try (Connection connection = source.getConnection()){
            PreparedStatement ps = connection.prepareStatement("SELECT r.role_name FROM users u " +
                    "JOIN users_roles ur ON  ur.username= u.username " +
                    "JOIN roles r ON r.id = ur.roles_id WHERE u.username = ?");
            ps.setString(1, email);
            if (ps.execute()) {
                ResultSet rs = ps.getResultSet();
                while (rs.next()) {
                    res.add(rs.getString("role_name"));
                }
                return res.toArray(new String[0]);
            }
            throw new RepositoryException(String.format("User %s not found!", email));
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }
}
