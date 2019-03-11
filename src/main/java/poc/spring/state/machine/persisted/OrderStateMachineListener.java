package poc.spring.state.machine.persisted;

import org.springframework.messaging.Message;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.stereotype.Component;

@Component
public class OrderStateMachineListener extends StateMachineListenerAdapter<OrderStates, OrderEvents>{

    @Override
    public void eventNotAccepted(final Message<OrderEvents> event) {
        super.eventNotAccepted(event);
        throw new RuntimeException("Event not accepted: " + event.getPayload());
    }

}
