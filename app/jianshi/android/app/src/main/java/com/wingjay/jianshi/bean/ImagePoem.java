/*
 * Created by wingjay on 11/16/16 3:31 PM
 * Copyright (c) 2016.  All rights reserved.
 *
 * Last modified 11/10/16 11:05 AM
 *
 * Reach me: https://github.com/wingjay
 * Email: yinjiesh@126.com
 */

package com.wingjay.jianshi.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Image with three-line poem in home page.
 */
public class ImagePoem {

  @SerializedName("image")
  private String imageUrl;

  // poem must include three part text, and splited by -.
  // such as: XXX-AAAA-BB
  @SerializedName("poem")
  private String poem;

  @SerializedName("next_fetch_time")
  private long nextFetchTime;

  public String getImageUrl() {
    return imageUrl;
  }

  public String getPoem() {
    return poem;
  }

  public long getNextFetchTimeSec() {
    return nextFetchTime;
  }
}
