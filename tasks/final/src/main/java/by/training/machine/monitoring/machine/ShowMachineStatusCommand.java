package by.training.machine.monitoring.machine;

import by.training.machine.monitoring.app.ApplicationConstant;
import by.training.machine.monitoring.app.SecurityService;
import by.training.machine.monitoring.command.CommandException;
import by.training.machine.monitoring.command.ServletCommand;
import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.entity.UserEntity;
import by.training.machine.monitoring.message.MessageManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.util.*;
import java.util.function.Function;

@Log4j
@Bean(name = ApplicationConstant.SHOW_MACHINE_STATUS_CMD)
@AllArgsConstructor
public class ShowMachineStatusCommand implements ServletCommand {
    private MachineService machineService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        MessageManager messageManager = MessageManager.getMessageManager(req);
        String idStr = req.getParameter("machine.id");
        try {
            Long machineId = Long.parseLong(idStr);
            Optional<MachineInfoDto> machineInfoOptional = machineService.getMachineInfoByMachineId(machineId);
            if (machineInfoOptional.isPresent()) {
                Gson gson = new Gson();
                List<Map<Object, Object>> fuelLevelList = new ArrayList<Map<Object, Object>>();
                List<Map<Object, Object>> oilPressureList = new ArrayList<Map<Object, Object>>();
                List<Map<Object, Object>> oilLevelList = new ArrayList<Map<Object, Object>>();
                List<Map<Object, Object>> coolantTempList = new ArrayList<Map<Object, Object>>();
                List<Map<Object, Object>> errorTempList = new ArrayList<Map<Object, Object>>();
                List<MachineLogDto> machineLogsList = machineInfoOptional.get().getMachineLogsList();
                List<MachineErrorDto> machineErrorList = machineInfoOptional.get().getMachineErrorsList();
                machineLogsList.sort(Comparator.comparingLong(o -> o.getDate().getTime()));
                machineLogsList.forEach(machineLogDto -> {
                    fuelLevelList.add(new HashMap<Object, Object>() {{
                        put("label", DateFormat.getDateInstance(DateFormat.DEFAULT, messageManager.getLocale()).format(machineLogDto.getDate()));
                        put("y", machineLogDto.getFuelLevel());
                    }});
                    oilPressureList.add(new HashMap<Object, Object>() {{
                        put("label", DateFormat.getDateInstance(DateFormat.DEFAULT, messageManager.getLocale()).format(machineLogDto.getDate()));
                        put("y", machineLogDto.getOilPressure());
                    }});
                    oilLevelList.add(new HashMap<Object, Object>() {{
                        put("label", DateFormat.getDateInstance(DateFormat.DEFAULT, messageManager.getLocale()).format(machineLogDto.getDate()));
                        put("y", machineLogDto.getOilLevel());
                    }});
                    coolantTempList.add(new HashMap<Object, Object>() {{
                        put("label", DateFormat.getDateInstance(DateFormat.DEFAULT, messageManager.getLocale()).format(machineLogDto.getDate()));
                        put("y", machineLogDto.getCoolantTemp());
                    }});
                });
                machineErrorList.forEach(errorDto -> {
                    String description = "";
                    if (MachineError.getMachineError(errorDto.getErrorCode()).isPresent()) {
                        description = MachineError.getMachineError(errorDto.getErrorCode()).get().getDescription(req);
                    }
                    String finalDescription = description;
                    errorTempList.add(new HashMap<Object, Object>() {{
                        put("label", DateFormat.getDateInstance(DateFormat.DEFAULT, messageManager.getLocale()).format(errorDto.getDate()));
                        put("y", finalDescription);
                    }});
                });
                String fuelLevelPoints = gson.toJson(fuelLevelList, new TypeToken<List<Map<Object, Object>>>() {}.getType());
                String oilPressurePoints = gson.toJson(oilPressureList, new TypeToken<List<Map<Object, Object>>>() {}.getType());
                String oilLevelPoints = gson.toJson(oilLevelList, new TypeToken<List<Map<Object, Object>>>() {}.getType());
                String coolantTempPoints = gson.toJson(coolantTempList, new TypeToken<List<Map<Object, Object>>>() {}.getType());
                String errorTempPoints = gson.toJson(errorTempList, new TypeToken<List<Map<Object, Object>>>() {}.getType());

                req.setAttribute("fuelLevelPoints", fuelLevelPoints);
                req.setAttribute("oilPressurePoints", oilPressurePoints);
                req.setAttribute("oilLevelPoints", oilLevelPoints);
                req.setAttribute("coolantTempPoints", coolantTempPoints);
                req.setAttribute("coolantTempPoints", coolantTempPoints);
                req.setAttribute("errorTempPoints", errorTempPoints);
                req.setAttribute("commandName", "showMachineStatus");
                req.setAttribute("showStatus", "showStatus");
                try {
                    req.getRequestDispatcher("/jsp/main.jsp").forward(req, resp);
                } catch (ServletException | IOException e) {
                    log.error("Failed to show status", e);
                    throw new CommandException(e);
                }
            } else {
                failForward(req, resp);
            }
        } catch (NumberFormatException e) {
            failForward(req, resp);
        }
    }

    private void failForward(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        req.setAttribute("toast", "Failed to show status of machine");
        try {
            req.getRequestDispatcher("/jsp/main.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            log.error("Failed to show status", e);
            throw new CommandException(e);
        }
    }
}
