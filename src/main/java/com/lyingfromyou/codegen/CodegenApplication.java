package com.lyingfromyou.codegen;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

public class CodegenApplication {

    public static void main(String[] args) throws Exception {

//        String urlBaidu = "http://www.baidu.com/";
//        OkHttpClient okHttpClient = new OkHttpClient(); // 创建OkHttpClient对象
//        Request request = new Request.Builder().url(urlBaidu).build(); // 创建一个请求
//        Response response = okHttpClient.newCall(request).execute(); // 返回实体
//        if (response.isSuccessful()) { // 判断是否成功
//            System.out.println(response.body().string()); // 打印数据
//        }else {
//            System.out.println("失败"); // 链接失败
//        }
//
//        System.err.println(HttpUtil.get("https://gwss.hseduyun.net/apis/organzation/V1.0/getGradeAndClassByOneOrgId?tenantId=002&orgId=224&access_token=7bfb4d92-c7f4-4d89-a17a-99071fb5acb7"));

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setSwagger2(true); //实体属性 Swagger2 注解

        gc.setServiceName("%sService");
        mpg.setGlobalConfig(gc);

        Props props = new Props("application.properties");
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(props.getProperty("datasource.url"));
        dsc.setDriverName(props.getProperty("datasource.driver-class-name"));
        dsc.setUsername(props.getProperty("datasource.username"));
        dsc.setPassword(props.getProperty("datasource.password"));
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(props.getProperty("moduleName"));
        pc.setParent(props.getProperty("package-url"));
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {

            }
        };

        // 如果模板引擎是 velocity
        String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setTablePrefix(props.getProperty("tablePrefix"));
        strategy.setNaming(NamingStrategy.underline_to_camel);

        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setEntitySerialVersionUID(false);
        String includeTables = props.getProperty("includeTables");
        if (StrUtil.isNotBlank(includeTables)) {
            strategy.setInclude(StrUtil.splitTrim(includeTables, StrUtil.COMMA).toArray(new String[]{}));
        }
        strategy.setControllerMappingHyphenStyle(false);
        mpg.setStrategy(strategy);
        mpg.execute();
    }

}
