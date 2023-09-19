package co.com.bancolombia.model.events.gateways;

import co.com.bancolombia.model.usuario.Usuario;
import reactor.core.publisher.Mono;

public interface EventsGateway {
    Mono<String> emit(Usuario usuario);

}
