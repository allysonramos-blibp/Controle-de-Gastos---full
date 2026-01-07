package domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class Teste {

    @Test
    void deveCriarTarefaComStatusAberta() {
        Tarefa tarefa = new Tarefa(
                "Estudar Java",
                "POO e Spring",
                LocalDate.now().plusDays(1)
        );

        assertNotNull(tarefa);
    }
}
