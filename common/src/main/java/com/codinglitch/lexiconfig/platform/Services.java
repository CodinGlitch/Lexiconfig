package com.codinglitch.lexiconfig.platform;

import com.codinglitch.lexiconfig.Lexiconfig;
import com.codinglitch.lexiconfig.platform.services.PlatformHelper;

public class Services {
    public static final PlatformHelper PLATFORM = Lexiconfig.loadService(PlatformHelper.class);
}