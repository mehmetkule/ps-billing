package com.mk.psbilling.service;

import com.mk.psbilling.api.dto.InvoiceRequest;
import com.mk.psbilling.api.dto.InvoiceResponse;
import com.mk.psbilling.db.Invoice;
import com.mk.psbilling.exception.InvoiceException;
import com.mk.psbilling.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService{

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public Invoice createInvoice(InvoiceRequest invoiceRequest) {
        Invoice invoice = Invoice.builder()
                .bill_type(invoiceRequest.getBill_type())
                .amount(invoiceRequest.getAmount())
                .build();
        invoice = invoiceRepository.save(invoice);
        return Invoice.builder().bill_type(invoice.getBill_type()).amount(invoice.getAmount()).build();
    }

    @Override
    public Invoice findInvoice(Long id) {
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(id);
        if (invoiceOptional.isEmpty()) {
            throw new InvoiceException("Invoice id:" + id + " not found");
        }
        return invoiceOptional.get();
    }

    @Override
    public InvoiceResponse getInvoiceById(Long id) {
        Invoice find = findInvoice(id);
        return InvoiceResponse.builder().bill_type(find.getBill_type()).amount(find.getAmount()).build();
    }

    @Override
    public InvoiceResponse updateInvoice(Long id, InvoiceRequest invoiceRequest) {
        return invoiceRepository.findById(id)
                .map(invoice -> {
                    invoice.setBill_type(invoiceRequest.getBill_type());
                    invoice.setAmount(invoiceRequest.getAmount());
                    invoiceRepository.save(invoice);
                    return InvoiceResponse.builder().bill_type(invoice.getBill_type()).amount(invoice.getAmount()).build();
                })
                .orElseThrow(() -> new InvoiceException("Invoice id:" + id + " not found"));
    }

    @Override
    public List<InvoiceResponse> getAllInvoices() {
        List<InvoiceResponse> responses = new ArrayList<>();
        invoiceRepository.findAll().forEach(invoice -> {
            responses.add(InvoiceResponse.builder().bill_type(invoice.getBill_type()).amount(invoice.getAmount()).build());
        });
        return responses;
    }

    @Override
    public void deleteInvoice(Long id) {
       Invoice invoice = findInvoice(id);
       invoiceRepository.delete(invoice);
    }
}
