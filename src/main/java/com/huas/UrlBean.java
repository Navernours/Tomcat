package com.huas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlBean {
    private String protocol;
    private String host;
    private String post;
    private String path;
    private String fileName;
    private String parameter;
}
