package repositories.interfaces;

import domain.Message;

import java.util.List;

public interface IMessageRepository extends IEntityRepository<Message> {
    List<Message> GetMessageListBySender(long sender_id);
    List<Message> GetMessageListByReceiver(long receiver_id);
}
