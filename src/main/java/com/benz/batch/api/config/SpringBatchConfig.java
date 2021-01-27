package com.benz.batch.api.config;

import com.benz.batch.api.model.Employee;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowJobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    //create a job
    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                   ItemReader<Employee> itemReader, ItemProcessor<Employee,Employee> itemProcessor,
                   ItemWriter<Employee> itemWriter)
    {
        Step step1 = stepBuilderFactory.get("EMP-file-load-1")
                .<Employee,Employee>chunk(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();

        Step step2 = stepBuilderFactory.get("EMP-file-load-2")
                .<Employee,Employee>chunk(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();

         Job job = jobBuilderFactory.get("EMP-Load")
                 .incrementer(new RunIdIncrementer())
                 .flow(step1)
                 .next(step2)
                 .end()
                 .build();

         return job;
    }

    //implement 'reader','processor' and 'writer'
    @Bean
    public FlatFileItemReader<Employee> flatFileItemReader(@Value("${input.file}") Resource resource,LineMapper<Employee>
                                                           lineMapper)
    {
        FlatFileItemReader<Employee> flatFileItemReader
                =new FlatFileItemReader<>();

        flatFileItemReader.setResource(resource);
        flatFileItemReader.setName("CSV-Reader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper);

        return flatFileItemReader;
    }

    //implement LineMapper
    @Bean
    public LineMapper<Employee> lineMapper()
    {
        DefaultLineMapper<Employee> defaultLineMapper
                =new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer
                =new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[]{"id","name","dept","salary"});

        BeanWrapperFieldSetMapper<Employee> fieldSetMapper
                =new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Employee.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }

}
