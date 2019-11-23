package com.searchprod.searcher.product.service.provider;

import com.searchprod.searcher.product.model.github.ViewerLogin;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

@Service
public class GithubService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EbayService.class);

    private final String url;
    private final String path;

    public GithubService(
            @Value("${github.url}") String url,
            @Value("${github.v4.path}") String path
    ) {
        this.url = url;
        this.path = path;
    }

    public Mono<ViewerLogin> getViewerLogin() {
        LOGGER.info("github - getViewerLogin");

        String schema = "type Query{hello: String} schema{query: Query}";

        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

        RuntimeWiring runtimeWiring = newRuntimeWiring()
                .type("Query", builder -> builder.dataFetcher("hello",
                        new StaticDataFetcher("world")))
                .build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
        ExecutionResult executionResult = build.execute("{hello}");

        executionResult.toSpecification();

        System.out.println(executionResult.getData().toString());

        return Mono.empty();
    }

}
