package co.com.bancolombia.events;

import co.com.bancolombia.model.events.gateways.EventsGateway;
import co.com.bancolombia.model.usuario.Usuario;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.reactivecommons.api.domain.Command;
import org.reactivecommons.async.api.AsyncQuery;
import org.reactivecommons.async.api.DirectAsyncGateway;
import org.reactivecommons.async.impl.config.annotations.EnableDirectAsyncGateway;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.logging.Level;

@Log
@AllArgsConstructor
@EnableDirectAsyncGateway
public class ReactiveDirectAsyncGateway {
    public static final String TARGET_NAME = "cleanArchitecture";// refers to remote spring.application.name property
    public static final String SOME_COMMAND_NAME = "some.command.name";
    public static final String SOME_QUERY_NAME = "some.query.name";
    private final DirectAsyncGateway gateway;
    private ObjectMapper om = new ObjectMapper();


    public Mono<Void> runRemoteJob(Object command/*change for proper model*/) {
        log.log(Level.INFO, "Sending command: {0}: {1}", new String[]{SOME_COMMAND_NAME, command.toString()});
        return gateway.sendCommand(new Command<>(SOME_COMMAND_NAME, UUID.randomUUID().toString(), command),
                TARGET_NAME);
    }

    public Mono<CloudEvent> requestForRemoteData(Usuario usuario) {
        //CloudEvent event = null;
        CloudEvent query = null;
        try {
            query = CloudEventBuilder.v1() //
                    .withId(UUID.randomUUID().toString()) //
                    .withSource(URI.create("https://spring.io/foos"))//
                    .withType("query") //
                    .withTime(OffsetDateTime.now())
                    .withData("application/json", om.writeValueAsBytes(usuario))
                    .build();


            /*event = CloudEventBuilder.v1() //
                    .withId(UUID.randomUUID().toString()) //
                    .withSource(URI.create("https://spring.io/foos"))//
                    .withType("event") //
                    .withDataContentType("application/json")
                    .withTime(OffsetDateTime.now())
                    .withData("application/json", om.writeValueAsBytes(usuario))
                    .build();*/
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        //return Mono.from(domainEventBus.emit(new DomainEvent<CloudEvent>(EVENT_USER, UUID.randomUUID().toString(), event))).thenReturn(usuario);
        return gateway.requestReply(query, "consumidorNewVersion", CloudEvent.class);
    }
}
