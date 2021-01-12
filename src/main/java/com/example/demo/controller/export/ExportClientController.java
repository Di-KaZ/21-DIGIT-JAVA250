package com.example.demo.controller.export;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.service.export.ClientExportCVSService;
import com.example.demo.service.export.ClientExportXLSXService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller pour réaliser export des articles.
 */
@Controller
@RequestMapping("export")
public class ExportClientController {

    @Autowired
    private ClientExportCVSService articleExportCVSService;

    @Autowired
    private ClientExportXLSXService articleExportXLSXService;

    /**
     * Export des articles au format CSV.
     */
    @GetMapping("/clients/csv")
    public void articlesCSV(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-clients.csv\"");
        PrintWriter writer = response.getWriter();
        articleExportCVSService.export(writer);
    }

    @GetMapping("/clients/xlsx")
    public void articlesXLSX(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-clients.xlsx\"");
        OutputStream outputStream = response.getOutputStream();
        articleExportXLSXService.export(outputStream);
    }

    /** Méthode utilisée pour l'export facture (point n°5) */
    @GetMapping("/clients/{id}/factures/xlsx")
    public void clientGetFacturesXLSX(@PathVariable Long id, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"client-" + id + "-factures.xlsx\"");
        OutputStream outputStream = response.getOutputStream();
        articleExportXLSXService.export(outputStream, id);
    }
}
