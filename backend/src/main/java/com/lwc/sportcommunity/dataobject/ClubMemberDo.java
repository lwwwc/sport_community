package com.lwc.sportcommunity.dataobject;

import java.io.Serializable;

public class ClubMemberDo implements Serializable {
    private Integer cmId;

    private Integer clubId;

    private Integer userId;

    private static final long serialVersionUID = 1L;

    public ClubMemberDo(Integer cmId, Integer clubId, Integer userId) {
        this.cmId = cmId;
        this.clubId = clubId;
        this.userId = userId;
    }

    public ClubMemberDo() {
        super();
    }

    public Integer getCmId() {
        return cmId;
    }

    public void setCmId(Integer cmId) {
        this.cmId = cmId;
    }

    public Integer getClubId() {
        return clubId;
    }

    public void setClubId(Integer clubId) {
        this.clubId = clubId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}