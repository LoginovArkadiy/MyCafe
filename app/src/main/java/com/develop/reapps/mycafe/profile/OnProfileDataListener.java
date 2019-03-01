package com.develop.reapps.mycafe.profile;

import com.develop.reapps.mycafe.server.user.User;

public interface OnProfileDataListener {
    void safeData(String email);

    User deserialization();

    void removeData();


}
