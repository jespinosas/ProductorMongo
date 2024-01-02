package co.com.bancolombia.model.events.gateways;

import co.com.bancolombia.model.usuario.Operation;
import reactor.core.publisher.Mono;

public interface EventsGateway {
    Mono<String> emit(Operation operation);

}
