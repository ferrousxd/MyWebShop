package services;

import domain.Message;
import repositories.MessageRepository;
import repositories.interfaces.IMessageRepository;
import services.interfaces.IMessageService;

import java.util.List;

public class MessageService implements IMessageService {
    private IMessageRepository messageRepo = new MessageRepository();

    @Override
    public List<Message> getMessageListBySender(long sender_id) {
        return messageRepo.getMessageListBySender(sender_id);
    }

    @Override
    public List<Message> getMessageListByReceiver(long receiver_id) {
        return messageRepo.getMessageListByReceiver(receiver_id);
    }
}
