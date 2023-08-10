package com.mk.psbilling.service;

import com.mk.psbilling.api.dto.InvoiceRequest;
import com.mk.psbilling.api.dto.InvoiceResponse;
import com.mk.psbilling.api.dto.MemberAccountRequest;
import com.mk.psbilling.db.Invoice;
import com.mk.psbilling.db.MemberAccount;
import com.mk.psbilling.exception.InvoiceException;
import com.mk.psbilling.repository.InvoiceRepository;
import com.mk.psbilling.repository.MemberAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientImplTest {
    private ClientImpl client;
    private InvoiceRepository invoiceRepository;
    private MemberAccountServiceImpl memberAccountService;
    private MemberAccountRepository memberAccountRepository;

    @BeforeEach
    public void setUp() {
        invoiceRepository = mock(InvoiceRepository.class);
        memberAccountRepository = mock(MemberAccountRepository.class);
        memberAccountService = new MemberAccountServiceImpl(memberAccountRepository);

        client = new ClientImpl(invoiceRepository, memberAccountService);

    }

    private static final Long invoiceID = 1L;

    @Test
    void createInvoice() throws Exception {
        InvoiceRequest request = InvoiceRequest.builder()
                .billType(1)
                .amount(100.0)
                .build();
        Invoice invoice = Invoice.builder()
                .billType(request.getBillType())
                .amount(request.getAmount())
                .build();
        when(invoiceRepository.save(invoice)).thenReturn(invoice);
        Invoice response = client.createInvoice(request);
        assertAll(
                () -> assertEquals(1, response.getBillType()),
                () -> assertEquals(100.0, response.getAmount()));
    }

    @Test
    void findInvoice() {
        when(invoiceRepository.findById(invoiceID)).thenReturn(java.util.Optional.of(new Invoice(invoiceID, 100.0, 1)));
        Invoice response = client.findInvoice(invoiceID);
        assertAll(
                () -> assertEquals(1, response.getBillType()),
                () -> assertEquals(100.0, response.getAmount()));
    }

    @Test
    void getNotFoundInvoice() throws Exception {
        when(invoiceRepository.findById(invoiceID)).thenReturn(java.util.Optional.empty());

        Exception e = assertThrows(InvoiceException.class, () -> {
            client.findInvoice(invoiceID);
        });

        assertEquals("Invoice id:" + invoiceID + " not found", e.getMessage());
    }

    @Test
    void getInvoiceById() throws Exception {
        when(invoiceRepository.findById(invoiceID)).thenReturn(java.util.Optional.of(new Invoice(invoiceID, 100.0, 1)));
        InvoiceResponse response = client.getInvoiceById(invoiceID);
        assertAll(
                () -> assertEquals(1, response.getBillType()),
                () -> assertEquals(100.0, response.getAmount()));
    }

    @Test
    void updateInvoice() throws Exception {
        InvoiceRequest request = InvoiceRequest.builder()
                .billType(1)
                .amount(100.0)
                .build();
        Invoice invoice = Invoice.builder()
                .billType(request.getBillType())
                .amount(request.getAmount())
                .build();
        when(invoiceRepository.findById(invoiceID)).thenReturn(java.util.Optional.of(new Invoice(invoiceID, 100.0, 1)));
        when(invoiceRepository.save(invoice)).thenReturn(invoice);
        InvoiceResponse response = client.updateInvoice(invoiceID, request);
        assertAll(
                () -> assertEquals(1, response.getBillType()),
                () -> assertEquals(100.0, response.getAmount()));
    }

    @Test
    void getAllInvoices() throws Exception {
        when(invoiceRepository.findAll()).thenReturn(java.util.List.of(new Invoice(invoiceID, 100.0, 1)));
        List<InvoiceResponse> response = client.getAllInvoices();
        assertAll(
                () -> assertEquals(1, response.get(0).getBillType()),
                () -> assertEquals(100.0, response.get(0).getAmount()));
    }

    @Test
    void deleteInvoice() throws Exception {
        Invoice invoice = Invoice.builder()
                .billType(1)
                .amount(100.0)
                .id(invoiceID)
                .build();
        client = spy(client);
        doReturn(invoice).when(client).findInvoice(invoiceID);
        doReturn(java.util.Optional.of(invoice)).when(invoiceRepository).findById(invoiceID);
        client.deleteInvoice(invoiceID);
        verify(invoiceRepository, times(1)).delete(invoice);
    }

    @Test
    void findByBillType() throws Exception {
        Invoice invoice = Invoice.builder()
                .billType(1)
                .amount(100.0)
                .id(invoiceID)
                .build();
        when(invoiceRepository.findByBillType(1)).thenReturn(invoice);
        Invoice response = client.findByBillType(1);
        assertAll(
                () -> assertEquals(1, response.getBillType()),
                () -> assertEquals(100.0, response.getAmount()));
    }

//    private final String memberCode = "1Me";
//
//    @Test
//    void makePayment() throws Exception {
//
//
//        Invoice invoice = Invoice.builder()
//                .id(1L)
//                .billType( 1)
//                .amount( 100.0)
//                .billType(1)
//                .build();
//        MemberAccountRequest memberRequest = MemberAccountRequest.builder()
//                .name("Mehmet")
//                .surname("KULE")
//                .balance(100.0)
//                .invoice(invoice)
//                .build();
//
//        MemberAccount memberAccount = MemberAccount.builder()
//                .id(1L)
//                .name(memberRequest.getName())
//                .surname(memberRequest.getSurname())
//                .balance(memberRequest.getBalance())
//                .code(memberCode)
//                .invoice(invoice)
//                .build();
//
//        when(memberAccountRepository.save(memberAccount)).thenReturn(memberAccount);
//
//
//        when(memberAccountRepository.findByCode(memberCode)).thenReturn(java.util.Optional.of(memberAccount));
//        when(invoiceRepository.findByBillType(1)).thenReturn(invoice);
//
//        InvoiceResponse invoiceResponse = client.makePayment(1, memberAccount.getCode(), 100.0);
//        assertAll(
//                () -> assertEquals(1, invoiceResponse.getBillType()),
//                () -> assertEquals(0.0, invoiceResponse.getAmount()));
//
//    }
}
