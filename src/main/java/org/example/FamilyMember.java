package org.example;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter(value = AccessLevel.PUBLIC)
@AllArgsConstructor
public class FamilyMember {

    private final String name;

    private FamilyMember father;

    private FamilyMember mother;

    public FamilyMember(String name){
        this.name=name;
    }

    public String getAncestors(){
        return this.name +
                "[" + getParentsAncestors(this.father) +
                "],[" +
                getParentsAncestors(this.mother) +
                "]";
    }

    private String getParentsAncestors(FamilyMember parent){
        return parent == null ? "" : parent.getAncestors();
    }

    public Set<FamilyMember> getOldestAncestors(){
        Set<FamilyMember> oldestAncestors = new HashSet();
        this.getOldestAncestors(oldestAncestors);
        return oldestAncestors;
    }

    private void getOldestAncestors(Set<FamilyMember> ancestors ){

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
