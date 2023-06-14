package com.example.lab8_20196137.controllers;

import com.example.lab8_20196137.entity.Evento;
import com.example.lab8_20196137.repository.EventosRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/evento")
public class EventosController {

    final EventosRepository eventosRepository;

    public EventosController(EventosRepository eventosRepository){
        this.eventosRepository = eventosRepository;
    }

    @GetMapping("")
    public ResponseEntity<HashMap<String, Object>> listarEventos(){
        HashMap<String,Object> responseMap = new HashMap<>();
        responseMap.put("Eventos",eventosRepository.findAll());
        return ResponseEntity.ok(responseMap);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> obtenerEventoPorId(@PathVariable("id") String idString){
        HashMap<String,Object> responseMap = new HashMap<>();

        try{
            int id = Integer.parseInt(idString);
            Optional<Evento> optionalEvento = eventosRepository.findById(id);
            if(optionalEvento.isPresent()){
                responseMap.put("evento", optionalEvento.get());
                responseMap.put("resultado", "exitoso");
                return ResponseEntity.ok(responseMap);
            }else{
                responseMap.put("msg", "Evento no encontrado");
            }
        }catch (NumberFormatException e){
            responseMap.put("msg", "El ID debe ser un número entero positivo");
        }
        responseMap.put("resultado", "Falla");
        return ResponseEntity.badRequest().body(responseMap);
    }

    @PostMapping("")
        public ResponseEntity<HashMap<String,Object>> crearArea(@RequestBody Evento evento,
                                                                @RequestParam(value = "fetchId", required = false) boolean fetchId){
        HashMap<String,Object> responseMap = new HashMap<>();
        eventosRepository.save(evento);
        if(fetchId){
            responseMap.put("id", evento.getId());
        }
        responseMap.put("estado", "creado");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
    }

    @PutMapping("")
    public ResponseEntity<HashMap<String,Object>> aprobarSolicitud(@RequestBody Evento evento){
        HashMap<String,Object> responseMap = new HashMap<>();
        if(evento.getId() > 0){
            Optional<Evento> optionalEvento = eventosRepository.findById(evento.getId());
            if(optionalEvento.isPresent()){
                Evento evento1 = optionalEvento.get();

                if(evento.getFecha() != null)
                    evento1.setFecha((evento.getFecha()));
                if(evento.getNombre() != null)
                    evento1.setNombre((evento.getNombre()));
                if(evento.getDescripcion() != null)
                    evento1.setDescripcion((evento.getDescripcion()));
                if(evento.getPathImage() != null)
                    evento1.setPathImage((evento.getPathImage()));
                if(evento.getLocal() != null)
                    evento1.setLocal((evento.getLocal()));

                eventosRepository.save(evento1);
                responseMap.put("estado", "actualizado");
                return ResponseEntity.ok(responseMap);
            }else{
                responseMap.put("estado", "error");
                responseMap.put("msg", "El evento a actualizar no existe");
                return ResponseEntity.badRequest().body((responseMap));
            }
        }else{
            responseMap.put("estado", "error");
            responseMap.put("msg", "Debe enviar un ID");
            return ResponseEntity.badRequest().body(responseMap);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String,Object>> borrar(@PathVariable("id") String idString){
        HashMap<String,Object> responseMap = new HashMap<>();
        try{
            int id = Integer.parseInt(idString);
            if(eventosRepository.existsById(id)){
                eventosRepository.deleteById(id);
                responseMap.put("estado", "borrado exitosamente");
                return ResponseEntity.ok(responseMap);
            }else{
                responseMap.put("estado", "error");
                responseMap.put("msg", "No se encontró el evento con id: "+ id);
                return ResponseEntity.badRequest().body(responseMap);
            }
        }catch (NumberFormatException e){
            responseMap.put("estado", "error");
            responseMap.put("msg", "El ID debe ser un número entero positivo");
            return ResponseEntity.badRequest().body(responseMap);
        }
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String, Object>> gestionExcepcion(HttpServletRequest request){
        HashMap<String,Object> responseMap = new HashMap<>();
        if(request.getMethod().equals("POST") || request.getMethod().equals("PUT") || request.getMethod().equals("DELETE")){
            responseMap.put("msg","Debe enviar un evento");
            responseMap.put("estado","error");
        }
        return ResponseEntity.badRequest().body(responseMap);

    }

}
