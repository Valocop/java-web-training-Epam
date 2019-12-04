package by.training.machine.monitoring.model;

import by.training.machine.monitoring.SecurityService;
import by.training.machine.monitoring.command.CommandException;
import by.training.machine.monitoring.command.ServletCommand;
import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.entity.UserEntity;
import by.training.machine.monitoring.manufacture.ManufactureDto;
import by.training.machine.monitoring.manufacture.ManufactureService;
import by.training.machine.monitoring.message.MessageManager;
import by.training.machine.monitoring.validator.ResultValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

@Bean(name = "addModel")
public class AddModelCommand implements ServletCommand {
    private final String DATE_FORMAT = "yyyy-MM-dd";
    private ModelService modelService;

    public AddModelCommand(ModelService modelService) {
        this.modelService = modelService;
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        MessageManager messageManager = MessageManager.getMessageManager(req);
        String modelName = req.getParameter("model.name");
        String releaseDateStr = req.getParameter("release.date");
        String description = req.getParameter("model.description");
        UserEntity currentUser = SecurityService.getInstance().getCurrentUser(req.getSession(false));
        ResultValidator rv = new AddModelValidator().validate(new HashMap<String, String>() {{
            put("model.name", modelName);
            put("release.date", releaseDateStr);
            put("model.description", description);
        }}, messageManager);
        if (rv.isValid()) {
            if (modelService.assignModelManufacture(currentUser.getId(), currentUser.getName())) {
                try {
                    ManufactureDto manufactureDto = modelService.getManufactureByUserId(currentUser.getId());
                    ModelDto modelDto = ModelDto.builder()
                            .name(modelName)
                            .releaseDate(new SimpleDateFormat(DATE_FORMAT).parse(releaseDateStr))
                            .manufactureId(manufactureDto.getId())
                            .description(description)
                            .build();
                    if (modelService.saveModel(modelDto)) {
                        req.setAttribute("toast", "Model was added success");
                        req.getRequestDispatcher("/jsp/main.jsp").forward(req, resp);
                    } else {
                        failVersion(req, resp, rv);
                    }
                } catch (ParseException e) {
                    throw new CommandException("Failed to save model", e);
                } catch (ServletException | IOException e) {
                    throw new CommandException("Failed to add model", e);
                }
            } else {
                failVersion(req, resp, rv);
            }
        } else {
            failVersion(req, resp, rv);
        }
    }

    private void failVersion(HttpServletRequest req, HttpServletResponse resp, ResultValidator rv) throws CommandException {
        rv.getExceptionMap().forEach(req::setAttribute);
        req.setAttribute("toast", "Fail to add model");
        try {
            req.getRequestDispatcher("/jsp/main.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandException("Fail to add model", e);
        }
    }
}
