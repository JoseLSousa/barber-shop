package me.dio.barber_shop_api.dtos.booking;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.sql.Time;


@Getter
@Setter
@NoArgsConstructor
public class RequestListByUserDTO {

    private String id;
    private Time time;
    private String serviceName;
    private String dayOfWeek;

    // Construtor que corresponde ao tipo dos campos retornados pela consulta
    public RequestListByUserDTO(String id, Time time, String serviceName, String dayOfWeek) {
        this.id = id;
        this.time = time;
        this.serviceName = serviceName;
        this.dayOfWeek = dayOfWeek;
    }
}
