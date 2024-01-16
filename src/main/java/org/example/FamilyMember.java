package org.example;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

@Getter(value = AccessLevel.PUBLIC)
@AllArgsConstructor
public class FamilyMember {

    private final Long id;

    private final String name;

    private FamilyMember father;

    private FamilyMember mother;

    public Set<FamilyMember> getAncestors(){

        Set<FamilyMember> ancestors = new HashSet<>(Collections.singleton(this));
        if(this.father != null){
            ancestors.add(this.father);
            ancestors.addAll(this.father.getAncestors());
        }
        if(this.mother != null){
            ancestors.add(this.mother);
            ancestors.addAll(this.mother.getAncestors());
        }
        return ancestors;
    }

    public String printAncestors(){

        return this.name +
                "[" + printParentsAncestors(this.father) +
                "],[" +
                printParentsAncestors(this.mother) +
                "]";
    }

    private String printParentsAncestors(FamilyMember parent){

        return parent == null ? "" : parent.printAncestors();
    }

    public Set<FamilyMember> getOldestAncestors(){

        Set<FamilyMember> oldestAncestors = new HashSet();
        this.getOldestAncestors(oldestAncestors);
        return oldestAncestors;
    }

    private void getOldestAncestors(Set<FamilyMember> ancestors) {

        if(this.father == null && this.mother == null){
            ancestors.add(this);
        }
        if(this.father != null){
            father.getOldestAncestors(ancestors);
        }
        if(this.mother != null){
            mother.getOldestAncestors(ancestors);
        }
    }

    public boolean isAncestor(FamilyMember candidate){

        if( this.father == candidate || this.mother == candidate){
            return true;
        }
        if(this.mother == null && this.father == null){
            return false;
        }
        return (this.father != null && this.father.isAncestor(candidate)) ||
                (this.mother != null && this.mother.isAncestor(candidate));
    }
}
