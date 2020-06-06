package repositories;

import domain.Message;
import repositories.interfaces.IDBRepository;
import repositories.interfaces.IMessageRepository;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MessageRepository implements IMessageRepository {

    private IDBRepository db_repo = new PostgresRepository();

    @Override
    public List<Message> GetMessageListBySender(long sender_id) {
        String sql =
    }

    @Override
    public List<Message> GetMessageListByReceiver(long receiver_id) {
        return null;
    }

    @Override
    public void add(Message entity) {

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
            Statement stmnt = db_repo.getConnection().createStatement();
            ResultSet rs = stmnt.executeQuery(sql);
        }
    }
}
