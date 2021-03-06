package com.makethings.infoplatform;

/**
 * Created by Call Me Bear on 2017/3/9.
 */

public class Event {
    private String aId;
    private String name;
    private String summary;
    private boolean isFollow;

    public Event(String aId,String name, String summary, boolean isFollow) {
        this.aId = aId;
        this.name = name;
        this.summary = summary;
        this.isFollow = isFollow;
    }

    public String getaId() {
        return aId;
    }

    public void setaId(String aId) {
        this.aId = aId;
    }

    public String getName() {
        return name;
    }

    public String getSummary() {
        return summary;
    }

    public boolean getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(boolean isFollow) {
        this.isFollow = isFollow;
    }
}
