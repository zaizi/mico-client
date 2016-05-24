package org.zaizi.mico.client.status.impl;

import java.util.List;

public class StatusResponse {
	
	private boolean finished;
    private String uri;
    private String time;
    private String syntacticalType;
    private boolean hasError;
    private String error;
    private List<ContentPartResponse> parts;
    private AssetLocation assetLocation;
    private String serializedAt;
    private String assetFormat;
    private String semanticType;
    private boolean hasAsset;

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

	public String getSyntacticalType() {
		return syntacticalType;
	}

	public void setSyntacticalType(String syntacticalType) {
		this.syntacticalType = syntacticalType;
	}

	public boolean isHasError() {
		return hasError;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public AssetLocation getAssetLocation() {
		return assetLocation;
	}

	public void setAssetLocation(AssetLocation assetLocation) {
		this.assetLocation = assetLocation;
	}

	public String getSerializedAt() {
		return serializedAt;
	}

	public void setSerializedAt(String serializedAt) {
		this.serializedAt = serializedAt;
	}

	public String getAssetFormat() {
		return assetFormat;
	}

	public void setAssetFormat(String assetFormat) {
		this.assetFormat = assetFormat;
	}

	public String getSemanticType() {
		return semanticType;
	}

	public void setSemanticType(String semanticType) {
		this.semanticType = semanticType;
	}

	public boolean isHasAsset() {
		return hasAsset;
	}

	public void setHasAsset(boolean hasAsset) {
		this.hasAsset = hasAsset;
	}

}
