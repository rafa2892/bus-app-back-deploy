package com.bus.app.services;

import com.bus.app.modelo.Carro;
import com.bus.app.modelo.Conductor;
import com.bus.app.repositorio.ConductorRepositorio;
import com.bus.app.tools.BusAppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ConductorServiceImpl implements ConductorService {


    @Autowired
    private ConductorRepositorio conductorRepositorio;

    @Autowired
    private AuditoriaService auditoriaService;

    /**
     * Guarda un conductor nuevo.
     * Al crear un conductor, asigna la fecha de alta en el formato DD/MM/YYYY HH:mm:ss
     *
     * @param conductor El conductor que se va a guardar
     * @return El conductor guardado
     */
    @Override
    public Conductor save(Conductor conductor) {
        // Asignamos la fecha de alta
        conductor.setFechaAlta(new Date());
        conductor.setDadoAltaPor(BusAppUtils.getUserName());

        // Guardamos el conductor
        return conductorRepositorio.save(conductor);
    }

    @Override
    public List<Conductor> findAll() {
        return conductorRepositorio.findAll();
    }

    @Override
    public void deleteById(Long id) {

        Conductor conductor = null;
        Optional<Conductor> co = conductorRepositorio.findById(id);

        if(co.isPresent()) {
            conductor = co.get();
            conductorRepositorio.delete(conductor);
        }
        //Creamos auditoria de eliminación
        auditoriaService.buildDeleteAudit(conductor);
        conductorRepositorio.deleteById(id);
    }

    @Override
    public Optional<Conductor> findById(Long id) {
       return conductorRepositorio.findById(id);
    }
}
