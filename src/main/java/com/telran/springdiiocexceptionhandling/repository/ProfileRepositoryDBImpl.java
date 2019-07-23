package com.telran.springdiiocexceptionhandling.repository;

import com.telran.springdiiocexceptionhandling.repository.entity.ProfileEntity;
import com.telran.springdiiocexceptionhandling.repository.exception.IllegalIdException;
import com.telran.springdiiocexceptionhandling.repository.exception.RepositoryException;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ProfileRepositoryDBImpl implements ProfileRepository{

    private DataSource source;

    public ProfileRepositoryDBImpl(DataSource source) {
        this.source = source;
    }

    @Override
    public boolean addProfile(ProfileEntity profile) {
        try (Connection connection = source.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO profile VALUES (?, ?, ?, ?)");
            ps.setString(1, profile.getFirstName());
            ps.setString(2, profile.getLastName());
            ps.setTimestamp(3, profile.getBDay());
            ps.setString(4, profile.getOwner());
            return ps.execute();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    @Override
    public boolean updProfile(ProfileEntity profile) {
        try (Connection connection = source.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("UPDATE profile SET first_name = ?, last_name = ?, b_day = ? WHERE username = ?");
            ps.setString(1, profile.getFirstName());
            ps.setString(2, profile.getLastName());
            ps.setTimestamp(3, profile.getBDay());
            ps.setString(4, profile.getOwner());
            return ps.execute(); //TODO need to check
        }
        catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    @Override
    public ProfileEntity getProfileByOwner(String owner) {
        try (Connection connection = source.getConnection()){
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM profile WHERE username = ?");
            ps.setString(1, owner);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return ProfileEntity.builder()
                        .firstName(rs.getString("first_name"))
                        .lastName(rs.getString("last_name"))
                        .bDay(rs.getTimestamp("b_day"))
                        .owner(rs.getString("username"))
                        .build();
            }
            throw new IllegalIdException(String.format("Profile of %s not found", owner));
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }
}
