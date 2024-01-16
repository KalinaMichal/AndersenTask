package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.security.PermitAll;
import lombok.AllArgsConstructor;

import org.example.dto.FamilyMemberDto;
import org.example.exception.EmptyGraphException;
import org.example.service.FamilyMemberService;
import org.example.validator.FamilyMemberValidator;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/family")
@Api(tags = "apis")
public class FamilyMemberController {

    private final FamilyMemberService familyMemberService;

    private final FamilyMemberValidator familyMemberValidator;

    @PermitAll
    @PostMapping("/add-family-member")
    @ApiOperation("Create new family member")
    public void addFamilyMember(@RequestBody FamilyMemberDto fm){

        this.familyMemberValidator.validate(fm);
        this.familyMemberService.addFamilyMember(fm);
    }

    @PermitAll
    @GetMapping("/find-ancestors/{id}")
    public String findAncestors(@PathVariable Long id){

        return this.familyMemberService.findAncestors(id);
    }

    @PermitAll
    @GetMapping("/cousins-check/{fm1}/{fm2}")
    public boolean cousinsCheck(@PathVariable Long fm1, @PathVariable Long fm2){

        return this.familyMemberService.areCosines(fm1, fm2);
    }

    @PermitAll
    @GetMapping("/common-ancestor-check/{fm1}/{fm2}")
    public boolean commonAncestorCheck(@PathVariable Long fm1, @PathVariable Long fm2){

        return this.familyMemberService.hasSharedAncestors(fm1, fm2);
    }

    @PermitAll
    @GetMapping("/display-graph/{id}")
    public List<FamilyMemberDto> displayGraph(@PathVariable Long id){

        return this.familyMemberService.displayGraph(id);
    }

    @PermitAll
    @PostMapping("/add-graph")
    public List<FamilyMemberDto> appGraph(@RequestBody List<FamilyMemberDto> fms){

        if(fms.isEmpty()){
            throw new EmptyGraphException();
        }
        this.familyMemberValidator.validateNewGraph(fms);
        this.familyMemberService.addGraph(fms);
        return this.familyMemberService.displayGraph(fms.get(1).getId());
    }
}
