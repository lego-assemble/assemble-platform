package xyz.leo.lego.assemble.platform.common.executor.executor;

import java.util.List;

/**
 * @author xuyangze
 * @date 2019/11/4 8:13 PM
 */
public interface IExecuteService<T, D> {

    /**
     * 执行
     * @param type
     * @param data
     */
    void execute(T type, D data);

    /**
     * 执行
     * @param executeInfos
     */
    void execute(List<ExecuteInfo<T, D>> executeInfos);

    class ExecuteInfo<T, D> {
        private T type;
        private D data;

        public ExecuteInfo(T type, D data) {
            this.type = type;
            this.data = data;
        }

        protected T getType() {
            return type;
        }

        protected D getData() {
            return data;
        }
    }
}
