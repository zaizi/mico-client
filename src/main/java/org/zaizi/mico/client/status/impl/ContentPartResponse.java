package org.zaizi.mico.client.status.impl;

import java.util.List;

public class ContentPartResponse {
	
    private String title;
    private String source;
    private List<Object> transitions;
    private String created;
    private String state;
    private String type;
    private String uri;
    private String creator;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<Object> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<Object> transitions) {
        this.transitions = transitions;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

}
