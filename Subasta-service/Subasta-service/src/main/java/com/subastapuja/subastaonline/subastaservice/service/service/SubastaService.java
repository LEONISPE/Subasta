package com.subastapuja.subastaonline.subastaservice.service.service;

import com.subastapuja.subastaonline.subastaservice.service.model.DTOFuturasSubastas;
import com.subastapuja.subastaonline.subastaservice.service.model.DTOMostarSubastas;
import com.subastapuja.subastaonline.subastaservice.service.model.DTOiniciarSubasta;

public interface SubastaService {

        DTOiniciarSubasta iniciarSubasta(DTOiniciarSubasta dto);
       DTOFuturasSubastas programarFuturasSubastas(DTOFuturasSubastas dto);
    DTOMostarSubastas getSubastaPorId(Long id);
    }

