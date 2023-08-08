package com.mk.psbilling.service;

import com.mk.psbilling.api.dto.MemberAccountRequest;
import com.mk.psbilling.api.dto.MemberAccountResponse;
import com.mk.psbilling.db.MemberAccount;

import java.util.List;

public interface MemberAccountService {
    public MemberAccount createMemberAccount(MemberAccountRequest memberAccountRequest);
    public MemberAccount findMemberAccount(Long id);
    public MemberAccountResponse getMemberAccountById(Long id);
    public MemberAccountResponse updateMemberAccount(Long id, MemberAccountRequest memberAccountRequest);
    public List<MemberAccountResponse> getAllMemberAccounts();
    public void deleteMemberAccount(Long id);

    MemberAccount findByCode(String code);

    public MemberAccountResponse getMemberCode(String code);

}
