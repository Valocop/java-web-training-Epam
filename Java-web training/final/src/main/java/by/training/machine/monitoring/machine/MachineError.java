package by.training.machine.monitoring.machine;

import by.training.machine.monitoring.message.MessageManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public enum MachineError {
    P0072, P0494, P0562, P0730, P0727, P0868;

    public static Optional<MachineError> getMachineError(String codeStr) {
        if (codeStr != null && !codeStr.isEmpty()) {
            try {
                int code = Integer.parseInt(codeStr);
                switch (code) {
                    case 1:
                        return Optional.of(P0072);
                    case 2:
                        return Optional.of(P0494);
                    case 3:
                        return Optional.of(P0562);
                    case 4:
                        return Optional.of(P0730);
                    case 5:
                        return Optional.of(P0727);
                    case 6:
                        return Optional.of(P0868);
                    default:
                        return Optional.empty();
                }
            } catch (NumberFormatException e) {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    public String getDescription(HttpServletRequest request) {
        MessageManager messageManager = MessageManager.getMessageManager(request);
        return messageManager.getMessage(this.name() + ".description.msg");
    }
}
