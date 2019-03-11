package poc.spring.state.machine.persisted;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private OrderRepository orderRepository;
    private StateMachineService<OrderStates, OrderEvents> stateMachineService;

    @PostMapping
    public ResponseEntity<Order> create() {
        final String uuid = UUID.randomUUID().toString();
        final Order order = new Order();
        final StateMachine<OrderStates, OrderEvents> stateMachine = stateMachineService.acquireStateMachine(uuid);
        stateMachine.start();
        order.setStateMachineId(uuid);
        order.setStatus(stateMachine.getState().getId());
        stateMachine.stop();
        stateMachineService.releaseStateMachine(uuid);
        return ResponseEntity.ok(orderRepository.save(order));
    }

    @PutMapping("/{orderId}/events/{event}")
    public ResponseEntity<?> applyEvent(
            @PathVariable("orderId") String orderId,
            @PathVariable("event") OrderEvents event) {
        final Order order = orderRepository.findById(orderId).orElseThrow(RuntimeException::new);

        final StateMachine<OrderStates, OrderEvents> stateMachine = stateMachineService.acquireStateMachine(order.getStateMachineId());
        stateMachine.start();

        if (stateMachine.sendEvent(event)) {
            order.setStatus(stateMachine.getState().getId());
            stateMachine.stop();
            stateMachineService.releaseStateMachine(order.getStateMachineId());

            return ResponseEntity.ok(orderRepository.save(order));
        } else {
            return ResponseEntity.badRequest().body("It was not possible to apply the event " + event + " on order status " + stateMachine.getState().getId());
        }

    }

}
