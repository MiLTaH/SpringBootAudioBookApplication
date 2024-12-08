package Application.SpringBootAudioBookApplication.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class FindVoicesDTO {
    @Id
    private int id;

    @NotEmpty(message = "Имя актера не должно быть пустым")
    @Size(max = 100, message = "Имя актера должно быть до 100 символов длиной")
    private String nameActor;

    public FindVoicesDTO(int id, String nameActor) {
        this.id = id;
        this.nameActor = nameActor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotEmpty(message = "Имя актера не должно быть пустым") @Size(max = 100, message = "Имя актера должно быть до 100 символов длиной") String getNameActor() {
        return nameActor;
    }

    public void setNameActor(@NotEmpty(message = "Имя актера не должно быть пустым") @Size(max = 100, message = "Имя актера должно быть до 100 символов длиной") String nameActor) {
        this.nameActor = nameActor;
    }
}
