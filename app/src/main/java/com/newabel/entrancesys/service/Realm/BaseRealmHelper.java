package com.newabel.entrancesys.service.Realm;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/13 0013.
 */

public class BaseRealmHelper {

    private static final String name = "sys.realm";
    private static long version = 1;

    private BaseRealmHelper(){

    }

    public static  void init(Context context) {
        Realm.init(context);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name(name)
//                    .encryptionKey(new byte[64]) //设置密钥
                .schemaVersion(version)
                .migration(new Migration())
                .build();
        Realm.setDefaultConfiguration(configuration);
    }
}
