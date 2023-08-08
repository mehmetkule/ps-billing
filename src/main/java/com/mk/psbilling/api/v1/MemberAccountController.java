package com.mk.psbilling.api.v1;

import com.mk.psbilling.api.dto.MemberAccountRequest;
import com.mk.psbilling.api.dto.MemberAccountResponse;
import com.mk.psbilling.db.MemberAccount;
import com.mk.psbilling.service.MemberAccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member/v1")
public class MemberAccountController {
    @Autowired
    private final MemberAccountServiceImpl memberAccountServiceImpl;

    public MemberAccountController(MemberAccountServiceImpl memberAccountServiceImpl) {
        this.memberAccountServiceImpl = memberAccountServiceImpl;
    }


    @PostMapping("/create")
    public ResponseEntity<MemberAccount> createMemberAccount(@RequestBody MemberAccountRequest dto) {
        MemberAccount response = memberAccountServiceImpl.createMemberAccount(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberAccountResponse> getMemberAccount(@PathVariable Long id) {
        MemberAccountResponse memberAccount = memberAccountServiceImpl.getMemberAccountById(id);
        return ResponseEntity.ok(memberAccount);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<MemberAccountResponse> getMemberCodes(@PathVariable("code") String code) {
        MemberAccountResponse memberAccount = memberAccountServiceImpl.getMemberCode(code);
        return ResponseEntity.ok(memberAccount);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MemberAccountResponse> updateMemberAccount(@PathVariable Long id, @RequestBody MemberAccountRequest dto) {
        MemberAccountResponse memberAccount = memberAccountServiceImpl.updateMemberAccount(id, dto);
        return ResponseEntity.ok(memberAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MemberAccount> deleteMemberAccount(@PathVariable Long id) {
        memberAccountServiceImpl.deleteMemberAccount(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<MemberAccountResponse>> getAllMemberAccounts() {
        List<MemberAccountResponse> memberAccount = memberAccountServiceImpl.getAllMemberAccounts();
        return ResponseEntity.ok(memberAccount);
    }
}
