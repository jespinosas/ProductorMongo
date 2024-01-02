package co.com.bancolombia.usecase.emitevent;

import co.com.bancolombia.model.events.gateways.EventsGateway;
import co.com.bancolombia.model.usuario.Operation;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class EmitEventUseCase {
    private final EventsGateway gateway;


    public Mono<String> emitEvent(Operation operation){

        return gateway.emit(operation);
    }


}
