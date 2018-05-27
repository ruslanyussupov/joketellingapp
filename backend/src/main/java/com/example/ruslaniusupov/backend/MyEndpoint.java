package com.example.ruslaniusupov.backend;


import com.example.ruslaniusupov.jokes.DataProvider;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;


/** An endpoint class we are exposing */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.ruslaniusupov.example.com",
                ownerName = "backend.ruslaniusupov.example.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    @ApiMethod(name = "getJokesJson")
    public Json getJokesJson() {
        String json = DataProvider.getJokesJson();
        return new Json(json);
    }

}
