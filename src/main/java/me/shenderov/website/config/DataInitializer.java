package me.shenderov.website.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.shenderov.website.dao.Block;
import me.shenderov.website.dao.SeoInfo;
import me.shenderov.website.repositories.BlockRepository;
import me.shenderov.website.repositories.SeoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class DataInitializer {

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private SeoRepository seoRepository;

    private ObjectMapper mapper = new ObjectMapper();
    private ClassLoader classLoader = getClass().getClassLoader();

    private static final String BLOCKS_JSON = "blocks.json";
    private static final String SEO_JSON = "seo.json";

    @Value( "${application.data-initializer.run-on-starup}" )
    private boolean initiateOnStartup;

    @Value( "${application.data-initializer.update-from-json-on-starup}" )
    private boolean updateOnStartup;

    @PostConstruct
    public void initiateData() throws IOException {
        if(initiateOnStartup){
            insertDefaultBlocks();
            insertSeoData();
        }
    }

    private void insertDefaultBlocks() throws IOException {
        Map<String, Block> blocks = mapper.readValue(new File(Objects.requireNonNull(classLoader.getResource(BLOCKS_JSON)).getFile()), new TypeReference<Map<String, Block>>() {});
        for(String s : blocks.keySet()){
            if(blockRepository.count() > 0 && updateOnStartup){
                blockRepository.save(blocks.get(s));
            }else if (blockRepository.count() == 0) {
                blockRepository.insert(blocks.get(s));
            }
        }
    }

    private void insertSeoData() throws IOException {
        SeoInfo seoData = mapper.readValue(new File(Objects.requireNonNull(classLoader.getResource(SEO_JSON)).getFile()), new TypeReference<SeoInfo>() {});
        if(seoRepository.count() > 0 && updateOnStartup){
            seoRepository.save(seoData);
        }else if (seoRepository.count() == 0) {
            seoRepository.insert(seoData);
        }
    }
}
