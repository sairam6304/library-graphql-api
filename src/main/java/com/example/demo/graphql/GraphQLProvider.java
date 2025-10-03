package com.example.demo.graphql;
import com.example.demo.resolver.BookResolver;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.io.File;


@Component
public class GraphQLProvider {
    private GraphQL graphQL;
    private final BookResolver bookResolver;
    public GraphQLProvider(BookResolver bookResolver) { this.bookResolver = bookResolver; }
    @PostConstruct
    public void init() throws Exception {
        File schemaFile = new File("src/main/resources/schema.graphqls");
        String sdl = Files.readString(schemaFile.toPath());
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(new SchemaParser().parse(sdl), buildWiring());
        graphQL = GraphQL.newGraphQL(schema).build();
    }
    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher("books", env -> bookResolver.getBooks())
                        .dataFetcher("bookById", env -> bookResolver.getBookById(env.getArgument("id")))
                ).build();
    }
    public GraphQL getGraphQL() { return graphQL; }
}