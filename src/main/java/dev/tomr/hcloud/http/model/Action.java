package dev.tomr.hcloud.http.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import dev.tomr.hcloud.http.HetznerJsonObject;
import dev.tomr.hcloud.http.model.enums.Status;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName("action")
public class Action extends HetznerJsonObject {

    private String command;
    private String finished;
    private Integer id;
    private Integer progress;
    private String started;
    private Error error;
    private Status status;
    private List<Resource> resources;

    public Action(String command, String finished, Integer id, Integer progress, String started, Error error, Status status, List<Resource> resources) {
        this.command = command;
        this.finished = finished;
        this.id = id;
        this.progress = progress;
        this.started = started;
        this.error = error;
        this.status = status;
        this.resources = resources;
    }

    public Action() {
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getFinished() {
        return finished;
    }

    public void setFinished(String finished) {
        this.finished = finished;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public String getStarted() {
        return started;
    }

    public void setStarted(String started) {
        this.started = started;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
}
