package poc.spring.state.machine.persisted;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class Order {
    @Id
    @Setter(AccessLevel.PRIVATE)
    private String id;
    private OrderStates status;
    private String stateMachineId;
}
