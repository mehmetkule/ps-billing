package com.mk.psbilling.service;

import com.mk.psbilling.api.dto.MemberAccountRequest;
import com.mk.psbilling.api.dto.MemberAccountResponse;
import com.mk.psbilling.db.MemberAccount;
import com.mk.psbilling.exception.MemberAccountException;
import com.mk.psbilling.repository.MemberAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberAccountServiceImpl implements com.mk.psbilling.service.MemberAccountService {


    @Autowired
    private MemberAccountRepository memberAccountRepository;

    @Override
    public MemberAccount createMemberAccount(MemberAccountRequest memberAccountRequest) {
        MemberAccount memberAccount = MemberAccount.builder()
                .name(memberAccountRequest.getName())
                .surname(memberAccountRequest.getSurname())
                .balance(memberAccountRequest.getBalance())
                .invoice(memberAccountRequest.getInvoice())
                .build();
        memberAccount = memberAccountRepository.save(memberAccount);
        memberAccount.setCode(getCode(memberAccount.getId(), memberAccount.getName()));
        memberAccount = memberAccountRepository.save(memberAccount);
        return MemberAccount.builder()
                .name(memberAccount.getName())
                .surname(memberAccount.getSurname())
                .balance(memberAccount.getBalance())
                .invoice(memberAccount.getInvoice())
                .build();
    }

    @Override
    public MemberAccount findMemberAccount(Long id) {
        Optional<MemberAccount> userOptional = memberAccountRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new MemberAccountException("MemberAccount id:" + id + " not found");
        }
        return userOptional.get();
    }


    @Override
    public MemberAccountResponse getMemberAccountById(Long id) {
        MemberAccount find = findMemberAccount(id);
        return MemberAccountResponse.builder().name(find.getName()).surname(find.getSurname()).balance(find.getBalance()).invoice(find.getInvoice()).build();
    }

    @Override
    public MemberAccountResponse updateMemberAccount(Long id, MemberAccountRequest memberAccountRequest) {
        return memberAccountRepository.findById(id)
                .map(memberAccount -> {
                    memberAccount.setName(memberAccountRequest.getName());
                    memberAccount.setSurname(memberAccountRequest.getSurname());
                    memberAccount.setBalance(memberAccountRequest.getBalance());
                    memberAccount.setInvoice(memberAccountRequest.getInvoice());
                    memberAccountRepository.save(memberAccount);
                    return MemberAccountResponse.builder()
                            .name(memberAccount.getName())
                            .surname(memberAccount.getSurname())
                            .balance(memberAccount.getBalance())
                            .invoice(memberAccount.getInvoice())
                            .build();
                })
                .orElseThrow(() -> new MemberAccountException("MemberAccount id:" + id + " not found"));
    }

    @Override
    public List<MemberAccountResponse> getAllMemberAccounts() {
        List<MemberAccountResponse> memberAccounts = new ArrayList<>();
        memberAccountRepository.findAll().forEach(memberAccount -> {
            memberAccounts.add(MemberAccountResponse.builder()
                    .id(memberAccount.getId())
                    .name(memberAccount.getName())
                    .surname(memberAccount.getSurname())
                    .balance(memberAccount.getBalance())
                    .invoice(memberAccount.getInvoice())
                    .build());
        });
        return memberAccounts;
    }

    @Override
    public void deleteMemberAccount(Long id) {
        MemberAccount memberAccount = findMemberAccount(id);
        memberAccountRepository.delete(memberAccount);
    }

    @Override
    public MemberAccount findByCode(String code) {
        Optional<MemberAccount> memberAccountOptional = memberAccountRepository.findByCode(code);
        if (memberAccountOptional.isEmpty()) {
            throw new MemberAccountException("MemberAccount code:" + code + " not found");
        }
        return memberAccountOptional.get();
    }

    @Override
    public MemberAccountResponse getMemberCode(String code) {
        MemberAccount find = findByCode(code);
        return MemberAccountResponse.builder().name(find.getName()).surname(find.getSurname()).balance(find.getBalance()).invoice(find.getInvoice()).build();
    }

    public String getCode(Long id, String name) {
        return id + name.substring(0, 2);
    }
}
