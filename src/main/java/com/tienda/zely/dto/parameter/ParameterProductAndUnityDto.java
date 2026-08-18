package com.tienda.zely.dto.parameter;

import com.tienda.zely.dto.measurementunity.MeasurementUnityResponseById;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ParameterProductAndUnityDto {

    private List<MeasurementUnityResponseById> datosUnidad;

    private Double precio;

}