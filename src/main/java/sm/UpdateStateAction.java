package sm;

import model.ClientsState;
import model.ContractorEvent;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Service;
import org.springframework.messaging.MessageHeaders;

@Service
public class UpdateStateAction implements Action<ClientsState, ContractorEvent> {
    @Override
    public void execute(StateContext<ClientsState, ContractorEvent> context) {
        System.out.println("Source state: " + context.getSource());
        System.out.println("Target state: " + context.getTarget());
        System.out.println("Event: " + context.getEvent());
        MessageHeaders headers = context.getMessageHeaders();
        System.out.println(headers.get("client1"));
        System.out.println(headers.get("client2"));
    }
}
