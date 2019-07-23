package com.telran.springdiiocexceptionhandling.repository;

import com.telran.springdiiocexceptionhandling.repository.entity.CommentEntity;
import com.telran.springdiiocexceptionhandling.repository.entity.TopicEntity;
import com.telran.springdiiocexceptionhandling.repository.exception.IllegalIdException;
import com.telran.springdiiocexceptionhandling.repository.exception.RepositoryException;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

@Repository
public class TopicRepositoryDBImpl implements TopicRepository {
    private DataSource source;

    public TopicRepositoryDBImpl(DataSource source) {
        this.source = source;
    }

    @Override
    public int addTopic(TopicEntity topic) {
        //TODO make it simple with one query
        try (Connection connection = source.getConnection()) {
            PreparedStatement psTopic = connection.prepareStatement("INSERT INTO posts (content, date_time, username) VALUES (?, ?, ?)");
            psTopic.setString(1, topic.getContent());
            psTopic.setTimestamp(2, topic.getDate());
            psTopic.setString(3, topic.getOwner());
            psTopic.execute();
            PreparedStatement getId = connection.prepareStatement("SELECT id FROM posts WHERE content = ? AND date_time = ? AND username = ?");
            getId.setString(1, topic.getContent());
            getId.setTimestamp(2, topic.getDate());
            getId.setString(3, topic.getOwner());
            ResultSet rs = getId.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
        return -1;
    }

    @Override
    public boolean removeTopic(int id) {
        try (Connection connection = source.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM posts WHERE id = ?");
            ps.setInt(1, id);
            if (ps.execute()) {
                return true;
            }
            throw new IllegalIdException(String.format("Post with id %d not found"));
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    @Override
    public Iterable<TopicEntity> getAllTopics() {
        Deque<TopicEntity> res = new LinkedList<>();
        try (Connection connection = source.getConnection()) {
            PreparedStatement getPost = connection.prepareStatement("SELECT * FROM posts p JOIN comments c on p.id = c.post_id");
            ResultSet rs = getPost.executeQuery();
            int postId = -1;
            TopicEntity tp = null;
            while (rs.next()) {
                if (!res.isEmpty()) {
                    postId = res.peek().getId();
                }
                if (postId != rs.getInt("id")) {
                    tp = TopicEntity.builder()
                            .id(rs.getInt("p.id"))
                            .content(rs.getString("content"))
                            .owner(rs.getString("username"))
                            .date(rs.getTimestamp("p.date_time"))
                            .build();
                    CommentEntity comment = CommentEntity.builder()
                            .id(rs.getInt("c.id"))
                            .message(rs.getString("comment"))
                            .date(rs.getTimestamp("c.date_time"))
                            .owner(rs.getString("c.username"))
                            .build();
                    List<CommentEntity> comments = new ArrayList<>();
                    comments.add(comment);
                    tp.setComments(comments);
                    res.addFirst(tp);
                } else {
                    tp = res.peek();
                    CommentEntity comment = CommentEntity.builder()
                            .id(rs.getInt("c.id"))
                            .message(rs.getString("comment"))
                            .date(rs.getTimestamp("c.date_time"))
                            .owner(rs.getString("c.username"))
                            .build();
                    tp.getComments().add(comment);
                }

            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
        return res;
    }

    @Override
    public int addComment(int topicId, CommentEntity comment) {
        try (Connection connection = source.getConnection()) {
            PreparedStatement addComment = connection.prepareStatement("INSERT INTO comments(comment, date_time, post_id, username) VALUES (?, ?, ?, ?)");
            addComment.setString(1, comment.getMessage());
            addComment.setTimestamp(2, comment.getDate());
            addComment.setInt(3, topicId);
            addComment.setString(4, comment.getOwner());
            addComment.execute();
            PreparedStatement getId = connection.prepareStatement("SELECT id FROM comments " +
                    "WHERE comment = ? AND date_time = ? AND post_id = ? AND username = ?");
            getId.setString(1, comment.getMessage());
            getId.setTimestamp(2, comment.getDate());
            getId.setInt(3, topicId);
            getId.setString(4, comment.getOwner());
            ResultSet rs = getId.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
            throw new IllegalIdException(String.format("Post with id %d not found"));

        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    @Override
    public boolean removeComment(int topicId, int commentId) {
        try (Connection connection = source.getConnection()) {
            PreparedStatement remove = connection.prepareStatement("DELETE FROM comments WHERE id = ? AND post_id = ?");
            remove.setInt(1, commentId);
            remove.setInt(2, topicId);
            if (remove.execute()) {
                return true;
            }
            throw new IllegalIdException(String.format("Comment with id %d to post with id %d not found", commentId, topicId));
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    @Override
    public TopicEntity getTopicById(int topicId) {
        try (Connection connection = source.getConnection()) {
            PreparedStatement getTopic = connection.prepareStatement("SELECT * FROM posts p JOIN comments c on c.post_id = p.id WHERE post_id = ?");
            getTopic.setInt(1, topicId);
            ResultSet rs = getTopic.executeQuery();
            TopicEntity topic = null;
            while (rs.next()) {
                if (topic == null) {
                    topic = TopicEntity.builder()
                            .id(rs.getInt("posts.id"))
                            .content(rs.getString("content"))
                            .date(rs.getTimestamp("posts.date_time"))
                            .owner(rs.getString("posts.username"))
                            .comments(new ArrayList<>())
                            .build();
                }
                CommentEntity comment = CommentEntity.builder()
                        .owner(rs.getString("comments.username"))
                        .message(rs.getString("comment"))
                        .date(rs.getTimestamp("comments.date_time"))
                        .id(rs.getInt("comments.id"))
                        .build();
                topic.getComments().add(comment);
            }
            if (topic != null) {
                return topic;
            }
            throw new IllegalIdException(String.format("Topic with id %d not found", topicId));
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    @Override
    public CommentEntity getCommentById(int topicId, int commentId) {
        try (Connection connection = source.getConnection()) {
            PreparedStatement getComment = connection.prepareStatement("SELECT * FROM comments WHERE id = ? AND post_id = ?");
            getComment.setInt(1, commentId);
            getComment.setInt(2, topicId);
            ResultSet rs = getComment.executeQuery();
            CommentEntity comment = null;
            if (rs.next()) {
                comment = CommentEntity.builder()
                        .id(rs.getInt("id"))
                        .message(rs.getString("comment"))
                        .owner(rs.getString("username"))
                        .date(rs.getTimestamp("date_time"))
                        .build();
            }
            if (comment != null) {
                return comment;
            }
            throw new IllegalIdException(String.format("Comment with id %d to post with id %d not found", commentId, topicId));

        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e);
        }

    }
}
