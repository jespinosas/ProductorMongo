package co.com.bancolombia.usecase.emitevent;

import co.com.bancolombia.model.events.gateways.EventsGateway;
import co.com.bancolombia.model.usuario.Usuario;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class EmitEventUseCase {
    private final EventsGateway gateway;


    public Mono<String> emitEvent(Usuario usuario){

        return gateway.emit(usuario);
    }


}
