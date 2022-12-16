package ru.cft.docs.keeper.service.authorization.core.context;

import lombok.Getter;
import org.slf4j.MDC;

@Getter
public final class ExecutionContext {

    private static final ThreadLocal<ExecutionContext> CONTEXT = ThreadLocal.withInitial(ExecutionContext::new);

    private static final String USER_ID_KEY = "userId";

    private Long userId;

    private ExecutionContext() {
    }

    public static ExecutionContext get() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
        MDC.clear();
    }

    public void setUserId(Long userId) {
        this.userId = userId;
        updateMdc(USER_ID_KEY, userId != null ? userId.toString() : null);
    }

    private static void updateMdc(String key, String value) {
        if (value != null) {
            MDC.put(key, value);
        } else {
            MDC.remove(key);
        }
    }
}
