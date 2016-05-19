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
package org.zaizi.mico.client;

import java.io.InputStream;

import org.zaizi.mico.client.exception.MicoClientException;
import org.zaizi.mico.client.model.ContentItem;
import org.zaizi.mico.client.model.ContentPart;

/**
 * Interface for creating and injecting content items for mico platform
 * @author Chalitha Perera
 *
 */
public interface Injector {
	public static final String INJECT_CREATE_PATH = "/broker/inject/create";
	public static final String INJECT_ADD_PATH = "/broker/inject/add";
	public static final String INJECT_SUBMIT_PATH = "/broker/inject/submit";
	
	
	/**
	 * Create a contentItem by requesting the mico platform server
	 * @return ContentItem
	 * @throws MicoClientException 
	 */
	public ContentItem createContentItem() throws MicoClientException;
	

	/**
	 * Create a content part for a content item
	 * @param contentItem
	 * @param mimeType
	 * @param name
	 * @return
	 * @throws MicoClientException
	 */
	public ContentPart addContentPart(ContentItem contentItem, String mimeType, String name, InputStream inputStream) throws MicoClientException;
	
	
	/**
	 * submit content item to mico platform
	 * @param contentItem
	 * @throws MicoClientException 
	 */
	public void submitContentItem(ContentItem contentItem) throws MicoClientException;
	 
	
}
