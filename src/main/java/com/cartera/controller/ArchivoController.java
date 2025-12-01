package com.cartera.controller;

import com.cartera.model.DtDocumentosAspirante;
import com.cartera.repository.DtDocumentoAspiranteRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;


@Controller
@RequiredArgsConstructor
public class ArchivoController {

    private final DtDocumentoAspiranteRepository documentosRepo;

    // ===================== VER ARCHIVO =====================
    @GetMapping("/files/{id}")
    public ResponseEntity<Resource> verArchivo(@PathVariable("id") Long id) {

        DtDocumentosAspirante doc = documentosRepo.findById(id)
                .orElse(null);

        if (doc == null || doc.getRutaArchivo() == null) {
            return ResponseEntity.notFound().build();
        }

        File file = new File(doc.getRutaArchivo());
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        FileSystemResource resource = new FileSystemResource(file);

        String contentType = obtenerContentType(doc.getNombreArchivo());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }


    // ===================== DESCARGAR ARCHIVO =====================
    @GetMapping("/files/{id}/download")
    public ResponseEntity<Resource> descargarArchivo(@PathVariable("id") Long id) {

        DtDocumentosAspirante doc = documentosRepo.findById(id)
                .orElse(null);

        if (doc == null) {
            return ResponseEntity.notFound().build();
        }

        File file = new File(doc.getRutaArchivo());
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        FileSystemResource resource = new FileSystemResource(file);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + doc.getNombreArchivo() + "\"")
                .body(resource);
    }



    // ===================== DETECTAR TIPO MIME =====================
    private String obtenerContentType(String nombre) {
        String nombreLower = nombre.toLowerCase();

        if (nombreLower.endsWith(".pdf")) return "application/pdf";
        if (nombreLower.endsWith(".jpg") || nombreLower.endsWith(".jpeg")) return "image/jpeg";
        if (nombreLower.endsWith(".png")) return "image/png";

        return "application/octet-stream";  // por defecto
    }
}
