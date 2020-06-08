package repositories;

import domain.Message;
import repositories.interfaces.IDBRepository;
import repositories.interfaces.IMessageRepository;

import javax.ws.rs.BadRequestException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MessageRepository implements IMessageRepository {

    private IDBRepository db_repo = new PostgresRepository();

    @Override
    public List<Message> getMessageListBySender(long sender_id) {
        String sql = "SELECT usend.username as sender, message, urec.username as receiver " +
                "FROM messages m JOIN users usend ON m.sender_id = usend.user_id " +
                "JOIN users urec ON m.receiver_id = urec.user_id WHERE usend.user_id = " + sender_id;
        return queryTwo(sql);
    }

    @Override
    public List<Message> getMessageListByReceiver(long receiver_id) {
        String sql = "SELECT urec.username as receiver, message, usend.username as sender " +
                "FROM messages m JOIN users usend ON m.sender_id = usend.user_id " +
                "JOIN users urec ON m.receiver_id = urec.user_id WHERE urec.user_id = " + receiver_id;
        return queryTwo(sql);
    }

    @Override
    public void add(Message entity) {
        try {
            Statement stmt = db_repo.getConnection().createStatement();
            String sql = "INSERT INTO messages (sender_id, message, receiver_id) VALUES ("
                    + entity.getSender_id() + ",'"
                    + entity.getMessage() + "',"
                    + entity.getReceiver_id() + ")";
            stmt.execute(sql);
        } catch(SQLException ex) {
            throw new BadRequestException();
        }
    }

    @Override
    public void update(Message entity) {

    }

    @Override
    public void remove(Message entity) {

    }

    @Override
    public Message queryOne(String sql) {
        return null;
    }

    @Override
    public List<Message> queryTwo(String sql) {
        try {
            List<Message> messages = new ArrayList<>();
            Statement stmt = db_repo.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Message message = new Message(
                        rs.getString("sender"),
                        rs.getString("message"),
                        rs.getString("receiver")
                );
                messages.add(message);
            }
            return messages;
        } catch (SQLException ex) {
            throw new BadRequestException();
        }
    }

    @Override
    public List<Message> queryThree(String sql) {
        try {
            List<Message> messages = new ArrayList<>();
            Statement stmt = db_repo.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Message message = new Message(
                        rs.getString("receiver"),
                        rs.getString("message"),
                        rs.getString("sender")
                );
                messages.add(message);
            }
            return messages;
        } catch (SQLException ex) {
            throw new BadRequestException();
        }
    }
}
