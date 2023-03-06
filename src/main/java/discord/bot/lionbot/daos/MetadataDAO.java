package discord.bot.lionbot.daos;

import discord.bot.lionbot.database.Database;
import discord.bot.lionbot.model.Metadata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class MetadataDAO implements DAO<Metadata>{

    private final Connection connection;

    public MetadataDAO(Database database) {
        connection = database.getConnection();
    }

    @Override
    public void save(Metadata data) {
        String sql = "INSERT INTO metadados(fullpath, name) VALUES(?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, data.getFullpath());
            preparedStatement.setString(2, data.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public Metadata get(int id) {
        String sql = "SELECT rowid, name, fullpath FROM metadados WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Metadata metadata = new Metadata(resultSet.getString("fullpath"), resultSet.getString("name"));
            metadata.setId(resultSet.getInt("rowid"));
            return metadata;
        }catch (SQLException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public Collection<Metadata> listAll() {
        return null;
    }
}
