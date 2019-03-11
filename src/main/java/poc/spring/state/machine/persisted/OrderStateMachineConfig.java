package poc.spring.state.machine.persisted;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
public class OrderStateMachineConfig extends StateMachineConfigurerAdapter<OrderStates, OrderEvents> {

    @Autowired
    private StateMachineListener<OrderStates, OrderEvents> stateMachineListener;

    @Autowired
    private StateMachineRuntimePersister<OrderStates, OrderEvents, String> stateMachineRuntimePersister;

    @Override
    public void configure(StateMachineConfigurationConfigurer<OrderStates, OrderEvents> config) throws Exception {
        config
            .withConfiguration()
            .listener(stateMachineListener);
        config
            .withPersistence()
            .runtimePersister(stateMachineRuntimePersister);
    }

    @Override
    public void configure(StateMachineStateConfigurer<OrderStates, OrderEvents> states) throws Exception {
        states
            .withStates()
            .initial(OrderStates.CREATED)
            .states(EnumSet.allOf(OrderStates.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStates, OrderEvents> transitions) throws Exception {
        transitions
            .withExternal()
            .source(OrderStates.CREATED).target(OrderStates.APPROVED)
            .event(OrderEvents.CONFIRMED_PAYMENT)
            .and().withExternal()
            .source(OrderStates.APPROVED).target(OrderStates.INVOICED)
            .event(OrderEvents.INVOICE_ISSUED)
            .and().withExternal()
            .source(OrderStates.APPROVED).target(OrderStates.CANCELLED)
            .event(OrderEvents.CANCEL)
            .and().withExternal()
            .source(OrderStates.INVOICED).target(OrderStates.SHIPPED)
            .event(OrderEvents.SHIP)
            .and().withExternal()
            .source(OrderStates.SHIPPED).target(OrderStates.DELIVERED)
            .event(OrderEvents.DELIVER);
    }

}
