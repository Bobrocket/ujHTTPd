package co.bobrocket.ujhttpd.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Bobrocket on 28/02/2016.
 */
public enum RuntimeParameters {
    PORT("-p", Integer.class),
    MULTITHREADED("-mt", Boolean.class),
    ;

    private String prefix;
    private Class clazz;
    RuntimeParameters(String prefix, Class clazz) {
        this.prefix = prefix;
        this.clazz = clazz;
    }

    public String getPrefix() {
        return prefix;
    }

    public Class getParsableClass() {
        return clazz;
    }

    public static Map<RuntimeParameters, Object> parse(String... params) {
        Map<RuntimeParameters, Object> values = new HashMap<>();
        for (int i = 0; i < params.length - 1; i++) {
            String parameter = params[i];
            for (RuntimeParameters runtimeParameter : values()) {
                if (runtimeParameter.getPrefix().equals(parameter)) {
                    values.put(runtimeParameter, params[i + 1]);
                }
            }
        }
        return values;
    }
}
