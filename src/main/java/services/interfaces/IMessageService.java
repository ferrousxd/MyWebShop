package services.interfaces;

import domain.Message;

import java.util.List;

public interface IMessageService {
    List<Message> getMessageListBySender(long sender_id);

    List<Message> getMessageListByReceiver(long receiver_id);
}
