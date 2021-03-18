package com.gis.application.controller;

import com.gis.application.model.Virus;
import com.gis.application.service.VirusService;
import com.gis.application.util.*;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * restful style
 */
@RestController
@RequestMapping("/virus")
public class VirusController {

    @Autowired
    VirusService service;

    @Value("classpath:static/data/abroad.json")
    private Resource lnglat;

    @Value("classpath:static/data/china.json")
    private Resource clnglat;
    
}
