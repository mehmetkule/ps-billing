package com.mk.psbilling.service;

import com.mk.psbilling.api.dto.InvoiceRequest;
import com.mk.psbilling.api.dto.InvoiceResponse;
import com.mk.psbilling.api.dto.MemberAccountRequest;
import com.mk.psbilling.db.Invoice;
import com.mk.psbilling.db.MemberAccount;
import com.mk.psbilling.exception.InvoiceException;
import com.mk.psbilling.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class ClientImpl implements InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    MemberAccountServiceImpl memberAccountServiceImpl;

    @Override
    public Invoice createInvoice(InvoiceRequest invoiceRequest) {
        Invoice invoice = Invoice.builder()
                .billType(invoiceRequest.getBill_type())
                .amount(invoiceRequest.getAmount())
                .build();
        invoice = invoiceRepository.save(invoice);
        return Invoice.builder().billType(invoice.getBillType()).amount(invoice.getAmount()).build();
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
        return InvoiceResponse.builder().bill_type(find.getBillType()).amount(find.getAmount()).build();
    }

    @Override
    public InvoiceResponse updateInvoice(Long id, InvoiceRequest invoiceRequest) {
        return invoiceRepository.findById(id)
                .map(invoice -> {
                    invoice.setBillType(invoiceRequest.getBill_type());
                    invoice.setAmount(invoiceRequest.getAmount());
                    invoiceRepository.save(invoice);
                    return InvoiceResponse.builder().bill_type(invoice.getBillType()).amount(invoice.getAmount()).build();
                })
                .orElseThrow(() -> new InvoiceException("Invoice id:" + id + " not found"));
    }

    @Override
    public List<InvoiceResponse> getAllInvoices() {
        List<InvoiceResponse> responses = new ArrayList<>();
        invoiceRepository.findAll().forEach(invoice -> {
            responses.add(InvoiceResponse.builder().bill_type(invoice.getBillType()).amount(invoice.getAmount()).build());
        });
        return responses;
    }

    @Override
    public void deleteInvoice(Long id) {
        Invoice invoice = findInvoice(id);
        invoiceRepository.delete(invoice);
    }

    @Override
    public Invoice findByBillType(int invoiceType) {
        return invoiceRepository.findByBillType(invoiceType);
    }

    @Override
    public InvoiceResponse makePayment(int invoiceType, String memberCode, double amount) {
        Invoice invoice = findByBillType(invoiceType);
        MemberAccount response = memberAccountServiceImpl.findByCode(memberCode);
        if (Objects.equals(response.getInvoice().getBillType(), invoice.getBillType()) && response.getBalance() >= invoice.getAmount()) {
            switch (invoice.getBillType()) {
                case 1, 2, 3 -> {
                    response.setBalance(response.getBalance() - amount);
                    invoice.setAmount(0.0);
                }
                default -> throw new InvoiceException("Invoice :" + invoice + " not found");
            }
        }
        invoiceRepository.save(invoice);
        memberAccountServiceImpl.updateMemberAccount(response.getId(), MemberAccountRequest.builder().name(response.getName()).surname(response.getSurname()).balance(response.getBalance()).invoice(response.getInvoice()).build());
        return InvoiceResponse.builder().id(invoice.getId()).bill_type(invoice.getBillType()).amount(invoice.getAmount()).process_date(invoice.getProcess_date()).build();
    }

    @Override
    public InvoiceResponse inquirePayment(int invoiceType, String memberCode) {
        Invoice invoice = findByBillType(invoiceType);
        MemberAccount response = memberAccountServiceImpl.findByCode(memberCode);
        if (Objects.equals(response.getInvoice().getId(), invoice.getId())) {
            switch (invoice.getBillType()) {
                case 1, 2, 3 -> {
                    return InvoiceResponse.builder().id(invoice.getId()).bill_type(invoice.getBillType()).amount(invoice.getAmount()).process_date(invoice.getProcess_date()).build();
                }
                default -> throw new InvoiceException("Invoice :" + invoice + " not found");
            }
        }
        return InvoiceResponse.builder().id(invoice.getId()).bill_type(invoice.getBillType()).amount(invoice.getAmount()).process_date(invoice.getProcess_date()).build();
    }

    @Override
    public InvoiceResponse cancelPayment(int invoiceType, String memberCode, double amount) {
        Invoice invoice = findByBillType(invoiceType);
        MemberAccount response = memberAccountServiceImpl.findByCode(memberCode);
        if (Objects.equals(response.getInvoice().getId(), invoice.getId())) {
            switch (invoice.getBillType()) {
                case 1, 2, 3 -> {
                    response.setBalance(response.getBalance() + amount);
                    invoice.setAmount(amount);
                }
                default -> throw new InvoiceException("Invoice :" + invoice + " not found");
            }
        }
        invoice = invoiceRepository.save(invoice);
        return InvoiceResponse.builder().id(invoice.getId()).bill_type(invoice.getBillType()).amount(invoice.getAmount()).process_date(invoice.getProcess_date()).build();
    }
}
