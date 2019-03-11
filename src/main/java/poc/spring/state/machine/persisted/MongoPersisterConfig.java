package poc.spring.state.machine.persisted;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.data.mongodb.MongoDbPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.mongodb.MongoDbStateMachineRepository;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;

@Configuration
public class MongoPersisterConfig {

    @Bean
    public StateMachineRuntimePersister<OrderStates, OrderEvents, String> stateMachineRuntimePersister(
            MongoDbStateMachineRepository jpaStateMachineRepository) {
        return new MongoDbPersistingStateMachineInterceptor<>(jpaStateMachineRepository);
    }

}
