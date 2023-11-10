package co.com.bancolombia.events;

import co.com.bancolombia.model.events.gateways.EventsGateway;
import co.com.bancolombia.model.usuario.Usuario;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.reactivecommons.api.domain.DomainEvent;
import org.reactivecommons.api.domain.DomainEventBus;
import org.reactivecommons.async.api.AsyncQuery;
import org.reactivecommons.async.api.DirectAsyncGateway;
import org.reactivecommons.async.impl.config.annotations.EnableDomainEventBus;
import org.reactivecommons.async.rabbit.converters.json.CloudEventBuilderExt;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.logging.Level;

import static reactor.core.publisher.Mono.from;

@Log
@RequiredArgsConstructor
@EnableDomainEventBus
public class ReactiveEventsGateway implements EventsGateway {
    public static final String EVENT_USER = "event.user";
    private final DomainEventBus domainEventBus;
    private final DirectAsyncGateway directAsyncGateway;
    private ObjectMapper om = new ObjectMapper();


    @Override
    public Mono<String> emit(Usuario usuario) {
        CloudEvent event = null;
        /*CloudEvent query = null;*/
        try {
            /*query = CloudEventBuilder.v1() //
                    .withId(UUID.randomUUID().toString()) //
                    .withSource(URI.create("https://spring.io/foos"))//
                    .withType("query") //
                    .withTime(OffsetDateTime.now())
                    .withData("application/json", om.writeValueAsBytes(usuario))
                    .build();*/

            event = CloudEventBuilder.v1() //
                    .withId(UUID.randomUUID().toString()) //
                    .withSource(URI.create("https://spring.io/foos"))//
                    .withSubject("hello")
                    .withType("event") //
                    .withDataContentType("application/json")
                    .withTime(OffsetDateTime.now())
                    .withData("application/json", om.writeValueAsBytes(usuario))
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return Mono.from(domainEventBus.emit(new DomainEvent<CloudEvent>(EVENT_USER, UUID.randomUUID().toString(),
                        event)))
                .thenReturn("ok");
        /*Mono<CloudEvent> response = directAsyncGateway
                .requestReply(new AsyncQuery<CloudEvent>("query",query),
                        "consumidorNewVersion", CloudEvent.class);
        byte[] bytes = response.block().getData().toBytes();
        String stringData = new String(bytes, StandardCharsets.UTF_8);
        System.out.println(stringData);
        return Mono.just(usuario);*/
    }
}
