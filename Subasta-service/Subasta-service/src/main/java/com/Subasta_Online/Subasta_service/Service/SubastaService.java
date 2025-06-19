package com.Subasta_Online.Subasta_service.Service;

import com.Subasta_Online.Subasta_service.Model.DTOFuturasSubastas;
import com.Subasta_Online.Subasta_service.Model.DTOiniciarSubasta;

public interface SubastaService {

        DTOiniciarSubasta iniciarSubasta(DTOiniciarSubasta dto);
       DTOFuturasSubastas programarFuturasSubastas(DTOFuturasSubastas dto);
    }

