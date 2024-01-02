package co.com.bancolombia.model.usuario;
import lombok.*;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Operation {

    private Document document;
    private List<Transaction> transactions;
}
