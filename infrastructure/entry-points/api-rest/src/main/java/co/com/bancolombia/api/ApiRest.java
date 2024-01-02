package co.com.bancolombia.api;
import co.com.bancolombia.model.usuario.Operation;
import co.com.bancolombia.usecase.emitevent.EmitEventUseCase;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ApiRest {
    private final EmitEventUseCase useCase;


    @PostMapping(path = "/path")
    public Mono<String> emitEvent(@RequestBody Operation operation) {

        return useCase.emitEvent(operation);
    }
}
