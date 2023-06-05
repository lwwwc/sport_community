package com.lwc.sportcommunity.dataobject;

import java.io.Serializable;

public class ClubDo implements Serializable {
    private Integer clubId;

    private String clubName;

    private String clubDesc;

    private static final long serialVersionUID = 1L;

    public ClubDo(Integer clubId, String clubName, String clubDesc) {
        this.clubId = clubId;
        this.clubName = clubName;
        this.clubDesc = clubDesc;
    }

    public ClubDo() {
        super();
    }

    public Integer getClubId() {
        return clubId;
    }

    public void setClubId(Integer clubId) {
        this.clubId = clubId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName == null ? null : clubName.trim();
    }

    public String getClubDesc() {
        return clubDesc;
    }

    public void setClubDesc(String clubDesc) {
        this.clubDesc = clubDesc == null ? null : clubDesc.trim();
    }
}