package com.example.celineneohpeiwen.pb_3;

import com.example.celineneohpeiwen.pb_3.Common.Common;

public class time {
    Long TIMEOUT;

    public Long getTIMEOUT(String level) {
        if (level.equals(Common.LEVEL.LEVEL1.toString()))
            TIMEOUT = (long)20000;
        else if (level.equals(Common.LEVEL.LEVEL1.toString()))
            TIMEOUT = (long)15000;
        else if (level.equals(Common.LEVEL.LEVEL1.toString()))
            TIMEOUT = (long)10000;
        else if (level.equals(Common.LEVEL.LEVEL1.toString()))
            TIMEOUT = (long)8000;
        else if (level.equals(Common.LEVEL.LEVEL1.toString()))
            TIMEOUT = (long)5000;
        return TIMEOUT;
    }
}
