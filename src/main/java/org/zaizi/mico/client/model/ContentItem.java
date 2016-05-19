/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.zaizi.mico.client.model;

import java.util.HashMap;
import java.util.Map;

public class ContentItem {
	private String uri;
	private String uuid;
	private Map<String, ContentPart> contentParts;
	
	public ContentItem(String uri, String uuid){
		this.uri = uri;
		this.uuid = uuid;
		contentParts = new HashMap<>();
	}
	
	public String getUri(){
		return this.uri;
	}
	
	public String getID(){
		return this.uuid;
	}
	
	public void addContentPart(ContentPart contentPart){
		this.contentParts.put(contentPart.getUri(), contentPart);
	}
	
	public Map<String, ContentPart> getContentParts(){
		return contentParts;
	}
	
	public ContentPart getContentPart(String uri){
		return contentParts.get(uri);
	}
}
