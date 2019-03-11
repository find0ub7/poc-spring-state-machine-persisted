package poc.spring.state.machine.persisted;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.statemachine.service.DefaultStateMachineService;
import org.springframework.statemachine.service.StateMachineService;

@Configuration
public class OrderStateMachineServiceConfig {

    @Bean
    public StateMachineService<OrderStates, OrderEvents> stateMachineService(
            StateMachineFactory<OrderStates, OrderEvents> stateMachineFactory,
            StateMachineRuntimePersister<OrderStates, OrderEvents, String> stateMachineRuntimePersister) {
        return new DefaultStateMachineService<OrderStates, OrderEvents>(stateMachineFactory, stateMachineRuntimePersister);
    }

}
