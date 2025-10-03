package com.example.demo.controller;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import com.example.demo.graphql.GraphQLProvider;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
@RequestMapping("/graphql")
public class GraphQLController {
    private final GraphQL graphQL;
    public GraphQLController(GraphQLProvider provider) { this.graphQL = provider.getGraphQL(); }
    @PostMapping
    public Map<String,Object> execute(@RequestBody Map<String,String> request) {
        ExecutionResult result = graphQL.execute(ExecutionInput.newExecutionInput().query(request.get("query")).build());
        return result.toSpecification();
    }
}