package cdq.asynchronus.task.processing.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "results")
public class Result {

    @Id
    @Column(name = "id")
    @JsonIgnore
    private int id;
    @Column(name = "position")
    private int position;
    @Column(name = "number_of_typos")
    private int typos;

}