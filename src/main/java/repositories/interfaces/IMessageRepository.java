package repositories.interfaces;

import domain.Message;

import java.util.List;

public interface IMessageRepository extends IEntityRepository<Message> {
    List<Message> getMessageListBySender(long sender_id);
    List<Message> getMessageListByReceiver(long receiver_id);
}
