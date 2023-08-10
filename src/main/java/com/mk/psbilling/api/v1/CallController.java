package com.mk.psbilling.api.v1;

import com.mk.psbilling.api.dto.InvoiceRequest;
import com.mk.psbilling.api.dto.InvoiceResponse;
import com.mk.psbilling.db.Invoice;
import com.mk.psbilling.service.ClientImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoice/v1")
public class CallController {
    @Autowired
    private final ClientImpl client;

    public CallController(ClientImpl client) {
        this.client = client;
    }


    @PostMapping("/create")
    public ResponseEntity<Invoice> createInvoice(@RequestBody InvoiceRequest request) {
        Invoice response = client.createInvoice(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponse> getInvoice(@PathVariable Long id) {
        InvoiceResponse response = client.getInvoiceById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<InvoiceResponse> updateInvoice(@PathVariable Long id, @RequestBody InvoiceRequest request) {
        InvoiceResponse response = client.updateInvoice(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<InvoiceResponse> deleteInvoice(@PathVariable Long id) {
        client.deleteInvoice(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<InvoiceResponse>> getAllInvoices() {
        List<InvoiceResponse> response = client.getAllInvoices();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/payment/{invoiceType}/{memberCode}/{amount}")
    public ResponseEntity<InvoiceResponse> makePayment(@PathVariable int invoiceType, @PathVariable String memberCode, @PathVariable double amount) {
        InvoiceResponse response = client.makePayment(invoiceType, memberCode,amount);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/inquire/{invoiceType}/{memberCode}")
    public ResponseEntity<InvoiceResponse> inquirePayment(@PathVariable  int invoiceType, @PathVariable String memberCode) {
        InvoiceResponse response = client.inquirePayment(invoiceType, memberCode);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cancel/{invoiceType}/{memberCode}/{amount}")
    public ResponseEntity<InvoiceResponse> cancelPayment(@PathVariable int invoiceType,@PathVariable String memberCode, @PathVariable double amount) {
        InvoiceResponse response = client.cancelPayment(invoiceType, memberCode, amount);
        return ResponseEntity.ok(response);
    }
}
