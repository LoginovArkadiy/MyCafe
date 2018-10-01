package com.develop.reapps.mycafe.server;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Leonid on 09.08.2018.
 */

public class AnswerBody {
    @SerializedName("id")
    public Integer id;
    @SerializedName("status")
    public Integer status;

    public AnswerBody(Integer id) {
        this.id = id;
    }

    public AnswerBody() {
    }
}
