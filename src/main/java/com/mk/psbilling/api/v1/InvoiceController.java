package com.mk.psbilling.api.v1;

import com.mk.psbilling.api.dto.InvoiceRequest;
import com.mk.psbilling.api.dto.InvoiceResponse;
import com.mk.psbilling.db.Invoice;
import com.mk.psbilling.service.InvoiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoice/v1")
public class InvoiceController {
    @Autowired
    private final InvoiceServiceImpl invoiceServiceImpl;

    public InvoiceController(InvoiceServiceImpl invoiceServiceImpl) {
        this.invoiceServiceImpl = invoiceServiceImpl;
    }

    @PostMapping("/create")
    public ResponseEntity<Invoice> createInvoice(@RequestBody InvoiceRequest request) {
        Invoice response = invoiceServiceImpl.createInvoice(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponse> getInvoice(@PathVariable Long id) {
        InvoiceResponse response = invoiceServiceImpl.getInvoiceById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceResponse> updateInvoice(@PathVariable Long id, @RequestBody InvoiceRequest request) {
        InvoiceResponse response = invoiceServiceImpl.updateInvoice(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<InvoiceResponse> deleteInvoice(@PathVariable Long id) {
        invoiceServiceImpl.deleteInvoice(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<InvoiceResponse>> getAllInvoices() {
        List<InvoiceResponse> response = invoiceServiceImpl.getAllInvoices();
        return ResponseEntity.ok(response);
    }
}
