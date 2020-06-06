package controllers;

import domain.Message;
import filters.customAnnotation.JWTTokenNeeded;
import org.glassfish.jersey.media.multipart.FormDataParam;
import repositories.MessageRepository;
import services.MessageService;
import services.interfaces.IMessageService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("messages")
public class MessageController {
    private IMessageService messageService = new MessageService();
    private MessageRepository messageRepository = new MessageRepository();

    @JWTTokenNeeded
    @GET
    @Path("/sender/{id}")
    public Response getMessageListBySender(@PathParam("id") long id) {
        List<Message> messages;
        try {
            messages = messageService.getMessageListBySender(id);
        } catch (ServerErrorException ex) {
            return Response
                    .serverError()
                    .build();
        }

        if (messages == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Messages list is empty!")
                    .build();
        } else {
            return Response
                    .status(Response.Status.OK)
                    .entity(messages)
                    .build();
        }
    }

    @JWTTokenNeeded
    @GET
    @Path("/receiver/{id}")
    public Response getMessageListByReceiver(@PathParam("id") long id) {
        List<Message> messages;
        try {
            messages = messageService.getMessageListByReceiver(id);
        } catch (ServerErrorException ex) {
            return Response
                    .serverError()
                    .build();
        }

        if (messages == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Messages list is empty!")
                    .build();
        } else {
            return Response
                    .status(Response.Status.OK)
                    .entity(messages)
                    .build();
        }
    }

    @JWTTokenNeeded
    @POST
    @Path("/send")
    public Response sendMessage(@FormDataParam("sender_id") int sender_id,
                                @FormDataParam("message") String message,
                                @FormDataParam("receiver_id") int receiver_id)
    {
        Message messageItSelf = new Message(sender_id, message, receiver_id);
        try {
            messageRepository.add(messageItSelf);
        } catch (BadRequestException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Cannot send the message!")
                    .build();
        }
        return Response
                .status(Response.Status.OK)
                .entity("Message was successfully sent! " + messageItSelf)
                .build();
    }
}
