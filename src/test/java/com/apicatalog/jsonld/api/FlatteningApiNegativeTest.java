package com.apicatalog.jsonld.api;

import java.net.URI;
import java.util.Optional;

import javax.json.JsonStructure;

import org.junit.Assert;
import org.junit.Test;

import com.apicatalog.jsonld.JsonLd;
import com.apicatalog.jsonld.document.RemoteContent;
import com.apicatalog.jsonld.document.RemoteDocument;

public class FlatteningApiNegativeTest {

    @Test    
    public void test1() {
        try {
            JsonLd.flatten((RemoteDocument)null);
            Assert.fail();
        
        } catch (IllegalArgumentException e) {}
    }

    @Test
    public void test2() {
        try {
            JsonLd.flatten((String)null);
            Assert.fail();
        
        } catch (IllegalArgumentException e) {}
    }
    
    @Test
    public void test3() {
        try {
            JsonLd.flatten((URI)null);
            Assert.fail();
        
        } catch (IllegalArgumentException e) {}
    }
    
    @Test    
    public void test4() {
        try {
            JsonLd.flatten("");
            Assert.fail();
        
        } catch (IllegalArgumentException e) {}
    }

    @Test
    public void test5() {
        try {
            JsonLd.flatten("   ");
            Assert.fail();
        
        } catch (IllegalArgumentException e) {}
    }
    
    @Test
    public void test6() {
        try {
            JsonLd.flatten("/relative");
            Assert.fail();
        
        } catch (IllegalArgumentException e) {}
    }
    
    @Test    
    public void test7() {
        try {
            JsonLd.flatten(URI.create("relative"));
            Assert.fail();
        
        } catch (IllegalArgumentException e) {}
    }

    @Test
    public void test8() {
        try {
            JsonLd.flatten(new RemoteDocument(new RemoteContent() {
                
                @Override
                public boolean isRawPayload() {
                    return false;
                }
                
                @Override
                public boolean isJsonStructure() {
                    return false;
                }
                
                @Override
                public Optional<byte[]> getRawPayload() throws JsonLdError {
                    return null;
                }
                
                @Override
                public Optional<JsonStructure> getJsonStructure() throws JsonLdError {
                    return null;
                }
            }));
            
            Assert.fail();
        
        } catch (IllegalArgumentException e) {}
    }

    @Test
    public void test9() {
        try {
            JsonLd.flatten(new RemoteDocument(null));
            
            Assert.fail();
        
        } catch (IllegalArgumentException e) {}
    }    
    
    @Test
    public void test10() {
        try {

            JsonLd.flatten("https://example.com").options(null);
            Assert.fail();
        
        } catch (IllegalArgumentException e) {}
    }
    
    @Test
    public void test11() {
        try {

            JsonLd.flatten("https://example.com").base("!!");
            Assert.fail();
        
        } catch (IllegalArgumentException e) {}
    }
    
    @Test
    public void test12() {
        try {

            JsonLd.flatten("https://example.com").context("#!");
            Assert.fail();
        
        } catch (IllegalArgumentException e) {}
    }
}
