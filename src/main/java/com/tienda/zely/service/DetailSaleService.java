package com.tienda.zely.service;

import com.tienda.zely.dto.detailsale.DetailSaleSpecial;
import java.util.Optional;

public interface DetailSaleService {

    Optional<DetailSaleSpecial> getDetailSalesToPayByPerson(Integer idPerson);

}
