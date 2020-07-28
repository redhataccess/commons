package com.redhat.common.jee.servlet;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A wrapper for http servlet requests that would let the user extract request body multiple times  {@code  
 *      RequestWrapper requestWrapper = new RequestWrapper(httpServletRequest);
 *      String body = requestWrapper.getBody();
 * }
 *
 * @author randalap
 */
public class RequestWrapper extends HttpServletRequestWrapper {

    private final String body;
    private static final Logger logger = LoggerFactory.getLogger(RequestWrapper.class);

    public RequestWrapper(HttpServletRequest request) throws IOException {
        //Let other request method behave just like before
        super(request);

        List<String> lists = new ArrayList<>();

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                //bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                lists = IOUtils.readLines(inputStream, Charset.defaultCharset());
            }
        } catch (IOException ex) {
            getLogger().warn("Could not extract body due to exception=" + ex);
        }
        //Store request pody content in 'body' variable
        body = StringUtils.join(lists, "");
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        ServletInputStream servletInputStream = new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener rl) {

            }
        };
        return servletInputStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    /**
     * Returns the body of the request. Use this method to extract request body as many times as required
     *
     * @return
     */
    public String getBody() {
        return this.body;
    }

    /**
     * @return the logger
     */
    public static Logger getLogger() {
        return logger;
    }
}
