package org.example;

import java.util.List;

public class FamilyMemberService {

    public String getAncestors(FamilyMember fm){
        return fm.getName() +
                "[" + getParentsAncestors(fm.getFather()) +
                "],[" +
                getParentsAncestors(fm.getMother()) +
                "]";
    }

    private String getParentsAncestors(FamilyMember parent){
        return parent == null ? "" : parent.getAncestors();
    }
}
