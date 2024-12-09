package com.yangnjo.dessert_atelier.common.argument_resolver;

import java.io.IOException;

import com.yangnjo.dessert_atelier.common.web_util.MultipartParserContext;

public interface CreateForm {

    void setMultipartParserContext(MultipartParserContext mpc) throws IOException;
    MultipartParserContext getMultipartParserContext();
    
}
