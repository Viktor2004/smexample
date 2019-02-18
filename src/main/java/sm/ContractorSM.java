package sm;

import model.ClientsState;
import model.ContractorEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.stereotype.Service;
import org.springframework.messaging.Message;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import static model.ClientsState.*;
import static model.ContractorEvent.*;

@Service
public class ContractorSM {
    @Autowired
    private UpdateStateAction updateStateAction;

    public void executeSM(ClientsState initialState, ContractorEvent event) throws Exception {

        StateMachineBuilder.Builder<ClientsState, ContractorEvent> builder = StateMachineBuilder.builder();

        builder.configureStates()
                .withStates()
                .initial(initialState)
                .states(EnumSet.allOf(ClientsState.class));

        builder.configureConfiguration()
                .withConfiguration()
                .autoStartup(true);


        builder.configureTransitions()
                .withExternal()
                .source(NO_CONNECTION)
                .target(INVITATION_RECEIVED)
                .event(INCOME_INVITATION)
                .action(updateStateAction)
                .and()
                .withExternal()
                .source(NO_CONNECTION)
                .target(CONNECTION_BROKEN)
                .event(INCOME_REJECT)
                .action(updateStateAction)
                .and()
                .withExternal()
                .source(NO_CONNECTION)
                .target(INVITATION_SAVED)
                .event(OUTCOME_INVITATION)
                .action(updateStateAction)
                .and()
                .withExternal()
                .source(NO_CONNECTION)
                .target(CONNECTION_BROKEN)
                .event(OUTCOME_REJECT)
                .action(updateStateAction)

                .and()
                .withExternal()
                .source(ARE_CONNECTED)
                .target(CONNECTION_BROKEN)
                .event(INCOME_REJECT)
                .action(updateStateAction)
                .and()
                .withExternal()
                .source(ARE_CONNECTED)
                .target(CONNECTION_BROKEN)
                .event(OUTCOME_REJECT)
                .action(updateStateAction)


                .and()
                .withExternal()
                .source(CONNECTION_BROKEN)
                .target(CONNECTION_ERROR)
                .event(RC_ERROR)
                .action(updateStateAction)
                .and()
                .withExternal()
                .source(CONNECTION_BROKEN)
                .target(INVITATION_RECEIVED)
                .event(INCOME_INVITATION)
                .action(updateStateAction)
                .and()
                .withExternal()
                .source(CONNECTION_BROKEN)
                .target(INVITATION_SAVED)
                .event(OUTCOME_INVITATION)
                .action(updateStateAction)


                .and()
                .withExternal()
                .source(INVITATION_SAVED)
                .target(INVITATION_SEND)
                .event(RC_SUCCESS)
                .action(updateStateAction)
                .and()
                .withExternal()
                .source(INVITATION_SAVED)
                .target(CONNECTION_ERROR)
                .event(RC_ERROR)
                .action(updateStateAction)
                .and()
                .withExternal()
                .source(INVITATION_SAVED)
                .target(ARE_CONNECTED)
                .event(INCOME_INVITATION)
                .action(updateStateAction)
                .and()
                .withExternal()
                .source(INVITATION_SAVED)
                .target(CONNECTION_BROKEN)
                .event(INCOME_REJECT)
                .action(updateStateAction)
                .and()
                .withExternal()
                .source(INVITATION_SAVED)
                .target(CONNECTION_BROKEN)
                .event(OUTCOME_REJECT)
                .action(updateStateAction)

                .and()
                .withExternal()
                .source(INVITATION_SEND)
                .target(CONNECTION_ERROR)
                .event(RC_ERROR)
                .action(updateStateAction)
                .and()
                .withExternal()
                .source(INVITATION_SEND)
                .target(ARE_CONNECTED)
                .event(INCOME_INVITATION)
                .action(updateStateAction)
                .and()
                .withExternal()
                .source(INVITATION_SEND)
                .target(CONNECTION_BROKEN)
                .event(INCOME_REJECT)
                .action(updateStateAction)
                .and()
                .withExternal()
                .source(INVITATION_SEND)
                .target(CONNECTION_BROKEN)
                .event(OUTCOME_REJECT)
                .action(updateStateAction)

                .and()
                .withExternal()
                .source(INVITATION_RECEIVED)
                .target(CONNECTION_ERROR)
                .event(RC_ERROR)
                .action(updateStateAction)
                .and()
                .withExternal()
                .source(INVITATION_RECEIVED)
                .target(CONNECTION_ERROR)
                .event(INCOME_REJECT)
                .action(updateStateAction)
                .and()
                .withExternal()
                .source(INVITATION_RECEIVED)
                .target(ARE_CONNECTED)
                .event(OUTCOME_INVITATION)
                .action(updateStateAction)
                .and()
                .withExternal()
                .source(INVITATION_RECEIVED)
                .target(CONNECTION_BROKEN)
                .event(OUTCOME_REJECT)
                .action(updateStateAction)

        ;


        StateMachine<ClientsState, ContractorEvent> sm = builder.build();
        Map<String, Object> clients = new HashMap<>();
        clients.put("client1", "client11");
        clients.put("client2", "client22");

        MessageHeaders headers = new MessageHeaders(clients);
        Message<ContractorEvent> message = new GenericMessage<>(event, headers);
        sm.sendEvent(message);
        sm.stop();
    }
}
