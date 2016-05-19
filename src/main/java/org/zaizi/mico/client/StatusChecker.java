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

import java.util.List;

import org.zaizi.mico.client.exception.MicoClientException;
import org.zaizi.mico.client.model.ContentItem;
import org.zaizi.mico.client.status.impl.StatusResponse;

/**
 * Check status of a given content item (As MICO platform is asynchronous, use this as a polling mechanism to 
 * check whether the content item is processed by the platform or not)
 * @author Chalitha Perera
 *
 */
public interface StatusChecker {
	
	public static final String STATUS_CHECK_PATH = "/broker/status/items";
	
	/**
	 * Check Item Status
	 * @param contentItemURI
	 * @param parts
	 * @return
	 * @throws MicoClientException
	 */
	public List<StatusResponse> checkItemStatus(String contentItemURI, boolean parts) throws MicoClientException;


}
