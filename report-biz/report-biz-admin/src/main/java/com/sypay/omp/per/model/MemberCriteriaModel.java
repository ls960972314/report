package com.sypay.omp.per.model;

import java.util.Arrays;
import java.util.List;

public class MemberCriteriaModel {

    private Long memberId;
    private String accNo;
    private String name;
    private List<String> groupCodeList;
    private String groupCodeListStr;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        if (accNo != null) {
            accNo = accNo.trim();
            if (accNo.length() > 0) {
                this.accNo = accNo + "%";
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null) {
            name = name.trim();
            if (name.length() > 0) {
                this.name = name + "%";
            }
        }
    }

    public List<String> getGroupCodeList() {
        return groupCodeList;
    }

    public void setGroupCodeList(List<String> groupCodeList) {
        if (groupCodeList != null && !groupCodeList.isEmpty()) {
            this.groupCodeList = groupCodeList;
        }
    }

    public String getGroupCodeListStr() {
        return groupCodeListStr;
    }

    public void setGroupCodeListStr(String groupCodeListStr) {
        if (groupCodeListStr != null) {
            groupCodeListStr = groupCodeListStr.trim();
            if (groupCodeListStr.length() > 0) {
                this.groupCodeList = Arrays.asList(groupCodeListStr.split(","));
            }
        }
    }
}
