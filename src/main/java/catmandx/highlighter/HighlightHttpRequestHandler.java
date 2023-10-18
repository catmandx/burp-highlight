/*
 * Copyright (c) 2023. PortSwigger Ltd. All rights reserved.
 *
 * This code may be used to extend the functionality of Burp Suite Community Edition
 * and Burp Suite Professional, provided that this usage does not violate the
 * license terms for those products.
 */

package catmandx.highlighter;


import burp.api.montoya.proxy.http.InterceptedRequest;
import burp.api.montoya.core.HighlightColor;
import burp.api.montoya.core.Annotations;
import burp.api.montoya.proxy.http.ProxyRequestHandler;
import burp.api.montoya.proxy.http.ProxyRequestReceivedAction;
import burp.api.montoya.proxy.http.ProxyRequestToBeSentAction;

import static burp.api.montoya.core.HighlightColor.RED;
import static burp.api.montoya.http.message.ContentType.JSON;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.logging.Logging;

class HighlightHttpRequestHandler implements ProxyRequestHandler {
     private final Logging logging;

    public HighlightHttpRequestHandler(MontoyaApi api)
    {
        logging = api.logging();
    }
    @Override
    public ProxyRequestReceivedAction handleRequestReceived(InterceptedRequest interceptedRequest) {
        String listener = interceptedRequest.listenerInterface().toString();
        Annotations annotations = interceptedRequest.annotations();
        if(listener.contains("8080")){
            annotations = annotations.withHighlightColor(HighlightColor.GREEN);
        }else if (listener.contains("8081")){
            annotations = annotations.withHighlightColor(HighlightColor.YELLOW);
        }
        return ProxyRequestReceivedAction.continueWith(interceptedRequest, annotations);
    }

    @Override
    public ProxyRequestToBeSentAction handleRequestToBeSent(InterceptedRequest interceptedRequest) {
        Annotations annotations = interceptedRequest.annotations();
        String listener = interceptedRequest.listenerInterface();
        if(listener.contains("8080")){
            annotations = annotations.withHighlightColor(HighlightColor.GREEN);
        }else if (listener.contains("8081")){
            annotations = annotations.withHighlightColor(HighlightColor.YELLOW);
        }

        return ProxyRequestToBeSentAction.continueWith(interceptedRequest, annotations);
    }
}

