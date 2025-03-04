package com.bus.app.tools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BusAppUtils {

    private static final Logger logger = LogManager.getLogger(BusAppUtils.class);

    public static String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (authentication != null && authentication.isAuthenticated()) {
            // Obtener el nombre de usuario del objeto Authentication
            username = authentication.getName();
            logger.info("Datos del usuario : {}", username);
        }
        return username;
    }

    public static String getNumeroUnidadFormateado(Long numeroUnidad) {
        if (numeroUnidad != null) {
            return String.format("UN-%03d", numeroUnidad);
        } else {
            return "";
        }
    }

    public static String fechaFormateada(Date fecha) {
        SimpleDateFormat fechaFormat = new SimpleDateFormat("dd/MM/yyyy");
        return fechaFormat.format(fecha);
    }

    public static String horaFormateada(Date fecha) {
        SimpleDateFormat horaFormat = new SimpleDateFormat("HH:mm:ss");
        return horaFormat.format(fecha);
    }

    public static Date ajustarFechaHasta(Date fechaHasta) {


        // Crear un objeto Calendar para manipular la fecha
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaHasta);

        // Establecer la hora al último momento del día (23:59:59)
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);  // Ajustar a milisegundos para estar seguro

        return calendar.getTime();  // Devolver la fecha ajustada
    }

    public static Map<String, Date> ajustarFechaDesdeHasta(Date fechaDesde, Date fechaHasta) {

        Calendar calendar = Calendar.getInstance();
        Map<String, Date> fechasAjustadas = new HashMap<>();

        // Ajustar fechaDesde a las 00:00:00 del día
        calendar.setTime(fechaDesde);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        fechasAjustadas.put("fechaDesde", calendar.getTime());

        if(fechaHasta == null) {
            fechaHasta = fechaDesde;
        }

        // Ajustar fechaHasta a las 23:59:59 del mismo día
        calendar.setTime(fechaHasta);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        fechasAjustadas.put("fechaHasta", calendar.getTime());

        return fechasAjustadas;
    }


    /**
     * Construye un objeto Pageable con paginación y ordenamiento.
     *
     * @param page    Número de página.
     * @param size    Tamaño de la página.
     * @param orderBy Campo por el que se ordenará (formato "campo-asc/desc").
     * @return Objeto Pageable con la configuración adecuada.
     */
    public static Pageable buildPagination(int page, int size, String orderBy) {
        if (orderBy != null && !orderBy.isEmpty()) {
            String[] parts = orderBy.split("-"); // Separar por "-"
            if (parts.length == 2) {
                String field = parts[0]; // Atributo para ordenar
                Sort.Direction direction = parts[1].equalsIgnoreCase("desc") ?
                        Sort.Direction.DESC : Sort.Direction.ASC;
                return PageRequest.of(page, size, Sort.by(direction, field));
            }
        }
        return PageRequest.of(page, size); // Sin orden si no hay entrada válida
    }

}
