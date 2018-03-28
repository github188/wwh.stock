package com.ijustyce.map;

import com.ijustyce.fastandroiddev3.baseLib.utils.StringUtils;

/**
 * Created by yc on 17-5-7.
 */

public class LocationBean {

    public double latitude, longitude;
    public String fullAddress, address, city;

    public boolean isDataRight() {
        return !StringUtils.isEmpty(address) && !StringUtils.isEmpty(fullAddress)
                && latitude != 0 && longitude != 0;
    }
}
