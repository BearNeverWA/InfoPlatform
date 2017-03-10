package com.makethings.infoplatform;

/**
 * Created by Call Me Bear on 2017/3/9.
 */

public class Club {
    private String cId;
    private String name;
    private String star;
    private boolean isFollow;

    public Club(String cId,String name, String star, boolean isFollow) {
        this.cId=cId;
        this.name = name;
        this.star = star;
        this.isFollow = isFollow;
    }

    public String getcId(){return cId;}

    public String getName() {
        return name;
    }

    public String getStar() {
        return star;
    }

    public boolean getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(boolean isFollow) {
        this.isFollow = isFollow;
    }
}
