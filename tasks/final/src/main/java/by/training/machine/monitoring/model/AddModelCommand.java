package by.training.machine.monitoring.model;

import by.training.machine.monitoring.app.ApplicationConstant;
import by.training.machine.monitoring.app.SecurityService;
import by.training.machine.monitoring.characteristic.CharacteristicDto;
import by.training.machine.monitoring.characteristic.CharacteristicService;
import by.training.machine.monitoring.command.CommandException;
import by.training.machine.monitoring.command.ServletCommand;
import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.entity.UserEntity;
import by.training.machine.monitoring.manufacture.ManufactureDto;
import by.training.machine.monitoring.manufacture.ManufactureService;
import by.training.machine.monitoring.message.MessageManager;
import by.training.machine.monitoring.validator.ResultValidator;
import lombok.AllArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Bean(name = ApplicationConstant.ADD_MODEL_CMD)
@AllArgsConstructor
public class AddModelCommand implements ServletCommand {
    private final String DATE_FORMAT = "yyyy-MM-dd";
    private ModelService modelService;
    private ManufactureService manufactureService;
    private CharacteristicService characteristicService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        MessageManager messageManager = MessageManager.getMessageManager(req);
        String modelName = req.getParameter("model.name");
        String releaseDateStr = req.getParameter("release.date");
        String description = req.getParameter("model.description");
        UserEntity currentUser = SecurityService.getInstance().getCurrentUser(req.getSession(false));
        Map<String, String> data = new HashMap<>();
        data.put("model.name", modelName);
        data.put("release.date", releaseDateStr);
        data.put("model.description", description);
        ResultValidator rv = new AddModelValidator().validate(data, messageManager);
        if (rv.isValid()) {
            if (manufactureService.getManufactureByUserId(currentUser.getId()).isPresent()) {
                try {
                    ManufactureDto manufactureDto = manufactureService.getManufactureByUserId(currentUser.getId()).get();
                    ModelDto modelDto = ModelDto.builder()
                            .name(modelName)
                            .releaseDate(new SimpleDateFormat(DATE_FORMAT).parse(releaseDateStr))
                            .manufactureId(manufactureDto.getId())
                            .description(description)
                            .build();
                    if (modelService.saveModel(modelDto)) {
                        resp.sendRedirect(req.getContextPath() + "/app?commandName=" + ApplicationConstant.SHOW_ADD_MACHINE_CMD);
                        return;
                    }
                } catch (ParseException | IOException e) {
                    throw new CommandException("Failed to add model", e);
                }
            }
        }
        failVersion(req, resp, rv);
    }

    private void failVersion(HttpServletRequest req, HttpServletResponse resp, ResultValidator rv) throws CommandException {
        UserEntity currentUser = SecurityService.getInstance().getCurrentUser(req.getSession(false));
        Optional<ManufactureDto> manufactureByUserId = manufactureService.getManufactureByUserId(currentUser.getId());
        if (manufactureByUserId.isPresent()) {
            List<CharacteristicDto> characteristics = characteristicService.getCharacteristicByManufacture(manufactureByUserId.get().getId());
            List<ModelDto> models = modelService.getModelByUserId(currentUser.getId());
            req.setAttribute("models", models);
            req.setAttribute("characteristics", characteristics);
        }
        rv.getExceptionMap().forEach(req::setAttribute);
        req.setAttribute(ApplicationConstant.TOAST, "Fail to add model");
        try {
            req.setAttribute(ApplicationConstant.COMMAND_NAME, ApplicationConstant.SHOW_ADD_MACHINE_CMD);
            req.getRequestDispatcher("/jsp/main.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandException("Fail to add model", e);
        }
    }
}
