package cdq.asynchronus.task.processing.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Matcher {

    private int typos;
    private int position;
    private String pattern;

}
