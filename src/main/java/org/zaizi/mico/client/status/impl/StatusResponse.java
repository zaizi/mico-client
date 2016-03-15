package org.zaizi.mico.client.status.impl;

import java.util.List;

public class StatusResponse {
	
	private boolean finished;
    private String uri;
    private String time;
    private List<ContentPartResponse> parts;

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<ContentPartResponse> getParts() {
        return parts;
    }

    public void setParts(List<ContentPartResponse> parts) {
        this.parts = parts;
    }

}
