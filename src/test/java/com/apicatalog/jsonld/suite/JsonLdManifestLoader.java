/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.apicatalog.jsonld.suite;

import java.net.URI;
import java.util.stream.Stream;

import javax.json.JsonObject;
import javax.json.JsonValue;

import org.junit.Assert;

import com.apicatalog.jsonld.api.JsonLdError;
import com.apicatalog.jsonld.api.JsonLdErrorCode;
import com.apicatalog.jsonld.document.Document;
import com.apicatalog.jsonld.json.JsonUtils;
import com.apicatalog.jsonld.loader.DocumentLoaderOptions;
import com.apicatalog.jsonld.suite.loader.ZipResourceLoader;


public final class JsonLdManifestLoader {

    public static final String JSON_LD_API_BASE = "zip://json-ld-test-suite-20201120.zip/com/github/w3c/json-ld-api/tests/";
    public static final String JSON_LD_FRAMING_BASE = "zip://json-ld-framing-test-suite-20200609.zip/com/github/w3c/json-ld-framing/tests/";
        
    private final String manifestName;
    private final String manifestBase;
    
    private JsonLdManifestLoader(final String manifestBase, final String manifestName) {
        this.manifestBase = manifestBase;
        this.manifestName = manifestName;
    }
    
    public static JsonLdManifestLoader load(final String manifestBase, final String manifestName) {
        return new JsonLdManifestLoader(manifestBase, manifestName);
    }
    
    public Stream<JsonLdTestCase> stream() throws JsonLdError {
     
        Document manifest = new ZipResourceLoader().loadDocument(URI.create(manifestBase + manifestName), new DocumentLoaderOptions());

        Assert.assertNotNull(manifest);

        final JsonObject manifestObject = manifest
                                                .getJsonContent()
                                                .filter(JsonUtils::isObject)
                                                .map(JsonObject.class::cast)
                                                .orElseThrow(() -> new JsonLdError(JsonLdErrorCode.LOADING_DOCUMENT_FAILED));
        
        String baseUri = manifestObject.getString("baseIri");
        
        return manifestObject
                .getJsonArray("sequence")
                    .stream()
                        .map(JsonValue::asJsonObject)
                        .map(o -> JsonLdTestCase.of(o, manifestName, manifestBase, baseUri));            
    }
}
