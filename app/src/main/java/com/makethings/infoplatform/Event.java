package com.makethings.infoplatform;

/**
 * Created by Call Me Bear on 2017/3/9.
 */

public class Event {
    private String name;
    private String summary;
    private boolean isFollow;
    public Event(String name,String summary,boolean isFollow){
        this.name=name;
        this.summary=summary;
        this.isFollow=isFollow;
    }

    public String getName(){
        return name;
    }

    public String getSummary(){
        return summary;
    }

    public boolean getIsFollow(){
        return isFollow;
    }
}
