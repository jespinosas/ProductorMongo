package co.com.bancolombia.model.usuario;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Document {

    private String documentType;
    private String documentNumber;
}
